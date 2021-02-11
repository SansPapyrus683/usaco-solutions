package official.o2018.jan.gold.bessieTaxFraud;

import java.io.*;
import java.util.*;

// 2018 jan gold
public final class AtLarge {
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("atlarge.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int farmNum = Integer.parseInt(initial.nextToken());
        int start = Integer.parseInt(initial.nextToken()) - 1;  // we're gonna root the tree @ the start
        ArrayList<Integer>[] neighbors = new ArrayList[farmNum];
        for (int f = 0; f < farmNum; f++) {
            neighbors[f] = new ArrayList<>();
        }
        for (int i = 0; i < farmNum - 1; i++) {
            StringTokenizer path = new StringTokenizer(read.readLine());
            int from = Integer.parseInt(path.nextToken()) - 1;
            int to = Integer.parseInt(path.nextToken()) - 1;
            neighbors[from].add(to);
            neighbors[to].add(from);
        }

        // first, just get the depths of each farm and the parents of each farm
        int[] depth = new int[farmNum];
        int[] parent = new int[farmNum];
        parent[start] = start;
        ArrayDeque<Integer> frontier = new ArrayDeque<>(Collections.singletonList(start));
        while (!frontier.isEmpty()) {
            int curr = frontier.poll();
            for (int n : neighbors[curr]) {
                if (n != parent[curr]) {
                    parent[n] = curr;
                    depth[n] = depth[curr] + 1;
                    frontier.add(n);
                }
            }
        }

        // get the exit farms and organize them by depth
        HashMap<Integer, ArrayList<Integer>> exits = new HashMap<>();
        for (int f = 0; f < farmNum; f++) {
            if (neighbors[f].size() != 1) {
                continue;
            }
            if (!exits.containsKey(depth[f])) {
                exits.put(depth[f], new ArrayList<>());
            }
            exits.get(depth[f]).add(f);
        }

        int currDepth = Arrays.stream(depth).max().getAsInt();
        /*
         * the farm, the depth of the closest exit barn,
         * and the amount of farmers needed to cover everything below and including that farm
         */
        ArrayList<int[]> currLevel = new ArrayList<>();
        for (int f : exits.get(currDepth)) {
            currLevel.add(new int[] {f, currDepth, 1});
        }
        while (currDepth >= 0) {
            currDepth--;
            for (int[] f : currLevel) {
                f[0] = parent[f[0]];
            }

            ArrayList<int[]> nextLevel = new ArrayList<>();
            // group all the farms that got merged together
            currLevel.sort(Comparator.comparingInt(f -> f[0]));
            ArrayList<int[]> merging = new ArrayList<>(Collections.singletonList(currLevel.get(0)));
            for (int i = 1; i < currLevel.size(); i++) {
                if (currLevel.get(i - 1)[0] != currLevel.get(i)[0]) {
                    nextLevel.add(merge(merging, currDepth));
                    merging = new ArrayList<>(Collections.singletonList(currLevel.get(i)));
                } else {
                    merging.add(currLevel.get(i));
                }
            }
            // add the new farms that had just met the depth requirement
            nextLevel.add(merge(merging, currDepth));
            for (int f : exits.getOrDefault(currDepth, new ArrayList<>())) {
                nextLevel.add(new int[] {f, currDepth, 1});
            }
            currLevel = nextLevel;
        }

        int minCops = currLevel.get(0)[2];
        PrintWriter written = new PrintWriter("atlarge.out");
        written.println(minCops);
        written.close();
        System.out.println(minCops);
        System.out.printf("bessie is wanted for numerous counts of tax fraud: %d ms%n", System.currentTimeMillis() - timeStart);
    }

    // assuming the first elements are all the same and the depth is the current depth of that farm
    private static int[] merge(List<int[]> incoming, int currDepth) {
        if (incoming.size() == 1) {  // just some constant factor optimization
            return incoming.get(0);
        }
        // the same farm, the minimum depth, and then the default is the sum of all the incoming farms
        int[] merged = new int[] {incoming.get(0)[0], incoming.stream().mapToInt(f -> f[1]).min().getAsInt(), incoming.stream().mapToInt(f -> f[2]).sum()};
        for (int[] f : incoming) {
            // if the closest farmer can reach the merging point before bessie, we only need 1
            if (f[1] - currDepth <= currDepth) {
                merged[2] = 1;
                break;
            }
        }
        return merged;
    }
}
