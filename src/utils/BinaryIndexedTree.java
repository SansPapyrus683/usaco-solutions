package utils;

/**
 * <a href="https://www.geeksforgeeks.org/binary-indexed-tree-or-fenwick-tree-2/">src is copied from here lol</a><br>
 * if you're too lazy to click that link i can just try really badly to explain it here<br>
 * but anyways a binary indexed tree is smth that can have O(log n) updating and O(log n) sum querying from 0 to n<br>
 * it stores the elements in it's array through powers of two<br>
 * each index stores the previous n elements, where n is the greatest power of 2 that can divide into that number<br>
 * and then when querying the data structure just takes up the largest power of two
 * (it's based on the binary representation of the number), adds it to the total, and then keeps on adding and adding
 * until it's reached the actual number<br>
 * say we wanted to query for 25<br>
 * the algo would first take 16, then 8, then 1, and accumulate all the values at those indices (16, 24, and 25)
 */
public final class BinaryIndexedTree {
    public enum OpType {
        SUM,
        XOR
    }

    private final int[] treeThing;
    private final int[] actualArr;
    private final int size;
    private final OpType opType;

    public BinaryIndexedTree(int size, OpType opType) {
        treeThing = new int[size + 1];  // to make stuff easier we'll just make it 1-indexed
        actualArr = new int[size];
        this.size = size;
        this.opType = opType;
    }

    public BinaryIndexedTree(int[] arr, OpType opType) {
        this(arr.length, opType);
        for (int i = 0; i < arr.length; i++) {
            set(i, arr[i]);
        }
    }

    public int get(int ind) {
        return actualArr[ind];
    }

    public void set(int ind, int val) {
        switch (opType) {
            case SUM:
                increment(ind, val - actualArr[ind]);
                break;
            case XOR:
                increment(ind, val ^ actualArr[ind]);
                break;
            default:
                throw new RuntimeException("this shouldn't happen, but you never know!");
        }
    }

    public void increment(int ind, int val) {
        actualArr[ind] += val;
        ind++;  // have the driver code not worry about 1-indexing
        // that bitwise thing returns the greatest power of two that's less than i
        for (; ind <= size; ind += ind & -ind) {
            switch (opType) {
                case SUM:
                    treeThing[ind] += val;
                    break;
                case XOR:
                    treeThing[ind] ^= val;
                    break;
                default:
                    throw new RuntimeException("this shouldn't happen, but you never know!");
            }
        }
    }

    public int query(int ind) {  // the bound is inclusive i think (returns sum of everything from 0 to ind)
        ind++;
        int sum = 0;
        for (; ind > 0; ind -= ind & -ind) {
            switch (opType) {
                case SUM:
                    sum += treeThing[ind];
                    break;
                case XOR:
                    sum ^= treeThing[ind];
                    break;
                default:
                    throw new RuntimeException("this shouldn't happen, but you never know!");
            }
        }
        return sum;
    }
}
