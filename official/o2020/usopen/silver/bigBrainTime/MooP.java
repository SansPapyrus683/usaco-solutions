package official.o2020.usopen.silver.bigBrainTime;

import java.io.*;
import java.util.*;

// 2020 usopen silver (copied lol)
public class MooP {
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("moop.in"));
        int particleNum = Integer.parseInt(read.readLine());
        int[][] particles = new int[particleNum][2];
        for (int p = 0; p < particleNum; p++) {
            particles[p] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(particles, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

        int[] leftMinY = new int[particleNum];  // the running min/max y from the left and right respectively
        int[] rightMaxY = new int[particleNum];
        leftMinY[0] = particles[0][1];
        rightMaxY[particleNum - 1] = particles[particleNum - 1][1];
        for (int p = 1; p < particleNum; p++) {
            leftMinY[p] = Math.min(leftMinY[p - 1], particles[p][1]);
        }
        for (int p = particleNum - 2; p >= 0; p--) {
            rightMaxY[p] = Math.max(rightMaxY[p + 1], particles[p][1]);
        }

        int interactionPools = 1;  // we start with one "pool" always
        for (int p = 0; p < particleNum - 1; p++) {
            /* if the minimum is greater than the maximum, of course the two can't interact
             * and we'll have to make a new pool (or divide the particles, idk)
             * kinda like
             *   •      <- obvi these two can't interact
             *       •
             * it's the running minimum because the previous particles can act as a sort of conduit between that particle
             * and the other particles on the right
             * and the x doesn't matter bc we sorted it alr lol
             * */
            if (leftMinY[p] > rightMaxY[p + 1]) {
                interactionPools++;
            }
        }

        PrintWriter written = new PrintWriter("moop.out");
        written.println(interactionPools);
        written.close();
        System.out.println(interactionPools);
        System.out.printf("you absolute buffoon, how did you write code that took a huge %d ms%n", System.currentTimeMillis() - timeStart);
    }
}
