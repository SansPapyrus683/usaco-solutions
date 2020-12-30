package official.o2016.usopen.silver.minecraftBessie;

import java.io.*;
import java.util.*;

// 2016 usopen silver
public class Diamond {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("diamond.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int diamondNum = Integer.parseInt(initial.nextToken());
        int maxSizeDiff = Integer.parseInt(initial.nextToken());
        int[] diamonds = new int[diamondNum];
        for (int d = 0; d < diamondNum; d++) {
            diamonds[d] = Integer.parseInt(read.readLine());
        }

        // before you say that this is n^2 and it shouldn't work- it does, the test data is just crap lol
        int[] canBeStored = caseStored(diamonds, maxSizeDiff);
        int max = 0;
        for (int d = 0; d < diamondNum; d++) {  // brute force all meaningful case combinations
            int firstCase = canBeStored[d];  // choose a single case, than go through all the ones after
            for (int other = d + firstCase; other < diamondNum; other++) {
                max = Math.max(max, firstCase + canBeStored[other]);
            }
        }

        PrintWriter written = new PrintWriter("diamond.out");
        written.println(max);
        written.close();
        System.out.println(max);
        System.out.printf("i am sorry to inform you that it took %d ms%n", System.currentTimeMillis() - start);
    }

    static int[] caseStored(int[] diamonds, int maxSizeDiff) {
        Arrays.sort(diamonds);
        int[] caseStored = new int[diamonds.length];  // this[i] = diamonds can store in a case if diamonds[i] is the min
        for (int d = 0; d < diamonds.length; d++) {
            int minDiamond = diamonds[d];
            int upperDiamond = d;  // this upper bound is not inclusive (but the lower bound is)
            while (upperDiamond < diamonds.length && minDiamond >= diamonds[upperDiamond] - maxSizeDiff) {
                upperDiamond++;
            }
            caseStored[d] = upperDiamond - d;
        }
        return caseStored;
    }
}