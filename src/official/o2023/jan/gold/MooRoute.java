package official.o2023.jan.gold;

import java.io.*;
import java.util.*;
import java.util.function.BiFunction;

public class MooRoute {
    private static final int MOD = (int) 1e9 + 7;
    private static final int MAX_CROSS = (int) 1e6;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int len = Integer.parseInt(read.readLine());
        StringTokenizer crosses = new StringTokenizer(read.readLine());
        
        final long[] fact = new long[MAX_CROSS + 1];
        fact[0] = 1;
        for (int i = 1; i <= MAX_CROSS; i++) {
            fact[i] = fact[i - 1] * i % MOD;
        }

        BiFunction<Integer, Integer, Long> choose = (n, k) -> {
            long top = fact[n];
            long bottom = exp(fact[k], MOD - 2) * exp(fact[n - k], MOD - 2) % MOD;
            return top * bottom % MOD;
        };

        long ways = 1;
        int prev = Integer.parseInt(crosses.nextToken()) / 2;
        for (int i = 1; i < len; i++) {
            int rls = Integer.parseInt(crosses.nextToken()) / 2;
            if (rls <= prev) {
                ways = ways * choose.apply(prev, rls) % MOD;
            } else {
                int stars = rls - prev;
                int bars = prev - 1;
                ways = ways * choose.apply(stars + bars, bars) % MOD;
            }
            prev = rls;
        }

        System.out.println(ways);
    }

    private static long exp(long x, long n) {
        assert n >= 0;
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
