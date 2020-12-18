package official.o2017.feb.gold.cookedCows3;

import java.io.*;
import java.util.*;

// 2017 feb gold
// for naming, i'm going to assume the first occurrence as the entrance and the second occurrence as the exit
public class CircleCross {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("circlecross.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int totalPoints = cowNum * 2;
        int[] circle = new int[totalPoints];
        int[][] betweenPoints = new int[cowNum][2];  // positions of the enter/exit (ends are both exclusive)
        for (int i = 0; i < cowNum; i++) {
            Arrays.fill(betweenPoints[i], -1);
        }
        for (int c = 0; c < totalPoints; c++) {
            circle[c] = Integer.parseInt(read.readLine()) - 1;
            if (betweenPoints[circle[c]][0] == -1) {
                betweenPoints[circle[c]][0] = c;
            } else {
                betweenPoints[circle[c]][1] = c;
            }
        }
        Arrays.sort(betweenPoints, Comparator.comparingInt(e -> e[0]));

        int crossed = 0;
        BITree biTree = new BITree(totalPoints);
        // for each of the ones, we detect the exit points that reside between them
        // two cows cross if their orientations are like ABAB, so for B and B, we try to detect that ending A
        for (int[] b : betweenPoints) {
            crossed += biTree.query(b[1]) - biTree.query(b[0]);
            biTree.increment(b[1], 1);  // update the exit
        }

        PrintWriter written = new PrintWriter("circlecross.out");
        written.println(crossed);
        written.close();
        System.out.println(crossed);
        System.out.printf("now listen up here's a story about some random code that ran for %d ms%n", System.currentTimeMillis() - start);
    }
}

// copied from https://www.geeksforgeeks.org/queries-number-distinct-elements-subarray/?ref=rp
class BITree {
    private final int[] treeThing;
    private final int size;

    public BITree(int size) {
        treeThing = new int[size + 1];  // so apparently binary trees have to be 1-indexed
        this.size = size;
    }

    public void increment(int updateAt, int val) {
        for (; updateAt <= size; updateAt += updateAt & -updateAt) {
            treeThing[updateAt] += val;
        }
    }

    public int query(int ind) {
        int sum = 0;
        for (; ind > 0; ind -= ind & -ind) {
            sum += treeThing[ind];
        }
        return sum;
    }
}
