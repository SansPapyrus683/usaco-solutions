package official.o2017.jan.plat.promote;

import java.io.*;
import java.util.*;

// 2017 jan plat
public final class Promote {
    private static final int PRES = 0;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("promote.in"));
        int cowNum = Integer.parseInt(read.readLine());

        int[] proficiency = new int[cowNum];
        for (int c = 0; c < cowNum; c++) {
            proficiency[c] = Integer.parseInt(read.readLine());
        }

        ArrayList<Integer>[] subs = new ArrayList[cowNum];
        for (int c = 0; c < cowNum; c++) {
            subs[c] = new ArrayList<>();
        }
        for (int c = 0; c < cowNum; c++) {
            if (c == PRES) {
                continue;
            }
            subs[Integer.parseInt(read.readLine()) - 1].add(c);
        }

        boolean[] processed = new boolean[cowNum];
        int[] starts = new int[cowNum];
        int[] ends = new int[cowNum];
        int timer = 0;
        ArrayDeque<Integer> frontier = new ArrayDeque<>(Collections.singletonList(PRES));
        while (!frontier.isEmpty()) {
            int curr = frontier.removeLast();
            if (processed[curr]) {
                ends[curr] = timer;
                timer++;
                continue;
            }
            starts[curr] = timer;
            frontier.add(curr);  // set a marker to record the outtime
            processed[curr] = true;
            frontier.addAll(subs[curr]);
            timer++;
        }

        Integer[] cowsOrder = new Integer[cowNum];  // stupid java
        for (int c = 0; c < cowNum; c++) {
            cowsOrder[c] = c;
        }
        Arrays.sort(cowsOrder, Comparator.comparingInt(c -> -proficiency[c]));

        int[] betterSubs = new int[cowNum];
        SegmentTree segtree = new SegmentTree(cowNum * 2);
        for (int c = 0; c < cowNum; c++) {
            int cow = cowsOrder[c];
            betterSubs[cow] = segtree.rangeSum(starts[cow] + 1, ends[cow]) / 2;
            segtree.set(starts[cow], 1);
            segtree.set(ends[cow], 1);
        }

        PrintWriter written = new PrintWriter("promote.out");
        Arrays.stream(betterSubs).forEach(written::println);
        written.close();
        Arrays.stream(betterSubs).forEach(System.out::println);

        System.out.printf("bruh why did you run this code that took %d ms lol%n", System.currentTimeMillis() - start);
    }
}

class SegmentTree {
    private final int[] segtree;
    private final int len;

    public SegmentTree(int len) {
        this.len = len;
        segtree = new int[len * 2];
    }

    public void set(int ind, int val) {
        assert (0 <= ind && ind < len);
        for (segtree[ind += len] = val; ind > 1; ind >>= 1) {
            segtree[ind >> 1] = segtree[ind] + segtree[ind ^ 1];
        }
    }

    public int rangeSum(int start, int end) {
        assert (0 <= start && start < len && 0 < end && end <= len);
        int total = 0;
        for (start += len, end += len; start < end; start >>= 1, end >>= 1) {
            if ((start & 1) != 0) {
                total += segtree[start++];
            }
            if ((end & 1) != 0) {
                total += segtree[--end];
            }
        }
        return total;
    }
}
