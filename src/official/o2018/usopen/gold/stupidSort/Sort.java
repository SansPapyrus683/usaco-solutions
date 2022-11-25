package official.o2018.usopen.gold.stupidSort;

import java.io.*;
import java.util.*;

public class Sort {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("sort.in"));
        int[][] arr = new int[Integer.parseInt(read.readLine())][2];
        for (int i = 0; i < arr.length; i++) {
            arr[i][0] = Integer.parseInt(read.readLine());
            arr[i][1] = i;
        }
        Arrays.sort(arr, Comparator.comparingInt(i -> i[0]));

        // just go to the official sol here lol: http://usaco.org/current/data/sol_sort_gold_open18.html
        int maxChangedAmt = 0;
        BITree sortedSeen = new BITree(arr.length);
        for (int i = 0; i < arr.length; i++) {
            sortedSeen.increment(arr[i][1], 1);
            maxChangedAmt = Math.max(maxChangedAmt, i - sortedSeen.query(i) + 1);
        }
        int mooTimes = maxChangedAmt == 0 ? 1 : maxChangedAmt;
        PrintWriter written = new PrintWriter("sort.out");
        written.println(mooTimes);
        written.close();
        System.out.println(mooTimes);
        System.out.printf("why do you do this to me bessie: %d ms%n", System.currentTimeMillis() - start);
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
        ind++;  // have the driver code not worry about 1-indexing
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
