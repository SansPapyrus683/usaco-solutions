package official.o2018.usopen.gold.orderOrder;

import java.io.*;
import java.util.*;

// 2018 usopen gold
public final class MilkOrder {
    private static final int[] INVALID = new int[] {-1};
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("milkorder.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int orderNum = Integer.parseInt(initial.nextToken());
        int[][] seqs = new int[orderNum][];
        for (int o = 0; o < orderNum; o++) {
            int[] rawOrder = Arrays.stream(read.readLine().split(" ")).mapToInt(c -> Integer.parseInt(c) - 1).toArray();
            seqs[o] = Arrays.copyOfRange(rawOrder, 1, rawOrder.length);
        }

        int lo = 0;
        int hi = seqs.length;
        int[] bestOrder = new int[cowNum];
        for (int c = 0; c < cowNum; c++) {  // default order if nothing's possible
            bestOrder[c] = c;
        }
        while (lo <= hi) {
            ArrayList<Integer>[] milkAfter = new ArrayList[cowNum];
            int mid = (lo + hi) / 2;
            for (int c = 0; c < cowNum; c++) {
                milkAfter[c] = new ArrayList<>();
            }
            for (int o = 0; o < mid; o++) {
                int[] sequence = seqs[o];  // don't think you have to add every pair for the sort
                for (int i = 0; i < sequence.length - 1; i++) {
                    milkAfter[sequence[i]].add(sequence[i + 1]);
                }
            }

            int[] order = milkingOrder(milkAfter);
            if (order != INVALID) {  // we can do this bc it's the same array
                bestOrder = order;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        StringBuilder ans = new StringBuilder();
        for (int c : bestOrder) {
            ans.append(c + 1).append(' ');  // make the cows start from 1 again
        }
        ans.setLength(ans.length() - 1);
        PrintWriter written = new PrintWriter("milkorder.out");
        written.println(ans);
        written.close();
        System.out.println(ans);
        System.out.printf("john my man why do you do this to me: %d ms%n", System.currentTimeMillis() - start);
    }

    private static int[] milkingOrder(List<Integer>[] milkAfter) {
        int cowNum = milkAfter.length;
        int[] milkBefore = new int[cowNum];
        for (List<Integer> after : milkAfter) {
            for (int ac : after) {
                milkBefore[ac]++;
            }
        }

        // sauce: https://www.geeksforgeeks.org/lexicographically-smallest-topological-ordering/
        PriorityQueue<Integer> frontier = new PriorityQueue<>();
        for (int c = 0; c < cowNum; c++) {
            if (milkBefore[c] == 0) {
                frontier.add(c);
            }
        }
        int visitedNum = 0;
        int[] order = new int[cowNum];
        int cowAt = 0;
        while (!frontier.isEmpty()) {
            int curr = frontier.poll();
            if (cowAt == cowNum) {
                return INVALID;
            }
            order[cowAt++] = curr;
            for (int ac : milkAfter[curr]) {
                if (--milkBefore[ac] == 0) {
                    frontier.add(ac);
                }
            }
            visitedNum++;
        }
        if (visitedNum != cowNum) {
            return INVALID;
        }
        return order;
    }
}
