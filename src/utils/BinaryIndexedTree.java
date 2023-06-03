package utils;

/**
 * A data structure that supports point updates and range sum queries in O(log n).
 * explanation <a href="https://cp-algorithms.com/data_structures/fenwick.html">here</a>
 * i'm too lazy to explain it
 */
public class BinaryIndexedTree {
    private final int[] treeThing;
    private final int[] actualArr;
    private final int size;

    public BinaryIndexedTree(int size) {
        treeThing = new int[size + 1];  // to make stuff easier we'll just make it 1-indexed
        actualArr = new int[size];
        this.size = size;
    }

    public BinaryIndexedTree(int[] arr) {
        this(arr.length);
        for (int i = 0; i < arr.length; i++) {
            increment(i, arr[i]);
        }
    }

    public int get(int ind) {
        return actualArr[ind];
    }

    public void increment(int ind, int val) {
        actualArr[ind] += val;
        ind++;  // have the driver code not worry about 1-indexing
        // that bitwise thing returns the greatest power of two that's less than i
        for (; ind <= size; ind += ind & -ind) {
            treeThing[ind] += val;
        }
    }

    public int query(int ind) {  // the bound is inclusive i think (returns sum of everything from 0 to ind)
        ind++;
        int sum = 0;
        for (; ind > 0; ind -= ind & -ind) {
            sum += treeThing[ind];
        }
        return sum;
    }
}
