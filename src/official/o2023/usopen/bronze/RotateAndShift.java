package official.o2023.usopen.bronze;

import java.io.*;
import java.util.*;

/** 2023 us open bronze */
public class RotateAndShift {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int activeNum = Integer.parseInt(initial.nextToken());
        int time = Integer.parseInt(initial.nextToken());
        int[] pos = Arrays.stream(read.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        assert pos.length == activeNum;
        assert pos[0] == 0;  // thank god for this invariant

        int[] newPos = new int[cowNum];
        int base = 0;
        for (int c = 0; c < cowNum; c++) {
            if (base + 1 < activeNum && c == pos[base + 1]) {
                base++;
            }

            int delta = (pos[(base + 1) % activeNum] - pos[base] + cowNum) % cowNum;
            int actualTime = time - (c - pos[base]);
            int kicks = (int) Math.ceil((double) actualTime / delta);
            newPos[(c + kicks * delta) % cowNum] = c;
        }

        for (int c = 0; c < cowNum - 1; c++) {
            System.out.print(newPos[c] + " ");
        }
        System.out.println(newPos[cowNum - 1]);
    }
}
