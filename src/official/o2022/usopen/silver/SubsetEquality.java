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
        for (int c1 = 0; c1 < charNum; c1++) {
            equal[1 << c1] = occ[c1] == 0;
            for (int c2 = c1 + 1; c2 < charNum; c2++) {
                char char1 = (char) ('a' + c1);
                char char2 = (char) ('a' + c2);
                equal[(1 << c1) + (1 << c2)] = filter(s1, char1, char2).equals(filter(s2, char1, char2));
            }
        }
        for (int subset = 1; subset < (1 << charNum); subset++) {
            if (Integer.bitCount(subset) <= 2) {
                continue;
            }
            equal[subset] = true;
            for (int rmv = 0; rmv < charNum; rmv++) {
                if ((subset & (1 << rmv)) == 0) {
                    continue;
                }
                int prev = subset & ~(1 << rmv);
                if (!equal[prev]) {
                    equal[subset] = false;
                    break;
                }
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

    private static String filter(String s, char a, char b) {
        StringBuilder ret = new StringBuilder();
        for (int c = 0; c < s.length(); c++) {
            if (s.charAt(c) == a || s.charAt(c) == b) {
                ret.append(s.charAt(c));
            }
        }
        return ret.toString();
    }
}
