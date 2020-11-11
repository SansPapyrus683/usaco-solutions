import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class LightsOn {
    static int barnSide;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("lightson.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        barnSide = Integer.parseInt(initial.nextToken());
        int switchNum = Integer.parseInt(initial.nextToken());
        ArrayList<int[]>[][] switches = new ArrayList[barnSide][barnSide];
        for (int i = 0; i < barnSide; i++) {
            for (int j = 0; j < barnSide; j++) {
                switches[i][j] = new ArrayList<>();
            }
        }

        for (int s = 0; s < switchNum; s++) {
            int[] light = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            switches[light[0] - 1][light[1] - 1].add(new int[] {light[2] - 1, light[3] - 1});  // -1 bc 0-based
        }

        boolean[][] lit = new boolean[barnSide][barnSide];
        boolean[][] visited = new boolean[barnSide][barnSide];
        Queue<int[]> frontier = new ArrayDeque<>(Collections.singletonList(new int[] {0, 0}));
        lit[0][0] = true;
        visited[0][0] = true;
        while (!frontier.isEmpty()) {
            int[] current = frontier.poll();
            // turn on all the lights
            for (int[] light : switches[current[0]][current[1]]) {
                lit[light[0]][light[1]] = true;
                if (!visited[light[0]][light[1]]) {  // if we haven't gone to the light we just turned on, see if we can now
                    for (int[] ln : neighbors(light)) {
                        if (visited[ln[0]][ln[1]]) {
                            frontier.add(light);
                            visited[light[0]][light[1]] = true;  // if we don't mark it now, it never will
                            break;
                        }
                    }
                }
            }

            for (int[] n : neighbors(current)) {  // classic grid bfs
                if (lit[n[0]][n[1]] && !visited[n[0]][n[1]]) {
                    frontier.add(n);
                    visited[n[0]][n[1]] = true;
                }
            }
        }

        int litNum = 0;
        for (boolean[] row : lit) {
            for (boolean p : row) {
                litNum += p ? 1 : 0;
            }
        }

        PrintWriter written = new PrintWriter("lightson.out");
        written.println(litNum);
        written.close();
        System.out.println(litNum);
        System.out.printf("AAAAAAA IT TOOK %d MS%n", System.currentTimeMillis() - start);
    }

    static ArrayList<int[]> neighbors(int[] curr) {
        ArrayList<int[]> valid = new ArrayList<>();  // i've found that doing it this shameful way is faster
        if (curr[0] - 1 >= 0) {
            valid.add(new int[] {curr[0] - 1, curr[1]});
        }
        if (curr[0] + 1 < barnSide) {
            valid.add(new int[] {curr[0] + 1, curr[1]});
        }
        if (curr[1] - 1 >= 0) {
            valid.add(new int[] {curr[0], curr[1] - 1});
        }
        if (curr[1] + 1 < barnSide) {
            valid.add(new int[] {curr[0], curr[1] + 1});
        }
        return valid;
    }
}
