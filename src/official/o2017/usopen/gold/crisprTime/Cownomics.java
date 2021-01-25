package official.o2017.usopen.gold.crisprTime;

import java.io.*;
import java.util.*;

// 2017 usopen gold
public final class Cownomics {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cownomics.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int genomeLen = Integer.parseInt(initial.nextToken());
        String[] spotty = new String[cowNum];
        String[] plain = new String[cowNum];
        for (int c = 0; c < cowNum; c++) {
            spotty[c] = read.readLine().toLowerCase();
        }
        for (int c = 0; c < cowNum; c++) {
            plain[c] = read.readLine().toLowerCase();
        }

        int lo = 0;
        int hi = genomeLen;
        int valid = -1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (distinguishable(spotty, plain, mid)) {
                valid = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        PrintWriter written = new PrintWriter("cownomics.out");
        written.println(valid);
        written.close();
        System.out.println(valid);
        System.out.printf("where did fj even get the cow's sequences i mean: %d ms%n", System.currentTimeMillis() - start);
    }

    // assumes all the things are the same length
    private static boolean distinguishable(String[] spotty, String[] plain, int segLen) {
        for (int i = segLen; i < spotty[0].length(); i++) {
            HashSet<String> spottyView = new HashSet<>();
            HashSet<String> plainView = new HashSet<>();
            for (String c : spotty) {
                spottyView.add(c.substring(i - segLen, i));
            }
            for (String c : plain) {
                plainView.add(c.substring(i - segLen, i));
            }
            if (Collections.disjoint(spottyView, plainView)) {
                return true;
            }
        }
        return false;
    }
}
