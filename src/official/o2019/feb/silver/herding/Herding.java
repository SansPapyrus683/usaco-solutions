package official.o2019.feb.silver.herding;

import java.io.*;
import java.util.*;

// 2019 feb silver
import java.io.*;
import java.util.*;

public final class Herding {
    public static void main(String[] args) throws IOException{
        BufferedReader read = new BufferedReader(new FileReader("herding.in"));

        int cowNum = Integer.parseInt(read.readLine());
        int[] herd = new int[cowNum];
        for (int i = 0; i < cowNum; i++) {
            herd[i] = Integer.parseInt(read.readLine());
        }
        Arrays.sort(herd);

        int minMoves = Integer.MAX_VALUE;
        if (herd[cowNum - 2] - herd[0] == cowNum - 2 && herd[cowNum - 1] - herd[cowNum - 2] > 2) {
            minMoves = 2;
        } else if (herd[cowNum - 1] - herd[1] == cowNum - 2 && herd[1] - herd[0] > 2) {
            minMoves = 2;
        } else {
            // min is the patch of length n that has the least # of gaps
            int farthestCow = 0;
            for (int currCow = 0; currCow < cowNum; currCow++) {
                while (
                        farthestCow + 1 < cowNum
                        && herd[farthestCow + 1] - herd[currCow] < cowNum
                ) {
                    farthestCow++;
                }
                minMoves = Math.min(minMoves, cowNum - (farthestCow - currCow + 1));
            }
        }

        // calculate the number of empty cells
        int gapNum = 0;
        for (int i = 1; i < cowNum; i++) {
            gapNum += herd[i] - herd[i - 1] - 1;
        }
        // max is the maximum of the total gap minus either the first or last gap
        int maxMoves = Math.max(
                gapNum - (herd[1] - herd[0] - 1),
                gapNum - (herd[cowNum - 1] - herd[cowNum - 2] - 1)
        );

        PrintWriter written = new PrintWriter("herding.out");
        written.println(minMoves);
        written.println(maxMoves);
        written.close();
    }
}
