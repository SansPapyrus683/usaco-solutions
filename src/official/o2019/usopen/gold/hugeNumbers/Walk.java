package official.o2019.usopen.gold.hugeNumbers;

import java.io.*;
import java.util.*;

// 2019 usopen gold
public final class Walk {
    private static final long FIRST = 2019201913;
    private static final long SECOND = 2019201949;
    private static final long MOD = 2019201997;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("walk.in"));
        StringTokenizer info = new StringTokenizer(read.readLine());  // some random placeholder code
        int cowNum = Integer.parseInt(info.nextToken());
        int groupNum = Integer.parseInt(info.nextToken());

        boolean[] inTree = new boolean[cowNum + 1];  // no index 1 because the dist. is dependent on cow ids
        int[] parents = new int[cowNum + 1];
        int[] distToTree = new int[cowNum + 1];
        parents[1] = -1;
        Arrays.fill(distToTree, Integer.MAX_VALUE);
        distToTree[1] = 0;
        for (int i = 0; i < cowNum; i++) {  // so we'll make an min spanning tree of all the cows
            int toAdd = -1;
            for (int c = 1; c <= cowNum; c++) {
                if (!inTree[c] && (toAdd == -1 || distToTree[c] < distToTree[toAdd])) {
                    toAdd = c;
                }
            }

            inTree[toAdd] = true;
            for (int c = 1; c <= cowNum; c++) {
                int dist;
                if (!inTree[c] && (dist = distance(c, toAdd)) < distToTree[c]) {
                    distToTree[c] = dist;
                    parents[c] = toAdd;
                }
            }
        }

        // then we decide which edges to cut, and thus which groups we form
        // always try to cut the largest edge so that distance is maximized
        ArrayList<Integer> edgeLengths = new ArrayList<>();
        for (int c = 2; c <= cowNum; c++) {
            edgeLengths.add(distance(c, parents[c]));
        }
        edgeLengths.sort(Comparator.comparingInt(l -> -l));  // sort them by distance from large to small
        PrintWriter written = new PrintWriter("walk.out");
        written.println(edgeLengths.get(groupNum - 1 - 1));
        written.close();
        System.out.println(edgeLengths.get(groupNum - 1 - 1));
        System.out.printf("%d ms is indeed the time that your code took%n", System.currentTimeMillis() - start);
    }

    static int distance(int c1, int c2) {
        return (int) ((FIRST * Math.min(c1, c2) + SECOND * Math.max(c1, c2)) % MOD);
    }
}
