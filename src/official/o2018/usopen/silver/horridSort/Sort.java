package official.o2018.usopen.silver.horridSort;

import java.io.*;
import java.util.*;

// 2018 usopen silver
public final class Sort {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("sort.in"));
        int[][] unsorted = new int[Integer.parseInt(read.readLine())][2];
        for (int i = 0; i < unsorted.length; i++) {
            unsorted[i] = new int[] {Integer.parseInt(read.readLine()), i};  // actual val, and their unsorted pos
        }
        Arrays.sort(unsorted, Comparator.comparingInt(n -> n[0]));

        /*
         * the bubble sort immediately moves large elements as far right as they can go
         * however, smol elements can only move to the left one at a time
         * so we see which element has to move the farthest to the left (by keeping a marker of their initial pos)
         * same val elements are handled ok bc they will NEVER cross positions and stuff
         */
        int movedFarthestLeft = 0;
        for (int i = 0; i < unsorted.length; i++) {
            movedFarthestLeft = Math.max(movedFarthestLeft, unsorted[i][1] - i);
        }
        movedFarthestLeft++;  // add 1 because the algo still needs a run to make sure everything's sorted

        PrintWriter written = new PrintWriter("sort.out");
        written.println(movedFarthestLeft);
        written.close();
        System.out.println(movedFarthestLeft);
        System.out.printf("moo %d ms moo moo%n", System.currentTimeMillis() - start);
    }
}
