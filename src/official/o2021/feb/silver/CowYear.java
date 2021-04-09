package official.o2021.feb.silver;

import java.io.*;
import java.util.*;

/**
 * 2021 feb silver
 * 5 3
 * 101
 * 85
 * 100
 * 46
 * 95 should output 36
 */
public final class CowYear {
    private static final int JUMP_MUL = 12;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int ancestorNum = Integer.parseInt(initial.nextToken());
        int jumpLimit = Integer.parseInt(initial.nextToken());
        int[] ancestors = new int[ancestorNum + 1];  // last element is 0 for the present year
        for (int a = 0; a < ancestorNum; a++) {
            ancestors[a] = -Integer.parseInt(read.readLine());
        }
        Arrays.sort(ancestors);

        int last = portalTime(ancestors[0]);
        ArrayList<List<Integer>> timeSegs = new ArrayList<>();
        ArrayList<Integer> currSeg = new ArrayList<>();
        for (int a : ancestors) {
            if (a - last > JUMP_MUL) {
                last = portalTime(a);
                timeSegs.add(currSeg);
                currSeg = new ArrayList<>();
            }
            currSeg.add(a);
        }
        timeSegs.add(currSeg);
        // remember that we have to jump back to the present
        if (portalTime(currSeg.get(0)) < -JUMP_MUL) {
            timeSegs.add(Collections.singletonList(0));
        }

        int[] intervalTime = new int[timeSegs.size() - 1];
        for (int s = 0; s < timeSegs.size() - 1; s++) {
            intervalTime[s] = portalTime(timeSegs.get(s + 1).get(0)) - (portalTime(timeSegs.get(s).get(0)) + JUMP_MUL);
        }
        Arrays.sort(intervalTime);

        // initially the worst time is just jumping all the way to the back and waiting until the present
        int travelTime = -portalTime(timeSegs.get(0).get(0));
        // we can jump jumpLimit - 1 times (because we alr used one jumping back) to save some time between intervals
        for (int j = 0; j < jumpLimit - 1; j++) {
            travelTime -= intervalTime[intervalTime.length - 1 - j];
        }
        System.out.println(travelTime);
        System.err.printf("i wonder if people actually read this stuff: %d ms%n", System.currentTimeMillis() - start);
    }

    private static int portalTime(int ancestor) {
        return ancestor % 12 == 0 ? ancestor : (ancestor / JUMP_MUL - 1) * JUMP_MUL;
    }
}
