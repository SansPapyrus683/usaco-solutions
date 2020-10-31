/*
ID: kevinsh4
LANG: JAVA
TASK: camelot
*/

import java.io.*;
import java.util.*;

public class Camelot {
    static int[][][][] distances;
    static int[][] cachedKing;
    static int[][][][] cachedNeighbors;
    static int rows, cols;
    static int[] kingPos;
    static int kingPickupDist = 2;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("camelot.in"));
        int[] dimensions = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        rows = dimensions[0];
        cols = dimensions[1];
        distances = new int[rows][cols][rows][cols];
        cachedKing = new int[rows][cols];
        cachedNeighbors = new int[rows][cols][][];

        for (int[] r : cachedKing) {
            Arrays.fill(r, Integer.MAX_VALUE);
        }

        ArrayList<int[]> knightPos = new ArrayList<>();
        String line;
        while ((line = read.readLine()) != null) {
            String[] splitLine = line.split(" ");
            for (int i = 0; i < splitLine.length / 2; i++) {
                String[] stringPos = Arrays.copyOfRange(splitLine, i * 2, i * 2 + 2);
                knightPos.add(new int[] {(int) stringPos[0].charAt(0) - 65, Integer.parseInt(stringPos[1]) - 1});
            }
        }
        read.close();
        kingPos = knightPos.remove(0);

        for (int c = 0; c < cols; c++) {  // calculate all distances (through a knight) too all other distances
            for (int r = 0; r < rows; r++) {
                knightExpand(new int[] {c, r});
            }
        }

        // note that it always isn't worth it for the king to travel like more than 2 distances
        // if it did then a knight can always to the job in equal or lesser moves
        int kingColStart = Math.max(kingPos[0] - kingPickupDist, 0);
        int kingColEnd = Math.min(kingPos[0] + kingPickupDist, cols);
        int kingRowStart = Math.max(kingPos[1] - kingPickupDist, 0);
        int kingRowEnd = Math.min(kingPos[1] + kingPickupDist, rows);

        int best = Integer.MAX_VALUE;
        for (int c = 0; c < cols; c++) {  // go through all meeting positions
            for (int r = 0; r < rows; r++) {
                int haveToTravel = 0;
                for (int[] kn : knightPos) {
                    haveToTravel += distances[kn[1]][kn[0]][r][c];
                }
                // cp and rp are column pickup and row pickup respectively
                int minDetour = kingCalc(kingPos, new int[] {c, r});
                for (int cp = kingColStart; cp < kingColEnd; cp++) {  // go through all pickup positions
                    for (int rp = kingRowStart; rp < kingRowEnd; rp++) {
                        for (int[] kn : knightPos) {
                            int detour = distances[kn[1]][kn[0]][rp][cp] + kingCalc(kingPos, new int[] {cp, rp}) +
                                         distances[rp][cp][r][c] - distances[kn[1]][kn[0]][r][c];
                            minDetour = Math.min(minDetour, detour);
                        }
                    }
                }
                best = Math.min(best, haveToTravel + minDetour);
            }
        }

        System.out.println(best);
        PrintWriter written = new PrintWriter("camelot.out");
        written.println(best);
        written.close();
        System.out.println(best);
        System.out.printf("time taken (ms): %s%n", System.currentTimeMillis() - start);
    }

    static int kingCalc(int[] kingPos, int[] goToPos) {
        if (cachedKing[goToPos[1]][goToPos[0]] != Integer.MAX_VALUE) {
            return cachedKing[goToPos[1]][goToPos[0]];
        }
        cachedKing[goToPos[1]][goToPos[0]] = Math.max(Math.abs(kingPos[0]- goToPos[0]),
                Math.abs(kingPos[1] - goToPos[1]));
        return cachedKing[goToPos[1]][goToPos[0]];
    }

    static int[][] knightNeighbors(int[] pos) {
        if (cachedNeighbors[pos[1]][pos[0]] != null) {
            return cachedNeighbors[pos[1]][pos[0]];
        }

        // given a knight's position and whether they have the king or not, returns possible neighbors
        int[] changeRow = new int[] {-2, -2, -1, 1, 2, 2, 1, -1};
        int[] changeCol = new int[] {-1, 1, 2, 2, 1, -1, -2, -2};
        int col  = pos[0];
        int row = pos[1];
        ArrayList<int[]> actualNeighbors = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int newCol = col + changeCol[i];
            int newRow = row + changeRow[i];
            if (0 <= newCol && newCol < cols && 0 <= newRow && newRow < rows) {
                actualNeighbors.add(new int[] {newCol, newRow});
            }
        }
        int[][] thing = new int[actualNeighbors.size()][2];
        actualNeighbors.toArray(thing);
        cachedNeighbors[pos[1]][pos[0]] = thing;
        return thing;
    }

    static void knightExpand(int[] pos) {  // calculates distances & updates the distances variable w/ it
        int[] nPos = Arrays.copyOf(pos, 3);
        Queue<int[]> frontier = new LinkedList<>();
        int[][] costs = new int[rows][cols];
        for (int[] r : costs) {
            Arrays.fill(r, 42069);  // can't do Integer.MAX_VALUE bc of overflowing
        }
        frontier.add(nPos);
        costs[nPos[1]][nPos[0]] = 0;

        while (!frontier.isEmpty()) {  // just a simple bfs expansion
            int[] current = frontier.poll();
            int rnCost = costs[current[1]][current[0]];
            for (int[] n : knightNeighbors(current)) {
                int newCost = rnCost + 1;
                if (costs[n[1]][n[0]] > newCost) {
                    costs[n[1]][n[0]] = newCost;
                    frontier.add(n);
                }
            }
        }
        distances[pos[1]][pos[0]] = costs;
    }
}
