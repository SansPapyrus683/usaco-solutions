package official.o2018.feb.silver.beamItUp;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2018 feb silver
public final class Teleport {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("teleport.in"));
        int transportNum = Integer.parseInt(read.readLine());
        int[][] toTransport = new int[transportNum][2];
        for (int t = 0; t < transportNum; t++) {
            toTransport[t] = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        long noTeleport = 0;
        TreeMap<Integer, Integer> distanceChanges = new TreeMap<>();
        for (int[] t : toTransport) {
            noTeleport += Math.abs(t[0] - t[1]);
            if (Math.abs(t[0])>= Math.abs(t[0] - t[1])) {
                continue;  // i mean just going to 0 would take too much, so this can't be helped
            }
            increment(distanceChanges, t[1], 2);  // the end is always the "peak", so change from -1 to 1 = 2
            if ((t[1] >= t[0] && t[0] < 0) || (t[0] >= t[1] && t[0] >= 0)) {  // if it goes from neg to pos or pos to neg
                increment(distanceChanges, 0, -1);
                increment(distanceChanges, 2 * t[1], -1);
            } else {
                increment(distanceChanges, 2 * t[0], -1);
                increment(distanceChanges, 2 * t[1] - 2 * t[0], -1);
            }
        }

        long leastDistance = Long.MAX_VALUE;  // go through the slope changes, kinda like a prefix sum & keep the min
        long currDistance = noTeleport;
        int currChange = 0;
        int lastInterestPoint = distanceChanges.firstKey();
        for (int c : distanceChanges.keySet()) {
            currDistance += (long) currChange * (c - lastInterestPoint);
            lastInterestPoint = c;
            leastDistance = Math.min(leastDistance, currDistance);
            currChange += distanceChanges.get(c);
        }

        PrintWriter written = new PrintWriter("teleport.out");
        written.println(leastDistance);
        written.close();
        System.out.println(leastDistance);
        System.out.printf("eeewwww it took %d ms that's gross%n", System.currentTimeMillis() - start);
    }

    static void increment(Map<Integer, Integer> toIncrement, int key, int value) {
        if (toIncrement.containsKey(key)) {
            toIncrement.put(key, toIncrement.get(key) + value);
        } else {
            toIncrement.put(key, value);
        }
    }
}
