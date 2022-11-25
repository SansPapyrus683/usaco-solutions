package official.o2021.jan.gold;

import java.io.*;
import java.util.*;

/**
 * 2021 jan gold
 * mildredree should output 3
 */
public class Uddered {
    private static final int ALPH_NUM = 26;
    private static final char REDACTED = '█';
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String rawTestament = new BufferedReader(new InputStreamReader(System.in)).readLine().toLowerCase();

        // compress the characters (not even sure if this is necessary)
        char charAt = 'a';
        char[] testament = rawTestament.toCharArray();
        char[] encoding = new char[ALPH_NUM];
        Arrays.fill(encoding, REDACTED);
        for (int i = 0; i < testament.length; i++) {
            if (encoding[testament[i] - 'a'] == REDACTED) {
                encoding[testament[i] - 'a'] = charAt++;
            }
            testament[i] = encoding[testament[i] - 'a'];
        }

        int charNum = charAt - 'a' + 1;
        int[][] consecNum = new int[charNum][charNum];
        for (int i = 1; i < testament.length; i++) {
            consecNum[testament[i - 1] - 'a'][testament[i] - 'a']++;
        }

        // you know what, just read the editorial lmao
        int[] cowphabetCosts = new int[1 << charNum];
        Arrays.fill(cowphabetCosts, Integer.MAX_VALUE);
        cowphabetCosts[0] = 1;  // no matter what, elsie will have to moo at least once
        for (int i = 1; i < (1 << charNum); i++) {
            for (int c = 0; c < charNum; c++) {  // go through all possible prev states
                if ((i & (1 << c)) == 0) {
                    continue;
                }
                int prev = i & ~(1 << c);  // put that letter back (make it 0)
                int thisCost = cowphabetCosts[prev];
                for (int pc = 0; pc < charNum; pc++) {
                    if ((i & (1 << pc)) != 0) {
                        thisCost += consecNum[c][pc];
                    }
                }
                cowphabetCosts[i] = Math.min(cowphabetCosts[i], thisCost);
            }
        }
        System.out.println(cowphabetCosts[(1 << charNum) - 1]);
        System.err.printf("it took %d ms for your joke of a program to run%n", System.currentTimeMillis() - start);
    }
}
