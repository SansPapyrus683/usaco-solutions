package official.o2014.mar.gold.foiledAgain;

import java.io.*;
import java.util.*;

// 2014 mar gold
public class Sabotage {
    private static final int PRECISION = (int) Math.pow(10, 5);  // be a bit more precise just in case
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("sabotage.in"));
        int machineNum = Integer.parseInt(read.readLine());
        int[] machines = new int[machineNum];
        for (int m = 0; m < machineNum; m++) {
            machines[m] = Integer.parseInt(read.readLine());
        }

        int lo = 0;
        int hi = Arrays.stream(machines).max().getAsInt() * PRECISION;
        double valid = 0;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (reachableAvg(machines, (double) mid / PRECISION)) {
                hi = mid - 1;
                valid = (double) mid / PRECISION;
            } else {
                lo = mid + 1;
            }
        }

        PrintWriter written = new PrintWriter("sabotage.out");
        written.printf("%.3f%n", valid);
        written.close();
        System.out.printf("%.3f%n", valid);
        System.out.printf("omg!!11!! what an evil plan!!111!1! (%d ms)%n", System.currentTimeMillis() - start);
    }

    private static boolean reachableAvg(int[] machines, double avg) {
        double[] normalizedMachines = Arrays.stream(machines).mapToDouble(m -> m - avg).toArray();
        double normalizedOutput = Arrays.stream(normalizedMachines).sum();
        double maxSum = normalizedMachines[1];
        double currSum = normalizedMachines[1];
        for (int m = 2; m < machines.length - 1; m++) {
            currSum = Math.max(currSum, 0);
            currSum += normalizedMachines[m];
            maxSum = Math.max(maxSum, currSum);
        }
        return normalizedOutput - maxSum <= 0;
    }
}
