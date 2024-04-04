package official.o2023.jan.silver;

import java.io.*;
import java.util.*;

public class MooRoute {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(read.readLine());  // can't think of a good name srry
        int[] crosses = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        assert crosses.length == n;

        StringBuilder dirs = new StringBuilder();
        dirs.append("R".repeat(n));

        for (int i = 0; i < n; i++) {
            crosses[i]--;
        }
        // ok at the end of this crosses is all 1's...uh honestly idk why haha
        for (int i = n - 1; i >= 0; i--) {
            while (crosses[i] > 1) {
                int moveAmt = 1;
                crosses[i] -= 2;
                for (int j = i - 1; j >= 0 && crosses[j] > 1; j--) {
                    crosses[j] -= 2;
                    moveAmt++;
                }
                dirs.append("L".repeat(moveAmt)).append("R".repeat(moveAmt));
            }
            dirs.append('L');
        }

        System.out.println(dirs);
    }
}
