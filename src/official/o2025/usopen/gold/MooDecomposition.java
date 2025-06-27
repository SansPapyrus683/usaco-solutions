package official.o2025.usopen.gold;

import java.io.*;
import java.util.*;

/** 2025 us open gold */
public class MooDecomposition {
    private static final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int mNum = Integer.parseInt(initial.nextToken());
        int strLen = Integer.parseInt(initial.nextToken());
        long repeats = Long.parseLong(initial.nextToken());

        long[] choose = new long[strLen + 1];
        choose[mNum] = 1;
        for (int i = mNum + 1; i <= strLen; i++) {
            choose[i] = choose[i - 1] * inv(i - mNum) % MOD * i % MOD;
        }

        String str = read.readLine();
        long oneCopy = 1;
        int oNum = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == 'M') {
                oneCopy = oneCopy * choose[oNum] % MOD;
                oNum -= mNum;
            } else if (str.charAt(i) == 'O') {
                oNum++;
            }
        }

        System.out.println(exp(oneCopy, repeats));
    }

    private static long inv(long x) {
        return exp(x, MOD - 2);
    }

    private static long exp(long x, long n) {
        x %= MOD;
        long res = 1;
        while (n > 0) {
            if (n % 2 == 1) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
            n /= 2;
        }
        return res;
    }
}
