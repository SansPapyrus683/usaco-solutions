package official.o2015.jan.gold.moovies;

import java.io.*;
import java.util.*;

// 2915 jan gold
public class Movie {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("movie.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int movieNum = Integer.parseInt(initial.nextToken());
        int hideTime = Integer.parseInt(initial.nextToken());

        int[] durations = new int[movieNum];
        int[][] startTimes = new int[movieNum][];
        for (int m = 0; m < movieNum; m++) {
            int[] show = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            durations[m] = show[0];
            startTimes[m] = Arrays.copyOfRange(show, 2, show.length);
            Arrays.sort(startTimes[m]);
        }

        int minMovies = Integer.MAX_VALUE;
        // using exactly the subset of movies specific in the index, this stores the max prefix of time we can cover
        int[] maxCovered = new int[1 << movieNum];
        maxCovered[0] = 0;  // just making it explicit
        for (int i = 1; i < (1 << movieNum); i++) {
            // see which movie we watch at the end
            for (int end = 0; end < movieNum; end++) {
                if ((i & (1 << end)) == 0) {
                    continue;
                }
                int prevTime = maxCovered[i & ~(1 << end)];
                // binserach for the optimal movie to watch
                int toWatch = bisectRight(startTimes[end], prevTime) - 1;
                if (toWatch != -1) {
                    maxCovered[i] = Math.max(maxCovered[i],  startTimes[end][toWatch] + durations[end]);
                }
            }
            if (maxCovered[i] >= hideTime) {
                minMovies = Math.min(minMovies, Integer.bitCount(i));
            }
        }

        int ans = minMovies == Integer.MAX_VALUE ? -1 : minMovies;
        PrintWriter written = new PrintWriter("movie.out");
        written.println(ans);
        written.close();
        System.out.println(ans);
        System.out.printf("bessie you absolute son of a gun: %d ms%n", System.currentTimeMillis() - start);
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
