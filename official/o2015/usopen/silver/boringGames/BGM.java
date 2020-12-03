package official.o2015.usopen.silver.boringGames;

import java.io.*;
import java.util.*;

// 2015 us open silver
public class BGM {
    private static final int MOD = 7;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("bgm.in"));
        int totalVarNum = Integer.parseInt(read.readLine());

        HashMap<Character, Integer> varToNum = new HashMap<>();
        int counter = 0;
        for (char c : "BESIGOM".toCharArray()) {
            varToNum.put(c, counter);
            counter++;
        }

        long[][] options = new long[8][7];
        for (int i = 0; i < totalVarNum; i++) {
            String[] raw = read.readLine().split(" ");
            int num = Integer.parseInt(raw[1]) % MOD;
            num += num < 0 ? MOD : 0;
            options[varToNum.get(raw[0].charAt(0))][num]++;
        }

        // HAHAHAHAHAHAHAHAHAHAHAHAHA (goes through all possible combinations)
        long total = 0;
        for (int b = 0; b < MOD; b++) {
            long bMul = options[varToNum.get('B')][b];
            for (int e = 0; e < MOD; e++) {
                long eMul = options[varToNum.get('E')][e];
                for (int s = 0; s < MOD; s++) {
                    long sMul = options[varToNum.get('S')][s];
                    for (int i = 0; i < MOD; i++) {
                        long iMul = options[varToNum.get('I')][i];
                        for (int g = 0; g < MOD; g++) {
                            long gMul = options[varToNum.get('G')][g];
                            for (int o = 0; o < MOD; o++) {
                                long oMul = options[varToNum.get('O')][o];
                                for (int m = 0; m < MOD; m++) {
                                    long mMul = options[varToNum.get('M')][m];
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
        System.out.printf("after that mind-numbing game we wasted %d ms%n", System.currentTimeMillis() - start);
    }
}
