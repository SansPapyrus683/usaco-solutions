package official.o2017.dec.bronze.shuffle;

import java.io.*;
import java.util.*;

public class Shuffle {
    private static final int MOVE_NUM = 3;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("shuffle.in"));
        int cowNum = Integer.parseInt(read.readLine());
        // gonna assume this is a permutation
        int[] moveTo = Arrays.stream(read.readLine().split(" "))
                .mapToInt(p -> Integer.parseInt(p) - 1).toArray();
        int[] finalPos = Arrays.stream(read.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        assert moveTo.length == cowNum && finalPos.length == cowNum;

        int[] moveBack = new int[cowNum];
        for (int c = 0; c < cowNum; c++) {
            moveBack[moveTo[c]] = c;
        }

        int[] pos = finalPos.clone();
        for (int m = 0; m < MOVE_NUM; m++) {
            int[] newPos = new int[cowNum];
            for (int c = 0; c < cowNum; c++) {
                newPos[moveBack[c]] = pos[c];
            }
            pos = newPos;
        }

        PrintWriter written = new PrintWriter("shuffle.out");
        Arrays.stream(pos).forEach(written::println);
        written.close();
    }
}
