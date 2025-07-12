package official.o2025.usopen.bronze;

import java.io.*;
import java.util.*;

/** 2025 us open bronze */
public class MooinTimeIII {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int len = Integer.parseInt(initial.nextToken());
        int queryNum = Integer.parseInt(initial.nextToken());
        String str = read.readLine();
        assert str.length() == len;

        TreeSet<Integer>[] in = new TreeSet[26];
        TreeSet<Integer>[] out = new TreeSet[26];
        for (int i = 0; i < 26; i++) {
            char test = (char) ('a' + i);
            in[i] = new TreeSet<>();
            out[i] = new TreeSet<>();
            for (int j = 0; j < len; j++) {
                (str.charAt(j) == test ? in : out)[i].add(j);  // lol
            }
        }

        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            int from = Integer.parseInt(query.nextToken()) - 1;
            int to = Integer.parseInt(query.nextToken()) - 1;
            long best = Long.MIN_VALUE;
            for (int i = 0; i < 26; i++) {
                Integer start = out[i].ceiling(from);
                Integer end = in[i].floor(to);
                if (start == null || end == null) {
                    continue;
                }

                NavigableSet<Integer> possible = in[i].headSet(end - 1, true);
                if (!possible.isEmpty() && start <= possible.last()) {
                    possible = possible.tailSet(start, true);
                } else {
                    continue;
                }
                int middle = (start + end) / 2;
                Integer poss1 = possible.ceiling(middle);
                Integer poss2 = possible.floor(middle);
                if (poss1 != null) {
                    best = Math.max(best, (long) (end - poss1) * (poss1 - start));
                }
                if (poss2 != null) {
                    best = Math.max(best, (long) (start - poss2) * (poss2 - end));
                }
            }

            System.out.println(best == Long.MIN_VALUE ? -1 : best);
        }
    }
}
