package official.o2021.jan.silver;

import java.io.*;
import java.util.*;

/**
 * 2021 jan silver
 * 8 2
 * ABBAABCB
 * 3 6
 * 1 4 should output 4 and 3, each on a newline
 */
public final class NoTime {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        initial.nextToken();
        int[] desired = read.readLine().toUpperCase().chars().toArray();
        int queryNum = Integer.parseInt(initial.nextToken());
        int[][] leftParts = new int[queryNum][2];  // end and the query index (we know the start alr)
        int[][] rightParts = new int[queryNum][2];  // start and the query index
        for (int q = 0; q < queryNum; q++) {
            int[] dontPaint = Arrays.stream(read.readLine().split(" ")).mapToInt(p -> Integer.parseInt(p) - 1).toArray();
            leftParts[q] = new int[] {dontPaint[0], q};
            rightParts[q] = new int[] {dontPaint[1], q};
        }
        Arrays.sort(leftParts, Comparator.comparingInt(i -> i[0]));
        Arrays.sort(rightParts, Comparator.comparingInt(i -> -i[0]));

        // process the left segments in order to save some time
        int[] minStrokes = new int[queryNum];
        TreeSet<Integer> paintedAlr = new TreeSet<>();
        int strokesSoFar = 0;  // this variable name is not to be taken out of context
        int lastPos = 0;
        for (int[] lp : leftParts) {
            for (; lastPos < lp[0]; lastPos++) {
                int seg = desired[lastPos];
                // clear out all the colors darker than this one from our history
                // we can't extend them because this color is lighter
                while (!paintedAlr.isEmpty() && seg < paintedAlr.last()) {
                    paintedAlr.pollLast();
                }
                // see if we can still just "extend" the color
                strokesSoFar += paintedAlr.contains(seg) ? 0 : 1;
                paintedAlr.add(desired[lastPos]);
            }
            minStrokes[lp[1]] += strokesSoFar;
        }

        // basically the same for the right
        paintedAlr = new TreeSet<>();  // reset all the variables
        strokesSoFar = 0;
        lastPos = desired.length - 1;
        for (int[] rp : rightParts) {
            for (; lastPos > rp[0]; lastPos--) {
                int seg = desired[lastPos];
                while (!paintedAlr.isEmpty() && seg < paintedAlr.last()) {
                    paintedAlr.pollLast();
                }
                strokesSoFar += paintedAlr.contains(seg) ? 0 : 1;
                paintedAlr.add(desired[lastPos]);
            }
            minStrokes[rp[1]] += strokesSoFar;
        }

        StringBuilder total = new StringBuilder();  // makes the io go vroom vroom
        for (int s : minStrokes) {
            total.append(s).append('\n');
        }
        System.out.print(total);
        System.err.printf("%d ms ahahahahaha%n", System.currentTimeMillis() - start);
    }
}
