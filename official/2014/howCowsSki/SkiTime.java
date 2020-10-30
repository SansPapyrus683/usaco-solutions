import java.io.*;
import java.util.*;

// 2014 jan silver i think (if i name this ccski intellij has a seizure)
public class SkiTime {

    static int width, length;
    static int[][] hills;
    static int checkpointNum = 0;
    static boolean[][] checkpoints;
    static Point randomCheckpoint;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("ccski.in"));
        String[] dimensions = read.readLine().split(" ");
        width = Integer.parseInt(dimensions[0]);
        length = Integer.parseInt(dimensions[1]);
        hills = new int[width][length];
        int upperBound = 0;
        int lowerBound = (int) Math.pow(10, 9);
        int answer = -1;

        for (int i = 0; i < hills.length; i++) {
            StringTokenizer row = new StringTokenizer(read.readLine());
            for (int c = 0; c < length; c++) {
                hills[i][c] = Integer.parseInt(row.nextToken());
                upperBound = Math.max(upperBound, hills[i][c]);  // the upperbound can only be SO large as the max hill
                lowerBound = Math.min(lowerBound, hills[i][c]);  // same for the lower bound
            }
        }
        if (lowerBound == upperBound) {  // if all the hills are the same, just do 0 (i hate edge cases)
            answer = 0;
        }

        checkpoints = new boolean[width][length];
        for (int r = 0; r < hills.length; r++) {
            StringTokenizer row = new StringTokenizer(read.readLine());
            for (int c = 0; c < length; c++) {
                checkpoints[r][c] = Integer.parseInt(row.nextToken()) == 1;
                if (checkpoints[r][c]) {
                    if (randomCheckpoint == null) {  // doesn't matter which cp we pick
                        randomCheckpoint = new Point(r, c);
                    }
                    checkpointNum++;
                }
            }
        }

        while (upperBound > lowerBound) {  // just binary search for the difficulty
            int toSearch = (upperBound + lowerBound) / 2;
            if (validWithDifficulty(toSearch)) {
                upperBound = toSearch - 1;
            } else {
                lowerBound = toSearch + 1;
            }
        }
        // binary search do be like this tho
        if (answer == -1) {  // if it wasn't that 0 we set earlier
            answer = validWithDifficulty(lowerBound) ? lowerBound : lowerBound + 1;
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("ccski.out"));
        written.println(answer);  // or upperBound - 1, both work
        written.close();
        System.out.println(answer);
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
        return gottenCheckpoints >= checkpointNum;  // i mean ==, >=, both work
    }
}

class Point {
    public int x;
    public int y;
    public Point(int y, int x) {
        this.x = x;
        this.y = y;
    }
}
