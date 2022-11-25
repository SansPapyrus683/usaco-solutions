package official.o2013.feb.silver.milkTime;

import java.io.*;
import java.util.*;

// 2013 feb silver
public class MSched {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("msched.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int reqNum = Integer.parseInt(initial.nextToken());
        int[] cows = new int[cowNum];
        ArrayList<Integer>[] milkAfter = new ArrayList[cowNum];
        ArrayList<Integer>[] milkBefore = new ArrayList[cowNum];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Integer.parseInt(read.readLine());
            milkAfter[c] = new ArrayList<>();
            milkBefore[c] = new ArrayList<>();
        }
        for (int r = 0; r < reqNum; r++) {
            StringTokenizer req = new StringTokenizer(read.readLine());
            // make the cows 1-indexed
            int before = Integer.parseInt(req.nextToken()) - 1;
            int after = Integer.parseInt(req.nextToken()) - 1;
            milkAfter[before].add(after);
            milkBefore[after].add(before);
        }

        int[] milkBeforeNum = new int[cowNum];
        for (List<Integer> after : milkAfter) {
            for (int ac : after) {
                milkBeforeNum[ac]++;
            }
        }

        // sauce: https://www.geeksforgeeks.org/lexicographically-smallest-topological-ordering/
        // i used an arraydeque instead because i don't need to find the lexicographically smallest
        ArrayDeque<Integer> frontier = new ArrayDeque<>();
        for (int c = 0; c < cowNum; c++) {
            if (milkBeforeNum[c] == 0) {
                frontier.add(c);
            }
        }
        int visitedNum = 0;
        int[] order = new int[cowNum];
        int cowAt = 0;
        while (!frontier.isEmpty()) {
            int curr = frontier.poll();
            assert cowAt != cowNum;
            order[cowAt++] = curr;
            for (int ac : milkAfter[curr]) {
                if (--milkBeforeNum[ac] == 0) {
                    frontier.add(ac);
                }
            }
            visitedNum++;
        }
        assert visitedNum == cowNum;

        // this[i] = min time (globally) to milk cow i
        int[] minTimes = new int[cowNum];
        for (int c : order) {
            minTimes[c] = milkBefore[c].stream().mapToInt(prev -> minTimes[prev]).max().orElse(0) + cows[c];
        }
        int totalMin = Arrays.stream(minTimes).max().getAsInt();

        PrintWriter written = new PrintWriter("msched.out");
        written.println(totalMin);
        written.close();
        System.out.println(totalMin);
        System.out.printf("these cows are so finicky istg: %d ms%n", System.currentTimeMillis() - start);
    }
}
