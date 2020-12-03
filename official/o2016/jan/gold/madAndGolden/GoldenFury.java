import java.io.*;
import java.util.*;

// 2016 jan gold
public class GoldenFury {
    private static final int MAX = 420696969;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("angry.in"));
        int hayNum = Integer.parseInt(read.readLine());
        int[] haybales = new int[hayNum];
        for (int h = 0; h < hayNum; h++) {
            haybales[h] = Integer.parseInt(read.readLine());
        }
        Arrays.sort(haybales);

        double validSoFar = -1;  // not taking any chances with floating point (greater precision)
        long lowerBound = 0;
        long upperBound = (long) Math.pow(10, 9) * 10;  // * 10 for the decimal precision
        while (lowerBound <= upperBound) {
            long toSearch = (lowerBound + upperBound) / 2;
            if (canKillAll(toSearch / 10.0, haybales)) {
                upperBound = toSearch - 1;
                validSoFar = toSearch / 10.0;
            } else {
                lowerBound = toSearch + 1;
            }
        }

        PrintWriter written = new PrintWriter("angry.out");
        written.printf("%.1f%n", validSoFar);
        written.close();
        System.out.printf("%.1f%n", validSoFar);
        System.out.printf("%d ms. boom.%n", System.currentTimeMillis() - start);
    }

    static boolean canKillAll(double power, int[] haybales) {  // assumes haybales is sorted, which we did do above
        int[] leftPowers = new int[haybales.length];  // min amt of power we have to launch at haybale h to destroy everything to the left
        int[] rightPowers = new int[haybales.length];  // and everything to the right
        int startIndex = 0;
        Arrays.fill(leftPowers, MAX);  // not MAX_VALUE because of overflow
        leftPowers[0] = 0;
        for (int h = 1; h < haybales.length; h++) {
            // move up the explosion start to a place where the leftPower index can cover it
            while (startIndex + 1 < h &&
                    haybales[h] - haybales[startIndex + 1] > leftPowers[startIndex + 1] + 1) {
                startIndex++;
            }
            leftPowers[h] = Math.min(Math.abs(haybales[startIndex] - haybales[h]), leftPowers[startIndex + 1] + 1);
        }
        // now basically do the exact same thing for moving to the right
        startIndex = haybales.length - 1;
        Arrays.fill(rightPowers, MAX);
        rightPowers[haybales.length - 1] = 0;
        for (int h = haybales.length - 2; h >= 0; h--) {
            while (startIndex - 1 > h &&
                    haybales[startIndex - 1] - haybales[h] > rightPowers[startIndex - 1] + 1) {
                startIndex--;
            }
            rightPowers[h] = Math.min(Math.abs(haybales[startIndex] - haybales[h]), rightPowers[startIndex - 1] + 1);
        }

        int endIndex = 0;
        for (int explosionStart = 0; explosionStart < haybales.length; explosionStart++) {
            while (endIndex + 1 < haybales.length && haybales[endIndex + 1] - haybales[explosionStart] <= 2 * power) {
                endIndex++;
            }
            if (leftPowers[explosionStart] <= power - 1 && rightPowers[endIndex] <= power - 1) {
                return true;
            }
        }
        return false;
    }
}
