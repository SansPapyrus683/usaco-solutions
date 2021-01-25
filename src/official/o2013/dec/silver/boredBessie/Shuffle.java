package official.o2013.dec.silver.boredBessie;

import java.io.*;
import java.util.*;

// 2013 dec silver
public final class Shuffle {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("shuffle.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        // note: cards start w/ 1 at the top and N @ the bottom
        // but bc i like 0-indexing imma just, ya know, make all the cards 0-indexed
        int totalCardNum = Integer.parseInt(initial.nextToken());
        int shuffleCardNum = Integer.parseInt(initial.nextToken());
        int queryNum = Integer.parseInt(initial.nextToken());
        int[] reverseShuffle = new int[shuffleCardNum];
        for (int c = 0; c < shuffleCardNum; c++) {
            reverseShuffle[Integer.parseInt(read.readLine()) - 1] = c;
        }

        PrintWriter written = new PrintWriter("shuffle.out");
        for (int q = 0; q < queryNum; q++) {
            int query = totalCardNum - Integer.parseInt(read.readLine());
            int relPos = Math.max(shuffleCardNum - (totalCardNum - query), 0);  // both of these are 0-indexed
            int absPos = query;
            int windowStart = Math.min(totalCardNum - shuffleCardNum, absPos);
            while (relPos != shuffleCardNum && windowStart >= 0) {
                int nextRelPos = reverseShuffle[relPos];
                absPos += nextRelPos - relPos;
                relPos = nextRelPos + 1;
                windowStart--;
            }
            written.println(absPos + 1);
            System.out.println(absPos + 1);  // turn it back to 1-indexing
        }
        written.close();
        System.out.printf("here it took %d ms are you happy%n", System.currentTimeMillis() - start);
    }
}
