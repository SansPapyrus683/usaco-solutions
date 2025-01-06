package official.o2021.dec.bronze;

import java.io.*;
import java.util.*;

/** 2021 dec bronze */
public class AirCownditioning {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int stallNum = Integer.parseInt(read.readLine());
        StringTokenizer want = new StringTokenizer(read.readLine());
        StringTokenizer have = new StringTokenizer(read.readLine());
        int[] delta = new int[stallNum + 1];
        for (int s = 0; s < stallNum; s++) {
            int w = Integer.parseInt(want.nextToken());
            int h = Integer.parseInt(have.nextToken());
            delta[s] = w - h;
        }

        int minOps = 0;
        List<Integer> run = new ArrayList<>();
        int currCmp = 0;
        for (int d : delta) {
            if (run.isEmpty()) {
                currCmp = Integer.compare(d, 0);
            }
            if (Integer.compare(d, 0) != currCmp) {
                int prev = 0;
                for (int i : run) {
                    i = Math.abs(i);
                    minOps += Math.max(i - prev, 0);
                    prev = i;
                }

                run = new ArrayList<>();
                currCmp = Integer.compare(d, 0);
            }
            if (d != 0) {
                run.add(d);
            }
        }

        System.out.println(minOps);
    }
}
