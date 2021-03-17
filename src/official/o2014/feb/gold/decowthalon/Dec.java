package official.o2014.feb.gold.decowthalon;

import java.io.*;
import java.util.*;

// 2014 feb gold
public final class Dec {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("dec.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int bonusNum = Integer.parseInt(initial.nextToken());

        ArrayList<int[]>[] bonuses = new ArrayList[cowNum];
        for (int c = 0; c < cowNum; c++) {
            bonuses[c] = new ArrayList<>();
        }
        for (int b = 0; b < bonusNum; b++) {
            StringTokenizer bonus = new StringTokenizer(read.readLine());
            bonuses[Integer.parseInt(bonus.nextToken()) - 1].add(new int[] {
                    Integer.parseInt(bonus.nextToken()),
                    Integer.parseInt(bonus.nextToken())
            });
        }
        int[][] skills = new int[cowNum][cowNum];
        for (int c = 0; c < cowNum; c++) {
            skills[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        // max points only using a subset of the cows defined by the bits set in the #
        int[] maxPoints = new int[1 << cowNum];
        for (int i = 1; i < (1 << cowNum); i++) {
            int roundNum = -1;  // start at -1 because of that 0-indexing
            for (int c = 0; c < cowNum; c++) {
                roundNum += (i & (1 << c)) != 0 ? 1 : 0;
            }
            // see which cow should have been the last one to have to optimal arrangement
            for (int c = 0; c < cowNum; c++) {
                if ((i & (1 << c)) == 0) {
                    continue;
                }
                int points = maxPoints[i & ~(1 << c)] + skills[c][roundNum];
                int afterBonuses = points;
                // apply all the bonuses and then see what we get
                for (int[] b : bonuses[roundNum]) {
                    afterBonuses += points >= b[0] ? b[1] : 0;
                }
                maxPoints[i] = Math.max(maxPoints[i], afterBonuses);
            }
        }

        PrintWriter written = new PrintWriter("dec.out");
        written.println(maxPoints[(1 << cowNum) - 1]);
        written.close();
        System.out.println(maxPoints[(1 << cowNum) - 1]);
        System.out.printf("this is a really weird grading system: %d ms%n", System.currentTimeMillis() - start);
    }
}

