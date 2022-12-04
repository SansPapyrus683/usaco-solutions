package official.o2016.dec.gold.moocast;

import java.io.*;
import java.util.*;

// 2016 dec gold
import java.io.*;
import java.util.*;

// 2016 dec gold
public class Moocast {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("moocast.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[][] cows = new int[cowNum][2];
        for (int c = 0; c < cowNum; c++) {
            StringTokenizer cow = new StringTokenizer(read.readLine());
            cows[c][0] = Integer.parseInt(cow.nextToken());
            cows[c][1] = Integer.parseInt(cow.nextToken());
        }

        int lo = 0;
        int hi = Integer.MAX_VALUE / 2;  // should be large enough
        int valid = -1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (allReachable(mid, cows)) {
                valid = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }

        PrintWriter written = new PrintWriter("moocast.out");
        written.println(valid);
        written.close();
    }

    static boolean allReachable(int power, int[][] cows) {
        int start = 0;
        ArrayDeque<Integer> frontier = new ArrayDeque<>();
        frontier.add(start);
        boolean[] reached = new boolean[cows.length];
        reached[start] = true;
        while (!frontier.isEmpty()) {
            int curr = frontier.poll();
            for (int c = 0; c < cows.length; c++) {
                if (!reached[c] && distSq(cows[curr], cows[c]) <= power) {
                    frontier.add(c);
                    reached[c] = true;
                }
            }
        }

        for (boolean c : reached) {
            if (!c) {
                return false;
            }
        }
        return true;
    }

    static int distSq(int[] c1, int[] c2) {
        return (int) Math.pow(c1[0] - c2[0], 2) + (int) Math.pow(c1[1] - c2[1], 2);
    }
}
