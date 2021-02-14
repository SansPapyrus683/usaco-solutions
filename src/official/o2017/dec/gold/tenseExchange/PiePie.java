package official.o2017.dec.gold.tenseExchange;

import java.io.*;
import java.util.*;

public class PiePie {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("piepie.in"));
        StringTokenizer inital = new StringTokenizer(read.readLine());
        int pieNum = Integer.parseInt(inital.nextToken());
        int valueThreshold = Integer.parseInt(inital.nextToken());
        int[][] bessie = new int[pieNum][2];
        for (int p = 0; p < pieNum; p++) {
            bessie[p] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(bessie, Comparator.comparingInt(p -> p[0]));
        int[][] elsie = new int[pieNum][2];
        for (int p = 0; p < pieNum; p++) {
            elsie[p] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(elsie, Comparator.comparingInt(p -> p[0]));

        // TODO: lmao this ain't done yet

        System.out.printf("do the cows like anime: %d ms%n", System.currentTimeMillis() - start);
    }

    private static int bisectLeft(int[][] arr, int elem, int compInd) {
        int lo = 0;
        int hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid][compInd] < elem) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    private static int bisectRight(int[][] arr, int elem, int compInd) {
        int lo = 0;
        int hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (elem < arr[mid][compInd]) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }
}
