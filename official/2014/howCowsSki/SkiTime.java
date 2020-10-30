import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2014 jan silver i think (if i name this ccski intellij has a seizure)
public class SkiTime {
    static int[] dimensions;
    static int[][] hills;
    static int checkpointNum = 0;
    static boolean[][] checkpoints;
    static int[] randomCheckpoint;
    static ArrayList<int[]>[][] neighbors;
    // static long[] times = new long[5];
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("ccski.in"));
        dimensions = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        hills = new int[dimensions[0]][dimensions[1]];

        neighbors = new ArrayList[dimensions[0]][dimensions[1]];  // precalculate neighbors
        for (int y = 0; y < dimensions[0]; y++) {
            for (int x = 0; x < dimensions[1]; x++) {
                neighbors[y][x] = neighbors(y, x);
            }
        }

        for (int i = 0; i < hills.length; i++) {
            hills[i] = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        checkpoints = new boolean[dimensions[0]][dimensions[1]];
        for (int i = 0; i < hills.length; i++) {
            int xVal = 0;
            for (String rawPt : read.readLine().split(" ")) {
                int pt = Integer.parseInt(rawPt);
                if (pt == 1) {
                    randomCheckpoint = new int[] {i, xVal};
                    checkpoints[i][xVal] = true;
                    checkpointNum++;
                }
                xVal++;
            }
        }

        int lowerBound = 0;
        int upperBound = (int) Math.pow(10, 9) + 1;  // not inclusive (but the lower bound is)
        while (upperBound > lowerBound) {
            int toSearch = (upperBound + lowerBound) / 2;
            if (validWithDifficulty(toSearch)) {
                upperBound = toSearch - 1;
            } else {
                lowerBound = toSearch + 1;
            }
        }
        PrintWriter written = new PrintWriter(new FileOutputStream("ccski.out"));
        written.println(lowerBound);  // or upperBound - 1, both work
        written.close();
        System.out.println(lowerBound);
        System.out.printf("grand total of %d ms ok buddy%n", System.currentTimeMillis() - start);
    }

    static ArrayList<int[]> neighbors(int c1, int c2) {
        int[][] possNeighbors = new int[][]{
                {c1 + 1, c2}, {c1 - 1, c2}, {c1, c2 + 1}, {c1, c2 - 1}
        };
        ArrayList<int[]> actualNeighbors = new ArrayList<>();
        for (int[] n : possNeighbors) {
            if (0 <= n[0] && n[0] < dimensions[0] && 0 <= n[1] && n[1] < dimensions[1]) {
                actualNeighbors.add(n);
            }
        }
        return actualNeighbors;
    }

    static boolean validWithDifficulty(int diff) {
        int[] start = randomCheckpoint;  // doesn't matter which cp we use honestly
        int leftToCheck = checkpointNum;  // we only go through each cp once so just a number is good enough
        ArrayDeque<int[]> frontier = new ArrayDeque<>();
        boolean[][] visited = new boolean[dimensions[0]][dimensions[1]];
        frontier.add(start);
        visited[start[0]][start[1]] = true;
        while (!frontier.isEmpty()) {
            int[] current = frontier.poll();
            if (checkpoints[current[0]][current[1]]) {
                leftToCheck--;
                if (leftToCheck == 0) {
                    return true;
                }
            }
            int currElevation = hills[current[0]][current[1]];
            for (int[] n : neighbors[current[0]][current[1]]) {
                if (Math.abs(currElevation - hills[n[0]][n[1]]) <= diff && !visited[n[0]][n[1]]) {
                    visited[n[0]][n[1]] = true;
                    frontier.add(n);
                }
            }
        }
        return leftToCheck == 0;  // handles the 1 checkpoint edge case
    }
}
