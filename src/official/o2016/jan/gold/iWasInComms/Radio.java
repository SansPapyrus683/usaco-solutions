package official.o2016.jan.gold.iWasInComms;

import java.io.*;
import java.util.*;

// 2016 jan gold
public class Radio {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("radio.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int fjAmt = Integer.parseInt(initial.nextToken());
        int bessieAmt = Integer.parseInt(initial.nextToken());
        int[] fjPos = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] bessiePos = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        char[] fjMovements = read.readLine().toCharArray();
        char[] bessieMovements = read.readLine().toCharArray();
        assert fjPos.length == fjAmt && bessiePos.length == bessieAmt;

        int[][] fjAfter = new int[fjAmt + 1][2];  // precalculate the positions after each step in the movement
        int[][] bessieAfter = new int[bessieAmt + 1][2];
        fjAfter[0] = fjPos;
        bessieAfter[0] = bessiePos;
        for (int i = 1; i < fjAmt + 1; i++) {
            fjAfter[i] = move(fjAfter[i - 1], fjMovements[i - 1]);
        }
        for (int i = 1; i < bessieAmt + 1; i++) {
            bessieAfter[i] = move(bessieAfter[i - 1], bessieMovements[i - 1]);
        }

        // each of these costs is the minimum cost with the index i and j as the NEXT movement (@ end means completed)
        int[][] minBattery = new int[fjAmt + 1][bessieAmt + 1];
        for (int i = 0; i < fjAmt + 1; i++) {
            Arrays.fill(minBattery[i], Integer.MAX_VALUE);
        }
        minBattery[0][0] = 0;
        PriorityQueue<int[]> frontier = new PriorityQueue<>(Comparator.comparingInt(p -> minBattery[p[0]][p[1]]));
        frontier.add(new int[] {0, 0});
        while (!frontier.isEmpty()) {
            int[] curr = frontier.poll();
            int rnCost = minBattery[curr[0]][curr[1]];
            int newCost;
            // see all the states we can move to and also calculate if they're even worth it
            // fj moves up one
            if (curr[0] < fjAmt && 
                    (newCost = rnCost + batteryCost(fjAfter[curr[0] + 1], bessieAfter[curr[1]]))
                            < minBattery[curr[0] + 1][curr[1]]) {
                minBattery[curr[0] + 1][curr[1]] = newCost;
                frontier.add(new int[] {curr[0] + 1, curr[1]});
            }
            // bessie moves up one
            if (curr[1] < bessieAmt && 
                    (newCost = rnCost + batteryCost(fjAfter[curr[0]], bessieAfter[curr[1] + 1]))
                            < minBattery[curr[0]][curr[1] + 1]) {
                minBattery[curr[0]][curr[1] + 1] = newCost;
                frontier.add(new int[] {curr[0], curr[1] + 1});
            }
            // both move up one
            if (curr[0] < fjAmt && curr[1] < bessieAmt && 
                    (newCost = rnCost + batteryCost(fjAfter[curr[0] + 1], bessieAfter[curr[1] + 1]))
                            < minBattery[curr[0] + 1][curr[1] + 1]) {
                minBattery[curr[0] + 1][curr[1] + 1] = newCost;
                frontier.add(new int[] {curr[0] + 1, curr[1] + 1});
            }
        }

        PrintWriter written = new PrintWriter("radio.out");
        written.println(minBattery[fjAmt][bessieAmt]);
        written.close();
        System.out.println(minBattery[fjAmt][bessieAmt]);
        System.out.printf("GUYS I SWEAR I WAS IN COMMS DON'T THROW ME OUT IN %d MS PLZ%n", System.currentTimeMillis() - start);
    }

    static int batteryCost(int[] p1, int[] p2) {
        return (int) (Math.pow(p1[0] - p2[0], 2) + Math.pow(p1[1] - p2[1], 2));
    }

    static int[] move(int[] pos, char move) {
        int x = pos[0], y = pos[1];
        switch (move) {
            case 'N':
                y++;
                break;
            case 'S':
                y--;
                break;
            case 'E':
                x++;
                break;
            case 'W':
                x--;
                break;
        }
        return new int[] {x, y};
    }
}
