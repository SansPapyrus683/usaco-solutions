package official.o2023.usopen.silver;

import java.io.*;
import java.util.*;

/** 2023 us open silver */
public class MilkSum {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());
        int[][] cows = new int[cowNum][2];
        StringTokenizer cowST = new StringTokenizer(read.readLine());
        for (int c = 0; c < cowNum; c++) {
            cows[c][0] = Integer.parseInt(cowST.nextToken());
            cows[c][1] = c;
        }
        Arrays.sort(cows, Comparator.comparingInt(c -> c[0]));

        int[] inds = new int[cowNum];
        long initMilk = 0;
        long[] milkPref = new long[cowNum + 1];
        for (int c = 0; c < cowNum; c++) {
            inds[cows[c][1]] = c;
            initMilk += (long) (c + 1) * cows[c][0];
            milkPref[c + 1] = milkPref[c] + cows[c][0];
        }

        int queryNum = Integer.parseInt(read.readLine());
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            int c = inds[Integer.parseInt(query.nextToken()) - 1];
            int setTo = Integer.parseInt(query.nextToken());
            long newMilk = initMilk;
            newMilk -= (long) (c + 1) * cows[c][0];
            newMilk -= milkPref[cowNum] - milkPref[c + 1];

            int lo = 0;
            int hi = cowNum - 1;
            int valid = cowNum;  // smallest index that's greater than setTo
            while (lo <= hi) {
                int mid = (lo + hi) / 2;
                if (cows[mid][0] > setTo) {
                    valid = mid;
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }

            // really surprised i didn't make any off by one errors here
            if (setTo >= cows[c][0]) {
                newMilk += (long) valid * setTo + milkPref[cowNum] - milkPref[valid];
            } else {
                newMilk += (long) (valid + 1) * setTo;
                newMilk += milkPref[cowNum] - milkPref[valid] - cows[c][0];
            }
            System.out.println(newMilk);
        }
    }
}
