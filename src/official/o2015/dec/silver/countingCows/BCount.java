package official.o2015.dec.silver.countingCows;

import java.io.*;
import java.util.*;

// 2015 dec silver (bro the old problems really are easier)
public class BCount {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("bcount.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int queryNum = Integer.parseInt(initial.nextToken());

        int[][] soFar = new int[cowNum + 1][3];  // to make prefix summing easier, we won't use index 0
        for (int c = 1; c < cowNum + 1; c++) {
            int cow = Integer.parseInt(read.readLine());
            soFar[c] = soFar[c - 1].clone();
            soFar[c][cow - 1]++;
        }

        // printing out all the answers to stdout takes too long so no stdout this time
        PrintWriter written = new PrintWriter("bcount.out");
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            int[] queryStart = soFar[Integer.parseInt(query.nextToken()) - 1];
            int[] queryEnd = soFar[Integer.parseInt(query.nextToken())];
            int[] queryAnswer = new int[queryStart.length];
            for (int b = 0; b < queryStart.length; b++) {
                queryAnswer[b] = queryEnd[b] - queryStart[b];  // simple prefix sum
            }
            written.printf("%s %s %s%n", queryAnswer[0], queryAnswer[1], queryAnswer[2]);
        }
        written.close();
        System.out.printf("%d ms. pathetic.", System.currentTimeMillis() - start);
    }
}
