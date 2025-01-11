package official.o2023.usopen.gold;

import java.io.*;
import java.util.*;

/** 2023 us open gold (can't believe this works haha) */
public class CustodialCleanup {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            read.readLine();  // i get pissed off by this every time istg
            StringTokenizer initial = new StringTokenizer(read.readLine());
            int roomNum = Integer.parseInt(initial.nextToken());
            int edgeNum = Integer.parseInt(initial.nextToken());
            int[] colors = Arrays.stream(read.readLine().split(" "))
                    .mapToInt(c -> Integer.parseInt(c) - 1).toArray();
            int[] keys = Arrays.stream(read.readLine().split(" "))
                    .mapToInt(k -> Integer.parseInt(k) - 1).toArray();
            int[] reqs = Arrays.stream(read.readLine().split(" "))
                    .mapToInt(k -> Integer.parseInt(k) - 1).toArray();

            List<Integer>[] neighbors = new List[roomNum];
            for (int r = 0; r < roomNum; r++) {
                neighbors[r] = new ArrayList<>();
            }
            for (int e = 0; e < edgeNum; e++) {
                StringTokenizer edge = new StringTokenizer(read.readLine());
                int from = Integer.parseInt(edge.nextToken()) - 1;
                int to = Integer.parseInt(edge.nextToken()) - 1;
                neighbors[from].add(to);
                neighbors[to].add(from);
            }

            boolean[] vis1 = canVisit(neighbors, colors, keys, false);
            int[] canColors = colors.clone();
            for (int r = 0; r < roomNum; r++) {
                if (!vis1[r]) {
                    canColors[r] = -1;
                }
            }
            boolean[] vis2 = canVisit(neighbors, canColors, reqs, true);

            int[] freq = new int[roomNum];
            boolean valid = true;
            for (int r = 0; r < roomNum; r++) {
                if (vis1[r] && vis2[r]) {
                    freq[keys[r]]++;
                    freq[reqs[r]]--;
                } else {
                    valid = valid && keys[r] == reqs[r];
                }
            }
            valid = valid && Arrays.stream(freq).allMatch(i -> i == 0);

            System.out.println(valid ? "YES" : "NO");
        }
    }

    private static boolean[] canVisit(
            List<Integer>[] neighbors, int[] colors, int[] keys, boolean mode
    ) {
        Map<Integer, List<Integer>> cRooms = new HashMap<>();
        for (int     r = 0; r < neighbors.length; r++) {
            cRooms.putIfAbsent(colors[r], new ArrayList<>());
            cRooms.get(colors[r]).add(r);
        }

        Map<Integer, Integer> fjHas = new HashMap<>();
        boolean[] visited = new boolean[neighbors.length];
        boolean[] coulda = new boolean[neighbors.length];
        visited[0] = true;
        Deque<Integer> frontier = new ArrayDeque<>(List.of(0));
        while (!frontier.isEmpty()) {
            int curr = frontier.poll();
            int newVal = fjHas.getOrDefault(keys[curr], 0) + 1;
            fjHas.put(keys[curr], newVal);
            for (int n : neighbors[curr]) {
                if (visited[n]) {
                    continue;
                }
                if (fjHas.containsKey(colors[n]) || (mode && colors[n] == keys[n])) {
                    visited[n] = true;
                    frontier.add(n);
                } else {
                    coulda[n] = true;
                }
            }

            if (newVal == 1) {
                for (int n : cRooms.getOrDefault(keys[curr], new ArrayList<>())) {
                    if (!visited[n] && coulda[n]) {
                        visited[n] = true;
                        frontier.add(n);
                    }
                }
            }
        }

        return visited;
    }
}
