package official.o2016.jan.gold.madAndGolden;

import java.io.*;
import java.util.*;

// 2016 jan gold
public final class GoldenFury {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("angry.in"));
        int hayNum = Integer.parseInt(read.readLine());
        TreeSet<Double> haybales = new TreeSet<>();
        for (int h = 0; h < hayNum; h++) {
            haybales.add(Double.parseDouble(read.readLine()));
        }

        double valid = -1;
        long loPower = 0;
        long hiPower = (long) (haybales.last() - haybales.first()) * 10L;
        while (loPower <= hiPower) {
            long midPower = (loPower + hiPower) / 2;
            if (canKillAll(midPower / 10.0, haybales)) {
                hiPower = midPower - 1;
                valid = midPower / 10.0;
            } else {
                loPower = midPower + 1;
            }
        }

        PrintWriter written = new PrintWriter("angry.out");
        written.printf("%.1f%n", valid);
        written.close();
        System.out.printf("%.1f%n", valid);
        System.out.printf("%d ms. boom.%n", System.currentTimeMillis() - start);
    }

    private static boolean canKillAll(double power, TreeSet<Double> haybales) {
        long lowerLoc = (long) ((double) haybales.first()) * 10L;
        long upperLoc = (long) ((double) haybales.last()) * 10L;
        while (lowerLoc <= upperLoc) {
            long mid = (lowerLoc + upperLoc) / 2;
            boolean leftValid = leftWipeout(power, mid / 10.0, haybales);
            boolean rightValid = rightWipeout(power, mid / 10.0, haybales);
            if (leftValid && rightValid) {
                return true;
            } else if (!leftValid) {
                upperLoc = mid - 1;
            } else {
                lowerLoc = mid + 1;
            }
        }
        return false;
    }

    private static boolean leftWipeout(double power, double pos, TreeSet<Double> haybales) {
        if (haybales.isEmpty()) {
            return true;
        }
        if (haybales.last() < pos - power) {
            return false;
        }
        double firstBale = haybales.first();
        while (power > 0) {
            double leftest = haybales.ceiling(pos - power);
            if (leftest == firstBale) {
                return true;
            }
            if (pos <= leftest) {
                return false;
            }
            pos = leftest;
            power--;
        }
        return false;
    }

    private static boolean rightWipeout(double power, double pos, TreeSet<Double> haybales) {
        if (haybales.isEmpty()) {
            return true;
        }
        if (pos + power < haybales.first()) {
            return false;
        }
        double lastBale = haybales.last();
        while (power > 0) {
            double righest = haybales.floor(pos + power);
            if (righest == lastBale) {
                return true;
            }
            if (righest <= pos) {
                return false;
            }
            pos = righest;
            power--;
        }
        return false;
    }
}
