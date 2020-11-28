import java.io.*;
import java.util.*;

// 2017 jan silver
public class CowDance {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cowdance.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int timeLimit = Integer.parseInt(initial.nextToken());
        int[] cows = new int[cowNum];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Integer.parseInt(read.readLine());
        }

        int leastAmt = 1;
        int lowerBound = 1;
        int upperBound = cowNum;
        while (lowerBound <= upperBound) {  // ngl binsearch is getting kinda boring at this point
            int toSearch = (upperBound + lowerBound) / 2;
            if (finishInTime(cows, timeLimit, toSearch)) {
                upperBound = toSearch - 1;
                leastAmt = toSearch;
            } else {
                lowerBound = toSearch + 1;
            }
        }

        PrintWriter written = new PrintWriter("cowdance.out");
        written.println(leastAmt);
        written.close();
        System.out.println(leastAmt);
        System.out.printf("my grandma can do better than your measly %d ms%n", System.currentTimeMillis() - start);
    }

    static boolean finishInTime(int[] cows, int timeLimit, int cowLimit) {
        PriorityQueue<Integer> onStage = new PriorityQueue<>();
        int timer = 0;
        int cowOnDeck = cowLimit;
        for (int c = 0; c < cowLimit; c++) {
            onStage.add(cows[c]);
        }
        while (true) {
            timer = onStage.poll();
            if (timer > timeLimit) {  // frick, we exceeded the time limit
                return false;
            }
            if (onStage.isEmpty()) {  // ok the stage is empty, let's go
                return true;
            }
            if (cowOnDeck < cows.length) {  // only add a cow if there's still more cows on deck
                onStage.add(cows[cowOnDeck] + timer);
                cowOnDeck++;
            }
        }
    }
}
