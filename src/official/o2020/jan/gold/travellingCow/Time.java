package official.o2020.jan.gold.travellingCow;

import java.io.*;
import java.util.*;

// 2020 jan gold
public final class Time {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("time.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cityNum = Integer.parseInt(initial.nextToken());
        int roadNum = Integer.parseInt(initial.nextToken());
        int costFactor = Integer.parseInt(initial.nextToken());
        int[] money = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        ArrayList<Integer>[] neighbors = new ArrayList[cityNum];
        for (int c = 0; c < cityNum; c++) {
            neighbors[c] = new ArrayList<>();
        }
        for (int r = 0; r < roadNum; r++) {
            StringTokenizer road = new StringTokenizer(read.readLine());
            int from = Integer.parseInt(road.nextToken()) - 1;
            int to = Integer.parseInt(road.nextToken()) - 1;
            neighbors[from].add(to);
        }

        int cityBestPay = Arrays.stream(money).max().getAsInt();
        int maxMoney = 0;
        int[] mostMoney = new int[cityNum];
        int cost;
        HashSet<Integer> frontier = new HashSet<>(Collections.singletonList(0));
        /*
         * only travel when the cost is "reasonable"
         * when the for loop hits it's end, that means that even if
         * we hit the best city over and over again, it wouldn't work
         * (days starts at 1 to make the calculations easier)
         */
        for (int days = 1; (cost = costFactor * days * days) <= cityBestPay * days; days++) {
            HashSet<Integer> inLine = new HashSet<>();
            int[] updatedMoney = new int[cityNum];
            for (int c : frontier) {
                for (int n : neighbors[c]) {
                    updatedMoney[n] = Math.max(updatedMoney[n], mostMoney[c] + money[n]);
                    inLine.add(n);
                }
            }
            maxMoney = Math.max(maxMoney, updatedMoney[0] - cost);  // remember we have to end @ the first one
            mostMoney = updatedMoney;
            frontier = inLine;
        }

        PrintWriter written = new PrintWriter("time.out");
        written.println(maxMoney);
        written.close();
        System.out.println(maxMoney);
        System.out.printf("what kind of business would a COW have: %d ms%n", System.currentTimeMillis() - start);
    }
}

