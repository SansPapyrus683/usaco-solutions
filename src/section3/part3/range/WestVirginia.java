package section3.part3.range;
/*
ID: kevinsh4
LANG: JAVA
TASK: range
*/

import java.io.*;

// pretty much a direct java translation
public final class WestVirginia {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("range.in"));
        int fieldWidth = Integer.parseInt(read.readLine());
        int[][] grass = new int[fieldWidth][fieldWidth];
        for (int i = 0; i < fieldWidth; i++) {
            char[] row = read.readLine().toCharArray();
            int[] inputTo = grass[i];
            for (int c = 0; c < fieldWidth; c++) {
                inputTo[c] = Character.getNumericValue(row[c]);
            }
        }
        read.close();

        int[][] grassSoFar = new int[fieldWidth + 1][fieldWidth + 1];
        grassSoFar[1][1] = grass[0][0];
        for (int i = 1; i < fieldWidth + 1; i++) {
            grassSoFar[1][i] = grassSoFar[1][i - 1] + grass[0][i - 1];
            grassSoFar[i][1] = grassSoFar[i - 1][1] + grass[i - 1][0];
        }
        for (int r = 2; r < fieldWidth + 1; r++) {
            for (int c = 2; c < fieldWidth + 1; c++) {
                grassSoFar[r][c] = (grassSoFar[r - 1][c] +
                                    grassSoFar[r][c - 1] -
                                    grassSoFar[r - 1][c - 1] +
                                    grass[r - 1][c - 1]);
            }
        }

        PrintWriter written = new PrintWriter("range.out");
        for (int w = 2; w <= fieldWidth; w++) {
            int targetSum = (int) Math.pow(w, 2);
            int validNumber = 0;
            for (int r = 0; r <= fieldWidth - w; r++) {
                for (int c = 0; c <= fieldWidth - w; c++) {
                    if ((grassSoFar[r + w][c + w] - grassSoFar[r][c + w] - grassSoFar[r + w][c] + grassSoFar[r][c]) == targetSum) {
                        validNumber++;
                    }
                }
            }
            if (validNumber > 0) {
                System.out.printf("%s %s%n", w, validNumber);
                written.printf("%s %s%n", w, validNumber);
            }
        }
        written.close();
        System.out.printf("MAMAAAAAAAA WEST VIRGINIAAAAAAAAAA %d ms%n", System.currentTimeMillis() - start);
    }
}
