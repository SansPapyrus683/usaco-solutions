package official.o2023.dec.silver;

import java.io.*;
import java.util.*;

// 2023 dec silver
public class BovineAcrobatics {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int weightNum = Integer.parseInt(initial.nextToken());
        int towerNum = Integer.parseInt(initial.nextToken());
        int weightDiff = Integer.parseInt(initial.nextToken());
        TreeMap<Integer, Integer> cows = new TreeMap<>();
        for (int w = 0; w < weightNum; w++) {
            StringTokenizer weightST = new StringTokenizer(read.readLine());
            cows.put(
                    Integer.parseInt(weightST.nextToken()),
                    Integer.parseInt(weightST.nextToken())
            );
        }

        TreeMap<Integer, Integer> towers = new TreeMap<>();
        towers.put(Integer.MIN_VALUE, towerNum);
        long maxCows = 0;
        for (Map.Entry<Integer, Integer> weight : cows.entrySet()) {
            List<int[]> take = new ArrayList<>();
            NavigableMap<Integer, Integer> canAddTo = towers.headMap(weight.getKey() - weightDiff, true);

            int useAmt = 0;
            for (Map.Entry<Integer, Integer> valid : canAddTo.entrySet()) {
                int addNum = Math.min(valid.getValue(), weight.getValue());
                useAmt += addNum;
                weight.setValue(weight.getValue() - addNum);
                take.add(new int[]{valid.getKey(), addNum});

                if (weight.getValue() == 0) {
                    break;
                }
            }

            maxCows += useAmt;
            towers.put(weight.getKey(), useAmt);
            for (int[] t : take) {
                towers.put(t[0], towers.get(t[0]) - t[1]);
                if (towers.get(t[0]) == 0) {
                    towers.remove(t[0]);
                }
            }
        }

        System.out.println(maxCows);
    }
}
