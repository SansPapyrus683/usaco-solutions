package utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * this does pretty much the same thing as the sum segment tree<br>
 * except for this one it returns the min or max of a range (updates are still a thing, don't worry)
 */
public class CompSegmentTree {
    public enum OpType {  // dk if this is a best practice but i don't wanna have two really similar classes
        MIN,
        MAX
    }

    private final int[] segtree;
    private final int arrSize;
    private final int size;
    private final OpType op;
    private final Comparator<Integer> cmp;
    public CompSegmentTree(int len, OpType op, Comparator<Integer> cmp) {  // constructs the thing kinda like an array
        int size = 1;
        while (size < len) {
            size *= 2;
        }
        this.size = size;
        this.op = op;
        this.cmp = cmp;
        arrSize = len;
        segtree = new int[size * 2];  // we won't necessarily use all of the element but that doesn't really matter
    }

    public CompSegmentTree(int[] arr, OpType op, Comparator<Integer> cmp) {  // constructs the thing with initial elements as well
        this(arr.length, op, cmp);
        for (int i = 0; i < arr.length; i++) {
            set(i, arr[i]);
        }
    }

    public CompSegmentTree(int[] arr, OpType op) {
        this(arr, op, Comparator.naturalOrder());
    }

    public CompSegmentTree(int len, OpType op) {
        this(len, op, Comparator.naturalOrder());
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
            switch (op) {
                case MIN:
                    segtree[currNode] = min(segtree[2 * currNode + 1], segtree[2 * currNode + 2]);
                    break;
                case MAX:
                    segtree[currNode] = max(segtree[2 * currNode + 1], segtree[2 * currNode + 2]);
                    break;
                default:
                    throw new RuntimeException("wait how did this even happen lmao");
            }
        }
    }

    // for this one, from and to follow "normal" slicing rules - left bound is inclusive, right bound isn't
    public int calc(int from, int to) {
        if (from < 0 || to > arrSize) {
            throw new IllegalArgumentException(String.format("the bounds %s and %s are out of bounds i think", from, to));
        } else if (to <= from) {
            throw new IllegalArgumentException(String.format("the bounds %s and %s don't make sense bro", from, to));
        }
        return calc(from, to, 0, 0, size);
    }

    private int calc(int from, int to, int currNode, int left, int right) {
        if (right <= from || to <= left) {
            return op == OpType.MIN ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        if (from <= left && right <= to) {
            return segtree[currNode];
        }
        int middle = (left + right) / 2;
        int leftPart = calc(from, to, 2 * currNode + 1, left, middle);
        int rightPart = calc(from, to, 2 * currNode + 2, middle, right);
        switch (op) {
            case MIN:
                return min(leftPart, rightPart);
            case MAX:
                return max(leftPart, rightPart);
            default:
                throw new RuntimeException("wait how did this even happen lmao");
        }
    }

    private int min(int x, int y) {
        if (x == Integer.MAX_VALUE) {
            return y;
        } else if (y == Integer.MAX_VALUE) {
            return x;
        }
        return Collections.min(Arrays.asList(x, y), cmp);
    }

    private int max(int x, int y) {
        if (x == Integer.MIN_VALUE) {
            return y;
        } else if (y == Integer.MIN_VALUE) {
            return x;
        }
        return Collections.max(Arrays.asList(x, y), cmp);
    }
}
