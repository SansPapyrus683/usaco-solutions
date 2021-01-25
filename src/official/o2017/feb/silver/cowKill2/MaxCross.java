package official.o2017.feb.silver.cowKill2;

import java.io.*;
import java.util.*;

// 2017 feb silver
public final class MaxCross {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("maxcross.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int lightNum = Integer.parseInt(initial.nextToken());
        int atLeast = Integer.parseInt(initial.nextToken());
        int brokenNum = Integer.parseInt(initial.nextToken());
        boolean[] lights = new boolean[lightNum];  // true = working, false = broken
        Arrays.fill(lights, true);
        for (int l = 0; l < brokenNum; l++) {
            lights[Integer.parseInt(read.readLine()) - 1] = false;
        }

        int workingNum = 0;
        for (int l = 0; l < atLeast; l++) {
            workingNum += lights[l] ? 1 : 0;
        }
        int leastToRepair = Integer.MAX_VALUE;
        for (int repairStart = 0; repairStart < lightNum - atLeast + 1; repairStart++) {
            leastToRepair = Math.min(leastToRepair, atLeast - workingNum);
            workingNum -= lights[repairStart] ? 1 : 0;  // use the prev calculation to calc this window size
            if (repairStart + atLeast < lightNum) {  // if we're at the end, don't process changes (throws error otherwise)
                workingNum += lights[repairStart + atLeast] ? 1 : 0;
            }
        }

        PrintWriter written = new PrintWriter("maxcross.out");
        written.println(leastToRepair);
        written.close();
        System.out.println(leastToRepair);
        System.out.printf("%d ms please don't run me more i can't take it anymore%n", System.currentTimeMillis() - start);
    }
}
