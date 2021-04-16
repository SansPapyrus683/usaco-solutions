package official.o2013.usopen.silver.beefalosBizarreAdventures;

import java.io.*;
import java.util.*;

/**
 * 2013 usopen silver
 * so the way the coordinates are represented is kinda weird
 * it's (y, x) and not (x, y), and the y coordinate increasing is actually going down
 * that's because of stupid array indexing shenanigans
 */
public final class Gravity {
    private static final char WALL = '#';
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("gravity.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int rowNum = Integer.parseInt(initial.nextToken());
        int colNum = Integer.parseInt(initial.nextToken());
        char[][] grid = new char[rowNum][colNum];
        // row position, column position, and what gravity we're at (0 = normal, 1 = flipped)
        int[] capState = new int[] {-1, -1, 0};
        int[] docPos = new int[] {-1, -1};
        for (int r = 0; r < rowNum; r++) {
            int col = 0;
            for (char c : read.readLine().toUpperCase().toCharArray()) {
                if (c == 'C') {
                    capState = new int[] {0, r, col, 0};
                } else if (c == 'D') {
                    docPos = new int[] {r, col};
                }
                grid[r][col++] = c;
            }
        }

        int[][][] minFlips = new int[rowNum][colNum][2];
        for (int r = 0; r < rowNum; r++) {
            for (int c = 0; c < colNum; c++) {
                Arrays.fill(minFlips[r][c], Integer.MAX_VALUE);
            }
        }
        minFlips[capState[1]][capState[2]][capState[3]] = 0;
        PriorityQueue<int[]> frontier = new PriorityQueue<>(Comparator.comparingInt(s -> s[0]));
        frontier.add(capState);
        while (!frontier.isEmpty()) {
            int[] curr = frontier.poll();
            int rnCost = minFlips[curr[1]][curr[2]][curr[3]];
            if (curr[0] != rnCost) {
                continue;
            }
            curr = Arrays.copyOfRange(curr, 1, curr.length);

            // apply the gravity for this square, sort of "distributing" the costs across all the squares
            if (curr[2] == 0) {  // gravity's normal, start falling down
                while (curr[0] + 1 < rowNum && grid[curr[0] + 1][curr[1]] != WALL) {
                    curr[0]++;
                    minFlips[curr[0]][curr[1]][curr[2]] = Math.min(minFlips[curr[0]][curr[1]][curr[2]], rnCost);
                }
            } else {
                while (curr[0] - 1 >= 0 && grid[curr[0] - 1][curr[1]] != WALL) {
                    curr[0]--;
                    minFlips[curr[0]][curr[1]][curr[2]] = Math.min(minFlips[curr[0]][curr[1]][curr[2]], rnCost);
                }
            }
            if ((curr[0] == rowNum - 1 && curr[2] == 0) || (curr[0] == 0 && curr[2] == 1)) {  // we fall into space
                continue;
            }

            // try moving to the right
            if (curr[1] + 1 < colNum && grid[curr[0]][curr[1] + 1] != WALL
                    && rnCost < minFlips[curr[0]][curr[1] + 1][curr[2]]) {
                minFlips[curr[0]][curr[1] + 1][curr[2]] = rnCost;
                frontier.add(new int[] {rnCost, curr[0], curr[1] + 1, curr[2]});
            }
            // try moving to the lleft
            if (curr[1] - 1 >= 0 && grid[curr[0]][curr[1] - 1] != WALL
                    && rnCost < minFlips[curr[0]][curr[1] - 1][curr[2]]) {
                minFlips[curr[0]][curr[1] - 1][curr[2]] = rnCost;
                frontier.add(new int[] {rnCost, curr[0], curr[1] - 1, curr[2]});
            }

            // flip the gravity
            int otherSide = curr[2] == 0 ? 1 : 0;
            if (rnCost + 1 < minFlips[curr[0]][curr[1]][otherSide]) {
                minFlips[curr[0]][curr[1]][otherSide] = rnCost + 1;
                frontier.add(new int[] {rnCost + 1, curr[0], curr[1], otherSide});
            }
        }

        int leastSwitches = Arrays.stream(minFlips[docPos[0]][docPos[1]]).min().getAsInt();
        leastSwitches = leastSwitches == Integer.MAX_VALUE ? -1 : leastSwitches;
        PrintWriter written = new PrintWriter("gravity.out");
        written.println(leastSwitches);
        written.close();
        System.out.println(leastSwitches);
        System.out.printf("NIGERUNDAYO, BOVIDIAN! (it took %d ms lol)%n", System.currentTimeMillis() - start);
    }
}
