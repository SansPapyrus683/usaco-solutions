package official.o2023.usopen.gold;

import java.io.*;
import java.util.*;

/** 2023 us open gold */
public class Pareidolia {
    private static final String MATCH = "bessie";

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        String str = read.readLine();
        int[] costs = Arrays.stream(read.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        assert str.length() == costs.length;

        final int mLen = MATCH.length();
        LinkedHashMap<Integer, Integer> allStatus = new LinkedHashMap<>();
        allStatus.put(0, 0);
        for (int i = 0; i < str.length(); i++) {
            int upTo = 0;
            // OH MY GOD JAVA JUST LET ME GET THE LAST KEY
            for (int k : allStatus.keySet()) {
                upTo = k + 1;
            }

            for (int j = upTo; j >= 0; j--) {
                if (j < upTo && !allStatus.containsKey(j)) {
                    break;
                }

                int val = Integer.MAX_VALUE;
                if (j > 0 && str.charAt(i) == MATCH.charAt((j - 1) % mLen)) {
                    val = Math.min(val, allStatus.getOrDefault(j - 1, Integer.MAX_VALUE));
                }

                if (allStatus.containsKey(j)) {
                    int noMatch = j % mLen == 0 ? 0 : costs[i];
                    val = Math.min(val, allStatus.get(j) + noMatch);
                }

                if (val != Integer.MAX_VALUE) {
                    allStatus.put(j, val);
                }
            }

            // this number (5) could be lower? i think?
            while (allStatus.size() > mLen * 5) {
                allStatus.remove(allStatus.keySet().iterator().next());
            }
        }

        int most = allStatus.keySet().stream().max(Integer::compare).get() / mLen;
        int minCost = Integer.MAX_VALUE;
        for (int i = most * mLen; i < (most + 1) * mLen; i++) {
            minCost = Math.min(minCost, allStatus.getOrDefault(i, Integer.MAX_VALUE));
        }

        System.out.println(most);
        System.out.println(minCost);
    }
}
