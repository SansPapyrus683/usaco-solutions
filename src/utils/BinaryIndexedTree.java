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
public class BinaryIndexedTree {
    private final int[] treeThing;
    private final int size;

    public BinaryIndexedTree(int size) {
        treeThing = new int[size + 1];  // to make stuff easier we'll just make it 1-indexed
        this.size = size;
    }

    public void increment(int updateAt, long val) {
        updateAt++;  // have the driver code not worry about 1-indexing
        // that bitwise thing returns the greatest power of two that's less than i
        for (; updateAt <= size; updateAt += updateAt & -updateAt) {
            treeThing[updateAt] += val;
        }
    }

    public long query(int ind) {  // the bound is inclusive i think
        ind++;
        long sum = 0;
        for (; ind > 0; ind -= ind & -ind) {
            sum += treeThing[ind];
        }
        return sum;
    }
}
