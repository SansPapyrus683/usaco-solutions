package utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * A data structure that allows for efficient answering of range minimum queries.
 * followed the explanation here: https://codeforces.com/blog/entry/18051
 */
public final class MinSegmentTree {
    private final int[] segtree;
    private final int len;
    private final Comparator<Integer> cmp;
    public MinSegmentTree(int len, Comparator<Integer> cmp) {  // constructs the thing kinda like an array
        this.len = len;
        this.cmp = cmp;
        segtree = new int[len * 2];  // note: we won't use index 0
    }

    public MinSegmentTree(int[] arr, Comparator<Integer> cmp) {  // constructs the thing with initial elements as well
        this(arr.length, cmp);
        for (int i = 0; i < arr.length; i++) {
            set(i, arr[i]);
        }
    }

    public MinSegmentTree(int[] arr) {
        this(arr, Comparator.naturalOrder());
    }

    public MinSegmentTree(int len) {
        this(len, Comparator.naturalOrder());
    }

    void set(int ind, int val) {
        for (segtree[ind += len] = val; ind > 1; ind >>= 1) {
            segtree[ind >> 1] = min(segtree[ind], segtree[ind ^ 1]);
        }
        System.out.println(Arrays.toString(segtree));
    }

    int rangeMin(int from, int to) {  // minimum from [from, to)
        int min = Integer.MAX_VALUE;
        for (from += len, to += len; from < to; from >>= 1, to >>= 1) {
            if ((from & 1) != 0) {
                min = min(min, segtree[from++]);
            }
            if ((to & 1) != 0) {
                min = min(min, segtree[--to]);
            }
        }
        return min;
    }

    private int min(int x, int y) {
        if (x == Integer.MAX_VALUE) {
            return y;
        } else if (y == Integer.MAX_VALUE) {
            return x;
        }
        return Collections.min(Arrays.asList(x, y), cmp);
    }
}
