package official.o2016.dec.silver.haybales;

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
            int haybaleNum = bisectRight(haybales, qEnd) - bisectLeft(haybales, qStart);
            written.println(haybaleNum);
        }
        written.close();
        System.out.printf("bruh %d ms you're so bad%n", System.currentTimeMillis() - start);
    }

    // these two are right from python's bisect module: https://github.com/python/cpython/blob/master/Lib/bisect.py
    private static int bisectLeft(int[] arr, int elem) {
        int lo = 0;
        int hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] < elem) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    private static int bisectRight(int[] arr, int elem) {
        int lo = 0;
        int hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (elem < arr[mid]) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }
}
