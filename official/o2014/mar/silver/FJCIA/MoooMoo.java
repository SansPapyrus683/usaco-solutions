package official.o2014.mar.silver.FJCIA;

import java.io.*;
import java.util.*;

// 2014 mar silver
public class MoooMoo {
    private static final int INVALID = 420696969;  // MAX_VALUE can result in an overflow -> min() always selects those values
    static int[] breedVolumes;
    static long start;

    public static void main(String[] args) throws IOException {
        start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("mooomoo.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int[] fields = new int[Integer.parseInt(initial.nextToken())];

        int breedNum = Integer.parseInt(initial.nextToken());
        HashSet<Integer> initVolumes = new HashSet<>();  // nowhere did they say breeds didn't have duplicate volumes
        for (int b = 0; b < breedNum; b++) {
            initVolumes.add(Integer.parseInt(read.readLine()));
        }
        breedVolumes = new int[initVolumes.size()];
        int volPointer = 0;
        for (int v : initVolumes) {
            breedVolumes[volPointer++] = v;
        }
        Arrays.sort(breedVolumes);

        for (int f = 0; f < fields.length; f++) {
            fields[f] = Integer.parseInt(read.readLine());
        }
        // go through the fields in reverse and "reverse" the wind changes so we just have the pure cow volumes
        for (int f = fields.length - 1; f > 0; f--) {
            fields[f] -= Math.max(fields[f - 1] - 1, 0);
        }

        if (!Arrays.stream(fields).allMatch(i -> i >= 0)) {
            writeOutput(-1);
        }
        int minCows = 0;
        for (int f : fields) {  // get the minimum amount of cows in each field
            minCows += sumToVol(f);
        }
        writeOutput(minCows);
    }

    static void writeOutput(Object toWrite) throws IOException {
        PrintWriter written = new PrintWriter("mooomoo.out");  // oh wow you can just straight up do the filename
        written.println(toWrite);
        written.close();
        System.out.println(toWrite);
        System.out.printf("*jeopardy music plays for 30 seconds* well... it took %d ms%n", System.currentTimeMillis() - start);
        System.exit(0);
    }

    static int sumToVol(int volume) throws IOException {
        int[][] dp = new int[breedVolumes.length + 1][volume + 1];  // this[i][j] = min value for first i breeds mooing at j volume
        Arrays.fill(dp[0], INVALID);
        dp[0][0] = 0;

        // https://www.geeksforgeeks.org/minimum-count-of-numbers-required-from-given-array-to-represent-s/ for reference
        for (int breedsUsed = 1; breedsUsed < breedVolumes.length + 1; breedsUsed++) {
            for (int sumTo = 1; sumTo < volume + 1; sumTo++) {
                // if that other breed is larger, we literally can't use it, so just go with the previous one
                if (breedVolumes[breedsUsed - 1] > sumTo) {
                    dp[breedsUsed][sumTo] = dp[breedsUsed - 1][sumTo];
                } else {  // we can either still go with the previous number, or use the new breed and add one to that cost
                    dp[breedsUsed][sumTo] = Math.min(dp[breedsUsed - 1][sumTo],
                            dp[breedsUsed][sumTo - breedVolumes[breedsUsed - 1]] + 1);
                }
            }
        }
        if (dp[breedVolumes.length][volume] == INVALID) {
            writeOutput(-1);
        }
        return dp[breedVolumes.length][volume];
    }
}
