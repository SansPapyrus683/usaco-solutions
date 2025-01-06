package official.o2021.dec.bronze;

import java.io.*;
import java.util.*;

/** 2021 dec bronze */
public class LonelyPhoto {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());
        String cows = read.readLine();
        assert cows.length() == cowNum;

        List<Integer> ranges = new ArrayList<>(Collections.singletonList(0));
        int run = 1;
        for (int i = 1; i < cowNum; i++) {
            if (cows.charAt(i) != cows.charAt(i - 1)) {
                ranges.add(run);
                run = 0;
            }
            run++;
        }
        if (!cows.isEmpty()) {
            ranges.add(run);
        }
        ranges.add(0);

        long lonelyNum = 0;
        for (int i = 1; i < ranges.size() - 1; i++) {
            lonelyNum += Math.max(ranges.get(i - 1) - 1, 0);
            lonelyNum += Math.max(ranges.get(i + 1) - 1, 0);
            if (ranges.get(i) == 1) {
                lonelyNum += (long) ranges.get(i - 1) * ranges.get(i + 1);
            }
        }

        System.out.println(lonelyNum);
    }
}
