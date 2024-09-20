package official.o2023.dec.gold;

import java.io.*;
import java.util.*;

/** 2023 dec gold */
public class MinLongestTrip {
    private static class Road {
        public int dest;
        public int label;

        public Road(int dest, int label) {
            this.dest = dest;
            this.label = label;
        }
    }

    private static class PathInfo {
        public int len;
        public List<Road> canTake;
        public long pathSum = 0;

        public PathInfo(int len, List<Road> canTake) {
            this.len = len;
            this.canTake = canTake;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int townNum = Integer.parseInt(initial.nextToken());
        int roadNum = Integer.parseInt(initial.nextToken());
        List<Road>[] neighbors = new ArrayList[townNum];
        List<Road>[] revNeighbors = new ArrayList[townNum];
        for (int t = 0; t < townNum; t++) {
            neighbors[t] = new ArrayList<>();
            revNeighbors[t] = new ArrayList<>();
        }
        int[] inDeg = new int[townNum];
        for (int r = 0; r < roadNum; r++) {
            StringTokenizer road = new StringTokenizer(read.readLine());
            int from = Integer.parseInt(road.nextToken()) - 1;
            int to = Integer.parseInt(road.nextToken()) - 1;
            int label = Integer.parseInt(road.nextToken());
            neighbors[from].add(new Road(to, label));
            revNeighbors[to].add(new Road(from, label));
            inDeg[to]++;
        }

        List<Integer> todo = new ArrayList<>();
        for (int t = 0; t < townNum; t++) {
            if (inDeg[t] == 0) {
                todo.add(t);
            }
        }
        List<Integer> topoOrder = new ArrayList<>();
        while (!todo.isEmpty()) {
            int curr = todo.remove(todo.size() - 1);
            topoOrder.add(curr);
            for (Road r : neighbors[curr]) {
                if (--inDeg[r.dest] == 0) {
                    todo.add(r.dest);
                }
            }
        }
        Collections.reverse(topoOrder);

        PathInfo[] longest = new PathInfo[townNum];
        List<Integer>[] pathLens = new List[townNum];
        for (int t = 0; t < townNum; t++) {
            longest[t] = new PathInfo(0, new ArrayList<>());
            pathLens[t] = new ArrayList<>();
        }
        for (int t : topoOrder) {
            pathLens[longest[t].len].add(t);
            for (Road n : revNeighbors[t]) {
                int newLen = longest[t].len + 1;
                Road road = new Road(t, n.label);
                if (newLen > longest[n.dest].len) {
                    longest[n.dest] =
                            new PathInfo(newLen, new ArrayList<>(Collections.singleton(road)));
                } else if (newLen == longest[n.dest].len) {
                    longest[n.dest].canTake.add(road);
                }
            }
        }

        Map<Integer, Integer> order = new HashMap<>();
        for (int len = 1; len < townNum; len++) {
            int finalLen = len;
            for (int t : pathLens[len]) {
                longest[t].canTake.sort(
                        (t1, t2) -> {
                            if (t1.label != t2.label || finalLen == 1) {
                                return t1.label - t2.label;
                            }
                            return order.get(t1.dest) - order.get(t2.dest);
                        });
                Road toTake = longest[t].canTake.get(0);
                longest[t].pathSum = toTake.label + longest[toTake.dest].pathSum;
            }

            pathLens[len].sort(
                    (t1, t2) -> {
                        Road p1 = longest[t1].canTake.get(0);
                        Road p2 = longest[t2].canTake.get(0);
                        return p1.label != p2.label || finalLen == 1
                                ? p1.label - p2.label
                                : order.get(p1.dest) - order.get(p2.dest);
                    });
            for (int i = 0; i < pathLens[len].size(); i++) {
                order.put(pathLens[len].get(i), i);
            }
        }

        for (PathInfo pi : longest) {
            System.out.println(pi.len + " " + pi.pathSum);
        }
    }
}
