package official.o2025.jan.bronze;

import java.io.*;
import java.util.*;

public class MooinTimeII {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int len = Integer.parseInt(read.readLine());
        int[] arr = Arrays.stream(read.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        assert arr.length == len;

        Map<Integer, Integer> freq = new HashMap<>();
        int[] canMoo = new int[len];
        Arrays.fill(canMoo, -1);
        for (int i = len - 1; i >= 0; i--) {
            freq.put(arr[i], freq.getOrDefault(arr[i], 0) + 1);
            if (freq.get(arr[i]) == 2) {
                canMoo[i] = arr[i];
            }
        }

        Set<Integer> seen = new HashSet<>();
        long moos = 0;
        for (int i = 0; i < len; i++) {
            if (canMoo[i] != -1) {
                moos += seen.size() - (seen.contains(arr[i]) ? 1 : 0);
            }
            seen.add(arr[i]);
        }

        System.out.println(moos);
    }
}
