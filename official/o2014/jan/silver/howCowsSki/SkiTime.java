import java.io.*;
import java.util.*;

/**
 * 2014 jan silver i think (if i name this ccski intellij has a seizure)
 * so idk why but the time limit for this problem is 2 seconds
 * so it's too slow for test case 9 but good enough lol
 */
public class SkiTime {
    static int width, length;
    static int[][] hills;
    static int checkpointNum = 0;
    static boolean[][] checkpoints;
    static int[] randomCheckpoint;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("ccski.in"));
        String[] dimensions = read.readLine().split(" ");
        width = Integer.parseInt(dimensions[0]);
        length = Integer.parseInt(dimensions[1]);
        hills = new int[width][length];
        int upperBound = 0;
        int lowerBound = 0;

        for (int i = 0; i < hills.length; i++) {
            StringTokenizer row = new StringTokenizer(read.readLine());
            for (int c = 0; c < length; c++) {
                hills[i][c] = Integer.parseInt(row.nextToken());
                upperBound = Math.max(upperBound, hills[i][c]);  // the upperbound can only be as large as the max hill
            }
        }

        checkpoints = new boolean[width][length];
        for (int r = 0; r < hills.length; r++) {
            StringTokenizer row = new StringTokenizer(read.readLine());
            for (int c = 0; c < length; c++) {
                checkpoints[r][c] = Integer.parseInt(row.nextToken()) == 1;
                if (checkpoints[r][c]) {
                    if (randomCheckpoint == null) {  // doesn't matter which cp we pick
                        randomCheckpoint = new int[] {r, c};
                    }
                    checkpointNum++;
                }
            }
        }

        int validSoFar = -1;
        while (lowerBound <= upperBound) {  // just binary search for the difficulty
            int toSearch = (upperBound + lowerBound) / 2;
            if (validWithDifficulty(toSearch)) {
                upperBound = toSearch - 1;
                validSoFar = toSearch;
            } else {
                lowerBound = toSearch + 1;
            }
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("ccski.out"));
        written.println(validSoFar);
        written.close();
        System.out.println(validSoFar);
        System.out.printf("grand total of %d ms ok buddy%n", System.currentTimeMillis() - start);
    }

    static boolean validWithDifficulty(int diff) {
        int[] start = randomCheckpoint;  // doesn't matter which cp we use honestly
        int checkpointsLeft = checkpointNum;
        ArrayDeque<int[]> frontier = new ArrayDeque<>();
        boolean[][] visited = new boolean[width][length];
        frontier.add(start);
        visited[start[0]][start[1]] = true;

        while (!frontier.isEmpty()) {
            int[] current = frontier.pop();
            int x = current[1];
            int y = current[0];
            if (checkpoints[y][x]) {
                checkpointsLeft--;
                if (checkpointsLeft == 0) {
                    return true;
                }
            }
            /* yes, this is a really hacky implementation.
             * yes, i am absolutely ashamed of such blasphemy.
             * but it runs under the limit, so here it stays.
             * (anyways this tries all the possible neighbors and checks if they're valid) */
            int currElevation = hills[y][x];
            if (x + 1 < length && Math.abs(currElevation - hills[y][x+1]) <= diff && !visited[y][x+1]) {
                visited[y][x+1] = true;
                frontier.add(new int[] {y, x + 1});
            }
            if (x - 1 >= 0 && Math.abs(currElevation - hills[y][x-1]) <= diff && !visited[y][x-1]) {
                visited[y][x-1] = true;
                frontier.add(new int[] {y, x - 1});
            }
            if (y + 1 < width && Math.abs(currElevation - hills[y+1][x]) <= diff && !visited[y+1][x]) {
                visited[y+1][x] = true;
                frontier.add(new int[] {y + 1, x});
            }
            if (y - 1 >= 0 && Math.abs(currElevation - hills[y-1][x]) <= diff && !visited[y-1][x]) {
                visited[y-1][x] = true;
                frontier.add(new int[] {y - 1, x});
            }
        }
        return checkpointsLeft <= 0;
    }
}
