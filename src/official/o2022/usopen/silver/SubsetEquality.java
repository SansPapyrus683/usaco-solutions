package official.o2022.usopen.silver;

import java.io.*;
import java.util.*;

/**
 * 2022 us open silver
 * aabcd
 * caabd
 * 4
 * a
 * ac
 * abd
 * abcd should output YNYN
 */
public final class SubsetEquality {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        // assuming valid input, if anything goes wrong it's on you
        char max = 'a';
        String s1 = read.readLine();
        String s2 = read.readLine();
        for (int c = 0; c < s1.length(); c++) {
            max = (char) Math.max(max, s1.charAt(c));
        }
        for (int c = 0; c < s2.length(); c++) {
            max = (char) Math.max(max, s2.charAt(c));
        }
        int charNum = max - 'a' + 1;

        int[] occ = new int[charNum];
        for (int c = 0; c < s1.length(); c++) {
            occ[s1.charAt(c) - 'a']++;
        }
        for (int c = 0; c < s2.length(); c++) {
            occ[s2.charAt(c) - 'a']--;
        }

        boolean[] equal = new boolean[1 << charNum];
        equal[0] = true;  // just for explicitness
        for (int subset = 1; subset < (1 << charNum); subset++) {
            if (Integer.bitCount(subset) == 1) {
                int set = 0;
                for (; (subset & (1 << set)) == 0; set++);
                equal[subset] = occ[set] == 0;
                continue;
            }

            boolean corrupted = false;
            for (int rmv = 0; rmv < charNum; rmv++) {
                if ((subset & (1 << rmv)) == 0) {
                    continue;
                }
                int prev = subset & ~(1 << rmv);
                if (!equal[prev]) {
                    corrupted = true;
                    break;
                }
            }
            if (corrupted) {
                StringBuilder newS1 = new StringBuilder();
                for (int c = 0; c < s1.length(); c++) {
                    if ((subset & (1 << (s1.charAt(c) - 'a'))) != 0) {
                        newS1.append(s1.charAt(c));
                    }
                }
                StringBuilder newS2 = new StringBuilder();
                for (int c = 0; c < s2.length(); c++) {
                    if ((subset & (1 << (s2.charAt(c) - 'a'))) != 0) {
                        newS2.append(s2.charAt(c));
                    }
                }
                equal[subset] = newS1.toString().equals(newS2.toString());
            }
        }

        int queryNum = Integer.parseInt(read.readLine());
        for (int q = 0; q < queryNum; q++) {
            String queryStr = read.readLine();

            int subset = 0;
            for (int c = 0; c < queryStr.length(); c++) {
                subset += 1 << (queryStr.charAt(c) - 'a');
            }
            System.out.print(equal[subset] ? 'Y' : 'N');
        }
        System.out.println();
    }
}
