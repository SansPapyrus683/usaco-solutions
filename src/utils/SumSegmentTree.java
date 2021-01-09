package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * <a href="https://codeforces.com/edu/course/2/lesson/4/1">this video does a really good explanation if you wanna see</a><br>
 * but if you really want my crappy explanation here we go:<br>
 * so a segment tree breaks down an array into halves, and breaks those halves into halves, and so on and so forth until
 * you just end up with a bunch of single numbers as the leaf nodes (which are the values of the array themselves)<br>
 * so you end up with a sort of tree with the root being the sum of the entire array. then the two children have the sum
 * of the left and right half respectively, and so on and so forth until you just get the array elements<br>
 */
public class SumSegmentTree {
    public enum OpType {  // dk if this is a best practice but i don't wanna have two really similar classes
        SUM,
        XOR
    }

    private final int[] segtree;
    private final int arrSize;
    private final int size;
    private final OpType op;

    public SumSegmentTree(int len, OpType op) {  // constructs the thing kinda like an array
        int size = 1;
        while (size < len) {
            size *= 2;
        }
        this.size = size;
        this.op = op;
        arrSize = len;
        segtree = new int[size * 2];  // we won't necessarily use all of the element but that doesn't really matter
    }

    public SumSegmentTree(int[] arr, OpType op) {  // constructs the thing with initial elements as well
        this(arr.length, op);
        for (int i = 0; i < arr.length; i++) {
            set(i, arr[i]);
        }
    }

    /**
     * just a small demonstration of the segtree<br>
     * 5 5 <- element num and query num respectively<br>
     * 5 4 2 3 5 <- initial elements in array<br>
     * 2 0 3 <- query of type 2 queries all elements from l to r (r isn't inclusive)<br>
     * 1 1 1 <- query of type 1 just sets the element at the first number to the second number<br>
     * 2 0 3<br>
     * 1 3 1<br>
     * 2 0 5<br>
     * in total, this should output 11, 8, and 14 each on a newline
     */
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        initial.nextToken();
        int queryNum = Integer.parseInt(initial.nextToken());
        SumSegmentTree segmentTree = new SumSegmentTree(Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(), OpType.SUM);
        for (int q = 0; q < queryNum; q++) {
            int[] query = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            if (query[0] == 1) {
                segmentTree.set(query[1], query[2]);
            } else {
                System.out.println(segmentTree.sum(query[1], query[2]));
            }
        }
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
                case SUM:
                    segtree[currNode] = segtree[2 * currNode + 1] + segtree[2 * currNode + 2];
                    break;
                case XOR:
                    segtree[currNode] = segtree[2 * currNode + 1] ^ segtree[2 * currNode + 2];
                    break;
                default:
                    throw new RuntimeException("wait how did this even happen lmao");
            }
        }
    }

    // for this one, from and to follow "normal" slicing rules - left bound is inclusive, right bound isn't
    public int sum(int from, int to) {
        if (from < 0 || to > arrSize) {
            throw new IllegalArgumentException(String.format("the bounds %s and %s are out of bounds i think", from, to));
        }
        return sum(from, to, 0, 0, size);
    }

    private int sum(int from, int to, int currNode, int left, int right) {
        if (right <= from || to <= left) {  // oof, out of bounds, so the sum is definitely 0
            return 0;
        }
        if (from <= left && right <= to) {
            return segtree[currNode];
        }
        int middle = (left + right) / 2;
        int leftPartSum = sum(from, to, 2 * currNode + 1, left, middle);
        int rightPartSum = sum(from, to, 2 * currNode + 2, middle, right);
        switch (op) {
            case SUM:
                return leftPartSum + rightPartSum;
            case XOR:
                return leftPartSum ^ rightPartSum;
            default:
                throw new RuntimeException("wait how did this even happen lmao");
        }
    }
}
