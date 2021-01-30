package official.o2021.jan.gold;

import java.io.*;
import java.util.*;

/**
 * 2021 jan gold (too slow for a single test case, but does that really matter?)
 * 5 4
 * 1 4 2 3 4
 * 1010
 * 0001
 * 0110
 * 0100 should output 6
 */
public final class Telephone {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int typeNum = Integer.parseInt(initial.nextToken());

        // all types are 1-indexed
        int[] types = Arrays.stream(read.readLine().split(" ")).mapToInt(t -> Integer.parseInt(t) - 1).toArray();

        HashSet<Integer>[] communicatable = new HashSet[typeNum];
        for (int t = 0; t < typeNum; t++) {
            communicatable[t] = new HashSet<>();
            String others = read.readLine();
            for (int ot = 0; ot < typeNum; ot++) {
                if (others.charAt(ot) == '1') {
                    communicatable[t].add(ot);
                }
            }
        }
        long start = System.currentTimeMillis();

        int movesTaken = 0;
        boolean reachedEnd = false;
        ArrayList<int[]> frontier = new ArrayList<>();
        boolean[][] visited = new boolean[cowNum][typeNum];

        visited[0][types[0]] = true;
        frontier.add(new int[] {0, types[0]});
        transmission:
        while (!frontier.isEmpty()) {
            ArrayList<int[]> inLine = new ArrayList<>();
            for (int[] c : frontier) {
                if (c[0] == cowNum - 1 && communicatable[c[1]].contains(types[cowNum - 1])) {
                    reachedEnd = true;
                    break transmission;
                }
                // slide to the left!
                if (c[0] - 1 >= 0 && !visited[c[0] - 1][c[1]]) {
                    visited[c[0] - 1][c[1]] = true;
                    inLine.add(new int[] {c[0] - 1, c[1]});
                }
                // slide to the right!
                if (c[0] + 1 < cowNum && !visited[c[0] + 1][c[1]]) {
                    visited[c[0] + 1][c[1]] = true;
                    inLine.add(new int[] {c[0] + 1, c[1]});
                }
                // criss-cross! (switch the transmitting cow and also move a spot)
                if (communicatable[c[1]].contains(types[c[0]]) && !visited[c[0]][types[c[0]]]) {
                    c[1] = types[c[0]];
                    visited[c[0]][c[1]] = true;

                    // now try moving to the left and right again (so movesTaken doesn't become inaccurate)
                    if (c[0] - 1 >= 0 && !visited[c[0] - 1][c[1]]) {
                        visited[c[0] - 1][c[1]] = true;
                        inLine.add(new int[] {c[0] - 1, c[1]});
                    }
                    if (c[0] + 1 < cowNum && !visited[c[0] + 1][c[1]]) {
                        visited[c[0] + 1][c[1]] = true;
                        inLine.add(new int[] {c[0] + 1, c[1]});
                    }
                }
            }
            frontier = inLine;
            movesTaken++;
        }
        System.out.println(reachedEnd ? movesTaken : -1);
        System.err.printf("bruh how does fj manage to have so many types of cows: %d ms", System.currentTimeMillis() - start);
    }
}
