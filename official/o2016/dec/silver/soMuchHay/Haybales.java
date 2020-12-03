package official.o2016.dec.silver.soMuchHay;

import java.io.*;
import java.util.*;

// 2016 dec silver
public class Haybales {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("haybales.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        initial.nextToken();
        int[] haybales = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(haybales);

        int queryNum = Integer.parseInt(initial.nextToken());
        PrintWriter written = new PrintWriter("haybales.out");
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            int qStart = Integer.parseInt(query.nextToken());
            int qEnd = Integer.parseInt(query.nextToken());
            // find the insert location of the intervals relative to the bales and then subtract them
            int haybaleNum = bisect_right(haybales, qEnd) - bisect_left(haybales, qStart);
            written.println(haybaleNum);
        }
        written.close();
        System.out.printf("bruh %d ms you're so bad%n", System.currentTimeMillis() - start);
    }

    // straight from https://stackoverflow.com/questions/2945017/javas-equivalent-to-bisect-in-python
    public static int bisect_right(int[] searchOn, int x) {
        int lo = 0;
        int hi = searchOn.length;
        if (hi == 0) {
            return 0;
        }
        if (x < searchOn[lo]) {
            return lo;
        }
        if (x > searchOn[hi - 1]) {
            return hi;
        }
        while (true) {
            if (lo + 1 == hi) {
                return lo + 1;
            }
            int mi = (hi + lo) / 2;
            if (x < searchOn[mi]) {
                hi = mi;
            } else {
                lo = mi;
            }
        }
    }

    public static int bisect_left(int[] searchOn, int x) {
        int lo = 0;
        int hi = searchOn.length;
        if (hi == 0) {
            return 0;
        }
        if (x < searchOn[lo]) {
            return lo;
        }
        if (x > searchOn[hi - 1]) {
            return hi;
        }
        while (true) {
            if (lo + 1 == hi) {
                return x == searchOn[lo] ? lo : (lo + 1);
            }
            int mi = (hi + lo) / 2;
            if (x <= searchOn[mi]) {
                hi = mi;
            } else {
                lo = mi;
            }
        }
    }
}
