package official.o2025.feb.bronze;

import java.io.*;
import java.util.*;

public class PrintingSequences {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            StringTokenizer initial = new StringTokenizer(read.readLine());
            int len = Integer.parseInt(initial.nextToken());
            int prNum = Integer.parseInt(initial.nextToken());
            int[] arr = Arrays.stream(read.readLine().split(" "))
                    .mapToInt(Integer::parseInt).toArray();
            assert arr.length == len;

            System.out.println(printable(arr, prNum) ? "YES" : "NO");
        }
    }

    private static boolean printable(int[] seq, int prints) {
        if (prints == 1) {
            return Arrays.stream(seq).allMatch(i -> i == seq[0]);
        }

        int period = 1;
        for (; period <= seq.length; period++) {
            if (seq.length % period != 0) {
                continue;
            }

            boolean valid = true;
            for (int i = 0; i < seq.length; i++) {
                if (seq[i] != seq[i % period]) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                break;
            }
        }

        if (period == 1) {
            return true;
        }
        for (int i = 1; i < period; i++) {
            int[] left = Arrays.copyOfRange(seq, 0, i);
            int[] right = Arrays.copyOfRange(seq, i, period);
            for (int leftAmt = 1; leftAmt < prints; leftAmt++) {
                if (printable(left, leftAmt) && printable(right, prints - leftAmt)) {
                    return true;
                }
            }
        }

        return false;
    }
}
