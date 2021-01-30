package official.o2021.jan.silver;

import java.io.*;
import java.util.*;

/**
 * 2021 jan silver
 * 5 4
 * 1 3
 * 1 2
 * 2 3
 * 2 4 should output 4, 4, 3, 4, and 1, each on a newline
 */
public final class Groovy {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int moveNum = Integer.parseInt(initial.nextToken());
        int[][] moves = new int[moveNum][2];
        for (int m = 0; m < moveNum; m++) {
            moves[m] = Arrays.stream(read.readLine().split(" ")).mapToInt(p -> Integer.parseInt(p) - 1).toArray();
        }

        long start = System.currentTimeMillis();
        ArrayList<int[]>[] swapWith = new ArrayList[cowNum];
        for (int c = 0; c < cowNum; c++) {
            swapWith[c] = new ArrayList<>();
        }
        for (int m = 0; m < moveNum; m++) {
            int[] move = moves[m];
            swapWith[move[0]].add(new int[] {m, move[1]});
            swapWith[move[1]].add(new int[] {m, move[0]});
        }

        // hashset because we might end up visiting a pos multiple times in a cycle, so just keep a set
        HashSet<Integer>[] uniquePos = new HashSet[cowNum];
        int[][] seenBefore = new int[cowNum][];
        for (int c = 0; c < cowNum; c++) {
            uniquePos[c] = new HashSet<>(Collections.singletonList(c));
            seenBefore[c] = new int[swapWith[c].size()];
            Arrays.fill(seenBefore[c], -1);
        }
        for (int c = 0; c < cowNum; c++) {
            if (swapWith[c].size() == 0) {
                continue;
            }
            int[] state = new int[] {c, 0, swapWith[c].get(0)[0]};
            // i have noticed that cows will always cycle back to their original position
            while (seenBefore[state[0]][state[1]] == -1) {
                seenBefore[state[0]][state[1]] = c;
                state[0] = swapWith[state[0]].get(state[1])[1];

                int newMoveInd = bisectLeft(swapWith[state[0]], state[2] + 1, 0);
                newMoveInd = newMoveInd == swapWith[state[0]].size() ? 0 : newMoveInd;  // wrap the index around
                state[1] = newMoveInd;
                state[2] = swapWith[state[0]].get(state[1])[0];
                uniquePos[c].add(state[0]);
            }
            uniquePos[c] = uniquePos[seenBefore[state[0]][state[1]]];
        }

        // without this this TLEs for half the test cases lol
        StringBuilder total = new StringBuilder();
        for (HashSet<Integer> p : uniquePos) {
            total.append(p.size()).append('\n');
        }
        System.out.print(total);
        System.err.printf("%d ms. dear god.%n", System.currentTimeMillis() - start);
    }

    // a horrid function to make, but a small price to pay for O(log n)
    private static int bisectLeft(List<int[]> arr, int elem, int compInd) {
        int lo = 0;
        int hi = arr.size();
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr.get(mid)[compInd] < elem) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
