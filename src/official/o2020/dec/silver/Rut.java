package official.o2020.dec.silver;

import java.io.*;
import java.util.*;

/**
 * 2020 dec silver
 * 6
 * E 3 5
 * N 5 3
 * E 4 6
 * E 10 4
 * N 11 1
 * E 9 2 should output 0, 0, 1, 2, 1, and 0, each on a newline
 */
public class Rut {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());
        int[][] cows = new int[cowNum][2];
        ArrayList<Integer> eastCowIndices = new ArrayList<>();
        ArrayList<Integer> northCowIndices = new ArrayList<>();

        for (int c = 0; c < cowNum; c++) {
            String[] cowInfo = read.readLine().split(" ");
            if (!cowInfo[0].equalsIgnoreCase("N") && !cowInfo[0].equalsIgnoreCase("E")) {
                throw new IllegalArgumentException("hold on i thought directions could only go N/E, what's " + cowInfo[0] + "!");
            }
            if (cowInfo[0].equalsIgnoreCase("N")) {
                northCowIndices.add(c);
            } else {
                eastCowIndices.add(c);
            }
            cows[c] = new int[] {Integer.parseInt(cowInfo[1]), Integer.parseInt(cowInfo[2])};
        }
        long start = System.currentTimeMillis();
        eastCowIndices.sort(Comparator.comparingInt(i -> cows[i][1]));  // sort east cows by y
        northCowIndices.sort(Comparator.comparingInt(i -> cows[i][0]));  // sort north cows by x

        int[] blame = new int[cowNum];
        boolean[] deadAlr = new boolean[cowNum];
        for (int e : eastCowIndices) {
            int[] eCow = cows[e];  // like an e-girl, but a cow
            for (int n : northCowIndices) {
                int[] nCow = cows[n];
                /*
                 * the arrangement has to be something like this for possibility of a block
                 * E
                 *      N (i mean tbh it's kinda obvi why this is true)
                 */
                if (!deadAlr[e] && !deadAlr[n] && eCow[0] < nCow[0] && nCow[1] < eCow[1]) {
                    int eCowTime = nCow[0] - eCow[0];
                    int nCowTime = eCow[1] - nCow[1];
                    if (eCowTime < nCowTime) {  // the east cow blocked the north cow
                        deadAlr[n] = true;
                        blame[e] += blame[n] + 1;  // carry over all the blame (plus the cow itself)
                    } else if (eCowTime > nCowTime) {  // the north cow blocked the east cow
                        deadAlr[e] = true;
                        blame[n] += blame[e] + 1;
                        break;
                    }
                }
            }
        }
        Arrays.stream(blame).forEach(System.out::println);
        System.err.printf("help me step-cow i'm stuck in a rut (i am so sorry): %d ms%n", System.currentTimeMillis() - start);
    }
}
