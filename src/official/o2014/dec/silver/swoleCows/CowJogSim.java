package official.o2014.dec.silver.swoleCows;

import java.io.*;
import java.util.*;

/*
 * 2014 dec silver
 * somehow even though this should take smth like n^2 operations it still worked
 * what it does is that it simulates >= 1 joinings at a time, so it's a bit better than raw brute force
 */
public class CowJogSim {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cowjog.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int[][] cows = new int[Integer.parseInt(initial.nextToken())][2];
        int time = Integer.parseInt(initial.nextToken());
        for (int c = 0; c < cows.length; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();  // they're alr sorted, just so you know
        }
        Arrays.sort(cows, Comparator.comparingInt(c -> c[0]));

        int[][] simulationCows = new int[cows.length][2];  // stupid pass-by-reference
        for (int c = 0; c < cows.length; c++) {
            simulationCows[c] = cows[c].clone();
        }
        ArrayList<int[]> groups = new ArrayList<>(Arrays.asList(simulationCows.clone()));  // why the heck not

        int[] catchupTimes = new int[cows.length];
        int timeLeft = time;
        while (true) {
            Arrays.fill(catchupTimes, Integer.MAX_VALUE);  // reset the stuff
            int nextCatchupTime = Integer.MAX_VALUE;
            for (int c = 0; c < groups.size() - 1; c++) {  // haha c++
                int[] behindCow = groups.get(c);
                int[] cowInFront = groups.get(c + 1);
                if (behindCow[1] > groups.get(c + 1)[1]) {
                    int theoreticalTime = (cowInFront[0] - behindCow[0]) / (behindCow[1] - cowInFront[1]);
                    nextCatchupTime = Math.min(nextCatchupTime, theoreticalTime);
                    catchupTimes[c] = theoreticalTime;
                }
            }

            if (nextCatchupTime == Integer.MAX_VALUE || timeLeft - nextCatchupTime < 0) {
                break;  // either all the groups have been done or we've run out of time before another merge
            }

            for (int c = 0; c < groups.size(); c++) {
                if (catchupTimes[c] > nextCatchupTime) {
                    groups.get(c)[0] += groups.get(c)[1] * nextCatchupTime;
                } else {
                    groups.set(c, null);
                }
            }
            groups.removeAll(Collections.singletonList(null));
            timeLeft -= nextCatchupTime;
        }

        PrintWriter written = new PrintWriter("cowjog.out");
        written.println(groups.size());
        written.close();
        System.out.println(groups.size());
        System.out.printf("ok so it took around %d ms%n", System.currentTimeMillis() - start);
    }
}
