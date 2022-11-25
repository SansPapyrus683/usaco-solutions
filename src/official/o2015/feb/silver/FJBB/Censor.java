package official.o2015.feb.silver.FJBB;

import java.io.*;
import java.util.*;

// 2015 feb silver
public class Censor {
    private static final int MOD = (int) 1e9 + 7;
    private static final int POW = 101;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("censor.in"));
        String toCensor = read.readLine();  // remember kids, nothing happened on june 4th 1989
        String badWord = read.readLine();

        long[] pows = new long[Math.max(toCensor.length(), badWord.length()) + 1];
        pows[0] = 1;
        for (int i = 1; i < pows.length; i++) {
            pows[i] = (pows[i - 1] * POW) % MOD;
        }

        long badHash = 0;
        for (int i = 0; i < badWord.length(); i++) {
            badHash = (badHash + pows[i] * badWord.charAt(i)) % MOD;
        }

        char[] cleanedArr = new char[toCensor.length()];
        long[] hashes = new long[toCensor.length() + 1];
        int indAt = 0;
        for (int i = 0; i < toCensor.length(); i++) {
            char c = toCensor.charAt(i);
            hashes[indAt + 1] = (hashes[indAt] + c * pows[indAt] + MOD) % MOD;
            cleanedArr[indAt] = c;
            if (indAt >= badWord.length() - 1) {
                int prevInd = indAt + 1 - badWord.length();
                long prevHash = hashes[prevInd];
                long diff = hashes[indAt + 1] - prevHash;
                long suffHash = (((diff * modInv(pows[prevInd])) % MOD) + MOD) % MOD;
                if (suffHash == badHash) {
                    indAt -= badWord.length();
                }
            }
            indAt++;
        }

        StringBuilder cleaned = new StringBuilder();
        for (int i = 0; i < indAt; i++) {
            cleaned.append(cleanedArr[i]);
        }
        System.out.println(cleaned);
        PrintWriter written = new PrintWriter(new FileOutputStream("censor.out"));
        written.println(cleaned);
        written.close();
        System.out.printf("%d ms: social credit -42069%n", System.currentTimeMillis() - start);
    }

    private static long modInv(long n) {
        return pow(n, MOD - 2);
    }

    private static long pow(long base, long exp) {
        base %= MOD;
        long res = 1;
        while (exp > 0) {
            if (exp % 2 == 1) {
                res = res * base % MOD;
            }
            base = base * base % MOD;
            exp /= 2;
        }
        return res;
    }
}
