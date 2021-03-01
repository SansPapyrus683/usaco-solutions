package official.o2017.feb.gold.cookedCows2;

import java.io.*;
import java.util.*;

// 2017 feb gold (also works for the plat version)
public final class NoCross {
    private static final int THRESHOLD = 4;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("nocross.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int fieldNum = Integer.parseInt(initial.nextToken());
        int[] firstFields = new int[fieldNum];
        int[] secondIDToPos = new int[fieldNum];  // tbh we don't really need the second field values
        for (int i = 0; i < fieldNum; i++) {
            firstFields[i] = Integer.parseInt(read.readLine()) - 1;
        }
        for (int i = 0; i < fieldNum; i++) {
            secondIDToPos[Integer.parseInt(read.readLine()) - 1] = i;
        }

        // maximum crosswalks we can draw given the ending position of the last crosswalk
        MaxSegTree maxWithEnd = new MaxSegTree(fieldNum);
        // set the base case
        for (int f = Math.max(0, firstFields[0] - THRESHOLD); f <= Math.min(fieldNum - 1, firstFields[0] + THRESHOLD); f++) {
            maxWithEnd.set(secondIDToPos[f], 1);
        }
        for (int i = 1; i < fieldNum; i++) {
            ArrayList<Integer> matchable = new ArrayList<>();
            for (int f = Math.max(0, firstFields[i] - THRESHOLD); f <= Math.min(fieldNum - 1, firstFields[i] + THRESHOLD); f++) {
                matchable.add(secondIDToPos[f]);
            }
            matchable.sort(Comparator.comparingInt(f -> -f));  // sort so we don't have any interference between the updates
            for (int m : matchable) {
                int previous = maxWithEnd.max(m, m + 1);
                int drawNew = maxWithEnd.max(0, m) + 1;
                maxWithEnd.set(m, Math.max(previous, drawNew));
            }
        }

        int max = maxWithEnd.max(0, fieldNum);
        PrintWriter written = new PrintWriter("nocross.out");
        written.println(max);
        written.close();
        System.out.println(max);
        System.out.printf("%d ms and we are DONE FRICK YEA%n", System.currentTimeMillis() - start);
    }
}

class MaxSegTree {
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
        } else if (to < from) {
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
