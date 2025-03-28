package official.o2025.feb.bronze;

import java.io.*;
import java.util.*;

public class Reflection {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int side = Integer.parseInt(initial.nextToken());
        assert side % 2 == 0;
        int updNum = Integer.parseInt(initial.nextToken());

        int[][] canvas = new int[side][side];
        for (int r = 0; r < side; r++) {
            String row = read.readLine();
            for (int c = 0; c < side; c++) {
                canvas[r][c] = row.charAt(c) == '#' ? 1 : -1;
            }
        }

        int minOps = 0;
        final int n = side / 2; // shorthand lol
        int[][] diff = new int[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                int[][] pos = new int[][] {
                        { r, c }, { side - 1 - r, c },
                        { r, side - 1 - c }, { side - 1 - r, side - 1 - c }
                };
                for (int[] p : pos) {
                    diff[r][c] += canvas[p[0]][p[1]];
                }
                minOps += 2 - Math.abs(diff[r][c]) / 2;
            }
        }

        System.out.println(minOps);
        for (int u = 0; u < updNum; u++) {
            StringTokenizer pos = new StringTokenizer(read.readLine());
            int r = Integer.parseInt(pos.nextToken()) - 1;
            int c = Integer.parseInt(pos.nextToken()) - 1;

            int nr = r < n ? r : side - 1 - r;
            int nc = c < n ? c : side - 1 - c;

            minOps -= 2 - Math.abs(diff[nr][nc]) / 2;

            diff[nr][nc] -= 2 * canvas[r][c];
            canvas[r][c] *= -1;

            minOps += 2 - Math.abs(diff[nr][nc]) / 2;
            System.out.println(minOps);
        }
    }
}
