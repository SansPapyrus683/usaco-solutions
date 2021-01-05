package official.o2020.usopen.gold.tiredCows;

import java.io.*;
import java.util.*;

/**
 * 2020 usopen gold
 * so notice that like no matter what permutation, it can be split into a bunch of "cycles"
 * so the cows in, sa, 1, 4, and 5 all rotate around, and 2 and 3 rotate around
 * the cycle time is just the LCM of the cycles (in this case it'd be 6)
 * so this is like finding the sum of LCM of all partitions of N
 * but because doing that would be slow as frick, we instead just consider the partitions which
 * consists only of distinct prime powers, because those would cover all possible LCMs
 */
public class Exercise {
    private static final int MAX = 1000000007;
    private static long mod;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        StringTokenizer initial = new StringTokenizer(new BufferedReader(new FileReader("exercise.in")).readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        mod = Long.parseLong(initial.nextToken());
        if (mod > MAX) {
            throw new IllegalArgumentException(String.format("sorry but the mod %s is too high- overflow could occur", mod));
        }

        long[] totalWithSum = new long[cowNum + 1];  // the sum of all the LCMs of the prime powers that sum to i (the array index)
        totalWithSum[0] = 1;
        for (int i = 2; i <= cowNum; i++) {
            if (!prime(i)) {
                continue;
            }
            // this is an array so the update happens "simultaneously"
            long[] updatedTotal = totalWithSum.clone();
            for (int p = i; p <= cowNum; p *= i) {  // go through all prime powers
                for (int from = 0; from + p <= cowNum; from++) {
                    updatedTotal[from + p] = add(updatedTotal[from + p], mul(p, totalWithSum[from]));
                }
            }
            totalWithSum = updatedTotal;
        }

        long total = 0;
        for (long prod : totalWithSum) {
            total = add(total, prod);
        }
        PrintWriter written = new PrintWriter("exercise.out");
        written.println(total);
        written.close();
        System.out.println(total);
        System.out.printf("this is the most convoluted exercise routine ever: %d ms%n", System.currentTimeMillis() - start);
    }

    // nothing beats copying src code (sauce: https://www.geeksforgeeks.org/primality-test-set-1-introduction-and-school-method/)
    public static boolean prime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        for (int i = 5; i * i <= n; i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
        return true;
    }

    private static long mul(long a, long b) {
        return (a * b) % mod;
    }

    private static long add(long a, long b) {
        return (a + b) % mod;
    }
}
