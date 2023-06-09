package official.o2022.feb.gold;

import java.io.*;
import java.util.*;

// 2022 feb gold (input omitted bc length)
public class RedistGifts {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());

        boolean[][] canTake = new boolean[cowNum][cowNum];
        for (int c = 0; c < cowNum; c++) {
            int[] prefs = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int g : prefs) {
                canTake[c][--g] = true;
                if (g == c) {
                    break;
                }
            }
        }

        long[][] validRedists = new long[1 << cowNum][cowNum];
        long[] actualRedists = new long[1 << cowNum];
        actualRedists[0] = 1;
        for (int s = 1; s < (1 << cowNum); s++) {
            if (Integer.bitCount(s) == 1) {
                int c = (int) Math.round(Math.log(s) / Math.log(2));
                actualRedists[s] = validRedists[s][c] = 1;
                continue;
            }

            int start = 0;
            while ((s & (1 << start)) == 0) {
                start++;
            }
            for (int end = 0; end < cowNum; end++) {
                if ((s & (1 << end)) == 0) {
                    continue;
                }
                int prev = s & ~(1 << end);
                if (start == end) {
                    validRedists[s][end] += actualRedists[prev];
                } else {
                    for (int addTo = 0; addTo < cowNum; addTo++) {
                        if ((prev & (1 << addTo)) == 0 || !canTake[end][addTo]) {
                            continue;
                        }
                        validRedists[s][end] += validRedists[prev][addTo];
                    }
                }
                if (canTake[start][end]) {
                    actualRedists[s] += validRedists[s][end];
                }
            }
        }

        StringBuilder ans = new StringBuilder();
        int queryNum = Integer.parseInt(read.readLine());
        for (int q = 0; q < queryNum; q++) {
            String query = read.readLine();
            int holstein = 0;
            int guernsey = 0;
            for (int c = 0; c < cowNum; c++) {
                if (query.charAt(c) == 'H') {
                    holstein += 1 << c;
                } else if (query.charAt(c) == 'G') {
                    guernsey += 1 << c;
                }
            }
            ans.append(actualRedists[holstein] * actualRedists[guernsey]).append('\n');
        }
        System.out.print(ans);
    }
}
