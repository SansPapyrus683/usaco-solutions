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
            StringTokenizer pie = new StringTokenizer(read.readLine());
            bessie[p] = new int[] {Integer.parseInt(pie.nextToken()), Integer.parseInt(pie.nextToken()), p};
        }
        Arrays.sort(bessie, Comparator.comparingInt(p -> p[1]));
        int[][] elsie = new int[pieNum][2];
        for (int p = 0; p < pieNum; p++) {
            elsie[p] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(elsie, Comparator.comparingInt(p -> p[0]));

        ArrayDeque<int[]> frontier = new ArrayDeque<>();
        // first row is for if bessie goes first, second is for if elsie goes first
        int[][] minPies = new int[2][pieNum];
        Arrays.fill(minPies[0], -1);
        Arrays.fill(minPies[1], -1);
        for (int i = 0; i < pieNum; i++) {
            if (bessie[i][1] == 0) {
                minPies[0][i] = 1;
                frontier.add(new int[] {0, i});
            }
            if (elsie[i][0] == 0) {
                minPies[1][i] = 1;
                frontier.add(new int[] {1, i});
            }
        }
        // we actually try to simulate the exchange in reverse
        while (!frontier.isEmpty()) {
            int[] curr = frontier.poll();
            int currCost = minPies[curr[0]][curr[1]];
            if (curr[0] == 0) {
                // binary search for all possible previous pies that could have resulted in this pie being given
                int prevMin = bisectLeft(elsie, bessie[curr[1]][0] - valueThreshold, 0);
                int prevMax = bisectRight(elsie, bessie[curr[1]][0], 0);
                for (int i = prevMin; i < prevMax; i++) {
                    if (minPies[1][i] == -1) {
                        minPies[1][i] = currCost + 1;
                        frontier.add(new int[] {1, i});
                    }
                }
            } else {
                int prevMin = bisectLeft(bessie, elsie[curr[1]][1] - valueThreshold, 1);
                int prevMax = bisectRight(bessie, elsie[curr[1]][1], 1);
                for (int i = prevMin; i < prevMax; i++) {
                    if (minPies[0][i] == -1) {
                        minPies[0][i] = currCost + 1;
                        frontier.add(new int[] {0, i});
                    }
                }
            }
        }

        int[] minExchanges = new int[pieNum];
        for (int p = 0; p < pieNum; p++) {
            minExchanges[bessie[p][2]] = minPies[0][p];
        }
        PrintWriter written = new PrintWriter("piepie.out");
        Arrays.stream(minExchanges).forEach(written::println);
        written.close();
        System.out.println(Arrays.toString(minExchanges));
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
