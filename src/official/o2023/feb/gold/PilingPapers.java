package official.o2023.feb.gold;

import java.io.*;
import java.util.*;

// 2023 feb gold
public class PilingPapers {
    private static final int MOD = (int) 1e9 + 7;
    private static final int MAX_DIG = 17;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int paperNum = Integer.parseInt(initial.nextToken());
        long lower = Long.parseLong(initial.nextToken()) - 1;  // trust me
        long upper = Long.parseLong(initial.nextToken());
        assert 0 <= lower && lower <= upper;
        int[] papers = Arrays.stream(read.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        assert papers.length == paperNum;

        long[][] binom = new long[paperNum + 1][paperNum + 1];
        binom[0][0] = 1;
        for (int i = 1; i <= paperNum; i++) {
            for (int j = 0; j <= i; j++) {
                if (j > 0) {
                    binom[i][j] += binom[i - 1][j - 1];
                }
                binom[i][j] = (binom[i][j] + binom[i - 1][j]) % MOD;
            }
        }

        int[] lb = Long.toString(lower).chars().map(i -> i - '0').toArray();
        int[] ub = Long.toString(upper).chars().map(i -> i - '0').toArray();
        assert ub.length <= MAX_DIG;

        long[][] queryAns = new long[paperNum][paperNum];
        for (int start = 0; start < paperNum; start++) {
            long[][][] startDP = new long[lb.length][lb.length][lb.length];
            long[][][] endDP = new long[ub.length][ub.length][ub.length];
            for (int i = start; i < paperNum; i++) {
                for (int d = lb.length + 1; d < ub.length; d++) {
                    queryAns[start][i] += binom[i - start + 1][d] * (1L << d) % MOD;
                    queryAns[start][i] %= MOD;
                }

                int curr = papers[i];
                for (int j = 0; j < 2; j++) {
                    int[] num = j == 0 ? lb : ub;
                    long[][][] dp = j == 0 ? startDP : endDP;

                    for (int d = 0; d < num.length; d++) {
                        boolean[] valid = new boolean[num.length];
                        for (int dig = 0; dig < num.length; dig++) {
                            valid[dig] = dig > d || (dig == d && curr > num[d]) || (dig < d && curr == num[dig]);
                        }

                        for (int len = num.length; len >= 2; len--) {
                            for (int from = 0; from + len - 1 < num.length; from++) {
                                int to = from + len - 1;
                                if (valid[from]) {
                                    dp[d][from][to] += dp[d][from + 1][to];
                                }
                                if (valid[to]) {
                                    dp[d][from][to] += dp[d][from][from + len - 2];
                                }
                                dp[d][from][to] %= MOD;
                            }
                        }
                        for (int dig = 0; dig < num.length; dig++) {
                            if (valid[dig]) {
                                dp[d][dig][dig] += 2;
                            }
                        }

                        long inc = dp[d][0][num.length - 1] * (j == 0 ? 1 : -1);
                        queryAns[start][i] = ((queryAns[start][i] + inc) % MOD + MOD) % MOD;
                    }
                }

                if (lb.length != ub.length) {
                    queryAns[start][i] += binom[i - start + 1][ub.length] * (1L << ub.length) % MOD;
                    queryAns[start][i] %= MOD;
                }
            }
        }

        int queryNum = Integer.parseInt(read.readLine());
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            int startPaper = Integer.parseInt(query.nextToken()) - 1;
            int endPaper = Integer.parseInt(query.nextToken()) - 1;
            System.out.println(queryAns[startPaper][endPaper]);
        }
    }
}
