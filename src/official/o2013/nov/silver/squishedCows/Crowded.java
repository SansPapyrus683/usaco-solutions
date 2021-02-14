package official.o2013.nov.silver.squishedCows;

import java.io.*;
import java.util.*;

// 2013 dec silver (just used a segment tree and binary search to absolutely cheese this problem)
public final class Crowded {
    private static final class MaxSegTree {
        private final int[] segtree;
        private final int arrSize;
        private final int size;
        public MaxSegTree(int len) {
            int size = 1;
            while (size < len) {
                size *= 2;
            }
            this.size = size;
            arrSize = len;
            segtree = new int[size * 2];  // we won't necessarily use all of the element but that doesn't really matter
        }

        public void set(int index, int element) {
            if (index < 0 || index > arrSize) {
                throw new IllegalArgumentException(String.format("%s should be out of bounds lol", index));
            }
            set(index, element, 0, 0, size);
        }

        private void set(int index, int element, int currNode, int left, int right) {
            if (right - left == 1) {
                segtree[currNode] = element;
            } else {
                int mid = (left + right) / 2;
                if (index < mid) {
                    set(index, element, 2 * currNode + 1, left, mid);
                } else {
                    set(index, element, 2 * currNode + 2, mid, right);
                }
                segtree[currNode] = Math.max(segtree[2 * currNode + 1], segtree[2 * currNode + 2]);
            }
        }

        // for this one, from and to follow "normal" slicing rules - left bound is inclusive, right bound isn't
        public int max(int from, int to) {
            if (from < 0 || to > arrSize) {
                throw new IllegalArgumentException(String.format("the bounds %s and %s are out of bounds i think", from, to));
            } else if (to <= from) {
                throw new IllegalArgumentException(String.format("the bounds %s and %s don't make sense bro", from, to));
            }
            return max(from, to, 0, 0, size);
        }

        private int max(int from, int to, int currNode, int left, int right) {
            if (right <= from || to <= left) {
                return Integer.MIN_VALUE;
            }
            if (from <= left && right <= to) {
                return segtree[currNode];
            }
            int middle = (left + right) / 2;
            int leftPart = max(from, to, 2 * currNode + 1, left, middle);
            int rightPart = max(from, to, 2 * currNode + 2, middle, right);
            return Math.max(leftPart, rightPart);
        }
    }

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
            boolean leftValid = segmentTree.max(farthestLeft, c) >= cows[c][1] * 2;
            boolean rightValid = segmentTree.max(c + 1, farthestRight + 1) >= cows[c][1] * 2;
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
