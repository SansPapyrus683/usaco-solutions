package official.o2025.feb.bronze;

import java.io.*;
import java.util.*;

public class MakingMexes {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int len = Integer.parseInt(read.readLine());
        int[] arr = Arrays.stream(read.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        assert arr.length == len;

        Map<Integer, Integer> freq = new HashMap<>();
        for (int i : arr) {
            freq.put(i, freq.getOrDefault(i, 0) + 1);
        }

        int rangeMissing = 0;
        for (int mex = 0; mex <= len; mex++) {
            int moveOut = freq.getOrDefault(mex, 0);
            System.out.println(moveOut + Math.max(rangeMissing - moveOut, 0));
            rangeMissing += freq.containsKey(mex) ? 0 : 1;
        }
    }
}
