package official.o2021.usopen.gold;

import java.io.*;
import java.util.*;

/**
 * 2021 usopen gold
 * 7
 * 1 2 3 4 3 2 5 should output 13
 */
public final class UCFJ {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());
        // make the cow breeds start from 0 instead of 1
        int[] cows = Arrays.stream(read.readLine().split(" ")).mapToInt(c -> Integer.parseInt(c) - 1).toArray();
        if (cows.length != cowNum) {
            throw new IllegalArgumentException("inconsistent cow number given screw you");
        }

        BITree uniqueBreeds = new BITree(cowNum);
        int[] lastSeen = new int[cowNum];  // breeds can only be [0, cowNum) so it's your fault if this errors
        Arrays.fill(lastSeen, -1);

        long validDelegs = 0;
        for (int i = 0; i < cowNum; i++) {
            int c = cows[i];
            if (lastSeen[c] != -1) {
                validDelegs += uniqueBreeds.query(i - 1) - uniqueBreeds.query(lastSeen[c]);
                uniqueBreeds.increment(lastSeen[c], -1);
            } else {
                validDelegs += uniqueBreeds.query(i - 1);
            }
            lastSeen[c] = i;
            uniqueBreeds.increment(lastSeen[c], 1);
        }
        System.out.println(validDelegs);
        System.err.printf("%d ms i know you can do better c'mon%n", System.currentTimeMillis() - start);
    }
}

class BITree {
    private final int[] treeThing;
    private final int size;
    public BITree(int size) {
        treeThing = new int[size + 1];  // to make stuff easier we'll just make it 1-indexed
        this.size = size;
    }

    public void increment(int ind, int val) {
        ind++;  // have the actual code not worry about 1-indexing
        for (; ind <= size; ind += ind & -ind) {
            treeThing[ind] += val;
        }
    }

    public int query(int ind) {  // returns from of [0, ind]
        ind++;
        int sum = 0;
        for (; ind > 0; ind -= ind & -ind) {
            sum += treeThing[ind];
        }
        return sum;
    }
}
