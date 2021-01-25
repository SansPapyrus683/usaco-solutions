package section3.part1.stamps;
/*
ID: kevinsh4
TASK: stamps
LANG: JAVA
*/
import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public final class Stamps {
    private static final int INVALID = 420696969;  // stupid integer overflow
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("stamps.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int maxStamps = Integer.parseInt(initial.nextToken());

        ArrayList<Integer> stamps = new ArrayList<>();
        String stampLine;
        while ((stampLine = read.readLine()) != null) {
            for (int s : Stream.of(stampLine.split(" ")).mapToInt(Integer::parseInt).toArray()) {
                stamps.add(s);
            }
        }
        Collections.sort(stamps);

        int[] minToMake = new int[maxStamps * stamps.get(stamps.size() - 1) + 1];
        Arrays.fill(minToMake, INVALID);
        minToMake[0] = 0;
        for (int s : stamps) {
            for (int v = 0; v < minToMake.length; v++) {
                // check if that value either can't be made or just alr used up all the stamps
                if (minToMake[v] >= maxStamps) {
                    continue;
                }
                minToMake[v + s] = Math.min(minToMake[v + s], minToMake[v] + 1);
            }
        }

        PrintWriter written = new PrintWriter("stamps.out");
        int highScore = 0;
        for (int m : minToMake) {
            if (m == INVALID) {
                break;
            }
            highScore++;
        }
        highScore--;  // we always overshoot by 1
        written.println(highScore);
        System.out.println(highScore);
        written.close();
        System.out.printf("dude epic it took %s ms%n", System.currentTimeMillis() - start);
    }
}
