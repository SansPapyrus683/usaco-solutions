package section3.part3.game1;
/*
ID: kevinsh4
LANG: JAVA
TASK: game1
*/

import java.io.*;
import java.util.*;

public final class Numbergasms {
    private static int[][] cachedFromTos;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("game1.in"));
        read.readLine();
        StringBuilder allNums = new StringBuilder();
        String rnLine;
        while ((rnLine = read.readLine()) != null) {
            allNums.append(rnLine);
            allNums.append(" ");
        }
        read.close();
        int[] numbers = Arrays.stream(allNums.toString().split(" ")).mapToInt(Integer::parseInt).toArray();

        int player1 = p1BestScore(numbers);
        int totalScore = Arrays.stream(numbers).sum();
        PrintWriter written = new PrintWriter("game1.out");
        written.printf("%s %s%n", player1, totalScore - player1);
        written.close();
        System.out.printf("%s %s%n", player1, totalScore - player1);
        System.out.printf("%d ms! ok... well imma go now%n", System.currentTimeMillis() - start);
    }

    static int p1BestScore(int[] board) {
        cachedFromTos = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            Arrays.fill(cachedFromTos[i], -1);
        }
        return p1BestScore(board, 0, board.length - 1);
    }

    // gives the best score with the board (and it's p1's turn) and only pieces from board[from: to + 1] are there
    private static int p1BestScore(int[] board, int from, int to) {
        if (cachedFromTos[from][to] != -1) {
            return cachedFromTos[from][to];
        }
        if (to - from <= 1) {
            return Math.max(board[from], board[to]);
        }
        cachedFromTos[from + 2][to] = p1BestScore(board, from + 2, to);
        cachedFromTos[from + 1][to - 1] = p1BestScore(board, from + 1, to - 1);
        cachedFromTos[from][to - 2] = p1BestScore(board, from, to - 2);
        // it's math.min because p2 will always try to screw up p1, so we assume worst case
        return Math.max(board[from] + Math.min(cachedFromTos[from + 2][to], cachedFromTos[from + 1][to - 1]),
                board[to] + Math.min(cachedFromTos[from][to - 2], cachedFromTos[from + 1][to - 1]));
    }
}
