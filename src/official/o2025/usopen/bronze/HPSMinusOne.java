package official.o2025.usopen.bronze;

import java.io.*;
import java.util.*;

/** 2025 us open bronze */
public class HPSMinusOne {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int symbolNum = Integer.parseInt(initial.nextToken());
        int matchNum = Integer.parseInt(initial.nextToken());
        Set<Integer>[] losesTo = new HashSet[symbolNum];
        for (int s1 = 0; s1 < symbolNum; s1++) {
            losesTo[s1] = new HashSet<>();
            String row = read.readLine();
            for (int s2 = 0; s2 < s1 + 1; s2++) {
                if (row.charAt(s2) == 'W') {
                    losesTo[s2].add(s1);
                } else if (row.charAt(s2) == 'L') {
                    losesTo[s1].add(s2);
                }
            }
        }

        for (int m = 0; m < matchNum; m++) {
            StringTokenizer match = new StringTokenizer(read.readLine());
            int e1 = Integer.parseInt(match.nextToken()) - 1;
            int e2 = Integer.parseInt(match.nextToken()) - 1;
            Set<Integer> bothLose = new HashSet<>(losesTo[e1]);
            bothLose.retainAll(losesTo[e2]);

            int n = bothLose.size();  // just a shorthand
            System.out.println(n * n + 2 * (symbolNum - n) * n);
        }
    }
}
