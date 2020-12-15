/*
ID: kevinsh4
TASK: buylow
LANG: JAVA
just see the python version for the explanation, this is p much the exact same
*/
package section4.part3.buylow;

import java.io.*;
import java.util.*;
import java.math.BigInteger;

public class BuyLow {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("buylow.in"));
        int stockNum = Integer.parseInt(read.readLine());
        int[] stocks = new int[stockNum];
        String line;
        int stockAt = 0;
        while ((line = read.readLine()) != null) {
            for (int s : Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray()) {
                stocks[stockAt++] = s;
            }
        }
        read.close();

        int[] longestSoFar = new int[stockNum];
        BigInteger[] longestNum = new BigInteger[stockNum];
        Arrays.fill(longestSoFar, 1);
        Arrays.fill(longestNum, new BigInteger("1"));

        for (int s = stockNum - 1; s >= 0; s--) {
            for (int j = s + 1; j < stockNum; j++) {
                if (stocks[s] > stocks[j]) {
                    longestSoFar[s] = Math.max(longestSoFar[s], longestSoFar[j] + 1);
                }
            }
            if (longestSoFar[s] == 1) {
                continue;
            }

            longestNum[s] = new BigInteger("0");
            HashSet<Integer> calcedBefore = new HashSet<>();
            for (int j = s + 1; j < stockNum; j++) {
                if (stocks[s] > stocks[j] && longestSoFar[j] + 1 == longestSoFar[s] && !calcedBefore.contains(stocks[j])) {
                    longestNum[s] = longestNum[s].add(longestNum[j]);
                    calcedBefore.add(stocks[j]);
                }
            }
        }

        int longest = Arrays.stream(longestSoFar).max().getAsInt();
        HashSet<Integer> calcedBefore = new HashSet<>();
        BigInteger combs = new BigInteger("0");
        for (int s = 0; s < stockNum; s++) {
            if (longestSoFar[s] == longest && !calcedBefore.contains(stocks[s])) {
                combs = combs.add(longestNum[s]);
                calcedBefore.add(stocks[s]);
            }
        }

        PrintWriter written = new PrintWriter("buylow.out");
        written.printf("%s %s%n", longest, combs);
        written.close();
        System.out.printf("%s %s%n", longest, combs);
        System.out.printf("%d ms les gooooo%n", System.currentTimeMillis() - start);
    }
}

