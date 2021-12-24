package official.o2019.feb.silver.adhdFJ;

import java.io.*;
import java.util.*;

// 2019 feb silver
public final class PaintBarn {
    private static final int BARN_WIDTH = 1000;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("paintbarn.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int rectNum = Integer.parseInt(initial.nextToken());
        int requirement = Integer.parseInt(initial.nextToken());
        int[][] startEnds = new int[BARN_WIDTH][BARN_WIDTH];
        for (int i = 0; i < rectNum; i++) {
            int[] corners = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int[][] rect = new int[][] {{corners[0], corners[1]}, {corners[2], corners[3]}};
            int startX = rect[0][0];
            int endX = rect[1][0];
            for (int y = rect[0][1]; y < rect[1][1]; y++) {
                startEnds[y][startX]++;  // the start
                startEnds[y][endX]--;  // the end
            }
        }

        int kAreaAmt = 0;
        for (int r = 0; r < BARN_WIDTH; r++) {
            int currAmt = 0;
            int[] increments = startEnds[r];
            for (int c = 0; c < BARN_WIDTH; c++) {
                if (currAmt == requirement) {
                    kAreaAmt++;
                }
                currAmt += increments[c];
            }
        }

        PrintWriter written = new PrintWriter("paintbarn.out");
        written.println(kAreaAmt);
        written.close();
        System.out.println(kAreaAmt);
        System.out.printf("took around %d milliseconds", System.currentTimeMillis() - start);
    }
}
