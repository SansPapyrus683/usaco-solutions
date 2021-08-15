/*
ID: kevinsh4
TASK: ariprog
LANG: JAVA
 */
package section1.part5.ariprog;

import java.io.*;
import java.util.*;

public final class AriProg {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("ariprog.in"));
        ArrayList<int[]> progressions = allProgressions(
                Integer.parseInt(read.readLine()), Integer.parseInt(read.readLine())
        );
        progressions.sort((a, b) -> a[1] != b[1] ? a[1] - b[1] : a[0] - b[0]);

        PrintWriter written = new PrintWriter("ariprog.out");
        boolean anyProgression = false;
        for (int[] p : progressions) {
            written.printf("%s %s%n", p[0], p[1]);
            anyProgression = true;
        }
        if (!anyProgression) {
            written.println("NONE");
        }
        written.close();
        System.out.printf("%d ms- huh wonder if that's a bisquare too lol%n", System.currentTimeMillis() - start);
    }

    static ArrayList<int[]> allProgressions(int progLen, int upperBound) {
        HashSet<Integer> bisquares = new HashSet<>();
        for (int i = 0; i <= upperBound; i++) {
            for (int j  = 0; j <= upperBound; j++) {
                bisquares.add(i * i + j * j);
            }
        }
        ArrayList<Integer> sortedBisqaures = new ArrayList<>(bisquares);
        sortedBisqaures.sort(Comparator.comparingInt(i -> i));
        ArrayList<int[]> valid = new ArrayList<>();
        for (int end = progLen - 1; end < sortedBisqaures.size(); end++) {
            int diffUBound = sortedBisqaures.get(end) / (progLen - 1);
            for (int befEnd = end - 1;
                 sortedBisqaures.get(end) - sortedBisqaures.get(befEnd) <= diffUBound;
                 befEnd--) {
                int elementAt = sortedBisqaures.get(befEnd);
                int commDiff = sortedBisqaures.get(end) - sortedBisqaures.get(befEnd);
                boolean good = true;
                for (int i = 0; i < progLen - 2; i++) {
                    elementAt -= commDiff;
                    if (!bisquares.contains(elementAt)) {
                        good = false;
                        break;
                    }
                }
                if (good) {
                    valid.add(new int[] {elementAt, commDiff});
                }
            }
        }
        return valid;
    }
}

