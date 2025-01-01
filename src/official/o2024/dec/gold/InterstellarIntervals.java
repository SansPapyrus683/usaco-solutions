package official.o2024.dec.gold;

import java.io.*;
import java.util.*;

/** 2024 dec gold (ty suhas) */
public class InterstellarIntervals {
    private static final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int len = Integer.parseInt(read.readLine());
        String must = read.readLine();
        assert must.length() == len;

        TreeSet<Integer> blue = new TreeSet<>(Collections.singletonList(-1));
        TreeSet<Integer> red = new TreeSet<>(Collections.singletonList(-1));
        for (int i = 0; i < len; i++) {
            if (must.charAt(i) == 'B') {
                blue.add(i);
            } else if (must.charAt(i) == 'R') {
                red.add(i);
            }
        }

        long[] dp = new long[len + 1];
        long[][] pref = new long[2][len + 2];
        dp[0] = pref[0][1] = 1;
        for (int i = 1; i <= len; i++) {
            if (must.charAt(i - 1) == 'X') {
                dp[i] = (dp[i] + dp[i - 1]) % MOD;
            }
            long[] thisPref = pref[i % 2];

            final int maxX = Math.min(i / 2, i - 1 - red.floor(i - 1));
            int x = 1;
            while (x <= maxX) {
                int start = i - 2 * x;
                int end = i - 1 - x;
                Integer blueIn = blue.ceiling(start);
                if (blueIn != null && blueIn <= end) {
                    x += end - blueIn + 1;
                } else {
                    int downTo = Math.min(x + (start - blue.lower(start) - 1) / 2, maxX);
                    long add = (thisPref[i / 2 - x + 1] - thisPref[i / 2 - downTo] + MOD) % MOD;
                    dp[i] = (dp[i] + add) % MOD;
                    x = downTo + 1;
                }
            }

            thisPref[i / 2 + 1] = (thisPref[i / 2] + dp[i]) % MOD;
        }

        System.out.println(dp[len]);
    }
}
