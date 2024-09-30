package official.o2024.usopen.gold;

import java.io.*;
import java.util.*;

/**
 * 2024 us open gold
 * bc this is java i had to submit this like 10 times to get all ac
 * it's the correct complexity, just, constant factor stuff, yknow?
 */
public class SmallerAverages {
    private static final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int len = Integer.parseInt(read.readLine());
        StringTokenizer rawA = new StringTokenizer(read.readLine());
        StringTokenizer rawB = new StringTokenizer(read.readLine());
        int[] a = new int[len], b = new int[len];
        for (int i = 0; i < len; i++) {
            a[i] = Integer.parseInt(rawA.nextToken());
            b[i] = Integer.parseInt(rawB.nextToken());
        }

        int[][] bInds = new int[len][];
        double[][] bAvg = new double[len][];
        for (int i = 0; i < len; i++) {
            Integer[] inds = new Integer[i + 1];
            int[] amt = new int[i + 1];
            for (int j = i; j >= 0; j--) {
                amt[j] = (j == i ? 0 : amt[j + 1]) + b[j]; // haha bj
                inds[j] = j;
            }
            final int finI = i;
            Arrays.sort(inds, (x, y) -> -(amt[x] * (finI - y + 1) - amt[y] * (finI - x + 1)));

            bInds[i] = new int[i + 1];
            bAvg[i] = new double[i + 1];
            for (int j = 0; j <= i; j++) {
                bAvg[i][j] = (double) amt[inds[j]] / (finI - inds[j] + 1);
                bInds[i][j] = inds[j];
            }
        }

        int[][] splitWays = new int[len + 1][len + 1];
        int[][][] splitPrefs = new int[len + 1][len + 1][];
        splitWays[0][0] = 1;
        for (int i = 1; i <= len; i++) {
            Integer[] aInds = new Integer[i];
            int[] aAmt = new int[i];
            for (int j = i - 1; j >= 0; j--) {
                aAmt[j] = (j == i - 1 ? 0 : aAmt[j + 1]) + a[j]; // haha bj
                aInds[j] = j;
            }
            final int finI = i;
            Arrays.sort(aInds, (x, y) -> -(aAmt[x] * (finI - y) - aAmt[y] * (finI - x)));

            double[] aAvg = new double[i];
            for (int j = 0; j < i; j++) {
                aAvg[j] = (double) aAmt[aInds[j]] / (finI - aInds[j]);
            }

            for (int j = 1; j <= len; j++) {
                splitPrefs[i - 1][j - 1] = new int[bAvg[j - 1].length + 1];
                // i wish i could explain this...
                for (int x = 1; x <= bAvg[j - 1].length; x++) {
                    splitPrefs[i - 1][j - 1][x] =
                            (splitPrefs[i - 1][j - 1][x - 1] + splitWays[i - 1][bInds[j - 1][x - 1]]) % MOD;
                }

                int bAt = 0;
                for (int k = 0; k < i; k++) {
                    while (bAt < bAvg[j - 1].length && bAvg[j - 1][bAt] >= aAvg[k]) {
                        bAt++;
                    }
                    splitWays[i][j] = (splitWays[i][j] + splitPrefs[aInds[k]][j - 1][bAt]) % MOD;
                }
            }
        }

        System.out.println(splitWays[len][len]);
    }
}
