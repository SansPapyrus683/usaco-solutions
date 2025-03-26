package official.o2025.feb.silver;

import java.io.*;
import java.util.*;

public class TransformingPairs {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            StringTokenizer info = new StringTokenizer(read.readLine());
            long a = Long.parseLong(info.nextToken());
            long b = Long.parseLong(info.nextToken());
            long c = Long.parseLong(info.nextToken());
            long d = Long.parseLong(info.nextToken());
            System.out.println(minSteps(a, b, c, d));
        }
    }

    private static long minSteps(long a, long b, long c, long d) {
        if (a > c || b > d) {
            return -1;
        }

        // ???? why does this work???????
        long steps = 0;
        while (a != c || b != d) {
            long subAmt;
            if (c > d) {
                subAmt = (c - a) / d;
                c -= d * subAmt;
            } else {
                subAmt = (d - b) / c;
                d -= c * subAmt;
            }

            if (subAmt == 0) {
                return -1;
            }
            steps += subAmt;
        }

        return steps;
    }
}
