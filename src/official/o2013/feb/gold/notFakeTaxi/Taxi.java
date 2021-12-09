package official.o2013.feb.gold.notFakeTaxi;

import java.io.*;
import java.util.*;

// 2013 feb gold
public final class Taxi {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("taxi.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int fenceLen = Integer.parseInt(initial.nextToken());
        int[][] cows = new int[cowNum][];
        HashMap<Integer, Integer> startingNum = new HashMap<>();
        HashMap<Integer, Integer> endingNum = new HashMap<>();
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            if (cows[c].length != 2) {
                throw new IllegalArgumentException("what kind of start/end position is this");
            }
            if ((cows[c][0] < 0 || fenceLen < cows[c][0]) || (cows[c][1] < 0 || fenceLen < cows[c][1])) {
                throw new IllegalArgumentException("the positions this cow will be in is invalid");
            }
            startingNum.put(cows[c][0], startingNum.getOrDefault(cows[c][0], 0) + 1);
            endingNum.put(cows[c][1], endingNum.getOrDefault(cows[c][1], 0) + 1);
        }

        // -1 is for a start, 1 is for an end (except for the initial thing bc the editorial said so)
        ArrayList<int[]> startEnds = new ArrayList<>(Arrays.asList(new int[][] {{0, 1}, {fenceLen, -1}}));
        for (int[] c : cows) {
            startEnds.add(new int[] {c[0], -1});
            startEnds.add(new int[] {c[1], 1});
        }
        startEnds.sort(Comparator.comparingInt(c -> c[0]));

        long dist = 0;
        for (int[] c : cows) {
            dist += Math.abs(c[1] - c[0]);
        }
        // counts the total difference between the end and start points
        int noCowCrosses = 0;
        for (int i = 0; i < startEnds.size() - 1; i++) {
            noCowCrosses += startEnds.get(i)[1];
            dist += (long) (startEnds.get(i + 1)[0] - startEnds.get(i)[0]) * Math.abs(noCowCrosses);
        }

        PrintWriter written = new PrintWriter("taxi.out");
        written.println(dist);
        written.close();
        System.out.println(dist);
        System.out.printf("are the cows even paying bessie anything: %d ms%n", System.currentTimeMillis() - start);
    }
}
