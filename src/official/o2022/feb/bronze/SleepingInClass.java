package official.o2022.feb.bronze;

import java.io.*;
import java.util.*;

public class SleepingInClass {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            int periodNum = Integer.parseInt(read.readLine());
            int[] log = new int[periodNum];
            StringTokenizer logST = new StringTokenizer(read.readLine());
            for (int p = 0; p < periodNum; p++) {
                log[p] = Integer.parseInt(logST.nextToken());
            }

            int total = Arrays.stream(log).sum();
            for (int resPeriods = periodNum; resPeriods >= 1; resPeriods--) {
                if (total % resPeriods != 0) {
                    continue;
                }
                int target = total / resPeriods;

                int currTimes = 0;
                boolean valid = true;
                for (int i : log) {
                    currTimes += i;
                    if (currTimes > target) {
                        valid = false;
                        break;
                    } else if (currTimes == target) {
                        currTimes = 0;
                    }
                }

                if (valid) {
                    System.out.println(periodNum - resPeriods);
                    break;
                }
            }
        }
    }
}
