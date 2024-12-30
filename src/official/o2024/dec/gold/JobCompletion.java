package official.o2024.dec.gold;

import java.io.*;
import java.util.*;

/** 2024 dec gold (had to have help w/ this one lol) */
public class JobCompletion {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int test = 0; test < testNum; test++) {
            int taskNum = Integer.parseInt(read.readLine());
            long[][] tasks = new long[taskNum][2];
            for (long[] t : tasks) {
                StringTokenizer task = new StringTokenizer(read.readLine());
                t[0] = Long.parseLong(task.nextToken());
                t[1] = Long.parseLong(task.nextToken());
            }
            Arrays.sort(tasks, Comparator.comparingLong(t -> t[0] + t[1]));

            TreeMap<Long, Integer> taken = new TreeMap<>();
            long time = 0;
            for (long[] t : tasks) {
                if (time <= t[0]) {
                    time += t[1];
                    taken.put(t[1], taken.getOrDefault(t[1], 0) + 1);
                } else {
                    final long last = taken.lastKey();
                    if (time - last <= t[0] && t[1] < last) {
                        if (taken.get(last) == 1) {
                            taken.remove(last);
                        } else {
                            taken.put(last, taken.get(last) - 1);
                        }
                        time = time - last + t[1];
                        taken.put(t[1], taken.getOrDefault(t[1], 0) + 1);
                    }
                }
            }

            int total = 0;
            for (Map.Entry<Long, Integer> e : taken.entrySet()) {
                total += e.getValue();
            }
            System.out.println(total);
        }
    }
}
