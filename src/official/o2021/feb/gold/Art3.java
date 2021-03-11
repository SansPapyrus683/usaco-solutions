package official.o2021.feb.gold;

import java.io.*;
import java.util.*;

/**
 * 2021 feb gold
 * 10
 * 1 2 3 4 1 4 3 2 1 6 should output 6
 */
public final class Art3 {
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int canvasLen = Integer.parseInt(read.readLine());
        int[] canvas = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        if (canvas.length != canvasLen) {
            throw new IllegalArgumentException(String.format("the lengths %d and %d are different", canvasLen, canvas.length));
        }
        // this[i][j] = min strokes to paint i to j, inclusive
        int[][] minStrokes = new int[canvasLen][canvasLen];
        for (int i = 0; i < canvasLen; i++) {
            Arrays.fill(minStrokes[i], Integer.MAX_VALUE);
            minStrokes[i][i] = 1;
        }

        for (int paintLen = 2; paintLen <= canvasLen; paintLen++) {
            for (int start = 0; start + paintLen <= canvasLen; start++) {
                int end = start + paintLen - 1;
                for (int splitAt = start; splitAt < end; splitAt++) {
                    // see if we can extend one of the strokes from the left to the right (no real clue why this works lol)
                    if (canvas[splitAt] == canvas[end]) {
                        minStrokes[start][end] = Math.min(
                                minStrokes[start][end],
                                minStrokes[start][splitAt] + minStrokes[splitAt + 1][end] - 1
                        );
                    } else {  // if not, just try merging the two segments together
                        minStrokes[start][end] = Math.min(
                                minStrokes[start][end],
                                minStrokes[start][splitAt] + minStrokes[splitAt + 1][end]
                        );
                    }
                }
            }
        }

        System.out.println(minStrokes[0][canvasLen - 1]);
        System.err.printf("picowso's art sucks- change my mind: %d ms%n", System.currentTimeMillis() - timeStart);
    }
}
