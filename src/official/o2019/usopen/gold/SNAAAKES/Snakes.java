package official.o2019.usopen.gold.SNAAAKES;

import java.io.*;
import java.util.*;

// 2019 us open gold
public final class Snakes {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("snakes.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int snakeNum = Integer.parseInt(initial.nextToken());
        int switchTimes = Integer.parseInt(initial.nextToken());
        int[] snakes = new int[snakeNum + 1];
        int snakeAt = 1;
        for (int s : Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray()) {
            snakes[snakeAt++] = s;
        }
        int[] snakesSoFar = new int[snakeNum + 1];
        for (int s = 1; s <= snakeNum; s++) {
            snakesSoFar[s] = snakesSoFar[s - 1] + snakes[s];
        }

        int[][] minSpaceWasted = new int[snakeNum + 1][switchTimes + 1];  // last snake we caught and the amt we still can switch
        for (int i = 1; i <= snakeNum; i++) {  // we won't be using the first row (to make prefix summing easier)
            Arrays.fill(minSpaceWasted[i], Integer.MAX_VALUE);
        }
        for (int s = 1; s <= snakeNum; s++) {
            minSpaceWasted[s][switchTimes] = spaceWasted(snakesSoFar, 1, s);
        }
        for (int s = 1; s <= snakeNum; s++) {
            for (int sw = 1; sw <= switchTimes; sw++) {  // if we can still switch, see what it'd be like
                int rnCost;
                if ((rnCost = minSpaceWasted[s][sw]) == Integer.MAX_VALUE) {  // wait is this state even valid
                    continue;
                }
                for (int goUntil = s + 1; goUntil <= snakeNum; goUntil++) {
                    minSpaceWasted[goUntil][sw - 1] = Math.min(minSpaceWasted[goUntil][sw - 1],
                            rnCost + spaceWasted(snakesSoFar, s + 1, goUntil));
                }
            }
        }

        int actualMin = Arrays.stream(minSpaceWasted[snakeNum]).min().getAsInt();
        PrintWriter written = new PrintWriter("snakes.out");
        written.println(actualMin);
        written.close();
        System.out.println(actualMin);
        System.out.printf("%d ms- frick.%n", System.currentTimeMillis() - start);
    }

    // tbh the prefix sum array was probably a bad idea but...
    private static int spaceWasted(int[] snakesSoFar, int start, int end) {  // bounds are inclusive
        int totalSnakes = snakesSoFar[end] - snakesSoFar[start - 1];
        int netSize = 0;
        for (int i = start; i <= end; i++) {
            netSize = Math.max(netSize, snakesSoFar[i] - snakesSoFar[i - 1]);
        }
        return netSize * (end - start + 1) - totalSnakes;
    }
}
