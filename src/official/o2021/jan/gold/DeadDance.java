package official.o2021.jan.gold;

import java.io.*;
import java.util.*;

/**
 * 2021 jan gold
 * 6 4 7
 * 1 2
 * 2 3
 * 3 4
 * 4 5 should output 5, 4, 3, 3, 3, and 1, each on a newline
 * (jesus christ this solution is convoluted as all hell)
 */
public class DeadDance {
    private static final long[] INVALID = new long[] {-1, -1};
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int moveNum = Integer.parseInt(initial.nextToken());
        long time = Long.parseLong(initial.nextToken());
        int[][] moves = new int[moveNum][2];
        for (int m = 0; m < moveNum; m++) {
            moves[m] = Arrays.stream(read.readLine().split(" ")).mapToInt(p -> Integer.parseInt(p) - 1).toArray();
        }

        ArrayList<int[]>[] swapWith = new ArrayList[cowNum];
        for (int c = 0; c < cowNum; c++) {
            swapWith[c] = new ArrayList<>();
        }
        for (int m = 0; m < moveNum; m++) {
            int[] move = moves[m];
            swapWith[move[0]].add(new int[] {m, move[1]});
            swapWith[move[1]].add(new int[] {m, move[0]});
        }

        // longs because of how large the time limit can be (stupid overflow)
        long[][][] seenBefore = new long[cowNum][][];
        int[][] seenAmts = new int[cowNum][];
        for (int c = 0; c < cowNum; c++) {
            // each of these state are 1: the pioneering cow that took up all this and 2: the ending time for its state
            seenBefore[c] = new long[swapWith[c].size()][2];
            seenAmts[c] = new int[swapWith[c].size()];
            Arrays.fill(seenBefore[c], INVALID);
        }
        ArrayList<long[]>[] loops = new ArrayList[cowNum];
        for (int c = 0; c < cowNum; c++) {
            if (swapWith[c].size() == 0 || seenAmts[c][0] > 0) {
                continue;
            }
            ArrayList<long[]> loop = new ArrayList<>();
            loops[c] = loop;
            int[] state = new int[] {c, 0, swapWith[c].get(0)[0]};
            int lastStepTime = -1;  // what are you doing step-timer?
            long loopAmt = 0;
            while (seenAmts[state[0]][state[1]] < 2) {  // loop 2 times (note: we'll always loop back to the start)
                seenAmts[state[0]][state[1]]++;

                int stepTime = state[2];  // don't ask me how this works, it just does
                loopAmt += stepTime <= lastStepTime ? 1 : 0;
                lastStepTime = stepTime;
                long timer = (loopAmt * moveNum) + stepTime;

                if (seenBefore[state[0]][state[1]] == INVALID) {
                    seenBefore[state[0]][state[1]] = new long[] {c, timer};
                }
                loop.add(new long[] {state[0], timer});
                state[0] = swapWith[state[0]].get(state[1])[1];

                int newMoveInd = bisectLeft(swapWith[state[0]], state[2] + 1, 0);
                newMoveInd = newMoveInd == swapWith[state[0]].size() ? 0 : newMoveInd;  // wrap the index around
                state[1] = newMoveInd;
                state[2] = swapWith[state[0]].get(state[1])[0];
            }
        }

        int[] uniquePos = new int[cowNum];
        HashMap<Integer, ArrayList<long[]>> queries = new HashMap<>();
        for (int c = 0; c < cowNum; c++) {
            if (seenBefore[c].length == 0) {
                uniquePos[c] = 1;
                continue;
            }
            long[] start = seenBefore[c][0];
            int pioneerCow = (int) start[0];
            if (!queries.containsKey(pioneerCow)) {
                queries.put(pioneerCow, new ArrayList<>());
            }
            // this start is when the pioneer cow starts accurately simulating c
            queries.get(pioneerCow).add(new long[] {start[1] - start[1] % moveNum, c});
        }

        for (Map.Entry<Integer, ArrayList<long[]>> qSet : queries.entrySet()) {
            ArrayList<long[]> cycle = loops[qSet.getKey()];
            ArrayList<int[]> actualQueries = new ArrayList<>();
            for (long[] q : qSet.getValue()) {
                // these are the start and end index of when the cow stars and stops
                int start = bisectLeft(cycle, q[0], 1);
                int end = Math.min(bisectLeft(cycle, q[0] + time, 1), cycle.size() - 1);
                actualQueries.add(new int[] {start, end, (int) q[1]});
            }
            actualQueries.sort(Comparator.comparingInt(q -> q[1]));

            // sauce: https://www.geeksforgeeks.org/queries-number-distinct-elements-subarray/
            int queryAt = 0;
            BITree elements = new BITree(cycle.size());
            int[] lastVisit = new int[cowNum];
            Arrays.fill(lastVisit, -1);
            for (int i = 0; i < cycle.size(); i++) {
                int pos = (int) cycle.get(i)[0];
                if (lastVisit[pos] != -1) {
                    elements.increment(lastVisit[pos], -1);
                }
                lastVisit[pos] = i;
                elements.increment(lastVisit[pos], 1);
                while (queryAt < actualQueries.size() && actualQueries.get(queryAt)[1] == i) {
                    int[] query = actualQueries.get(queryAt);
                    uniquePos[actualQueries.get(queryAt)[2]] = elements.query(query[1]) - elements.query(query[0] - 1);
                    queryAt++;
                }
            }
        }

        StringBuilder total = new StringBuilder();  // this makes it faster (no string concat bc it's O(n))
        for (int p : uniquePos) {
            total.append(p).append('\n');
        }
        System.out.print(total);
        System.err.printf("%d ms. dear god.%n", System.currentTimeMillis() - startTime);
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

    // functions like these make me want to self.hang()
    private static int bisectLeft(List<long[]> arr, long elem, int compInd) {
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

/**
 * link for explanation is at {@link utils.BinaryIndexedTree} is you wanted it
 */
class BITree {
    private final int[] treeThing;
    private final int size;
    public BITree(int size) {
        treeThing = new int[size + 1];
        this.size = size;
    }

    public void increment(int updateAt, int val) {
        updateAt++;  // have the driver code not worry about 1-indexing
        for (; updateAt <= size; updateAt += updateAt & -updateAt) {
            treeThing[updateAt] += val;
        }
    }

    public int query(int ind) {  // the bound is inclusive i think
        ind++;
        int sum = 0;
        for (; ind > 0; ind -= ind & -ind) {
            sum += treeThing[ind];
        }
        return sum;
    }
}
