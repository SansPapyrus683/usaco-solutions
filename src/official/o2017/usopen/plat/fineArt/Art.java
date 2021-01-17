package official.o2017.usopen.plat.fineArt;

import java.io.*;
import java.util.*;

// 2017 usopen plat (don't expect more of these lol)
public class Art {
    // first is how much the row is off (for the first one), second is how much the col is off (for the second one)
    private static final int[][] CORNER_ADJ = new int[][] {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("art.in"));

        int width = Integer.parseInt(read.readLine());
        int[][] canvas = new int[width][width];
        HashMap<Integer, int[]> visible = new HashMap<>();
        for (int r = 0; r < width; r++) {
            // used this weird \\s+ regex so deubgging would be easier
            canvas[r] = Arrays.stream(read.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();
            if (canvas[r].length != width) {
                throw new IllegalArgumentException("you idiot picowso only paints on square canvases");
            }
            for (int c = 0; c < width; c++) {
                int[] bounds;
                if (!visible.containsKey(canvas[r][c])) {
                    // these values will be the lowest row num, highest row num, lowest col num, and highest col num
                    visible.put(canvas[r][c], (bounds = new int[] {r, r, c, c}));
                } else {
                    bounds = visible.get(canvas[r][c]);
                }
                bounds[0] = Math.min(r, bounds[0]);
                bounds[1] = Math.max(r, bounds[1]);
                bounds[2] = Math.min(c, bounds[2]);
                bounds[3] = Math.max(c, bounds[3]);
            }
        }
        visible.remove(0);

        // all the non-visible ones could've been painted first
        int possFirst = (int) Math.pow(width, 2) - visible.size();
        if (visible.size() > 1) {  // if statement for special edge case handling
            ArrayList<int[]> allBounds = new ArrayList<>(visible.values());
            int[][] rectNum = new int[width + 1][width + 1];  // dw, we won't care about the outer areas
            for (int[] b : allBounds) {
                // make a 2d diff array so that when we take this[i][j] to be the sum of all the above ones,
                // we'll get the # of rectangles that contain the cell
                rectNum[b[0]][b[2]]++;
                rectNum[b[0]][b[3] + 1]--;
                rectNum[b[1] + 1][b[2]]--;
                rectNum[b[1] + 1][b[3] + 1]++;
            }

            Set<Integer> validColors = visible.keySet();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < width; j++) {
                    if (i > 0) {
                        rectNum[i][j] += rectNum[i - 1][j];
                    }
                    if (j > 0) {
                        rectNum[i][j] += rectNum[i][j - 1];
                    }
                    if (i > 0 && j > 0) {
                        rectNum[i][j] -= rectNum[i - 1][j - 1];
                    }
                    if (rectNum[i][j] > 1) {
                        // ok, so two rectangles overlap- the one that's at the top definitely can't have been painted first
                        validColors.remove(canvas[i][j]);
                    }
                }
            }
            possFirst += validColors.size();
        }
        PrintWriter written = new PrintWriter("art.out");
        written.println(possFirst);
        written.close();
        System.out.println(possFirst);
        System.out.printf("it's a bunch of squares. and it's ART. (%d ms)%n", System.currentTimeMillis() - start);
    }
}
