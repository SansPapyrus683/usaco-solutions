package official.o2014.dec.gold.swoleCows;

import java.io.*;
import java.util.*;

// 2014 dec gold
public final class CowJog {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cowjog.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        long[][] cows = new long[cowNum][2];
        int time = Integer.parseInt(initial.nextToken());
        // quick note: 2 cows won't run into each other if their starts and ends aren't nested within each other
        for (int c = 0; c < cows.length; c++) {
            StringTokenizer cow = new StringTokenizer(read.readLine());
            cows[c][0] = Integer.parseInt(cow.nextToken());
            int speed = Integer.parseInt(cow.nextToken());
            cows[c][1] = cows[c][0] + (long) speed * time;
        }
        // super long comparator because of possible threat of overflow when casting from long to int
        Arrays.sort(cows, (a, b) -> {
            if (a[0] != b[0]) {
                return a[0] < b[0] ? -1 : 1;
            } else if (a[1] != b[1]) {
                return a[1] < b[1] ? -1 : 1;
            }
            return 0;
        });

        long[] ascendingEndings = new long[cowNum];
        for (int c = 0; c < cowNum; c++) {
            ascendingEndings[c] = cows[c][1];
        }

        /*
         * ok so now the beginnings are definitely ascending, we find the min tracks needed
         * so that each track has an ascending ending time so prevent nesting (as mentioned above)
         * tracks contains the ending (so far) of each track, and is always decreasing
         */
        ArrayList<Long> tracks = new ArrayList<>(Collections.singletonList(ascendingEndings[0]));
        for (int c = 1; c < cowNum; c++) {
            if (ascendingEndings[c] <= tracks.get(tracks.size() - 1)) {
                tracks.add(ascendingEndings[c]);
                continue;
            }

            // reverse binsearch for the most recent ending we can put this cow on
            int lo = 0;
            int hi = tracks.size() - 1;
            int valid = -1;
            while (lo <= hi) {
                int mid = (lo + hi) / 2;
                if (ascendingEndings[c] > tracks.get(mid)) {
                    valid = mid;
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            /*
             * this still preserves the descending order because if there were some n that comes before the cow
             * and is lower than the cow, the binsearch would've found that spot instead
             */
            tracks.set(valid, ascendingEndings[c]);
        }

        PrintWriter written = new PrintWriter("cowjog.out");
        written.println(tracks.size());
        written.close();
        System.out.println(tracks.size());
        System.out.printf("%d ms jesus fuh-ricking christ%n", System.currentTimeMillis() - start);
    }
}

