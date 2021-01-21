package official.o2019.jan.gold.gluttony;

import java.io.*;
import java.util.*;

// 2019 jan gold
public class Shortcut {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("shortcut.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int fieldNum = Integer.parseInt(initial.nextToken());
        int pathNum = Integer.parseInt(initial.nextToken());
        int addedLen = Integer.parseInt(initial.nextToken());
        int[] cowAmts = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        ArrayList<int[]>[] neighbors = new ArrayList[fieldNum];
        for (int f = 0; f < fieldNum; f++) {
            neighbors[f] = new ArrayList<>();
        }
        for (int p = 0; p < pathNum; p++) {
            StringTokenizer path = new StringTokenizer(read.readLine());
            int from = Integer.parseInt(path.nextToken()) - 1;  // make the fields 0-indexed (so barn's at 0 now)
            int to = Integer.parseInt(path.nextToken()) - 1;
            int travelTime = Integer.parseInt(path.nextToken());
            neighbors[from].add(new int[] {to, travelTime});
            neighbors[to].add(new int[] {from, travelTime});
        }

        int[] minDist = new int[fieldNum];
        int[] cameFrom = new int[fieldNum];
        Arrays.fill(minDist, Integer.MAX_VALUE);
        minDist[0] = 0;
        Arrays.fill(cameFrom, -1);
        cameFrom[0] = 0;
        // use cost as the primary comparator, breaking ties with the lowest field num if they occur
        PriorityQueue<Integer> frontier = new PriorityQueue<>((a, b) -> minDist[a] != minDist[b] ? minDist[a] - minDist[b] : a - b);
        frontier.add(0);
        while (!frontier.isEmpty()) {
            int curr = frontier.poll();
            int rnCost = minDist[curr];
            for (int[] n : neighbors[curr]) {
                if (rnCost + n[1] < minDist[n[0]] || (rnCost + n[1] == minDist[n[0]]) && curr < cameFrom[n[0]]) {
                    minDist[n[0]] = rnCost + n[1];
                    cameFrom[n[0]] = curr;
                    frontier.add(n[0]);
                }
            }
        }

        long[] savedDist = new long[fieldNum];  // the distance we can save if we put a shortcut at field i
        for (int f = 1; f < fieldNum; f++) {
            int at = f;
            while (at != 0) {
                int timeLeft = minDist[at];  // if we're at this field, we still have to travel this much
                if (addedLen < timeLeft) {
                    // if the cows'll actually take this, we add the dist saved to the total
                    savedDist[at] += (long) cowAmts[f] * (timeLeft - addedLen);
                }
                at = cameFrom[at];
            }
        }

        long mostSaved = Arrays.stream(savedDist).max().getAsLong();
        PrintWriter written = new PrintWriter("shortcut.out");
        written.println(mostSaved);
        written.close();
        System.out.println(mostSaved);
        System.out.printf("how do the cows have such good hearing lol: %d ms%n", System.currentTimeMillis() - start);
    }
}
