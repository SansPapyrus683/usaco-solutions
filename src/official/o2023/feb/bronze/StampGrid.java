package official.o2023.feb.bronze;

import java.io.*;

// 2023 feb bronze
public class StampGrid {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            read.readLine();
            int wantSide = Integer.parseInt(read.readLine());
            boolean[][] want = new boolean[wantSide][wantSide];
            for (int r = 0; r < wantSide; r++) {
                String row = read.readLine();
                for (int c = 0; c < wantSide; c++) {
                    want[r][c] = row.charAt(c) == '*';
                }
            }

            int haveSide = Integer.parseInt(read.readLine());
            boolean[][][] have = new boolean[4][haveSide][haveSide];
            for (int r = 0; r < haveSide; r++) {
                String row = read.readLine();
                for (int c = 0; c < haveSide; c++) {
                    have[0][r][c] = row.charAt(c) == '*';
                }
            }
            for (int i = 1; i < 4; i++) {
                for (int r = 0; r < haveSide; r++) {
                    for (int c = 0; c < haveSide; c++) {
                        have[i][r][c] = have[i - 1][c][r];
                    }
                }
                for (int r = 0; r < haveSide; r++) {
                    for (int c = 0; c < haveSide / 2; c++) {
                        boolean temp = have[i][r][c];
                        have[i][r][c] = have[i][r][haveSide - 1 - c];
                        have[i][r][haveSide - 1 - c] = temp;
                    }
                }
            }

            boolean[][] stamped = new boolean[wantSide][wantSide];
            for (int sr = 0; sr + haveSide - 1 < wantSide; sr++) {
                for (int sc = 0; sc + haveSide - 1 < wantSide; sc++) {
                    for (boolean[][] curr : have) {
                        boolean fits = true;
                        check:
                        for (int r = sr; r < sr + haveSide; r++) {
                            for (int c = sc; c < sc + haveSide; c++) {
                                if (curr[r - sr][c - sc] && !want[r][c]) {
                                    fits = false;
                                    break check;
                                }
                            }
                        }

                        if (fits) {
                            for (int r = sr; r < sr + haveSide; r++) {
                                for (int c = sc; c < sc + haveSide; c++) {
                                    stamped[r][c] = stamped[r][c] || curr[r - sr][c - sc];
                                }
                            }
                        }
                    }
                }
            }

            boolean possible = true;
            check:
            for (int r = 0; r < wantSide; r++) {
                for (int c = 0; c < wantSide; c++) {
                    if (stamped[r][c] != want[r][c]) {
                        possible = false;
                        break check;
                    }
                }
            }

            System.out.println(possible ? "YES" : "NO");
        }
    }
}
