package official.o2024.jan.bronze;

import java.io.*;
import java.util.*;

/**
 * 2024 jan bronze
 * 6 4
 * 0 3
 * 1 1
 * 1 2
 * 1 1
 * 0 1
 * 1 1 should output 3
 */
public class Cannonball {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int locNum = Integer.parseInt(initial.nextToken());
        int start = Integer.parseInt(initial.nextToken()) - 1;  // make 0-indexed
        boolean[] types = new boolean[locNum];
        int[] strength = new int[locNum];
        for (int i = 0; i < locNum; i++) {
            StringTokenizer loc = new StringTokenizer(read.readLine());
            // 0 means jump pad, 1 means target
            types[i] = Integer.parseInt(loc.nextToken()) != 0;
            strength[i] = Integer.parseInt(loc.nextToken());
        }

        int at = start;
        int vel = 1;
        Set<Integer> broken = new HashSet<>();
        int[] prevVels = new int[locNum];
        Arrays.fill(prevVels, Integer.MAX_VALUE);
        while (0 <= at && at < locNum) {
            if (types[at]) {
                if (Math.abs(vel) >= strength[at]) {
                    broken.add(at);
                }
            } else {
                vel = -vel + strength[at] * (vel < 0 ? 1 : -1);
            }
            // not sure if checking before or after is more correct
            if (prevVels[at] == vel) {
                break;
            }
            prevVels[at] = vel;
            at += vel;
        }

        System.out.println(broken.size());
    }
}
