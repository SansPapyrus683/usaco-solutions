package official.o2025.feb.gold;

import java.io.*;
import java.util.*;

public class TheBestSubsequence {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int len = Integer.parseInt(initial.nextToken());
        int updNum = Integer.parseInt(initial.nextToken());
        int queryNum = Integer.parseInt(initial.nextToken());

        int[][] updates = new int[updNum][2];
        for (int[] u : updates) {
            StringTokenizer upd = new StringTokenizer(read.readLine());
            u[0] = Integer.parseInt(upd.nextToken()) - 1;
            u[1] = Integer.parseInt(upd.nextToken()) - 1;
        }

        StupidStr ss = new StupidStr(len, updates);
        StringBuilder ans = new StringBuilder();
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            int from = Integer.parseInt(query.nextToken()) - 1;
            int to = Integer.parseInt(query.nextToken()) - 1;
            int subLen = Integer.parseInt(query.nextToken());
            
            int lo = from;
            int hi = to;
            int valid = from - 1;
            while (lo <= hi) {
                int mid = (lo + hi) / 2;
                if (ss.onesB4(mid) - ss.onesB4(from - 1) + to - mid >= subLen) {
                    valid = mid;
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }

            long init = ss.hash(valid + 1, to);
            long add = StupidStr.exp(2, subLen) - StupidStr.exp(2, to - valid);
            long qAns = (init + add + 2 * StupidStr.MOD) % StupidStr.MOD;
            ans.append(qAns).append('\n');
        }

        System.out.print(ans);
    }
}

class StupidStr {
    public static final int MOD = (int) 1e9 + 7;

    private final int len;
    private final TreeSet<int[]> intervals = new TreeSet<>(Arrays::compare);
    private final List<Integer> pref1 = new ArrayList<>(Arrays.asList(0));
    private final List<Long> prefH = new ArrayList<>(Arrays.asList(0L));

    public StupidStr(int len, int[][] updates) {
        this.len = len;

        TreeMap<Integer, Integer> inversions = new TreeMap<>();
        for (int[] u : updates) {
            inversions.put(u[0], inversions.getOrDefault(u[0], 0) + 1);
            inversions.put(u[1] + 1, inversions.getOrDefault(u[1] + 1, 0) - 1);
        }

        int active = 0;
        int prev = 0;
        for (Map.Entry<Integer, Integer> i : inversions.entrySet()) {
            if (active % 2 == 1) {
                final int end = i.getKey() - 1; // just a shorthand
                pref1.add(pref1.get(pref1.size() - 1) + end - prev + 1);
                long newH = (exp(2, len - prev) - exp(2, len - end - 1) + MOD) % MOD;
                prefH.add((prefH.get(prefH.size() - 1) + newH) % MOD);
                intervals.add(new int[] { prev, end, intervals.size() });
            }
            active += i.getValue();
            prev = i.getKey();
        }
    }

    public int onesB4(int to) {
        int[] i = intervals.floor(new int[] { to, len, len });
        return i == null ? 0 : pref1.get(i[2] + 1) + Math.min(0, to - i[1]);
    }

    public long prefHash(int to) {
        int[] i = intervals.floor(new int[] { to, len, len });
        if (i == null) {
            return 0;
        }
        long raw = prefH.get(i[2] + 1);
        long sub = exp(2, len - to - 1) - exp(2, len - Math.max(to, i[1]) - 1);
        long b4Div = ((raw - sub) % MOD + MOD) % MOD;
        return b4Div * modInv(exp(2, len - to - 1)) % MOD;
    }

    public long hash(int from, int to) {
        long raw = (prefHash(to) - (prefHash(from - 1) * exp(2, to - from + 1)));
        return (raw % MOD + MOD) % MOD;
    }

    public static long modInv(long x) {
        return exp(x, MOD - 2);
    }

    public static long exp(long x, long n) {
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
