package official.o2022.usopen.gold;

import java.io.*;
import java.util.*;

public class TreeBalance {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int testNum = Integer.parseInt(initial.nextToken());
        boolean concreteData = Integer.parseInt(initial.nextToken()) == 1;
        for (int t = 0; t < testNum; t++) {
            int cowNum = Integer.parseInt(read.readLine());
            int[] parents = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            ArrayList<Integer>[] children = new ArrayList[cowNum];
            for (int c = 0; c < cowNum; c++) {
                children[c] = new ArrayList<>();
            }
            for (int p = 0; p < cowNum - 1; p++) {
                children[--parents[p]].add(p + 1);
            }

            Pair[] cows = new Pair[cowNum];
            for (int c = 0; c < cowNum; c++) {
                StringTokenizer cow = new StringTokenizer(read.readLine());
                cows[c] = new Pair(Integer.parseInt(cow.nextToken()), Integer.parseInt(cow.nextToken()));
            }

            EvoTree tree = new EvoTree(children, cows);
            System.out.println(tree.getMinImbalance());
            if (concreteData) {
                for (int c = 0; c < cowNum - 1; c++) {
                    System.out.print(tree.getVal(c) + " ");
                }
                System.out.println(tree.getVal(cowNum - 1));
            }
        }
    }
}

class EvoTree {
    private static final int ROOT = 0;

    private final List<Integer>[] children;
    private final Pair[] cows;
    private final Pair[] intervals;
    private int minImbalance = 0;
    private final int[] vals;

    // assuming valid input screw u
    public EvoTree(List<Integer>[] children, Pair[] cows) {
        this.children = children;
        this.cows = cows;
        intervals = cows.clone();

        vals = new int[children.length];
        Arrays.fill(vals, -1);

        processNodes(ROOT);
        assignVals(ROOT);
    }

    private void processNodes(int at) {
        List<Pair> childPairs = new ArrayList<>();
        for (int c : children[at]) {
            processNodes(c);
            childPairs.add(intervals[c]);
        }
        childPairs.add(intervals[at]);

        Pair before = intervals[at];
        intervals[at] = reduce(childPairs);
        if (intervals[at].f > intervals[at].s) {
            vals[at] = (intervals[at].f + intervals[at].s) / 2;
            vals[at] = Math.min(vals[at], before.s);
            vals[at] = Math.max(vals[at], before.f);
            minImbalance = Math.max(
                    Math.max(minImbalance, Math.abs(vals[at] - intervals[at].f)),
                    Math.abs(vals[at] - intervals[at].s)
            );
        }
    }

    private void assignVals(int at) {
        if (vals[at] == -1) {
            vals[at] = intervals[at].f;
        }
        for (int c : children[at]) {
            if (vals[c] == -1) {
                if (vals[at] > cows[c].s) {
                    vals[c] = cows[c].s;
                } else if (vals[at] < cows[c].f){
                    vals[c] = cows[c].f;
                } else {
                    vals[c] = vals[at];
                }
            }
            assignVals(c);
        }
    }

    private static Pair reduce(List<Pair> intervals) {
        Collections.sort(intervals);
        Pair ret = new Pair(0, Integer.MAX_VALUE);
        for (Pair interval : intervals) {
            ret.f = Math.max(ret.f, interval.f);
            ret.s = Math.min(ret.s, interval.s);
        }
        return ret;
    }

    public int getMinImbalance() { return minImbalance; }

    public int getVal(int n) { return vals[n]; }
}

class Pair implements Comparable<Pair> {
    public int f, s;
    public Pair(int f, int s) {
        this.f = f;
        this.s = s;
    }

    @Override
    public int compareTo(Pair o) {
        return o.f != f ? o.f - f : o.s - s;
    }
}
