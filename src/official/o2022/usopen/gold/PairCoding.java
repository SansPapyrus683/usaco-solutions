package official.o2022.usopen.gold;

import java.io.*;

// 2022 us open gold (input omitted due to length)
public class PairCoding {
    private static final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            // this won't be used fu benq
            int progLen = Integer.parseInt(read.readLine());
            String bessie = read.readLine().replace("1", "");
            String elsie = read.readLine().replace("1", "");

            bessie = bessie.contains("0") ? bessie.substring(bessie.lastIndexOf('0')) : bessie;
            elsie = elsie.contains("0") ? elsie.substring(elsie.lastIndexOf('0')) : elsie;

            int[][] numDiff = new int[bessie.length() + 1][elsie.length() + 1];
            numDiff[0][0] = 1;
            for (int b = 0; b <= bessie.length(); b++) {
                // when b is 0, we won't use this so it doesn't matter
                char bes = bessie.charAt(Math.max(0, b - 1));
                for (int e = 0; e <= elsie.length(); e++) {
                    if (b == 0 || e == 0) {
                        numDiff[b][e] = 1;
                        continue;
                    }
                    char els = elsie.charAt(e - 1);
                    numDiff[b][e] = (numDiff[b - 1][e] + numDiff[b][e - 1]) % MOD;
                    if (Character.isDigit(bes) == Character.isDigit(els)) {
                        numDiff[b][e] -= numDiff[b - 1][e - 1];
                    }
                    numDiff[b][e] = (numDiff[b][e] + MOD) % MOD;
                }
            }
            System.out.println(numDiff[bessie.length()][elsie.length()]);
        }
    }
}
