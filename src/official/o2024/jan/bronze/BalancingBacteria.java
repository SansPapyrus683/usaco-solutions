package official.o2024.jan.bronze;

import java.io.*;
import java.util.*;

/**
 * 2024 jan bronze
 * 5
 * 1 3 -2 -7 5 should output 26
 */
public class BalancingBacteria {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int patchNum = Integer.parseInt(read.readLine());
        long[] grass = Arrays.stream(read.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
        assert grass.length == patchNum;

        long sprayNum = 0;
        long currDelta = 0;
        long deltaInc = 0;
        for (int i = 0; i < patchNum; i++) {
            long currBact = grass[i] + currDelta;
            long sprays;
            int sign;
            if (currBact < 0) {
                sprays = -currBact;
                sign = 1;
            } else {
                sprays = currBact;
                sign = -1;
            }

            sprayNum += sprays;
            deltaInc += sprays * sign;
            currDelta += sprays * sign + deltaInc;
        }

        System.out.println(sprayNum);
    }
}
