package official.o2014.feb.silver.evilCows;

import java.io.*;
import java.util.*;

// 2014 feb silver (or feb gold, both are the same i think)
public final class RBlock {
    static int fieldNum;
    static ArrayList<Integer>[] neighbors;
    static int[][] lengths;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("rblock.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        fieldNum = Integer.parseInt(initial.nextToken());
        neighbors = new ArrayList[fieldNum];
        lengths = new int[fieldNum][fieldNum];
        int pathNum = Integer.parseInt(initial.nextToken());

        for (int n = 0; n < fieldNum; n++) {
            neighbors[n] = new ArrayList<>();
        }
        for (int p = 0; p < pathNum; p++) {
            StringTokenizer path = new StringTokenizer(read.readLine());
            int field1 = Integer.parseInt(path.nextToken()) - 1, field2 = Integer.parseInt(path.nextToken()) - 1;
            int length = Integer.parseInt(path.nextToken());
            neighbors[field1].add(field2);  // paths are bidirectional i mean
            neighbors[field2].add(field1);
            lengths[field1][field2] = lengths[field2][field1] = length;  // invalid lengths are just 0 but that doesn't matter
        }

        // the algo's philosophy is that i mean it only makes sense for the cows to build on fj's original shortest path
        ArrayList<Integer> path = path(0, fieldNum - 1);
        int maxDetour = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            // apply the haybale building (the other line just reverts it)
            lengths[path.get(i)][path.get(i + 1)] = lengths[path.get(i + 1)][path.get(i)] = lengths[path.get(i)][path.get(i + 1)] * 2;
            maxDetour = Math.max(maxDetour, distance(0, fieldNum - 1));  // calc distance with this haybale
            lengths[path.get(i)][path.get(i + 1)] = lengths[path.get(i + 1)][path.get(i)] = lengths[path.get(i)][path.get(i + 1)] / 2;
        }
        int originalLength = distance(0, fieldNum - 1);  // yes ik i calculated it previously

        PrintWriter written = new PrintWriter(new FileOutputStream("rblock.out"));
        written.println(maxDetour - originalLength);
        written.close();
        System.out.println(maxDetour - originalLength);
        System.out.printf("hahahahahahaha it took %s ms you slow frick%n", System.currentTimeMillis() - start);
    }

    static ArrayList<Integer> path(int start, int end) {
        int[] distances = new int[fieldNum];
        PriorityQueue<Integer> frontier = new PriorityQueue<>(Comparator.comparingInt(f -> distances[f]));
        int[] cameFrom = new int[fieldNum];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;
        cameFrom[start] = 0;
        frontier.add(start);
        while (!frontier.isEmpty()) {
            int current = frontier.poll();
            if (current == end) {
                break;
            }
            int rnCost = distances[current];
            for (int n : neighbors[current]) {
                if (rnCost + lengths[current][n] < distances[n]) {
                    distances[n] = rnCost + lengths[current][n];
                    cameFrom[n] = current;
                    frontier.add(n);
                }
            }
        }

        int at = fieldNum - 1;
        ArrayList<Integer> path = new ArrayList<>(Collections.singletonList(at));
        while (at != start) {
            at = cameFrom[at];
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    static int distance(int start, int end) {
        int[] distances = new int[fieldNum];
        PriorityQueue<Integer> frontier = new PriorityQueue<>(Comparator.comparingInt(f -> distances[f]));
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;
        frontier.add(start);
        while (!frontier.isEmpty()) {
            int current = frontier.poll();
            if (current == end) {
                return distances[current];
            }
            int rnCost = distances[current];
            for (int n : neighbors[current]) {
                if (rnCost + lengths[current][n] < distances[n]) {
                    distances[n] = rnCost + lengths[current][n];
                    frontier.add(n);
                }
            }
        }
        return -1;
    }
}
