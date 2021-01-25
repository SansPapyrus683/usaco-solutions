package official.o2019.jan.gold.bessieAllanPoe;

import java.io.*;
import java.util.*;

// 2019 jan gold (i'd use biginteger but it makes the calculations too slow)
public final class Poetry {
    private static final long MOD = (long) Math.pow(10, 9) + 7;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("poetry.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int wordNum = Integer.parseInt(initial.nextToken());
        int lineNum = Integer.parseInt(initial.nextToken());
        int syllableReq = Integer.parseInt(initial.nextToken());

        HashMap<Integer, ArrayList<Integer>> rhymeClasses = new HashMap<>();
        int[] words = new int[wordNum];
        for (int w = 0; w < wordNum; w++) {
            StringTokenizer word = new StringTokenizer(read.readLine());
            int syllables = Integer.parseInt(word.nextToken());
            int rhymeClass = Integer.parseInt(word.nextToken());
            if (!rhymeClasses.containsKey(rhymeClass)) {
                rhymeClasses.put(rhymeClass, new ArrayList<>());
            }
            rhymeClasses.get(rhymeClass).add(syllables);
            words[w] = syllables;
        }
        Arrays.sort(words);  // probably not necessary, just makes debug info prettier

        HashMap<String, Integer> lines = new HashMap<>();
        for (int l = 0; l < lineNum; l++) {
            String pattern = read.readLine();
            lines.put(pattern, lines.getOrDefault(pattern, 0) + 1);
        }

        // this[i] = total number of line possibilities for a line w/ i syllables
        // using longs because i'm not taking any chances with overflow
        long[] linePossibilities = new long[syllableReq + 1];
        linePossibilities[0] = 1;
        // sauce: https://codeforces.com/blog/entry/70018#coin-combinations
        for (int s = 1; s < syllableReq + 1; s++) {
            for (int w : words) {
                if (s - w >= 0) {
                    linePossibilities[s] = (linePossibilities[s] + linePossibilities[s - w]) % MOD;
                }
            }
        }

        // contains the number of ways to make a line end with a certain rhyme final class
        long[] rhymeAmts = new long[rhymeClasses.size()];
        int ind = 0;
        for (ArrayList<Integer> similar : rhymeClasses.values()) {
            long rhymeTotal = 0;
            for (int w : similar) {
                if (syllableReq - w >= 0) {
                    rhymeTotal = (rhymeTotal + linePossibilities[syllableReq - w]) % MOD;
                }
            }
            rhymeAmts[ind++] = rhymeTotal;
        }

        long totalWays = 1;
        // for each of the lines, we count the amount of ways to fill them up then do the product
        for (int l : lines.values()) {
            long lineCombs = 0;
            for (long n : rhymeAmts) {
                lineCombs = (lineCombs + power(n, l)) % MOD;
            }
            totalWays = (totalWays * lineCombs) % MOD;
        }

        PrintWriter written = new PrintWriter("poetry.out");
        written.println(totalWays);
        written.close();
        System.out.println(totalWays);
        System.out.printf("Quoth the cow, \"Nevermore.\" (%d ms)%n", System.currentTimeMillis() - start);
    }

    // sauce: https://www.geeksforgeeks.org/modular-exponentiation-power-in-modular-arithmetic/
    private static long power(long x, long y) {
        long res = 1;
        x %= MOD;
        if (x == 0) {
            return 0;
        }

        while (y > 0) {
            if((y & 1) == 1) {
                res = (res * x) % MOD;
            }
            y = y >> 1;
            x = (x * x) % MOD;
        }
        return res;
    }
}
