package utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * A data structure that allows for efficient answering of range minimum queries.
 * explanation here: https://cp-algorithms.com/data_structures/segment_tree.html
 * except for this one it returns the min of a range (updates are still a thing, don't worry)
 */
public final class MinSegmentTree {
    private final int[] segtree;
    private final int arrSize;
    private final int size;
    private final Comparator<Integer> cmp;
    public MinSegmentTree(int len, Comparator<Integer> cmp) {  // constructs the thing kinda like an array
        int size = 1;
        while (size < len) {
            size *= 2;
        }
        this.size = size;
        this.cmp = cmp;
        arrSize = len;
        segtree = new int[size * 2];  // we won't necessarily use all of the element but that doesn't really matter
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
            segtree[currNode] = min(segtree[2 * currNode + 1], segtree[2 * currNode + 2]);
        }
    }

    // for this one, from and to follow "normal" slicing rules - left bound is inclusive, right bound isn't
    public int rangeMin(int from, int to) {
        if (from < 0 || to > arrSize) {
            throw new IllegalArgumentException(String.format("the bounds %s and %s are out of bounds i think", from, to));
        } else if (to <= from) {
            throw new IllegalArgumentException(String.format("the bounds %s and %s don't make sense bro", from, to));
        }
        return rangeMin(from, to, 0, 0, size);
    }

    private int rangeMin(int from, int to, int currNode, int left, int right) {
        if (right <= from || to <= left) {
            return Integer.MIN_VALUE;
        }
        if (from <= left && right <= to) {
            return segtree[currNode];
        }
        int mid = (left + right) / 2;
        int leftPart = rangeMin(from, to, 2 * currNode + 1, left, mid);
        int rightPart = rangeMin(from, to, 2 * currNode + 2, mid, right);
        return min(leftPart, rightPart);
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
