package official.o2020.dec.silver;

import java.io.*;
import java.util.*;

/**
 * 2020 dec silver
 * 4
 * 1 2
 * 1 3
 * 1 4 should output 5
 */
public class Cowntagion {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int farmNum  = Integer.parseInt(read.readLine());
        ArrayList<Integer>[] neighbors = new ArrayList[farmNum];
        for (int f = 0; f < farmNum; f++) {
            neighbors[f] = new ArrayList<>();
        }
        for (int i = 0; i < farmNum - 1; i++) {  // the thing is a tree if you didn't know alr
            StringTokenizer path = new StringTokenizer(read.readLine());
            int farm1 = Integer.parseInt(path.nextToken()) - 1;
            int farm2 = Integer.parseInt(path.nextToken()) - 1;
            neighbors[farm1].add(farm2);
            neighbors[farm2].add(farm1);
        }

        long start = System.currentTimeMillis();  // because it's stdin, i'm just going to neglect input parsing
        ArrayList<Integer>[] spreadTo = new ArrayList[farmNum];
        for (int f = 0; f < farmNum; f++) {
            spreadTo[f] = new ArrayList<>();
        }
        HashSet<Integer> visited = new HashSet<>(Collections.singletonList(0));
        ArrayDeque<Integer> frontier = new ArrayDeque<>(Collections.singletonList(0));
        while (!frontier.isEmpty()) {
            int current = frontier.poll();
            for (int n : neighbors[current]) {
                if (!visited.contains(n)) {
                    spreadTo[current].add(n);
                    visited.add(n);
                    frontier.add(n);
                }
            }
        }
        int days = 0;
        frontier = new ArrayDeque<>(Collections.singletonList(0));
        while (!frontier.isEmpty()) {
            int current = frontier.poll();
            ArrayList<Integer> nextUp = spreadTo[current];
            if (nextUp.isEmpty()) {
                continue;
            }
            frontier.addAll(nextUp);
            days += (int) Math.ceil(Math.log(nextUp.size() + 1) / Math.log(2)) + nextUp.size();
        }
        System.out.println(days);
        // i'm using System.err because it interferes with how like the grader thing works
        System.err.printf("i am listening to earrape whilst writing this: %d ms", System.currentTimeMillis() - start);
    }
}

