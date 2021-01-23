package official.o2017.dec.silver.cowOfTheYear;

import java.io.*;
import java.util.*;

// 2017 dec silver
public class Measurement {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("measurement.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int measurementNum = Integer.parseInt(initial.nextToken());
        int initialMilk = Integer.parseInt(initial.nextToken());
        int[][] measurements = new int[measurementNum][3];
        HashMap<Integer, Integer> cowToMilk = new HashMap<>();  // this.get(i) = the milk that cow i is producing
        TreeMap<Integer, HashSet<Integer>> milkToCows = new TreeMap<>();  // this.get(i) = the set of cows that are producing i milk
        milkToCows.put(initialMilk, new HashSet<>());
        for (int m = 0; m < measurementNum; m++) {
            // time taken, cow id, milk change
            int[] measurement = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            measurements[m] = measurement;
            if (!cowToMilk.containsKey(measurement[1])) {
                cowToMilk.put(measurement[1], initialMilk);
                milkToCows.get(initialMilk).add(measurement[1]);
            }
        }
        Arrays.sort(measurements, Comparator.comparingInt(m -> m[0]));

        processMeasurement(measurements[0], cowToMilk, milkToCows);
        int changeAmts = 1;  // the first transition always results in a change (initially no cows on the lb)
        for (int i = 1; i < measurementNum; i++) {
            HashSet<Integer> oldBestCows = new HashSet<>(milkToCows.lastEntry().getValue());
            processMeasurement(measurements[i], cowToMilk, milkToCows);
            HashSet<Integer> newBestCows = milkToCows.lastEntry().getValue();
            if (!oldBestCows.equals(newBestCows)) {  // see if there's a change in the lb
                changeAmts++;
            }
        }

        PrintWriter written = new PrintWriter("measurement.out");
        written.println(changeAmts);
        written.close();
        System.out.println(changeAmts);
        System.out.printf("and it took %d ms- that's the highest time we have so far! (jk)%n", System.currentTimeMillis() - start);
    }

    private static void processMeasurement(int[] m, Map<Integer, Integer> cows, Map<Integer, HashSet<Integer>> milkCows) {
        int oldMilk = cows.get(m[1]);
        milkCows.get(oldMilk).remove(m[1]);
        if (milkCows.get(oldMilk).isEmpty()) {
            milkCows.remove(oldMilk);
        }
        int newMilk = cows.get(m[1]) + m[2];
        cows.put(m[1], newMilk);
        if (!milkCows.containsKey(newMilk)) {
            milkCows.put(newMilk, new HashSet<>());
        }
        milkCows.get(newMilk).add(m[1]);
    }
}
