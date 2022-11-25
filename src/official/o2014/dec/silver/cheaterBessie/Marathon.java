package official.o2014.dec.silver.cheaterBessie;

import java.io.*;
import java.util.*;

// 2014 dec silver (shamelessly copied from the official solution)
public class Marathon {
    static int[][] distances;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("marathon.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int checkpointNum = Integer.parseInt(initial.nextToken());
        int[][] checkpoints = new int[checkpointNum][2];
        int badnessLevel = Integer.parseInt(initial.nextToken());
        for (int i = 0; i < checkpointNum; i++) {
            checkpoints[i] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        distances = new int[checkpoints.length][checkpoints.length];
        for (int p1 = 0; p1 < checkpoints.length; p1++) {  // we probably won't even end up using half the things here lol
            for (int p2 = 0; p2 < checkpoints.length; p2++) {  // but it takes like no time so idk and idc really
                distances[p1][p2] = pointDist(checkpoints[p1], checkpoints[p2]);
            }
        }

        // this array[n][k] is the min distance it takes to get to point n (0-indexed) having skipped EXACTLY k points
        int[][] minMarathonDistances = new int[checkpointNum][badnessLevel + 1];
        for (int[] d : minMarathonDistances) {
            Arrays.fill(d, Integer.MAX_VALUE);
        }
        minMarathonDistances[0][0] = 0;

        int atRn = 0;
        for (int[] p : minMarathonDistances) {
            int alrSkipped = 0;
            for (int value : p) {
                if (value == Integer.MAX_VALUE) {  // stop for no integer overflow
                    break;
                }
                for (int i = 0; i <= badnessLevel; i++) {
                    // make sure she skips within her limits
                    if (alrSkipped + i > badnessLevel || atRn + i + 1 > checkpointNum - 1) {
                        break;
                    }
                    minMarathonDistances[atRn + i + 1][alrSkipped + i] = Math.min(minMarathonDistances[atRn + i + 1][alrSkipped + i],
                                                                                  value + distances[atRn][atRn + i + 1]);
                }
                alrSkipped++;
            }
            atRn++;
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("marathon.out"));
        written.println(minMarathonDistances[checkpointNum - 1][badnessLevel]);
        written.close();
        System.out.println(minMarathonDistances[checkpointNum - 1][badnessLevel]);
        System.out.printf("took about %d ms idk man%n", System.currentTimeMillis() - start);
    }

    static int pointDist(int[] p1, int[] p2) {
        return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
    }
}
