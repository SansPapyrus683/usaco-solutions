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

    private static class PastureData {
        public long[] minFertilizer;
        public long fertSum;
        public int subPastureNum;
        public int deepest;

        public PastureData(long[] minFertilizer, long fertSum, int subPastureNum, int deepest) {
            this.minFertilizer = minFertilizer;
            this.fertSum = fertSum;
            this.subPastureNum = subPastureNum;
            this.deepest = deepest;
        }
    }
    private final PastureData rootData;

    public Pastures(List<Integer>[] neighbors, int[] growthRates, int start) {
        this.neighbors = neighbors;
        this.growthRates = growthRates;

        rootData = processPastures(start, -1, 0);
    }

    private PastureData processPastures(int at, int prev, int depth) {
        PastureData pasture = new PastureData(new long[2], growthRates[at], 1, depth);
        List<PastureData> kids = new ArrayList<>();
        for (int n : neighbors[at]) {
            if (n != prev) {
                PastureData kid = processPastures(n, at, depth + 1);
                pasture.fertSum += kid.fertSum;
                pasture.subPastureNum += kid.subPastureNum;
                pasture.deepest = Math.max(pasture.deepest, kid.deepest);
                kids.add(kid);
            }
        }
        if (kids.isEmpty()) {
            return pasture;
        }

        // WHAT?????????
        kids.sort(Comparator.comparingDouble(k -> -(double) k.fertSum / k.subPastureNum));
        int time = 1;
        long[] sumSuff = new long[kids.size()];
        int ind = 0;
        for (PastureData k : kids) {
            sumSuff[ind++] = k.fertSum;
            pasture.minFertilizer[0] += k.minFertilizer[0] + k.fertSum * time;
            time += (k.subPastureNum - 1) * 2 + 2;
        }
        for (int i = kids.size() - 2; i >= 0; i--) {
            sumSuff[i] += sumSuff[i + 1];
        }

        pasture.minFertilizer[1] = Long.MAX_VALUE;
        int finalTime = time;
        time = 1;
        for (int i = 0; i < kids.size(); i++) {
            PastureData k = kids.get(i);
            int travTime = (k.subPastureNum - 1) * 2 + 2;
            if (k.deepest != pasture.deepest) {
                time += travTime;
                continue;
            }
            long val = pasture.minFertilizer[0] - k.minFertilizer[0] + k.minFertilizer[1];
            val -= travTime * (sumSuff[i] - k.fertSum) + k.fertSum * time;
            val += (finalTime - travTime) * k.fertSum;
            pasture.minFertilizer[1] = Math.min(pasture.minFertilizer[1], val);
            time += travTime;
        }

        return pasture;
    }

    public int minTime(boolean mustLoop) {
        int rawAmt = 2 * (rootData.subPastureNum - 1);
        return mustLoop ? rawAmt : rawAmt - rootData.deepest;
    }

    public long minFertilizer(boolean mustLoop) {
        return rootData.minFertilizer[mustLoop ? 0 : 1];
    }
}
