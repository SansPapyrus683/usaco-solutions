package official.o2021.feb.gold;

import java.io.*;
import java.util.*;

/**
 * 2021 feb gold
 * 7
 * 10 0 0
 * 10 0 1
 * 9 0 2
 * 8 0 2
 * 0 1 7
 * 1 1 7
 * 2 1 7 should output 11, 0, 4, 3, 1, 2, and 2, each on a newline
 */
public class CowCount {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int queryNum = Integer.parseInt(read.readLine());
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            System.out.println(cowNum(
                    Long.parseLong(query.nextToken()),
                    Long.parseLong(query.nextToken()),
                    Long.parseLong(query.nextToken())
            ));
        }
        System.err.printf("my man you don't just have infinite cows like wth: %d ms%n", System.currentTimeMillis() - start);
    }

    // col is x and row is y (for me to not be confused as all hell)
    private static long cowNum(long dist, long col, long row) {
        if (col < row) {  // always make it so that it's on the top half (symmetry ftw)
            return cowNum(dist, row, col);
        }
        long diagNum = col - row;
        long initial = cowNum(row + dist, diagNum);
        long subtracted = (row > 0 ? cowNum(row - 1, diagNum) : 0);
        return initial - subtracted;
    }

    // the same thing except row is now assumed to be 0
    private static long cowNum(long dist, long col) {
        if (dist == 0 || col % 2 == 1) {  // these 2 are the base cases (when col is odd, will always return 0)
            return ternary(col).contains("1") ? 0 : 1;
        }
        long threeWidth = 1;  // right now this is the 3-pow square that the query is right outside of
        int threePow = 0;
        while (threeWidth * 3 <= col + dist) {
            threeWidth *= 3;
            threePow++;
        }
        if (col < threeWidth) {
            return squareCowNum(threePow, col) + (dist >= threeWidth ? cowNum(dist - threeWidth, col) : 0);
        } else if (col < threeWidth * 2) {
            return dist - (threeWidth * 2 - col) >= 0
                    ? cowNum(dist - (threeWidth * 2 - col), threeWidth * 2 - col) : 0;
        } else {
            return cowNum(dist, col - threeWidth * 2);
        }
    }

    private static long squareCowNum(int powWidth, long diagNum) {
        if (diagNum % 2 == 1 || diagNum >= pow(3, powWidth) || diagNum < 0) {
            return 0;
        }
        if (powWidth == 0) {
            return 1;
        }
        if (diagNum < pow(3, powWidth - 1)) {
            // if it's somewhere in the "middle", then it's just the previous power repeated 3 times
            return 3 * squareCowNum(powWidth - 1, diagNum);
        } else {
            // if not, then we can "shift" it over to the middle, where it only gets repeated one time
            return squareCowNum(powWidth - 1, Math.abs(diagNum - 2 * pow(3, powWidth - 1)));
        }
    }

    private static long pow(long base, int exp) {  // java's math.pow sucks bc it uses doubles lol
        long total = 1;
        for (int i = 0; i < exp; i++) {
            total *= base;
        }
        return total;
    }

    private static String ternary(long n) {  // damn i love one-liners
        return n / 3 == 0 ? String.valueOf(n % 3) : ternary(n / 3) + n % 3;
    }
}
