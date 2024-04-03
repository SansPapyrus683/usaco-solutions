package official.o2023.jan.silver;

import java.io.*;
import java.util.*;

public class FollowingDirections {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int side = Integer.parseInt(read.readLine());

        final int right = -1, down = -2;
        int[][] signs = new int[side + 1][side + 1];
        for (int r = 0; r < side; r++) {
            StringTokenizer row = new StringTokenizer(read.readLine());
            String signRow = row.nextToken();
            for (int c = 0; c < signRow.length(); c++) {
                signs[r][c] = signRow.charAt(c) == 'R' ? right : down;
            }
            signs[r][side] = Integer.parseInt(row.nextToken());
        }
        signs[side] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int[][] occNum = new int[side + 1][side + 1];
        for (int r = 0; r < side; r++) {
            for (int c = 0; c < side; c++) {
                occNum[r][c]++;
                if (signs[r][c] == right) {
                    occNum[r][c + 1] += occNum[r][c];
                } else {
                    occNum[r + 1][c] += occNum[r][c];
                }
            }
        }

        int tot = 0;
        for (int i = 0; i < side; i++) {
            tot += occNum[side][i] * signs[side][i] + occNum[i][side] * signs[i][side];
        }
        StringBuilder queryAns = new StringBuilder();
        queryAns.append(tot).append('\n');

        int queryNum = Integer.parseInt(read.readLine());
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer flipped = new StringTokenizer(read.readLine());
            int flipRow = Integer.parseInt(flipped.nextToken()) - 1;
            int flipCol = Integer.parseInt(flipped.nextToken()) - 1;
            int change = occNum[flipRow][flipCol];
            int[] at = new int[]{flipRow, flipCol};
            while (at[0] != side && at[1] != side) {
                int ind = signs[at[0]][at[1]] == right ? 1 : 0;
                at[ind]++;
                occNum[at[0]][at[1]] -= change;
            }

            int currSign = signs[flipRow][flipCol];
            signs[flipRow][flipCol] = currSign == right ? down : right;
            at = new int[]{flipRow, flipCol};
            while (at[0] != side && at[1] != side) {
                int ind = signs[at[0]][at[1]] == right ? 1 : 0;
                at[ind]++;
                occNum[at[0]][at[1]] += change;
            }

            tot = 0;
            for (int i = 0; i < side; i++) {
                tot += occNum[side][i] * signs[side][i] + occNum[i][side] * signs[i][side];
            }
            queryAns.append(tot).append('\n');
        }

        System.out.print(queryAns);
    }
}
