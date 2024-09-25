package official.o2024.usopen.silver;

import java.io.*;
import java.util.*;

public class BessieInterview {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int farmerNum = Integer.parseInt(initial.nextToken());
        int[] cows =
                Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        assert cows.length == cowNum;

        Map<Long, List<Integer>> finishes = new HashMap<>();
        PriorityQueue<Long> interviews = new PriorityQueue<>();
        for (int f = 0; f < farmerNum; f++) {
            interviews.add((long) cows[f]);
            if (!finishes.containsKey((long) cows[f])) {
                finishes.put((long) cows[f], new ArrayList<>());
            }
            finishes.get((long) cows[f]).add(f);
        }
        long doneTime;
        int cowAt = farmerNum;
        while (true) {
            long time = interviews.peek();
            List<Long> done = new ArrayList<>();
            while (!interviews.isEmpty() && interviews.peek() == time) {
                done.add(interviews.poll());
            }

            if (cowAt + done.size() > cowNum) {
                doneTime = time;
                break;
            }

            for (long i : done) {
                long finTime = i + cows[cowAt];
                interviews.add(finTime);
                if (!finishes.containsKey(finTime)) {
                    finishes.put(finTime, new ArrayList<>());
                }
                finishes.get(finTime).add(cowAt);
                cowAt++;
            }
        }

        Set<Long> visited = new HashSet<>();
        List<Long> todo = new ArrayList<>(Collections.singletonList(doneTime));
        boolean[] canTake = new boolean[farmerNum];
        while (!todo.isEmpty()) {
            long curr = todo.remove(todo.size() - 1);
            for (int prev : finishes.get(curr)) {
                if (prev < farmerNum) {
                    canTake[prev] = true;
                    continue;
                }
                long next = curr - cows[prev];
                if (!visited.contains(next)) {
                    visited.add(next);
                    todo.add(next);
                }
            }
        }

        System.out.println(doneTime);
        for (int f = 0; f < farmerNum; f++) {
            System.out.print(canTake[f] ? 1 : 0);
        }
        System.out.println();
    }
}
