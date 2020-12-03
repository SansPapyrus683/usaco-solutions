package official.o2018.dec.silver.straightRipoff;

import java.io.*;
import java.util.*;

// 2018 dec silver
public class MooyoMooyo {
    private static final int WIDTH = 10;
    private static final int[] X_CHANGE = new int[] {1, -1, 0, 0};
    private static final int[] Y_CHANGE = new int[] {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("mooyomooyo.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int boardHeight = Integer.parseInt(initial.nextToken());
        int pieceReq = Integer.parseInt(initial.nextToken());
        int[][] board = new int[WIDTH][boardHeight];  // i actually rotated the board to the side to make gravity easier
        for (int i = 0; i < boardHeight; i++) {
            int row = 0;
            for (char c : read.readLine().toCharArray()) {
                board[row++][i] = Character.getNumericValue(c);
            }
        }

        ArrayList<int[]> toKill;
        while ((toKill = listToKill(board, pieceReq)).size() > 0) {  // go until there's no more points to kill
            for (int[] p : toKill) {
                board[p[0]][p[1]] = 0;
            }
            for (int c = 0; c < WIDTH; c++) {
                board[c] = noZeroShiftRight(board[c]);
            }
        }

        PrintWriter written = new PrintWriter("mooyomooyo.out");
        for (int r = 0; r < boardHeight; r++) {
            for (int c = 0; c < WIDTH; c++) {
                written.print(board[c][r]);
                System.out.print(board[c][r]);
            }
            written.println();
            System.out.println();
        }
        written.close();
        System.out.printf("hey mason, i know what the number %d means!%n", System.currentTimeMillis() - start);
    }

    static ArrayList<int[]> listToKill(int[][] board, int sizeReq) {
        ArrayList<int[]> toKill = new ArrayList<>();
        boolean[][] processed = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (processed[i][j] || board[i][j] == 0) {
                    continue;
                }
                processed[i][j] = true;  // like always, ez bfs to find all points that are of the same val
                int size = 0;
                int target = board[i][j];
                ArrayDeque<int[]> frontier = new ArrayDeque<>(Collections.singletonList(new int[] {i, j}));
                ArrayList<int[]> visited = new ArrayList<>();
                while (!frontier.isEmpty()) {
                    int[] current = frontier.poll();
                    visited.add(current);
                    size++;
                    for (int i_ = 0; i_ < 4; i_++) {
                        int[] n = new int[] {current[0] + X_CHANGE[i_], current[1] + Y_CHANGE[i_]};
                        if (0 <= n[0] && n[0] < board.length && 0 <= n[1] && n[1] < board[0].length &&
                                board[n[0]][n[1]] == target && !processed[n[0]][n[1]]) {
                            processed[n[0]][n[1]] = true;
                            frontier.add(n);
                        }
                    }
                }
                if (size >= sizeReq) {
                    toKill.addAll(visited);
                }
            }
        }
        return toKill;
    }

    static int[] noZeroShiftRight(int[] arr) {
        int[] result = new int[arr.length];
        int index = arr.length - 1;
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] != 0) {  // put it at the right end of the array if it isn't 0
                result[index--] = arr[i];
            }
        }
        return result;
    }
}
