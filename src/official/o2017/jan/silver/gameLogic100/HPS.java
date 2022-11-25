package official.o2017.jan.silver.gameLogic100;

import java.io.*;
import java.util.*;

// 2017 jan silver
public class HPS {
    // goddamit usaco when will you upgrade to java like 11 or 13
    private static final HashMap<String, Integer> MOVES = new HashMap<String, Integer>() {{
        put("P", 0);
        put("H", 1);
        put("S", 2);
    }};
    private static final int OPTION_NUM = MOVES.size();
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("hps.in"));
        int gameNum = Integer.parseInt(read.readLine());
        int[][] movesSoFar = new int[3][gameNum + 1];  // paper moves, hoof moves, and scissors moves
        for (int g = 1; g < gameNum + 1; g++) {
            movesSoFar[0][g] = movesSoFar[0][g - 1];
            movesSoFar[1][g] = movesSoFar[1][g - 1];
            movesSoFar[2][g] = movesSoFar[2][g - 1];
            movesSoFar[MOVES.get(read.readLine())][g]++;
        }
        int maxWins = 0;
        for (int i = 0; i < OPTION_NUM; i++) {  // brute force bessie's first & second move (i & j respectively)
            for (int j = 0; j < OPTION_NUM; j++) {
                int firstEnemy = (i + 1) % OPTION_NUM;
                int secondEnemy = (j + 1) % OPTION_NUM;
                if (i == j) {  // if they're equal, just see the total amount of wins that can happen with a single thing
                    maxWins = Math.max(maxWins, movesSoFar[firstEnemy][gameNum]);
                    continue;
                }
                for (int g = 1; g <= gameNum; g++) {
                    // prefix sum or smth idk
                    maxWins = Math.max(
                            maxWins,
                            movesSoFar[firstEnemy][g - 1]
                                    + (movesSoFar[secondEnemy][gameNum]
                                    - movesSoFar[secondEnemy][g - 1])
                    );
                }
            }
        }
        PrintWriter written = new PrintWriter("hps.out");
        written.println(maxWins);
        written.close();
        System.out.println(maxWins);
        System.out.printf("%d ms- look i'm not proud of that time either%n", System.currentTimeMillis() - start);
    }
}
