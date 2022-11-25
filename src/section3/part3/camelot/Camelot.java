package section3.part3.camelot;
/*
ID: kevinsh4
LANG: JAVA
TASK: camelot
*/
import java.io.*;
import java.util.*;

public class Camelot {
    private static final int INVALID = 4206969;  // to avoid overflow
    private static final int[] CHANGE_R = new int[] {-2, -2, -1, 1, 2, 2, 1, -1};
    private static final int[] CHANGE_C = new int[] {-1, 1, 2, 2, 1, -1, -2, -2};
    // it's only optimal for the king to move at most 2 spaces
    // bc if he moves 3 a knight can always do it in 3 or less steps as well
    private static final int KING_MOVE = 2;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("camelot.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int colNum = Integer.parseInt(initial.nextToken());
        int rowNum = Integer.parseInt(initial.nextToken());

        ArrayList<int[]> knights = new ArrayList<>();
        String line;
        while ((line = read.readLine()) != null) {
            String[] splitLine = line.toUpperCase().split(" ");
            for (int i = 0; i < splitLine.length / 2; i++) {
                knights.add(new int[] {splitLine[i * 2].charAt(0) - 'A', Integer.parseInt(splitLine[i * 2 + 1]) - 1});
            }
        }
        int[] kingPos = knights.remove(0);

        int[][][][] knightDists = new int[rowNum][colNum][rowNum][colNum];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                for (int k = 0; k < rowNum; k++) {
                    Arrays.fill(knightDists[i][j][k], INVALID);
                }
            }
        }
        for (int r = 0; r < rowNum; r++) {
            for (int c = 0; c < colNum; c++) {
                int[][] thisDists = knightDists[r][c];
                thisDists[r][c] = 0;
                ArrayDeque<int[]> frontier = new ArrayDeque<>(Collections.singletonList(new int[] {r, c}));
                while (!frontier.isEmpty()) {
                    int[] curr = frontier.poll();
                    int newCost = thisDists[curr[0]][curr[1]] + 1;
                    for (int i = 0; i < 8; i++) {
                        int newR = curr[0] + CHANGE_R[i];
                        int newC = curr[1] + CHANGE_C[i];
                        if (0 <= newR && newR < rowNum && 0 <= newC && newC < colNum && newCost < thisDists[newR][newC]) {
                            thisDists[newR][newC] = newCost;
                            frontier.add(new int[] {newR, newC});
                        }
                    }
                }
            }
        }

        int KRStart = Math.max(kingPos[0] - KING_MOVE, 0);
        int KREnd = Math.min(kingPos[0] + KING_MOVE, rowNum - 1);
        int KCStart = Math.max(kingPos[1] - KING_MOVE, 0);
        int KCEnd = Math.min(kingPos[1] + KING_MOVE, colNum - 1);

        int minMeetingDist = Integer.MAX_VALUE;
        for (int mr = 0; mr < rowNum; mr++) {
            for (int mc = 0; mc < colNum; mc++) {
                int knightCosts = 0;
                for (int[] kn : knights) {
                    knightCosts += knightDists[kn[0]][kn[1]][mr][mc];
                }
                int kingCost = Math.max(Math.abs(kingPos[0] - mr), Math.abs(kingPos[1] - mc));
                for (int kr = KRStart; kr <= KREnd; kr++) {
                    for (int kc = KCStart; kc <= KCEnd; kc++) {
                        for (int[] kn : knights) {
                            int carryExtra = knightDists[kn[0]][kn[1]][kr][kc]
                                    + knightDists[kr][kc][mr][mc]
                                    - knightDists[kn[0]][kn[1]][mr][mc]
                                    + Math.max(Math.abs(kingPos[0] - kr), Math.abs(kingPos[1] - kc));
                            kingCost = Math.min(kingCost, carryExtra);
                        }
                    }
                }
                minMeetingDist = Math.min(minMeetingDist, knightCosts + kingCost);
            }
        }
        PrintWriter written = new PrintWriter("camelot.out");
        written.println(minMeetingDist);
        written.close();
        System.out.println(minMeetingDist);
        System.out.printf("time taken (ms): %s%n", System.currentTimeMillis() - start);
    }
}
