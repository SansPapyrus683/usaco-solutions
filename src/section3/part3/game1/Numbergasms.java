package section3.part3.game1;
/*
ID: kevinsh4
LANG: JAVA
TASK: game1
*/

import java.io.*;
import java.util.*;

public class Numbergasms {
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("game1.in"));
        int size = Integer.parseInt(read.readLine());
        StringBuilder allNums = new StringBuilder();
        String rnLine;
        while ((rnLine = read.readLine()) != null) {
            allNums.append(rnLine);
            allNums.append(" ");
        }
        read.close();
        int[] board = Arrays.stream(allNums.toString().split(" ")).mapToInt(Integer::parseInt).toArray();
        assert board.length == size;

        int[][] maxScores = new int[size][size];
        // handle sub-boards of length 1 and 2
        for (int i = 0; i < size; i++) {
            maxScores[i][i] = board[i];
        }
        for (int i = 0; i <= size - 2; i++) {
            maxScores[i][i + 1] = Math.max(board[i], board[i + 1]);
        }
        for (int subLen = 3; subLen <= size; subLen++) {
            for (int from = 0; from <= size - subLen; from++) {
                int to = from + subLen - 1;
                maxScores[from][to] = Math.max(
                        Math.min(maxScores[from + 1][to - 1] + board[from], maxScores[from + 2][to] + board[from]),
                        Math.min(maxScores[from + 1][to - 1] + board[to], maxScores[from][to - 2] + board[to])
                );
            }
        }
        int p1Score = maxScores[0][size - 1];
        int p2Score = Arrays.stream(board).sum() - p1Score;
        PrintWriter written = new PrintWriter("game1.out");
        written.printf("%d %d%n", p1Score, p2Score);
        written.close();
        System.out.printf("%d %d%n", p1Score, p2Score);
        System.out.printf("ok it took %d ms!! well... imma go now%n", System.currentTimeMillis() - timeStart);
    }
}
