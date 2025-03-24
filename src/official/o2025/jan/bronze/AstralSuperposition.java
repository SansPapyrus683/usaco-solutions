package official.o2025.jan.bronze;

import java.io.*;
import java.util.*;

public class AstralSuperposition {
    private static Map<Character, Integer> COLORS = new HashMap<>() {{
            put('W', 0);
            put('G', 1);
            put('B', 2);
    }};

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            StringTokenizer initial = new StringTokenizer(read.readLine());
            int side = Integer.parseInt(initial.nextToken());
            int colMove = Integer.parseInt(initial.nextToken());
            int rowMove = Integer.parseInt(initial.nextToken());

            int[][] needed = new int[side][side];
            for (int r = 0; r < side; r++) {
                String row = read.readLine();
                for (int c = 0; c < side; c++) {
                    needed[r][c] = COLORS.get(row.charAt(c));
                }
            }

            int minStars = 0;
            boolean valid = true;
            int[][] stars = new int[side][side];
            filling: for (int r = 0; r < side; r++) {
                int rr = r + rowMove;
                for (int c = 0; c < side; c++) {
                    if (needed[r][c] == 0) {
                        continue;
                    }

                    int cc = c + colMove;
                    boolean downstream = rr < side && cc < side && needed[rr][cc] == 2;
                    if (stars[r][c] >= needed[r][c] && !downstream) {
                        continue;
                    }

                    minStars++;
                    stars[r][c]++;
                    if (rr < side && cc < side) {
                        stars[rr][cc]++;
                    }

                    if (stars[r][c] < needed[r][c]) {
                        valid = false;
                        break filling;
                    }
                }
            }

            System.out.println(valid ? minStars : -1);
        }
    }
}
