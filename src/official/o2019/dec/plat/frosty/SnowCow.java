package official.o2019.dec.plat.frosty;

import java.io.*;
import java.util.*;

// 2019 dec plat
public final class SnowCow {
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("snowcow.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int snowballNum = Integer.parseInt(initial.nextToken());
        int queryNum = Integer.parseInt(initial.nextToken());

        ArrayList<Integer>[] neighbors = new ArrayList[snowballNum];
        for (int sb = 0; sb < snowballNum; sb++) {
            neighbors[sb] = new ArrayList<>();
        }
        for (int b = 0; b < snowballNum - 1; b++) {
            StringTokenizer branch = new StringTokenizer(read.readLine());
            int sb1 = Integer.parseInt(branch.nextToken()) - 1;
            int sb2 = Integer.parseInt(branch.nextToken()) - 1;
            neighbors[sb1].add(sb2);
            neighbors[sb2].add(sb1);
        }

        boolean[] processed = new boolean[snowballNum];
        int[] start = new int[snowballNum];
        int[] end = new int[snowballNum];
        int timer = 0;
        ArrayDeque<Integer> frontier = new ArrayDeque<>(Collections.singletonList(0));
        while (!frontier.isEmpty()) {
            int curr = frontier.removeLast();
            if (processed[curr]) {
                end[curr] = timer;
                timer++;
                continue;
            }
            start[curr] = timer;
            frontier.add(curr);  // set a marker to record the outtime
            processed[curr] = true;
            for (int n : neighbors[curr]) {
                if (!processed[n]) {
                    frontier.add(n);
                }
            }
            timer++;
        }

        int[] subSize = new int[snowballNum];
        for (int s = 0; s < snowballNum; s++) {
            subSize[s] = (end[s] - start[s] + 1) / 2;
        }

        Map<Integer, TreeMap<Integer, Integer>> colors = new HashMap<>();
        BITree aboveUnique = new BITree(snowballNum * 2);
        BITree belowUnique = new BITree(snowballNum * 2);
        StringBuilder ans = new StringBuilder();
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            int type = Integer.parseInt(query.nextToken());
            int sb = Integer.parseInt(query.nextToken()) - 1;  // snowball
            if (type == 1) {
                int color = Integer.parseInt(query.nextToken());
                if (!colors.containsKey(color)) {
                    colors.put(color, new TreeMap<>());
                }

                TreeMap<Integer, Integer> painted = colors.get(color);
                Map.Entry<Integer, Integer> lClosest = painted.floorEntry(start[sb]);
                if (lClosest != null && end[sb] <= end[lClosest.getValue()]) {
                    continue;
                }
                while (true) {
                    Integer rNext = painted.higherKey(start[sb]);
                    if (rNext == null || end[sb] <= end[painted.get(rNext)]) {
                        break;
                    }
                    int rNextSB = painted.get(rNext);
                    aboveUnique.increment(start[rNextSB], -1);
                    aboveUnique.increment(end[rNextSB], 1);
                    belowUnique.increment(start[rNextSB], -subSize[rNextSB]);
                    painted.remove(rNext);
                }
                painted.put(start[sb], sb);
                aboveUnique.increment(start[sb], 1);
                aboveUnique.increment(end[sb], -1);
                belowUnique.increment(start[sb], subSize[sb]);
            } else if (type == 2) {
                long aboveColors = subSize[sb] * aboveUnique.query(start[sb]);
                long belowColors = belowUnique.query(end[sb]) - belowUnique.query(start[sb]);
                ans.append(aboveColors + belowColors).append('\n');
            }
        }

        PrintWriter written = new PrintWriter("snowcow.out");
        written.print(ans);
        written.close();
        System.out.print(ans);

        System.out.printf("time: %d ms%n", System.currentTimeMillis() - timeStart);
    }
}

class BITree {
    private final long[] treeThing;
    private final int size;

    public BITree(int size) {
        treeThing = new long[size + 1];
        this.size = size;
    }

    public void increment(int updateAt, long val) {
        updateAt++;  // have the driver code not worry about 1-indexing
        for (; updateAt <= size; updateAt += updateAt & -updateAt) {
            treeThing[updateAt] += val;
        }
    }

    public long query(int ind) {  // the bound's inclusive
        ind++;
        long sum = 0;
        for (; ind > 0; ind -= ind & -ind) {
            sum += treeThing[ind];
        }
        return sum;
    }
}
