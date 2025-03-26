package official.o2024.jan.gold;

import java.io.*;
import java.util.*;

public class Napsort {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            int len = Integer.parseInt(read.readLine());
            long[] arr = Arrays.stream(read.readLine().split(" "))
                    .mapToLong(Long::parseLong).toArray();
            assert arr.length == len;

            Arrays.sort(arr);
            long allNap = arr[arr.length - 1];

            int lo = 1;
            int hi = len;
            int valid = -1;
            while (lo <= hi) {
                int mid = (lo + hi) / 2;
                if ((long) mid * (mid + 1) / 2 >= allNap) {
                    hi = mid - 1;
                    continue;
                }

                long[][] events = new long[mid + len][];
                for (int i = 0; i < len; i++) {
                    events[i] = new long[] { arr[i], 1 };
                }
                long acc = 0;
                for (int i = mid; i >= 1; i--) {
                    acc += i;
                    events[len + mid - i] = new long[] { acc, 0 };
                }
                Arrays.sort(events, Arrays::compare);

                Deque<Integer> bessie = new ArrayDeque<>();
                Deque<Integer> helper = new ArrayDeque<>();
                for (int i = 0; i < events.length; i++) {
                    (events[i][1] == 1 ? helper : bessie).addLast(i);
                }

                while (!bessie.isEmpty()) {
                    while (!helper.isEmpty() && bessie.peek() > helper.peek()) {
                        helper.pop();
                    }
                    bessie.pop();
                    if (!helper.isEmpty()) {
                        helper.pop();
                    }
                }

                if (helper.isEmpty()) {
                    valid = mid;
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }

            long bessieBest = (long) valid * (valid + 1) / 2;
            System.out.println(valid == -1 ? allNap : Math.min(bessieBest, allNap));
        }
    }
}
