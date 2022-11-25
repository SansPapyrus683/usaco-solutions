package official.o2013.feb.gold.cutCows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// 2013 feb gold
public class Partition {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("partition.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int width = Integer.parseInt(initial.nextToken());
        int fenceNum = Integer.parseInt(initial.nextToken());
        int[][] pastures = new int[width][width];
        for (int r = 0; r < width; r++) {
            pastures[r] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        fenceNum = Math.min(fenceNum, 2 * width - 2);
        int minMaxCows = Integer.MAX_VALUE;
        for (int i = 0; i < (1 << width - 1); i++) {
            int rowFenceNum = Integer.bitCount(i);
            if (rowFenceNum > fenceNum) {
                continue;
            }

            int[][] squishedRowsPref = new int[rowFenceNum + 1][width];
            int row = 0;
            for (int r = 0; r < width; r++) {
                if (r != 0 && (i & (1 << (r - 1))) != 0) {
                    row++;
                }
                for (int c = 0; c < width; c++) {
                    squishedRowsPref[row][c] += pastures[r][c];
                }
            }
            // make it into an actual prefix sum array
            for (int r = 0; r < squishedRowsPref.length; r++) {
                for (int c = 1; c < width; c++) {
                    squishedRowsPref[r][c] += squishedRowsPref[r][c - 1];
                }
            }

            int remaining = fenceNum - rowFenceNum;
            int[][] minCowGroup = new int[width + 1][remaining + 1];
            for (int c = 1; c <= width; c++) {
                Arrays.fill(minCowGroup[c], Integer.MAX_VALUE);

                int finalC = c;  // stupid java lambdas
                minCowGroup[c][0] = Arrays.stream(squishedRowsPref).mapToInt(r -> r[finalC - 1]).max().getAsInt();
                for (int r = 1; r <= remaining; r++) {
                    for (int prev = 1; prev < c; prev++) {
                        int splitMax = 0;
                        for (int[] rowPref : squishedRowsPref) {
                            splitMax = Math.max(splitMax, rowPref[c - 1] - rowPref[prev - 1]);
                        }
                        minCowGroup[c][r] = Math.min(minCowGroup[c][r], Math.max(minCowGroup[prev][r - 1], splitMax));
                    }
                }
            }
            minMaxCows = Math.min(minMaxCows, minCowGroup[width][remaining]);
        }

        PrintWriter written = new PrintWriter("partition.out");
        written.println(minMaxCows);
        written.close();
        System.out.println(minMaxCows);
        System.out.printf("i have given up™: %d ms%n", System.currentTimeMillis() - start);
    }
}
