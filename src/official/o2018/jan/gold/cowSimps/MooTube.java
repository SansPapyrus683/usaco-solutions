package official.o2018.jan.gold.cowSimps;

import java.io.*;
import java.util.*;

public class MooTube {
    /**
     * based on {@link utils.DisjointSets} and there's also a link explaining it
     */
    private static final class DisjointSets {
        private final int[] parents;
        private final int[] sizes;
        public DisjointSets(int size) {
            parents = new int[size];
            sizes = new int[size];
            for (int i = 0; i < size; i++) {
                parents[i] = i;
                sizes[i] = 1;
            }
        }

        public int getUltimate(int n) {
            return parents[n] == n ? n : (parents[n] = getUltimate(parents[n]));
        }

        public int size(int n) {
            return sizes[getUltimate(n)];
        }

        public void link(int e1, int e2) {
            e1 = getUltimate(e1);
            e2 = getUltimate(e2);
            if (e1 == e2) {
                return;
            }
            if (sizes[e2] > sizes[e1]) {
                int temp = e1;
                e1 = e2;
                e2 = temp;
            }
            parents[e2] = e1;
            sizes[e1] += sizes[e2];
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("mootube.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int vidNum = Integer.parseInt(initial.nextToken());
        int queryNum = Integer.parseInt(initial.nextToken());

        // all the videos will be transformed from begin 1-indexed to being 0-indexed
        HashMap<Integer, ArrayList<int[]>> links = new HashMap<>();
        for (int e = 0; e < vidNum - 1; e++) {
            StringTokenizer relation = new StringTokenizer(read.readLine());
            int from = Integer.parseInt(relation.nextToken()) - 1;
            int to = Integer.parseInt(relation.nextToken()) - 1;
            int relevance = Integer.parseInt(relation.nextToken());
            if (!links.containsKey(relevance)) {
                links.put(relevance, new ArrayList<>());
            }
            links.get(relevance).add(new int[] {from, to});
        }
        TreeMap<Integer, ArrayList<int[]>> queries = new TreeMap<>();
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            int threshold = Integer.parseInt(query.nextToken());
            if (!queries.containsKey(threshold)) {
                queries.put(threshold, new ArrayList<>());
            }
            queries.get(threshold).add(new int[] {q, Integer.parseInt(query.nextToken()) - 1});
        }

        int[] relevant = new int[queryNum];
        DisjointSets videos = new DisjointSets(vidNum);
        ArrayList<Integer> linkVals = new ArrayList<>(links.keySet());
        linkVals.sort(Comparator.comparingInt(i -> -i));
        int valAt = 0;
        for (int t : queries.descendingKeySet()) {  // go through the keys from large to small
            // add all the edges that meet this threshold
            while (valAt < links.size() && linkVals.get(valAt) >= t) {
                for (int[] edge : links.get(linkVals.get(valAt))) {
                    videos.link(edge[0], edge[1]);
                }
                valAt++;
            }
            for (int[] q : queries.get(t)) {
                relevant[q[0]] = videos.size(q[1]) - 1;  // -1 bc we're not including the video itself
            }
        }
        PrintWriter written = new PrintWriter("mootube.out");
        Arrays.stream(relevant).forEach(written::println);
        written.close();
        System.out.println(Arrays.toString(relevant));
        System.out.printf("i can only imagine an onlyfans run by bessie herself: %d ms%n", System.currentTimeMillis() - start);
    }
}
