package official.o2022.feb.bronze;

import java.io.*;
import java.util.*;

public class Photoshoot2 {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());
        // assuming that these are permutations
        int[] order = Arrays.stream(read.readLine().split(" ")).mapToInt(c -> Integer.parseInt(c) - 1).toArray();
        int[] target = Arrays.stream(read.readLine().split(" ")).mapToInt(c -> Integer.parseInt(c) - 1).toArray();
        assert order.length == cowNum && target.length == cowNum;

        int[] cowInd = new int[cowNum];
        for (int i = 0; i < cowNum; i++) {
            cowInd[target[i]] = i;
        }

        // if we map the cows in the target perm to 0, 1, 2, 3... this would be the init order
        int[] newOrder = new int[cowNum];
        for (int i = 0; i < cowNum; i++) {
            newOrder[i] = cowInd[order[i]];
        }

        int needCross = 0;
        int maxSoFar = 0;
        for (int i = 0; i < cowNum; i++) {
            if (newOrder[i] < maxSoFar) {
                needCross++;
            }
            maxSoFar = Math.max(maxSoFar, newOrder[i]);
        }

        System.out.println(needCross);
    }
}
