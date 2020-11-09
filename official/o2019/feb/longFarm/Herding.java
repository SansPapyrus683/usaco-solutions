import java.io.*;
import java.util.*;

// 2019 feb silver
public class Herding {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("herding.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[] cows = new int[cowNum];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Integer.parseInt(read.readLine());
        }
        Arrays.sort(cows);

        int best = minMoves(cows);
        /*
         * to find the worst, you have to notice that if you take the endpoints, you can constantly put them at the
         * farthest end possible, you can occupy every empty space at least once
         * the thing is, we have to choose one end to start, so we choose the end w/ the least empty spaces
         * */
        int worst;
        if (cows[1] - cows[0] <= cows[cows.length - 1] - cows[cows.length - 2]) {
            worst = (cows[cows.length - 1] - cows[1] + 1) - (cows.length - 1);
        } else {
            worst = (cows[cows.length - 2] - cows[0] + 1) - (cows.length - 1);
        }

        PrintWriter written = new PrintWriter("herding.out");
        written.printf("%s%n%s%n", best, worst);
        written.close();
        System.out.printf("%s%n%s%n", best, worst);
        System.out.printf("zzzzzzz OH uh idk it took %d ms? (please don't kill me)%n", System.currentTimeMillis() - start);
    }

    static int minMoves(int[] cows) {
        if ((checkConsec(Arrays.copyOfRange(cows, 1, cows.length)) && cows[0] < cows[1] - 2) ||
                checkConsec(Arrays.copyOfRange(cows, 0, cows.length - 1)) && cows[cows.length - 2] + 2 < cows[cows.length - 1]) {
            return 2;
        }
        /*
         * slide a window through the cows and find the window w/ the most cows,
         * bc then we'll have to move the least cows to fill the gaps between them
         * */
        int minBest = Integer.MAX_VALUE;
        int herdStart = 0;
        int herdEnd = 0;
        for (int c : cows) {
            while (herdEnd < cows.length && cows[herdEnd] <= c + cows.length - 1) {
                herdEnd++;
            }
            if (herdEnd >= cows.length) {
                herdEnd--;
            }
            if (cows[herdEnd] > c + cows.length - 1) {
                herdEnd--;
            }

            minBest = Math.min(minBest, cows.length - (herdEnd - herdStart + 1));
            herdStart++;
        }
        return minBest;
    }

    static boolean checkConsec(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[i - 1] + 1) {
                return false;
            }
        }
        return true;
    }
}
