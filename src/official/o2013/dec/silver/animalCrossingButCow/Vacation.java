package official.o2013.dec.silver.animalCrossingButCow;

import java.io.*;
import java.util.*;

// 2013 dec silver
public final class Vacation {
    // i think this is a reasonable upper bound on a singular distance, right?
    private static final int INVALID = 420696969;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("vacation.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int farmNum = Integer.parseInt(initial.nextToken());
        int flightNum = Integer.parseInt(initial.nextToken());
        int hubs = Integer.parseInt(initial.nextToken());  // 0 to hubs - 1 are all hubs
        int queryNum = Integer.parseInt(initial.nextToken());
        
        ArrayList<int[]>[] neighbors = new ArrayList[farmNum];
        for (int f = 0; f < farmNum; f++) {
            neighbors[f] = new ArrayList<>();
        }
        for (int f = 0; f < flightNum; f++) {
            StringTokenizer flight = new StringTokenizer(read.readLine());
            neighbors[Integer.parseInt(flight.nextToken()) - 1].add(new int[] {
                    Integer.parseInt(flight.nextToken()) - 1, // destination
                    Integer.parseInt(flight.nextToken())  // cost
                });
        }

        int[][] distances = new int[farmNum][farmNum];
        for (int f = 0; f < farmNum; f++) {
            Arrays.fill(distances[f], INVALID);
        }
        // run ez floyd warshall (only 200 farms, so we can do this)
        for (int f = 0; f < farmNum; f++) {
            distances[f][f] = 0;
            for (int[] n : neighbors[f]) {
                distances[f][n[0]] = n[1];
            }
        }
        for (int i = 0; i < farmNum; i++) {
            for (int j = 0; j < farmNum; j++) {
                for (int k = 0; k < farmNum; k++) {
                    distances[j][k] = Math.min(distances[j][k], distances[j][i] + distances[i][k]);
                }
            }
        }

        int possible = 0;
        long total = 0;
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            int from = Integer.parseInt(query.nextToken()) - 1;
            int to = Integer.parseInt(query.nextToken()) - 1;
            int minDist = INVALID;
            for (int h = 0; h < hubs; h++) {  // brute force all possible hubs
                if (distances[from][h] < INVALID && distances[h][to] < INVALID) {
                    minDist = Math.min(minDist, distances[from][h] + distances[h][to]);
                }
            }
            if (minDist != INVALID) {
                total += minDist;
                possible++;
            }
        }
        read.close();  // just so vscode stops bugging me

        PrintWriter written = new PrintWriter("vacation.out");
        written.printf("%s%n%s%n", possible, total);
        written.close();
        System.out.println(possible + " " + total);
        System.out.printf("%d ms. ok now HELP I'M TRAPPED IN MY NEIGHBOR'S BASEME-%n", System.currentTimeMillis() - start);
    }
}
