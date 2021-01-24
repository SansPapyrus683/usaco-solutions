package official.o2014.usopen.silver.photoDiscrimination;

import java.io.*;
import java.util.*;

/**
 * 2014 usopen silver
 * sol sauce: https://stackoverflow.com/questions/28356453/longest-positive-sum-substring
 */
public class FairPhoto {
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("fairphoto.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[][] cows = new int[cowNum][2];
        for (int c = 0; c < cowNum; c++) {
            StringTokenizer unparsed = new StringTokenizer(read.readLine());
            // white = -1, spotted = 1 (it's this way so the binary search below works)
            cows[c] = new int[] {Integer.parseInt(unparsed.nextToken()), unparsed.nextToken().equals("W") ? 1 : -1};
        }
        Arrays.sort(cows, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);

        int[] cowSums = new int[cowNum];
        cowSums[0] = cows[0][1];
        for (int c = 1; c < cowNum; c++) {
            cowSums[c] = cowSums[c - 1] + cows[c][1];
        }
        int[] rightMaxes = new int[cowNum];
        rightMaxes[cowNum - 1] = cowSums[cowNum - 1];
        for (int c = cowNum - 1 - 1; c >= 0; c--) {
            rightMaxes[c] = Math.max(rightMaxes[c + 1], cowSums[c]);
        }

        int longest = 0;
        // try both the possible "offsets" to preserve the even amt of cows
        for (int start = 0; start < 2; start++) {
            int left = start;
            int right = start + 1;
            while (right < cowNum) {
                int rnSum = left > 0 ? rightMaxes[right] - cowSums[left - 1] : rightMaxes[right];
                if (rnSum >= 0) {
                    if (left < cowNum) {  // sometimes the left array index will just go wack, so here's a failsafe
                        longest = Math.max(longest, cows[right][0] - cows[left][0]);
                    }
                    right += 2;
                } else {
                    left += 2;
                }
            }
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("fairphoto.out"));
        written.println(longest);
        written.close();
        System.out.println(longest);
        System.out.printf("*click* it took something like %d ms%n", System.currentTimeMillis() - timeStart);
    }
}
