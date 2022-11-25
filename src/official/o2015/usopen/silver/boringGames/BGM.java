package official.o2015.usopen.silver.boringGames;

import java.io.*;
import java.util.*;

// 2015 us open silver
public class BGM {
    private static final Map<Character, Integer> CHAR_TO_NUM = Map.ofEntries(
            Map.entry('B', 0),
            Map.entry('E', 1),
            Map.entry('S', 2),
            Map.entry('I', 3),
            Map.entry('G', 4),
            Map.entry('O', 5),
            Map.entry('M', 6)
    );
    private static final int MOD = 7;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("bgm.in"));
        int totalVarNum = Integer.parseInt(read.readLine());

        long[][] options = new long[CHAR_TO_NUM.size()][MOD];
        for (int i = 0; i < totalVarNum; i++) {
            String[] raw = read.readLine().split(" ");
            int num = (Integer.parseInt(raw[1]) % MOD + MOD) % MOD;
            options[CHAR_TO_NUM.get(raw[0].charAt(0))][num]++;
        }

        // HAHAHAHAHAHAHAHAHAHAHAHAHA (goes through all possible combinations)
        long total = 0;
        for (int b = 0; b < MOD; b++) {
            long bMul = options[CHAR_TO_NUM.get('B')][b];
            for (int e = 0; e < MOD; e++) {
                long eMul = options[CHAR_TO_NUM.get('E')][e];
                for (int s = 0; s < MOD; s++) {
                    long sMul = options[CHAR_TO_NUM.get('S')][s];
                    for (int i = 0; i < MOD; i++) {
                        long iMul = options[CHAR_TO_NUM.get('I')][i];
                        for (int g = 0; g < MOD; g++) {
                            long gMul = options[CHAR_TO_NUM.get('G')][g];
                            for (int o = 0; o < MOD; o++) {
                                long oMul = options[CHAR_TO_NUM.get('O')][o];
                                for (int m = 0; m < MOD; m++) {
                                    long mMul = options[CHAR_TO_NUM.get('M')][m];
                                    if (((b + 2 * e + 2 * s + i) * (g + o + e + s) * (m + 2 * o)) % 7 == 0) {
                                        total += bMul * eMul * sMul * iMul * gMul * oMul * mMul;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("bgm.out"));
        written.println(total);
        written.close();
        System.out.println(total);
        System.out.printf("that mind-numbing game wasted %d ms%n", System.currentTimeMillis() - start);
    }
}
