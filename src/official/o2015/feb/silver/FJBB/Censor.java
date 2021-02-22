package official.o2015.feb.silver.FJBB;

import java.io.*;
import java.util.*;

// 2015 feb silver
public final class Censor {
    private static final int MAX_LEN = (int) Math.pow(10, 6);
    private static final long MOD = (long) Math.pow(10, 9) + 9;
    private static final long POWER = 31;  // some website told me to do this (https://cp-algorithms.com/string/string-hashing.html)
    private static final long[] HASH_POWERS = new long[MAX_LEN];

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        long nowPower = 1;
        for (int i = 0; i < MAX_LEN; i++) {  // precompute powers (takes surprisingly little time)
            HASH_POWERS[i] = nowPower;
            nowPower = (nowPower * POWER) % MOD;
        }

        BufferedReader read = new BufferedReader(new FileReader("censor.in"));
        String toCensor = read.readLine();  // remember kids, nothing happened on june 4th 1989
        String badWord = read.readLine();
        if (toCensor.length() > MAX_LEN || badWord.length() > MAX_LEN) {
            throw new IllegalArgumentException("look i'm sure your input is good but i can't handle strings that are too long");
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("censor.out"));
        String after = clean(toCensor, badWord);
        written.println(after);
        written.close();
        System.out.println(after);
        System.out.printf("our great leader fj says it took %d ms so it did%n", System.currentTimeMillis() - start);
    }

    private static long hashConcat(long rnHashVal, int nowStrLen, long toAdd) {
        return (rnHashVal + toAdd * HASH_POWERS[nowStrLen]) % MOD;
    }

    private static String clean(String toCensor, String badWord) {
        toCensor = toCensor.toLowerCase();
        badWord = badWord.toLowerCase();

        long badHashCode = 0;  // hashcode for the badword to censor
        for (int i = 0; i < badWord.length(); i++) {
            // - 'a' + 1 just translates a to 1, b to 2, etc...
            badHashCode = hashConcat(badHashCode, i, badWord.charAt(i) - 'a' + 1);
        }

        char[] all = new char[toCensor.length()];
        long[] hashesSoFar = new long[toCensor.length() + 1];  // + 1 for the empty string, which has a hash of 0
        int index = 0;
        for (char c : toCensor.toCharArray()) {
            all[index++] = c;
            int headIndex = Math.max(index - badWord.length(), 0);
            hashesSoFar[index] = hashConcat(hashesSoFar[index - 1], index - 1, c - 'a' + 1);
            long currViewHash = (hashesSoFar[index] - hashesSoFar[headIndex]) % MOD;
            currViewHash += currViewHash >= 0 ? 0 : MOD;  // let's make it positive
            if (currViewHash == (badHashCode * HASH_POWERS[headIndex]) % MOD) {  // no divisibility issues now ha
                index -= badWord.length();  // reset the pointer back to "checkpoint"? idk
            }
        }
        return new String(Arrays.copyOfRange(all, 0, index));
    }
}
