 package official.o2024.usopen.gold;

import java.io.*;
import java.util.*;

/** 2024 us open gold (had to have help w/ this one :skull:) */
public class Cowreography {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int delta = Integer.parseInt(initial.nextToken());
        String startString = read.readLine();
        String endString = read.readLine();

        TreeSet<int[]> toMatch = new TreeSet<>(Arrays::compare);
        Boolean currMatching = null;
        long minSwaps = 0;
        for (int c = 0; c < cowNum; c++) {
            boolean s = startString.charAt(c) == '1';
            boolean e = endString.charAt(c) == '1';
            if (s == e) {
                continue;
            }

            if (toMatch.isEmpty() || Boolean.valueOf(s).equals(currMatching)) {
                toMatch.add(new int[] {c % delta, c});
                currMatching = s;
            } else {
                int[] match = toMatch.ceiling(new int[] {c % delta, Integer.MIN_VALUE});
                if (match == null) {
                    match = toMatch.first();
                }
                minSwaps += Math.floorDiv(c - match[1] + delta - 1, delta);
                toMatch.remove(match);
            }
        }

        System.out.println(minSwaps);
    }
}
