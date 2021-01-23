package official.o2020.dec.silver.karenCows;

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

        int minDays = 0;
        boolean[] visited = new boolean[farmNum];
        ArrayDeque<Integer> frontier = new ArrayDeque<>(Collections.singletonList(0));
        visited[0] = true;
        while (!frontier.isEmpty()) {
            int current = frontier.poll();
            // this stores the # of farms that this farm should spread to
            int spreadTo = 0;
            for (int n : neighbors[current]) {
                if (!visited[n]) {
                    spreadTo++;
                    visited[n] = true;
                    frontier.add(n);
                }
            }
            // the log base 2 is so enough superspreader events go around so that there are enough cows, and then we need to add the length of spreadTo so the cows can actually go to the other farms
            minDays += ceilLog2(spreadTo + 1) + spreadTo;
        }

        System.out.println(minDays);
        // i'm using System.err because using stdout interferes with how like the grader thing works
        System.err.printf("i am listening to earrape whilst writing this: %d ms", System.currentTimeMillis() - start);
    }

    // returns the smallest x such that 2^x >= n
    private static int ceilLog2(int n) { 
        int count = 0;
        int so_far = 1;  
        while (so_far < n) { 
            so_far *= 2;
            count++;
        }
        return count;
    }
}
