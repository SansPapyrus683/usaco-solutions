package official.o2019.jan.silver.epicViewHere;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2019 jan silver
public class Mountains {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("mountains.in"));
        int mountainNum = Integer.parseInt(read.readLine());
        int[][] mountains = new int[mountainNum][2];
        for (int m = 0; m < mountainNum; m++) {
            mountains[m] = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(mountains, Comparator.comparingInt(m -> -m[1]));

        int seenMountains = 0;
        for (int i = 0; i < mountainNum; i++) {
            int[] m = mountains[i];
            int[] groundArea = new int[] {m[0] - m[1], m[0] + m[1]};

            // only can be seen if for every prev mountain it doesn't cover
            // if mul. mountains are blocking another one, then at least 1 of those multiple is completely blocking it
            boolean covered = false;
            for (int j = 0; j < i; j++) {
                int[] upperMountain = mountains[j];
                if (upperMountain[0] - upperMountain[1] <= groundArea[0]
                        && groundArea[1] <= upperMountain[0] + upperMountain[1]) {
                    covered = true;
                    break;
                }
            }
            seenMountains += covered ? 0 : 1;
        }

        PrintWriter written = new PrintWriter("mountains.out");
        written.println(seenMountains);
        written.close();
        System.out.println(seenMountains);
        System.out.printf("*sigh* your crappy code took %d ms, what else?%n", System.currentTimeMillis() - start);
    }
}
