package official.o2017.jan.gold.gameLogic100;

import java.io.*;
import java.util.*;

// 2017 jan gold
public class HPS {
    private static final HashMap<String, Integer> MOVES = new HashMap<String, Integer>() {{
        put("P", 0);
        put("H", 1);
        put("S", 2);
    }};
    private static final int OP_NUM = MOVES.size();
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("hps.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int roundNum = Integer.parseInt(initial.nextToken());
        int switchNum = Integer.parseInt(initial.nextToken());
        int[] plays = new int[roundNum];
        for (int r = 0; r < roundNum; r++) {
            plays[r] = MOVES.get(read.readLine().toUpperCase());
        }

        int[][][] maxScore = new int[roundNum + 1][switchNum + 1][OP_NUM];
        for (int r = 1; r <= roundNum; r++) {
            for (int switchTimes = 0; switchTimes <= switchNum; switchTimes++) {
                int[] thisBest = maxScore[r][switchTimes];  // convenient shorthand
                for (int o = 0; o < OP_NUM; o++) {
                    int win = plays[r - 1] == (o + 1) % OP_NUM ? 1 : 0;
                    // maybe we could just continue our previous streak?
                    thisBest[o] = maxScore[r - 1][switchTimes][o] + win;

                    if (switchTimes >= 1) {
                        // ok let's try switching from another strategy
                        for (int switchFrom = 0; switchFrom < OP_NUM; switchFrom++) {
                            thisBest[o] = Math.max(thisBest[o], maxScore[r - 1][switchTimes - 1][switchFrom] + win);
                        }
                    }
                }
            }
        }
        int overallBest = Arrays.stream(maxScore[roundNum][switchNum]).max().getAsInt();
        PrintWriter written = new PrintWriter("hps.out");
        written.println(overallBest);
        written.close();
        System.out.println(overallBest);
        System.out.printf("wait how can a hoof get cut what the frick: %d ms%n", System.currentTimeMillis() - start);
    }
}
