package official.o2013.nov.silver.squishedCows;

import java.io.*;
import java.util.*;

// 2013 nov silver (just used a segment tree and binary search to absolutely cheese this problem)
public class Crowded {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("crowded.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int distThreshold = Integer.parseInt(initial.nextToken());
        int[][] cows = new int[cowNum][2];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(cows, Comparator.comparingInt(c -> c[0]));
        int[] positions = new int[cowNum];
        for (int c = 0; c < cowNum; c++) {
            positions[c] = cows[c][0];
        }

        MaxSegTree segmentTree = new MaxSegTree(cowNum);
        for (int i = 0; i < cowNum; i++) {
            segmentTree.set(i, cows[i][1]);
        }
        int totalCrowded = 0;
        for (int c = 1; c < cowNum - 1; c++) {
            int farthestLeft = bisectLeft(positions, positions[c] - distThreshold);
            int farthestRight = bisectRight(positions, positions[c] + distThreshold) - 1;
            boolean leftValid = segmentTree.rangeMax(farthestLeft, c) >= cows[c][1] * 2;
            boolean rightValid = segmentTree.rangeMax(c + 1, farthestRight + 1) >= cows[c][1] * 2;
            if (leftValid && rightValid) {
                totalCrowded++;
            }
        }

        PrintWriter written = new PrintWriter("crowded.out");
        written.println(totalCrowded);
        written.close();
        System.out.println(totalCrowded);
        System.out.printf("%d ms. nothing more, nothing less%n", System.currentTimeMillis() - start);
    }

    // these two are right from python's bisect module: https://github.com/python/cpython/blob/master/Lib/bisect.py
    private static int bisectLeft(int[] arr, int elem) {
        int lo = 0;
        int hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] < elem) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    private static int bisectRight(int[] arr, int elem) {
        int lo = 0;
        int hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (elem < arr[mid]) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }
}

class MaxSegTree {
    private final int[] segtree;
    private final int len;
    public MaxSegTree(int len) {  // constructs the thing kinda like an array
        this.len = len;
        segtree = new int[len * 2];  // note: we won't use index 0
    }

    public void set(int ind, int val) {
        assert 0 <= ind && ind < len;
        for (segtree[ind += len] = val; ind > 1; ind >>= 1) {
            segtree[ind >> 1] = Math.max(segtree[ind], segtree[ind ^ 1]);
        }
    }

    int rangeMax(int from, int to) {  // minimum from [from, to)
        assert from <= to && 0 <= from && from <= len && 0 < to && to <= len;
        int max = Integer.MIN_VALUE;
        for (from += len, to += len; from < to; from >>= 1, to >>= 1) {
            if ((from & 1) != 0) {
                max = Math.max(max, segtree[from++]);
            }
            if ((to & 1) != 0) {
                max = Math.max(max, segtree[--to]);
            }
        }
        return max;
    }
}
