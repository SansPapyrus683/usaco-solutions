package official.o2019.jan.gold.drunkCows;

import java.io.*;
import java.util.*;

// 2019 jan gold
public class Sleepy {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("sleepy.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[] cows = Arrays.stream(read.readLine().split(" ")).mapToInt(c -> Integer.parseInt(c) - 1).toArray();
        assert cows.length == cowNum;

        int last = Integer.MAX_VALUE;
        BITree sortedAlr = new BITree(cowNum);
        int badUpTo = cowNum - 1;
        for (; badUpTo >= 0; badUpTo--) {
            sortedAlr.increment(cows[badUpTo], 1);
            if (cows[badUpTo] > last) {
                sortedAlr.increment(cows[badUpTo], -1);  // we went one too far, so let's just back up one
                break;
            }
            last = cows[badUpTo];
        }
        badUpTo++;

        int[] moveAmts = new int[badUpTo];
        for (int c = 0; c < badUpTo; c++) {
            moveAmts[c] = badUpTo - (c + 1) + sortedAlr.query(cows[c]);
            sortedAlr.increment(cows[c], 1);
        }
        StringBuilder moves = new StringBuilder();
        for (int m : moveAmts) {  // i could probably do this in a single for loop but this makes everything neater tbh
            moves.append(m).append(" ");
        }
        moves.setLength(moves.length() - 1);  // remove that pesky trailing space

        PrintWriter written = new PrintWriter("sleepy.out");
        written.println(badUpTo);
        written.println(moves);
        written.close();
        System.out.println(badUpTo);
        System.out.println(moves);
        System.out.printf("damit fj don't let your cows near vodka: %d ms%n", System.currentTimeMillis() - start);
    }
}

class BITree {
    private final int[] treeThing;
    private final int size;
    public BITree(int size) {
        treeThing = new int[size + 1];
        this.size = size;
    }

    public void increment(int updateAt, int val) {
        updateAt++;  // have the driver code not worry about 1-indexing
        for (; updateAt <= size; updateAt += updateAt & -updateAt) {
            treeThing[updateAt] += val;
        }
    }

    public int query(int ind) {  // the bound is inclusive i think
        ind++;
        int sum = 0;
        for (; ind > 0; ind -= ind & -ind) {
            sum += treeThing[ind];
        }
        return sum;
    }
}
