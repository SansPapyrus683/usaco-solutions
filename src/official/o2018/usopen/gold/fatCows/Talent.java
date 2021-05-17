package official.o2018.usopen.gold.fatCows;

import java.io.*;
import java.util.*;

// 2018 usopen gold
public final class Talent {
    private static final int PRECISION = (int) Math.pow(10, 3);
    private static class Cow {
        int weight;
        int talent;
        public Cow(int weight, int talent) {
            this.weight = weight;
            this.talent = talent;
        }
    }
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("talent.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int weightReq = Integer.parseInt(initial.nextToken());
        Cow[] cows = new Cow[cowNum];
        for (int c = 0; c < cowNum; c++) {
            int[] cow = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            cows[c] = new Cow(cow[0], cow[1]);
        }

        long lo = 0;
        long hi = (long) Arrays.stream(cows).mapToInt(c -> c.talent).sum() * PRECISION;
        long valid = -1;
        while (lo <= hi) {
            long mid = (lo + hi) / 2;
            if (achievableScore(mid, weightReq, cows)) {
                lo = mid + 1;
                valid = mid;
            } else {
                hi = mid - 1;
            }
        }
        PrintWriter written = new PrintWriter("talent.out");
        written.println(valid);
        written.close();
        System.out.println(valid);
        System.out.printf("i swear one day i will find benq and strangle him: %d ms%n", System.currentTimeMillis() - start);
    }

    // we're going to represent score with an integer to PRECISION
    private static boolean achievableScore(long score, int weightReq, Cow[] cows) {
        // so let's define the "leeway" of a set of cows as the talent sum - required score * weight sum
        double[][] maxLeeway = new double[cows.length + 1][weightReq + 1];
        Arrays.fill(maxLeeway[0], -Double.MAX_VALUE);  // MIN_VALUE will be when we literally can't achieve weight amt
        maxLeeway[0][0] = 0;
        for (int c = 0; c < cows.length; c++) {
            maxLeeway[c + 1] = maxLeeway[c].clone();
            Cow nxt = cows[c];
            for (int w = 0; w <= weightReq; w++) {
                if (maxLeeway[c][w] == -Double.MAX_VALUE) {
                    continue;
                }
                int newWeight = Math.min(weightReq, w + nxt.weight);
                double newLeeway = maxLeeway[c][w] + nxt.talent * PRECISION - nxt.weight * score;
                maxLeeway[c + 1][newWeight] = Math.max(maxLeeway[c + 1][newWeight], newLeeway);
            }
        }
        return maxLeeway[cows.length][weightReq] >= 0.0;
    }
}
