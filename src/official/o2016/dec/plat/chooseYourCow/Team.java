package official.o2016.dec.plat.chooseYourCow;

import java.io.*;
import java.util.*;

// 2016 dec plat
public class Team {
    private static final int MOD = (int) Math.pow(10, 9) + 9;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("team.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int johnNum = Integer.parseInt(initial.nextToken());
        int paulNum = Integer.parseInt(initial.nextToken());
        int teamReq = Integer.parseInt(initial.nextToken());
        int[] johnCows = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] paulCows = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        assert johnNum == johnCows.length && paulNum == paulCows.length;

        /*
         * this[t][j][p] = winnable outcomes given that
         * the team requirement is t
         * fj uses his first j cows and fp uses his first p cows
         */
        long[][][] winnablePairings = new long[teamReq + 1][johnNum + 1][paulNum + 1];
        for (int j = 0; j < johnNum; j++) {
            int thisWinnable = 0;
            for (int p = 0; p < paulNum; p++) {
                thisWinnable += johnCows[j] > paulCows[p] ? 1 : 0;
                winnablePairings[1][j + 1][p + 1] = thisWinnable + (j > 0 ? winnablePairings[1][j][p + 1] : 0);
            }
        }

        for (int t = 2; t <= teamReq; t++) {
            for (int j = 1; j <= johnNum; j++) {
                for (int p = 1; p <= paulNum; p++) {
                    /*
                     * line 1: first add the first two previous team sizes
                     * line 2: then bc we overcounted the ones that used everything less than j and p,
                     * let's remove one copy of those
                     * line 3: finally if this cow beat paul's cow, we can slap it onto a previous arrangement
                     * where we're confident they aren't already used
                     */
                    long prev = winnablePairings[t][j - 1][p] + winnablePairings[t][j][p - 1];
                    long over = winnablePairings[t][j - 1][p - 1];
                    long addOn = johnCows[j - 1] > paulCows[p - 1] ? winnablePairings[t - 1][j - 1][p - 1] : 0;
                    winnablePairings[t][j][p] = prev - over + addOn;
                    winnablePairings[t][j][p] = (winnablePairings[t][j][p] + MOD) % MOD;
                }
            }
        }

        long totalWinnable = winnablePairings[teamReq][johnNum][paulNum];
        PrintWriter written = new PrintWriter("team.out");
        written.println(totalWinnable);
        written.close();
        System.out.println(totalWinnable);
        System.out.printf("who is this farmer PAUL???? (%d ms)%n", System.currentTimeMillis() - start);
    }
}
