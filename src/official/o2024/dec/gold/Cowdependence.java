package official.o2024.dec.gold;

import java.io.*;
import java.util.*;

/** 2024 dec gold */
public class Cowdependence {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());
        int[] cows = Arrays.stream(read.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        assert cows.length == cowNum;
        Map<Integer, List<Integer>> groups = new HashMap<>();
        for (int i = 0; i < cowNum; i++) {
            if (!groups.containsKey(cows[i])) {
                groups.put(cows[i], new ArrayList<>());
            }
            groups.get(cows[i]).add(i);
        }

        final int sqrt = (int) Math.sqrt(cowNum);
        int[] minGroups = new int[cowNum];
        int[] prefGroups = new int[cowNum];
        for (List<Integer> g : groups.values()) {
            if (g.size() > sqrt) {
                g.add(cowNum);
                int[] nextCow = new int[cowNum];
                Arrays.fill(nextCow, g.get(0));
                for (int i = 0; i < g.size() - 1; i++) {
                    final int currNext = g.get(i + 1);
                    for (int j = g.get(i); j < currNext; j++) {
                        nextCow[j] = currNext;
                    }
                }

                for (int dist = 1; dist <= cowNum; dist++) {
                    int at = g.get(0);
                    int groupNum = 0;
                    while (at != cowNum) {
                        at = nextCow[Math.min(at + dist, cowNum - 1)];
                        groupNum++;
                    }
                    minGroups[dist - 1] += groupNum;
                }
                continue;
            }

            int dist = 1;
            while (dist <= cowNum) {
                int start = g.get(0);
                int groupNum = 1;
                int nextBreak = cowNum;
                for (int c : g) {
                    if (c > start + dist) {
                        nextBreak = Math.min(nextBreak, c - start - dist);
                        start = c;
                        groupNum++;
                    }
                }
                prefGroups[dist - 1] += groupNum;
                dist += nextBreak;
                if (dist - 1 < cowNum) {
                    prefGroups[dist - 1] -= groupNum;
                }
            }
        }

        for (int i = 0; i < cowNum; i++) {
            prefGroups[i] += i > 0 ? prefGroups[i - 1] : 0;
            minGroups[i] += prefGroups[i];
        }

        Arrays.stream(minGroups).forEach(System.out::println);
    }
}
