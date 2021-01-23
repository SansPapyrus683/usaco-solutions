package official.o2014.mar.silver.iHateBessie;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2014 mar silver
public class Lazy {
    private static int side;
    private static int[][] soFar;
    private static int walkingDist;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("lazy.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        side = Integer.parseInt(initial.nextToken());
        walkingDist = Integer.parseInt(initial.nextToken());

        int[][] field = new int[side][side];
        for (int i = 0; i < side; i++) {
            field[i] = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        // simple prefix sum (just 1d, not 2d) because if we just do raw calculation it's too slow
        soFar = new int[side][side + 1];
        for (int i = 0; i < side; i++) {
            int[] row = soFar[i];
            for (int j = 1; j < side + 1; j++) {
                row[j] = row[j - 1] + field[i][j - 1];
            }
        }

        int mostGrass = 0;
        for (int r = 0; r < side; r++) {
            for (int c = 0; c < side; c++) {
                mostGrass = Math.max(mostGrass, reachedGrass(r, c));
            }
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("lazy.out"));
        written.println(mostGrass);
        written.close();
        System.out.println(mostGrass);
        System.out.printf("uuuuuuuhhhhhhh it took %d ms%n", System.currentTimeMillis() - start);
    }

    static int rowSum(int row, int start, int end) {
        return soFar[row][end + 1] - soFar[row][start];
    }

    static int reachedGrass(int row, int col) {
        int gottenGrass = 0;
        int rowHalfWidth = 0;
        // calculate the top row (the if statement, max and min are for handling out of bounds)
        for (int r = row - walkingDist; r < row; r++) {
            if (r < 0) {
                continue;
            }
            gottenGrass += rowSum(r, Math.max(col - rowHalfWidth, 0), Math.min(col + rowHalfWidth, side - 1));
            rowHalfWidth++;
        }
        // here's the the middle row (just 1 special row, no for loop)
        gottenGrass += rowSum(row, Math.max(col - walkingDist, 0), Math.min(col + walkingDist, side - 1));
        // and finally the bottom part
        rowHalfWidth = walkingDist - 1;
        for (int r = row + 1; r <= row + walkingDist; r++) {
            if (r >= side) {
                continue;
            }
            gottenGrass += rowSum(r, Math.max(col - rowHalfWidth, 0), Math.min(col + rowHalfWidth, side - 1));
            rowHalfWidth--;
        }
        return gottenGrass;
    }
}
