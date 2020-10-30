import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2014 jan silver i think (if i name this ccski intellij has a seizure)
public class SkiTime {
    static int width, length;
    static int[][] hills;
    static int checkpointNum = 0;
    static boolean[][] checkpoints;
    static Point randomCheckpoint;
    static long asdf = 0;

    static class Point {
        public int x;
        public int y;
        public Point(int y, int x) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("ccski.in"));
        String[] dimensions = read.readLine().split(" ");
        width = Integer.parseInt(dimensions[0]);
        length = Integer.parseInt(dimensions[1]);
        hills = new int[width][length];

        for (int i = 0; i < hills.length; i++) {
            StringTokenizer row = new StringTokenizer(read.readLine());
            for (int c = 0; c < length; c++) {
                hills[i][c] = Integer.parseInt(row.nextToken());
            }
        }

        checkpoints = new boolean[width][length];
        for (int i = 0; i < hills.length; i++) {
            StringTokenizer row = new StringTokenizer(read.readLine());
            for (int c = 0; c < length; c++) {
                checkpoints[i][c] = Integer.parseInt(row.nextToken()) == 1;
                if (checkpoints[i][c]) {
                    if (randomCheckpoint == null) {
                        randomCheckpoint = new Point(i, c);
                    }
                    checkpointNum++;
                }
            }
        }

        int lowerBound = 0;
        int upperBound = (int) Math.pow(10, 9);
        while (upperBound > lowerBound) {
            int toSearch = (upperBound + lowerBound) / 2;
            if (validWithDifficulty(toSearch)) {
                upperBound = toSearch - 1;
            } else {
                lowerBound = toSearch + 1;
            }
        }
        // binary search do be like this tho
        int answer = validWithDifficulty(lowerBound) ? lowerBound : lowerBound + 1;

        PrintWriter written = new PrintWriter(new FileOutputStream("ccski.out"));
        written.println(answer);  // or upperBound - 1, both work
        written.close();
        System.out.println(answer);
        System.out.println(asdf / Math.pow(10, 9));
        System.out.printf("grand total of %d ms ok buddy%n", System.currentTimeMillis() - start);
    }

    static boolean validWithDifficulty(int diff) {
        Point current = randomCheckpoint;  // doesn't matter which cp we use honestly
        int gottenCheckpoints = 0;
        ArrayDeque<Point> frontier = new ArrayDeque<>();
        boolean[][] visited = new boolean[width][length];
        frontier.add(current);
        visited[current.y][current.x] = true;

        while (!frontier.isEmpty()) {
            current = frontier.pop();
            int x = current.x;
            int y = current.y;
            if (checkpoints[y][x]) {
                gottenCheckpoints++;
            }
            /* yes, this is a really hacky implementation.
             yes, i am absolutely ashamed of such blasphemy.
             but it runs under the limit, so here it stays. */
            int currElevation = hills[y][x];
            if (x + 1 < length && Math.abs(currElevation - hills[y][x+1]) <= diff && !visited[y][x+1]) {
                visited[y][x+1] = true;
                frontier.add(new Point(y, x + 1));
            }
            if (x - 1 >= 0 && Math.abs(currElevation - hills[y][x-1]) <= diff && !visited[y][x-1]) {
                visited[y][x-1] = true;
                frontier.add(new Point(y, x- 1));
            }
            if (y + 1 < width && Math.abs(currElevation - hills[y+1][x]) <= diff && !visited[y+1][x]) {
                visited[y+1][x] = true;
                frontier.add(new Point(y + 1, x));
            }
            if (y - 1 >= 0 && Math.abs(currElevation - hills[y-1][x]) <= diff && !visited[y-1][x]) {
                visited[y-1][x] = true;
                frontier.add(new Point(y - 1, x));
            }
        }
        return gottenCheckpoints >= checkpointNum;  // handles the 1 checkpoint edge case
    }
}
