package official.o2024.jan.gold;

import java.io.*;
import java.util.*;

public class Cowmpetency {
    private static final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int infoNum = Integer.parseInt(initial.nextToken());
        int maxScore = Integer.parseInt(initial.nextToken());
        Map<Integer, Integer> infoMap = new HashMap<>();
        for (int i = 0; i < infoNum; i++) {
            StringTokenizer piece = new StringTokenizer(read.readLine());
            int a = Integer.parseInt(piece.nextToken()) - 1;
            int h = Integer.parseInt(piece.nextToken()) - 1;
            infoMap.put(h, Math.min(infoMap.getOrDefault(h, Integer.MAX_VALUE), a));
        }

        int[][] info = new int[infoMap.size()][];
        int at = 0;
        for (Map.Entry<Integer, Integer> i : infoMap.entrySet()) {
            info[at++] = new int[] { i.getValue(), i.getKey() };
        }
        Arrays.sort(info, Arrays::compare);

        long[] seqMaxNum = new long[maxScore];
        Arrays.fill(seqMaxNum, 1);
        at = 1; // yeah i'm reusing variables cry about it
        for (int[] i : info) {
            long[] prefSum = seqMaxNum.clone();
            for (int s = 1; s < maxScore; s++) {
                prefSum[s] = (prefSum[s] + prefSum[s - 1]) % MOD;
            }

            long[] next = new long[maxScore];
            int any = i[0] - at + 1;
            for (int s = 0; s < maxScore; s++) {
                long preserveMax = exp(s + 1, any) * seqMaxNum[s] % MOD;
                long newMax = (exp(s + 1, any) - exp(s, any) + MOD) % MOD;
                newMax = newMax * (s == 0 ? 0 : prefSum[s - 1]) % MOD;
                long sum = (preserveMax + newMax) % MOD;
                next[s] = sum * exp(s + 1, i[1] - i[0] - 1) % MOD;
            }

            long[] nnext = next.clone();
            nnext[0] = 0;
            for (int s = 1; s < maxScore; s++) {
                nnext[s] = (nnext[s - 1] + next[s - 1]) % MOD;
            }

            seqMaxNum = nnext;
            at = i[1] + 1;
        }

        long mulBy = exp(maxScore, cowNum - at);
        long total = 0;
        for (long n : seqMaxNum) {
            total = (total + n * mulBy % MOD) % MOD;
        }
        System.out.println(total);
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
