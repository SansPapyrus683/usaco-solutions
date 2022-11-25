package official.o2018.jan.silver.cowSimps;

import java.io.*;
import java.util.*;

// 2018 jan silver
public class MooTube {
    static int vidNum;
    static ArrayList<int[]>[] neighbors;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("mootube.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        vidNum  = Integer.parseInt(initial.nextToken());
        int queryNum = Integer.parseInt(initial.nextToken());
        neighbors = new ArrayList[vidNum + 1];  // INDEX 0 WILL NOT BE USED
        for (int i = 1; i <= vidNum; i++) {
            neighbors[i] = new ArrayList<>();
        }

        for (int i = 0; i < vidNum - 1; i++) {
            int[] relationship  = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            neighbors[relationship[0]].add(new int[] {relationship[1], relationship[2]});
            neighbors[relationship[1]].add(new int[] {relationship[0], relationship[2]});
        }
        int[][] queries  = new int[queryNum][2];
        for (int i = 0; i < queryNum; i++) {
            queries[i] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        int[] answers = new int[queryNum];
        int pointer = 0;
        for (int[] q : queries) {
            answers[pointer] = ytRecommendations(q[0], q[1]);
            pointer++;
        }

        PrintWriter written = new PrintWriter(new FileWriter(new File("mootube.out")));  // so i don't need that buffered writer; neat
        for (int a : answers) {
            written.println(a);
        }
        written.close();
        System.out.println(Arrays.toString(answers));
        System.out.printf("ok so it took around %s milliseconds%n", System.currentTimeMillis() - start);
    }

    static int ytRecommendations(int threshold, int start) {  // ngl yt's recommendations are actually pretty good
        int vidsRecced = 0;
        ArrayDeque<Integer> frontier = new ArrayDeque<>();
        boolean[] traversed = new boolean[vidNum + 1];  // again, index 1 won't be used
        frontier.add(start);
        traversed[start] = true;

        while (!frontier.isEmpty()) {
            int current = frontier.poll();
            for (int[] n : neighbors[current]) {
                if (n[1] >= threshold && !traversed[n[0]]) {  // visit if not visited before and it passes the relevance threshold
                    vidsRecced++;
                    frontier.add(n[0]);
                    traversed[n[0]] = true;
                }
            }
        }
        return vidsRecced;
    }
}
