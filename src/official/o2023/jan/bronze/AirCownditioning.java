package official.o2023.jan.bronze;

import java.io.*;
import java.util.*;

/** 2023 jan bronze (input omitted due to length) */
public class AirCownditioning {
    static class Cow {
        public int start, end;
        public int coolReq;
        public Cow(int start, int end, int coolReq) {
            this.start = start;
            this.end = end;
            this.coolReq = coolReq;
        }
    }

    static class AC {
        public int start, end;
        public int coolAmt;
        public int cost;
        public AC(int start, int end, int coolReq, int cost) {
            this.start = start;
            this.end = end;
            this.coolAmt = coolReq;
            this.cost = cost;
        }
    }

    static final int MAX_STALL = 100;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int acNum = Integer.parseInt(initial.nextToken());

        Cow[] cows = new Cow[cowNum];
        for (int c = 0; c < cowNum; c++) {
            StringTokenizer cow = new StringTokenizer(read.readLine());
            cows[c] = new Cow(
                    Integer.parseInt(cow.nextToken()),
                    Integer.parseInt(cow.nextToken()),
                    Integer.parseInt(cow.nextToken())
            );
        }

        AC[] acs = new AC[acNum];
        for (int a = 0; a < acNum; a++) {
            StringTokenizer ac = new StringTokenizer(read.readLine());
            acs[a] = new AC(
                    Integer.parseInt(ac.nextToken()),
                    Integer.parseInt(ac.nextToken()),
                    Integer.parseInt(ac.nextToken()),
                    Integer.parseInt(ac.nextToken())
            );
        }

        int minCost = Integer.MAX_VALUE;
        for (int ss = 0; ss < (1 << acNum); ss++) {
            int[] stalls = new int[MAX_STALL + 1];  // Index 0 won't be used

            int cost = 0;
            for (int a = 0; a < acNum; a++) {
                if ((ss & (1 << a)) != 0) {
                    for (int i = acs[a].start; i <= acs[a].end; i++) {
                        stalls[i] += acs[a].coolAmt;
                    }
                    cost += acs[a].cost;
                }
            }

            boolean valid = true;
            check:
            for (Cow c : cows) {
                for (int i = c.start; i <= c.end; i++) {
                    if (stalls[i] < c.coolReq) {
                        valid = false;
                        break check;
                    }
                }
            }
            if (valid) {
                minCost = Math.min(minCost, cost);
            }
        }

        System.out.println(minCost);
    }
}
