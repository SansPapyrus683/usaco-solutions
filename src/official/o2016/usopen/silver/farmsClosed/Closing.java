package official.o2016.usopen.silver.farmsClosed;

import java.io.*;
import java.util.*;

// 2016 usopen silver (this solution sometimes times out, sometimes it doesn't, depends on your luck)
public final class Closing {
    static boolean[] visited;  // most definitely bad practice, but it probably (?) makes it faster
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("closing.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int barnNum = Integer.parseInt(initial.nextToken());
        int pathNum = Integer.parseInt(initial.nextToken());
        visited = new boolean[barnNum];
        HashSet<Integer>[] paths = new HashSet[barnNum];  // hashset bc it removes faster
        for (int b = 0; b < barnNum; b++) {
            paths[b] = new HashSet<>();
        }
        for (int p = 0; p < pathNum; p++) {
            StringTokenizer path = new StringTokenizer(read.readLine());
            int b1 = Integer.parseInt(path.nextToken()) - 1;
            int b2 = Integer.parseInt(path.nextToken()) - 1;
            paths[b1].add(b2);
            paths[b2].add(b1);
        }

        PrintWriter written = new PrintWriter("closing.out");
        HashSet<Integer> barnsLeft = new HashSet<>();
        for (int b = 0; b < barnNum; b++) {
            barnsLeft.add(b);
        }
        for (int b = 0; b < barnNum; b++) {
            boolean connected = connected(paths, barnsLeft);
            written.println(connected ? "YES" : "NO");  // again, no sout bc of the huge output amt

            int toRemove = Integer.parseInt(read.readLine()) - 1;
            barnsLeft.remove(toRemove);
            for (HashSet<Integer> p : paths) {  // just remove all references to the closed barn
                p.remove(toRemove);
            }
        }
        written.close();
        System.out.printf("sir, i don't know how, but your code finished in %d ms.%n", System.currentTimeMillis() - start);
    }

    static boolean connected(HashSet<Integer>[] paths, HashSet<Integer> allNodes) {
        int randomStart = allNodes.iterator().next();
        ArrayDeque<Integer> frontier = new ArrayDeque<>(Collections.singletonList(randomStart));
        Arrays.fill(visited, false);
        visited[randomStart] = true;
        while (!frontier.isEmpty()) {  // do a simple bfs to see if it's connected
            int current = frontier.poll();
            for (int n : paths[current]) {
                if (!visited[n]) {
                    frontier.add(n);
                    visited[n] = true;
                }
            }
        }

        for (int n : allNodes) {  // check if all the nodes were visited -> graph's connectivity
            if (!visited[n]) {
                return false;
            }
        }
        return true;
    }
}
