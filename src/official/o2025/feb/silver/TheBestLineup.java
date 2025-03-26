package official.o2025.jan.silver;

import java.io.*;
import java.util.*;

public class TheBestLineup {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            int len = Integer.parseInt(read.readLine());
            int[] arr = Arrays.stream(read.readLine().split(" "))
                    .mapToInt(Integer::parseInt).toArray();
            assert arr.length == len;

            int[] maxInd = new int[len];
            boolean[] unique = new boolean[len];
            int[] underMaxInd = new int[len];
            Arrays.fill(underMaxInd, Integer.MAX_VALUE);
            TreeMap<Integer, Deque<Integer>> freq = new TreeMap<>();
            for (int i = len - 1; i >= 0; i--) {
                if (!freq.containsKey(arr[i])) {
                    freq.put(arr[i], new ArrayDeque<>());
                }
                freq.get(arr[i]).addLast(i);
                
                Map.Entry<Integer, Deque<Integer>> max = freq.lastEntry();
                maxInd[i] = max.getValue().getLast();

                unique[i] = max.getValue().size() == 1;

                Map.Entry<Integer, Deque<Integer>> b4 = freq.lowerEntry(max.getKey());
                if (b4 != null) {
                    underMaxInd[i] = b4.getValue().getLast();
                }
            }

            StringBuilder best = new StringBuilder();
            int at = 0;
            while (at < len && !(maxInd[at] > underMaxInd[at] && unique[at])) {
                best.append(arr[maxInd[at]]).append(' ');
                at = maxInd[at] + 1;
            }

            if (at < len) {
                best.append(arr[maxInd[at]]).append(' ');
                arr[maxInd[at]] = Integer.MIN_VALUE;

                maxInd = new int[len];
                TreeMap<Integer, Integer> freqRedo = new TreeMap<>();
                for (int i = len - 1; i >= 0; i--) {
                    freqRedo.put(arr[i], i);
                    maxInd[i] = freqRedo.lastEntry().getValue();
                }

                while (at < len) {
                    at = maxInd[at];
                    if (arr[at] != Integer.MIN_VALUE) {
                        best.append(arr[at]).append(' ');
                    }
                    at++;
                }
            }

            best.setLength(best.length() - 1);
            System.out.println(best);
        }
    }
}
