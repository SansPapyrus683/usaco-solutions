package official.o2023.feb.gold;

import java.io.*;
import java.util.*;

// 2023 feb gold
public class EqualSubarrays {
    private static class Subarray {
        public int start;
        public int end;
        public long sum;

        public Subarray(int start, int end, long sum) {
            this.start = start;
            this.end = end;
            this.sum = sum;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int len = Integer.parseInt(read.readLine());
        long[] arr = Arrays.stream(read.readLine().split(" "))
                .mapToLong(Long::parseLong).toArray();
        assert arr.length == len;

        Subarray[] subarrSums = new Subarray[arr.length * (arr.length + 1) / 2];
        int ind = 0;
        for (int l = 0; l < len; l++) {
            long sum = 0;
            for (int r = l; r < len; r++) {
                sum += arr[r];
                subarrSums[ind++] = new Subarray(l, r, sum);
            }
        }
        Arrays.sort(subarrSums, Comparator.comparingLong(s -> s.sum));

        for (int i = 0; i < len; i++) {
            List<Long> containSums = new ArrayList<>();
            List<Long> otherSums = new ArrayList<>(Collections.singletonList(-Long.MAX_VALUE / 2));
            for (Subarray s : subarrSums) {
                if (s.start <= i && i <= s.end) {
                    containSums.add(s.sum);
                } else {
                    otherSums.add(s.sum);
                }
            }
            otherSums.add(Long.MAX_VALUE / 2);

            long minAdd = Long.MAX_VALUE;
            int max_lt = 0;
            for (long s : containSums) {
                while (otherSums.get(max_lt + 1) < s) {
                    max_lt++;
                }
                minAdd = Math.min(minAdd, s - otherSums.get(max_lt));
                minAdd = Math.min(minAdd, otherSums.get(max_lt + 1) - s);
            }

            System.out.println(minAdd);
        }
    }
}
