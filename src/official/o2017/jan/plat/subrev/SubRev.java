package official.o2017.jan.plat.subrev;

import java.io.*;

// 2017 jan plat
public final class SubRev {
    private static final int MAX_HEIGHT = 50;
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("subrev.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[] heights = new int[cowNum];
        for (int c = 0; c < cowNum; c++) {
            heights[c] = Integer.parseInt(read.readLine()) - 1;
            if (heights[c] < 0 || heights[c] >= MAX_HEIGHT) {
                throw new IllegalArgumentException("yeah...");
            }
        }

        int[][][][] longest = new int[cowNum][cowNum][MAX_HEIGHT][MAX_HEIGHT];
        for (int c = 0; c < cowNum; c++) {
            longest[c][c][heights[c]][heights[c]] = 1;
        }

        for (int len = 2; len <= cowNum; len++) {
            for (int start = 0; start + len - 1 < cowNum; start++) {
                int end = start + len - 1;

                int left = heights[start];
                int right = heights[end];

                int min = Math.min(right, left);
                int max = Math.max(right, left);

                int[][] curr = longest[start][end];
                for (int sh = 0; sh < MAX_HEIGHT; sh++) {
                    for (int eh = sh; eh < MAX_HEIGHT; eh++) {
                        curr[sh][eh] = Math.max(
                                curr[sh][eh],
                                Math.max(
                                        longest[start + 1][end][sh][eh],
                                        longest[start][end - 1][sh][eh]
                                )
                        );

                        if (left <= sh) {
                            curr[left][eh] = Math.max(
                                    curr[left][eh], 1 + longest[start + 1][end][sh][eh]
                            );
                        }
                        if (eh <= right) {
                            curr[sh][right] = Math.max(
                                    curr[sh][right], longest[start][end - 1][sh][eh] + 1
                            );
                        }

                        int mid = longest[start + 1][end - 1][sh][eh];
                        if (left <= sh) {
                            curr[left][eh] = Math.max(curr[left][eh], mid + 1);
                        }
                        if (right <= sh) {
                            curr[right][eh] = Math.max(curr[right][eh], mid + 1);
                        }
                        if (eh <= left) {
                            curr[sh][left] = Math.max(curr[sh][left], mid + 1);
                        }
                        if (eh <= right) {
                            curr[sh][right] = Math.max(curr[sh][right], mid + 1);
                        }
                        if (min <= sh && eh <= max) {
                            curr[min][max] = Math.max(curr[min][max], mid + 2);
                        }
                    }
                }
            }
        }

        int best = 0;
        for (int sh = 0; sh < MAX_HEIGHT; sh++) {
            for (int eh = sh; eh < MAX_HEIGHT; eh++) {
                best = Math.max(best, longest[0][cowNum - 1][sh][eh]);
            }
        }

        PrintWriter written = new PrintWriter("subrev.out");
        written.println(best);
        written.close();
        System.out.println(best);
    }
}
