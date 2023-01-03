package official.o2016.jan.plat.fortmoo;

import java.io.*;
import java.util.*;

public class FortMoo {
    private static final char BAD = 'X';
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("fortmoo.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int rowNum = Integer.parseInt(initial.nextToken());
        int colNum = Integer.parseInt(initial.nextToken());

        boolean[][] sturdy = new boolean[rowNum][colNum];
        int[][] rowBadNums = new int[rowNum][colNum + 1];  // colNum + 1 because of how pref sums work
        for (int r = 0; r < rowNum; r++) {
            String row = read.readLine();
            for (int c = 0; c < colNum; c++) {
                rowBadNums[r][c + 1] = rowBadNums[r][c] + (row.charAt(c) == BAD ? 1 : 0);
                sturdy[r][c] = row.charAt(c) != BAD;
            }
        }

        int maxArea = 0;
        for (int cStart = 0; cStart < colNum; cStart++) {
            for (int cEnd = cStart + 1; cEnd < colNum; cEnd++) {
                int rStart = 0;
                for (int rEnd = 0; rEnd < rowNum; rEnd++) {
                    boolean rowValid = rowBadNums[rEnd][cEnd + 1] - rowBadNums[rEnd][cStart] == 0;
                    if (rEnd == rStart && !rowValid) {
                        rStart++;
                        continue;
                    }
                    if (!sturdy[rEnd][cStart] || !sturdy[rEnd][cEnd]) {
                        rStart = rEnd + 1;
                    } else if (rowValid && rStart != rEnd) {
                        maxArea = Math.max(maxArea, (cEnd - cStart + 1) * (rEnd - rStart + 1));
                    }
                }
            }
        }

        PrintWriter written = new PrintWriter("fortmoo.out");
        written.println(maxArea);
        written.close();
        System.out.println(maxArea);
        System.out.printf("what they gonna do in the fort anyways: %d ms%n", System.currentTimeMillis() - start);
    }
}
