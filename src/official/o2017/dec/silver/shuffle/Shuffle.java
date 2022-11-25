package official.o2017.dec.silver.shuffle;

import java.io.*;
import java.util.*;

// 2017 dec silver
public final class Shuffle {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("shuffle.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[] moveTo = Arrays.stream(read.readLine().split(" ")).mapToInt(i -> Integer.parseInt(i) - 1).toArray();
        int[] sourceCount = new int[cowNum];
        for (int c = 0; c < cowNum; c++) {
            sourceCount[moveTo[c]]++;
        }
        int aliveNum = cowNum;
        ArrayDeque<Integer> canKillOthers = new ArrayDeque<>();
        for (int c = 0; c < cowNum; c++) {
            if (sourceCount[c] <= 0) {  // oof, already dead
                aliveNum--;
                canKillOthers.add(c);
            }
        }

        while (!canKillOthers.isEmpty()) {
            int curr = canKillOthers.removeFirst();
            sourceCount[moveTo[curr]]--;  // do some damage
            if (sourceCount[moveTo[curr]] <= 0) {  // see if that tile is dead
                aliveNum--;  // DEAD BODY REPORTED
                /*
                 * it's kinda like a "chain reaction" this dependency breaks
                 * this dependency which breaks that dependency and so on
                 */
                canKillOthers.add(moveTo[curr]);
            }
        }

        PrintWriter written = new PrintWriter("shuffle.out");
        written.println(aliveNum);
        written.close();
        System.out.println(aliveNum);
        System.out.printf("time: %d ms%n", System.currentTimeMillis() - start);
    }
}
