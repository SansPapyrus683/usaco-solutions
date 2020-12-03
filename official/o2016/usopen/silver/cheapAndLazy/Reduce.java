package official.o2016.usopen.silver.cheapAndLazy;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2016 usopen silver
public class Reduce {
    private static final int REMOVED = 3;
    static ArrayList<int[]> leastX = new ArrayList<>();
    static ArrayList<int[]> mostX = new ArrayList<>();
    static ArrayList<int[]> leastY = new ArrayList<>();
    static ArrayList<int[]> mostY = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("reduce.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[][] cows = new int[cowNum][2];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        // only makes sense to remove points from edges, so make a list containing points on each edge
        // 4 points so that even after we remove 3 points from 1 edge there's still a point for calcing the area
        Arrays.sort(cows, Comparator.comparingInt(c -> c[0]));
        for (int i = 0; i <= REMOVED; i++) {
            leastX.add(cows[i]);
        }
        for (int i = cowNum - 1; i >= cowNum - REMOVED - 1; i--) {
            mostX.add(cows[i]);
        }
        Arrays.sort(cows, Comparator.comparingInt(c -> c[1]));
        for (int i = 0; i <= REMOVED; i++) {
            leastY.add(cows[i]);
        }
        for (int i = cowNum - 1; i >= cowNum - REMOVED - 1; i--) {
            mostY.add(cows[i]);
        }

        // haha just brute force each possible set of points to remove on the edge
        int leastArea = Integer.MAX_VALUE;
        for (int sx = 0; sx <= REMOVED; sx++) {  // each of these points are "hey what if we removed from [0, i)?" so not inclusive
            for (int mx = 0; mx <= REMOVED; mx++) {
                for (int sy = 0; sy <= REMOVED; sy++) {
                    for (int my = 0; my <= REMOVED; my++) {
                        if (valid(sx, mx, sy, my)) {
                            leastArea = Math.min(leastArea,
                                    (mostX.get(mx)[0] - leastX.get(sx)[0]) *
                                    (mostY.get(my)[1] - leastY.get(sy)[1]));
                        }
                    }
                }
            }
        }

        PrintWriter written = new PrintWriter("reduce.out");
        written.println(leastArea);
        written.close();
        System.out.println(leastArea);
        System.out.printf("why in blue blazes did it take %d ms%n", System.currentTimeMillis() - start);
    }

    // check if the amt of removed points is <= REMOVED
    static boolean valid(int sx, int mx, int sy, int my) {
        HashSet<int[]> removed = new HashSet<>();  // i can do this bc it's all the same references to the same cows
        removed.addAll(leastX.subList(0, sx));
        removed.addAll(mostX.subList(0, mx));
        removed.addAll(leastY.subList(0, sy));
        removed.addAll(mostY.subList(0, my));
        return removed.size() <= REMOVED;
    }
}
