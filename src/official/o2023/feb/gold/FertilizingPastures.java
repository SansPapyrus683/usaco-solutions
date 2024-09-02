package official.o2023.feb.gold;

import java.io.*;
import java.util.*;

public class FertilizingPastures {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int pastureNum = Integer.parseInt(initial.nextToken());
        boolean mustLoop = Integer.parseInt(initial.nextToken()) == 0;
        List<Integer>[] neighbors = new ArrayList[pastureNum];
        for (int p = 0; p < pastureNum; p++) {
            neighbors[p] = new ArrayList<>();
        }
        int[] growthRates = new int[pastureNum];
        for (int p = 1; p < pastureNum; p++) {
            StringTokenizer path = new StringTokenizer(read.readLine());
            int to = Integer.parseInt(path.nextToken()) - 1;
            int growth = Integer.parseInt(path.nextToken());
            growthRates[p] = growth;
            neighbors[to].add(p);
            neighbors[p].add(to);
        }

        Pastures pastures = new Pastures(neighbors, growthRates, 0);
        System.out.println(pastures.minTime(mustLoop) + " " + pastures.minFertilizer(mustLoop));
    }
}

class Pastures {
    private final List<Integer>[] neighbors;
    private final int[] growthRates;
    private final int start;

    private final int[] subSizes;
    private final int[] deepest;
    private final long[] subSums;
    private final long[][] minFertilizer;

    public Pastures(List<Integer>[] neighbors, int[] growthRates, int start) {
        this.neighbors = neighbors;
        this.growthRates = growthRates;
        this.start = start;

        this.subSizes = new int[neighbors.length];
        this.deepest = new int[neighbors.length];
        this.subSums = new long[neighbors.length];
        this.minFertilizer = new long[neighbors.length][2];
        processPastures(start, -1, 0);
    }

    private void processPastures(int at, int prev, int depth) {
        subSums[at] = growthRates[at];
        subSizes[at] = 1;
        deepest[at] = depth;
        List<Integer> kids = new ArrayList<>();
        for (int n : neighbors[at]) {
            if (n != prev) {
                processPastures(n, at, depth + 1);
                subSums[at] += subSums[n];
                subSizes[at] += subSizes[n];
                kids.add(n);
                deepest[at] = Math.max(deepest[at], deepest[n]);
            }
        }
        if (kids.isEmpty()) {
            return;
        }

        // WHAT?????????
        kids.sort(Comparator.comparingDouble(k -> -(double) subSums[k] / subSizes[k]));
        int time = 1;
        long[] sumSuff = new long[kids.size()];
        int ind = 0;
        for (int k : kids) {
            sumSuff[ind++] = subSums[k];
            minFertilizer[at][0] += minFertilizer[k][0] + subSums[k] * time;
            time += (subSizes[k] - 1) * 2 + 2;
        }
        for (int i = kids.size() - 2; i >= 0; i--) {
            sumSuff[i] += sumSuff[i + 1];
        }

        minFertilizer[at][1] = Long.MAX_VALUE;
        int finalTime = time;
        time = 1;
        for (int i = 0; i < kids.size(); i++) {
            int k = kids.get(i);
            int travTime = (subSizes[k] - 1) * 2 + 2;
            if (deepest[k] != deepest[at]) {
                time += travTime;
                continue;
            }
            long val = minFertilizer[at][0] - minFertilizer[k][0] + minFertilizer[k][1];
            val -= travTime * (sumSuff[i] - subSums[k]) + subSums[k] * time;
            val += (finalTime - travTime) * subSums[k];
            minFertilizer[at][1] = Math.min(minFertilizer[at][1], val);
            time += travTime;
        }
    }

    public int minTime(boolean mustLoop) {
        int rawAmt = 2 * (subSizes[start] - 1);
        return mustLoop ? rawAmt : rawAmt - Arrays.stream(deepest).max().getAsInt();
    }

    public long minFertilizer(boolean mustLoop) {
        return minFertilizer[start][mustLoop ? 0 : 1];
    }
}
