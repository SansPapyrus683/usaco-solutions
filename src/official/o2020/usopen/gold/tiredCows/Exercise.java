package official.o2020.usopen.gold.tiredCows;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * 2020 usopen gold
 * so notice that like no matter what permutation, it can be split into a bunch of "cycles"
 * so the cows in, sa, 1, 4, and 5 all rotate around, and 2 and 3 rotate around
 * the cycle time is just the LCM of the cycles (in this case it'd be 6)
 * so this is like finding the sum of LCM of all partitions of N
 * but because doing that would be slow as frick, we instead just consider the partitions which
 * consists only of distinct prime powers, because those would cover all possible LCMs
 *
 * also this is slow mostly bc of the bigintegers
 * if you want like super speed, just switch to longs and do everything mod the mod idk
 */
public final class Exercise {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        StringTokenizer initial = new StringTokenizer(new BufferedReader(new FileReader("exercise.in")).readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        BigInteger mod = new BigInteger(initial.nextToken());

        // the sum of all the LCMs of the prime powers that sum to i (the array index)
        BigInteger[] totalWithSum = new BigInteger[cowNum + 1];
        Arrays.fill(totalWithSum, BigInteger.ZERO);
        totalWithSum[0] = new BigInteger("1");
        for (int i = 2; i <= cowNum; i++) {
            if (!prime(i)) {
                continue;
            }
            // this is an array so the update happens "simultaneously"
            BigInteger[] updatedTotal = totalWithSum.clone();
            for (int p = i; p <= cowNum; p *= i) {  // go through all prime powers
                for (int from = 0; from + p <= cowNum; from++) {
                    updatedTotal[from + p] = updatedTotal[from + p].add(totalWithSum[from].multiply(new BigInteger(String.valueOf(p))));
                }
            }
            totalWithSum = updatedTotal;
        }

        BigInteger total = BigInteger.ZERO;
        for (BigInteger prod : totalWithSum) {
            total = total.add(prod);
        }
        total = total.mod(mod);
        PrintWriter written = new PrintWriter("exercise.out");
        written.println(total);
        written.close();
        System.out.println(total);
        System.out.printf("this is the most convoluted exercise routine ever: %d ms%n", System.currentTimeMillis() - start);
    }

    // nothing beats copying src code (sauce: https://www.geeksforgeeks.org/primality-test-set-1-introduction-and-school-method/)
    private static boolean prime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        for (int i = 5; i * i <= n; i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
        return true;
    }
}
