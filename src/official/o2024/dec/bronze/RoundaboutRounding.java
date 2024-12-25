package official.o2024.dec.bronze;

import java.io.*;

/** 2024 dec bronze */
public class RoundaboutRounding {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            int upTo = Integer.parseInt(read.readLine());
            int diffRounds = 0;
            long firstAt = 40;
            int digits = 1;  // the # of digits past the first one (so one less)
            while (firstAt <= upTo) {
                for (int trail4 = 0; trail4 <= digits - 1; trail4++) {
                    for (int rounding = 5; rounding <= 9; rounding++) {
                        long actualNum = firstAt + rounding * (long)Math.pow(10, digits - trail4 - 1);
                        for (long i = digits - trail4; i < digits; i++) {
                            actualNum += 4 * (long)Math.pow(10, i);
                        }
                        long total = (long)Math.pow(10, digits - trail4 - 1);
                        // pretty sure it's +1? not sure if the test data tests this
                        diffRounds += (int)Math.max(Math.min(total, upTo - actualNum + 1), 0);
                    }
                }
                firstAt *= 10;
                digits++;
            }

            System.out.println(diffRounds);
        }
    }
}
