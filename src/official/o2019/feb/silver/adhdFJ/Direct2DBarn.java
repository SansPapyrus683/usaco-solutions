// package official.o2019.feb.silver.adhdFJ;

import java.io.*;
import java.util.*;

// 2019 feb silver
public class Direct2DBarn {
    private static final int MAX_WIDTH = 1000;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("paintbarn.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int rectNum = Integer.parseInt(initial.nextToken());  // sounds like rectum ngl
        int optimalAmt = Integer.parseInt(initial.nextToken());
        int[][] rectBounds = new int[MAX_WIDTH + 1][MAX_WIDTH + 1];  // coordinates can be between 0 and 1000
        for (int r = 0; r < rectNum; r++) {
            int[] rect = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            rectBounds[rect[0]][rect[1]]++;
            rectBounds[rect[0]][rect[3]]--;
            rectBounds[rect[2]][rect[1]]--;
            rectBounds[rect[2]][rect[3]]++;
        }
        // this one was from section 3 part 3 home on the range thing
        int[][] barn = new int[MAX_WIDTH + 1][MAX_WIDTH + 1];
        barn[1][1] = rectBounds[0][0];
        for (int i = 1; i < MAX_WIDTH + 1; i++) {
            barn[1][i] = barn[1][i - 1] + rectBounds[0][i - 1];
            barn[i][1] = barn[i - 1][1] + rectBounds[i - 1][0];
        }
        int goodArea = 0;
        for (int r = 2; r < MAX_WIDTH + 1; r++) {
            for (int c = 2; c < MAX_WIDTH + 1; c++) {
                barn[r][c] = barn[r - 1][c] +
                        barn[r][c - 1] -
                        barn[r - 1][c - 1] +
                        rectBounds[r - 1][c - 1];
                goodArea += barn[r][c] == optimalAmt ? 1 : 0;
            }
        }

        PrintWriter written = new PrintWriter("paintbarn.out");
        written.println(goodArea);
        written.close();
        System.out.println(goodArea);
        System.out.printf("guys sub to poofesure he's really funny- %d ms%n", System.currentTimeMillis() - start);
    }
}

