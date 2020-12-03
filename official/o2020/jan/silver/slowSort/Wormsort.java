package official.o2020.jan.silver.slowSort;

import java.util.*;
import java.io.*;

// 2020 jan silver
public class Wormsort {
    static int[] cows;
    static int[] parents;
    static int[] sizes;
    static int[][] wormholes;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("wormsort.in"));
        PrintWriter written = new PrintWriter("wormsort.out");
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int wormholeNum = Integer.parseInt(initial.nextToken());
        
        cows = new int[cowNum + 1];  // stupid 1-based indexing
        parents = new int[cowNum + 1];  // index 0 will never be used just so you know
        sizes = new int[cowNum + 1];
        ArrayList<int[]> toSort = new ArrayList<>();
        boolean alrSorted = true;
        String[] rawCows = read.readLine().split(" ");
        for (int i = 1; i <= cowNum; i++) {
            cows[i] = Integer.parseInt(rawCows[i - 1]);
            if (cows[i] != i) {
                alrSorted = false;
                toSort.add(new int[] {cows[i], i});
            }
        }
        if (alrSorted) {
            System.out.println("bruh this is already sorted");
            written.println(-1);
            written.close();
            System.exit(0);
        }

        wormholes = new int[wormholeNum][3];
        for (int i = 0; i < wormholeNum; i++) {
            wormholes[i] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        int lowerBound = 1;
        int upperBound = -1;
        for (int[] w : wormholes) {
            upperBound = Math.max(upperBound, w[2] + 1);  // +1 because it isn't inclusive
        }
        while (upperBound - lowerBound > 1) {  // binary search the possible lower bound for wormhole width
            int toSearch = (upperBound + lowerBound) / 2;
            for (int i = 1; i <= cowNum; i++) {  // reset parents, sizes, & the cached parents
                parents[i] = i;
            }
            Arrays.fill(sizes, 1);

            boolean goodToGo = true;
            for (int[] w : wormholes) {
                if (w[2] >= toSearch) {
                    merge(w[0], w[1]);
                }
            }

            for (int[] ts: toSort) {
                if (getUltimate(ts[0]) != getUltimate(ts[1])) {  // test if this cow can still be sorted
                    goodToGo = false;
                    break;
                }
            }

            if (goodToGo) {
                lowerBound = toSearch;
            }
            else {
                upperBound = toSearch;
            }
        }
        
        System.out.println(lowerBound);
        written.println(lowerBound);
        written.close();
        System.out.printf("so it took about this many ms: %s%n", System.currentTimeMillis() - start);
    }

    static int getUltimate(int start) {
        if (parents[start] == start) {
            return start;  // i mean the start is its own parent so it doesn't really matter
        }
        return parents[start] = getUltimate(parents[start]);
    }

    static void merge(int nodeA, int nodeB) {
        nodeA = getUltimate(nodeA);  // always merge roots, not leaves
        nodeB = getUltimate(nodeB);
        if (sizes[nodeB] > sizes[nodeA]) {  // make sure that a is always larger than b
            int temp = nodeA;
            nodeA = nodeB;
            nodeB = temp;
        }
        parents[nodeB] = nodeA;
        sizes[nodeA] += sizes[nodeB];
    }
}
