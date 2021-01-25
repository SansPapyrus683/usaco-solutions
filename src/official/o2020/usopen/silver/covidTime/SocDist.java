package official.o2020.usopen.silver.covidTime;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public final class SocDist {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("socdist.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int patchNum = Integer.parseInt(initial.nextToken());
        long[][] patches = new long[patchNum][2];
        long leftestPatch = (long) Math.pow(10, 5), rightestPatch = 0;
        for (int p = 0; p < patchNum; p++) {
            patches[p] = Stream.of(read.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
            leftestPatch = Math.min(leftestPatch, patches[p][0]);
            rightestPatch = Math.max(rightestPatch, patches[p][1]);
        }
        Arrays.sort(patches, Comparator.comparingLong(a -> a[0]));

        // just an ez binsearch for the answer
        long validDistance = -1;
        long lowerBound = 1;  // they said a solution was guaranteed to exist where the distance >= 1
        long upperBound = rightestPatch - leftestPatch;  // yes ik binsearch is log n but optimization doesn't hurt
        while (lowerBound <= upperBound) {
            long toSearch = (upperBound + lowerBound) / 2;
            if (fittable(patches, cowNum, toSearch)) {
                lowerBound = toSearch + 1;
                validDistance = toSearch;
            } else {
                upperBound = toSearch - 1;
            }
        }

        PrintWriter written = new PrintWriter("socdist.out");
        written.println(validDistance);
        written.close();
        System.out.println(validDistance);
        System.out.printf("ok it took %d ms and i have to go now immediately%n", System.currentTimeMillis() - start);
    }

    // tests if a distance is valid by greedily squeezing cows wherever there's space
    static boolean fittable(long[][] patches, int cows, long distance) {  // assumes patches are sorted, use at your own risk
        long lastCow = Integer.MIN_VALUE;  // the position of the last cow we just put them in
        int lastPatch = 0;  // only start from the last patch (otherwise it takes too long bc it's like at the higher end of n^2)
        for (int c = 0; c < cows; c++) {
            boolean stillSpace = false;
            for (int i = lastPatch; i < patches.length; i++) {
                long[] p = patches[i];
                if (lastCow + distance <= p[1]) {
                    lastCow = Math.max(lastCow + distance, p[0]);
                    lastPatch = i;
                    stillSpace = true;
                    break;
                }
            }
            if (!stillSpace) {
                return false;
            }
        }
        return true;
    }
}
