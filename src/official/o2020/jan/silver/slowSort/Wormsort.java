package official.o2020.jan.silver.slowSort;

import java.io.*;
import java.util.*;

// 2020 jan silver
// 1/6/2021 - i was young and innocent when i wrote this code, so it's full of bad practices
public final class Wormsort {
    private static final long start = System.currentTimeMillis();
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("wormsort.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int wormholeNum = Integer.parseInt(initial.nextToken());
        
        int[] cows = Arrays.stream(read.readLine().split(" ")).mapToInt(c -> Integer.parseInt(c) - 1).toArray();
        int[][] wormholes = new int[wormholeNum][3];
        for (int i = 0; i < wormholeNum; i++) {
            wormholes[i] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            wormholes[i][0]--;  // make the wormholes 0-indexed (the cows were also 1-indexed if you didn't notice)
            wormholes[i][1]--;
        }

        ArrayList<int[]> toSort = new ArrayList<>();
        boolean alrSorted = true;
        for (int i = 0; i < cowNum; i++) {
            if (cows[i] != i) {
                alrSorted = false;
                toSort.add(new int[] {cows[i], i});
            }
        }
        if (alrSorted) {
           outputAndDie(-1);
        }

        int lowerBound = 1;
        int upperBound = Arrays.stream(wormholes).mapToInt(w -> w[2]).max().getAsInt();
        int valid = -1;
        while (lowerBound <= upperBound) {  // binary search the possible lower bound for wormhole width
            DisjointSets linkedCows = new DisjointSets(cowNum);
            int toSearch = (upperBound + lowerBound) / 2;
            for (int[] w : wormholes) {
                if (w[2] >= toSearch) {
                    linkedCows.link(w[0], w[1]);
                }
            }

            boolean goodToGo = true;
            for (int[] ts : toSort) {  // as long as the two are still linked in some way, we can sort them
                if (!linkedCows.sameSet(ts[0], ts[1])) {  // test if this cow can still be sorted
                    goodToGo = false;
                    break;
                }
            }

            if (goodToGo) {
                lowerBound = toSearch + 1;
                valid = toSearch;
            } else {
                upperBound = toSearch - 1;
            }
        }
        outputAndDie(valid);

    }

    private static void outputAndDie(Object output) throws IOException {
        PrintWriter written = new PrintWriter("wormsort.out");
        written.println(output);
        written.close();
        System.out.println(output);
        System.out.printf("so it took about this many ms: %s%n", System.currentTimeMillis() - start);
        System.exit(0);
    }
}

/**
 * based on {@link utils.DisjointSets} and there's also a link explaining it
 */
class DisjointSets {
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

    public boolean sameSet(int a, int b) {
        return getUltimate(a) == getUltimate(b);
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
