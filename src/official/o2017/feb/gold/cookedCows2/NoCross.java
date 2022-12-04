package official.o2017.feb.gold.cookedCows2;

import java.io.*;
import java.util.*;

// 2017 feb gold (also works for the plat version)
public class NoCross {
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
                int previous = maxWithEnd.rangeMax(m, m + 1);
                int drawNew = maxWithEnd.rangeMax(0, m) + 1;
                maxWithEnd.set(m, Math.max(previous, drawNew));
            }
        }

        int max = maxWithEnd.rangeMax(0, fieldNum);
        PrintWriter written = new PrintWriter("nocross.out");
        written.println(max);
        written.close();
        System.out.println(max);
        System.out.printf("%d ms and we are DONE FRICK YEA%n", System.currentTimeMillis() - start);
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
