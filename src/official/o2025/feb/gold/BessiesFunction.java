package official.o2025.feb.gold;

import java.io.*;
import java.util.*;

public class BessiesFunction {
    // mfw recursion
    private static List<Integer>[] actualKids;
    private static int[] costs;
    private static long[][] treeCost;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(read.readLine());
        int[] f = Arrays.stream(read.readLine().split(" "))
                .mapToInt(i -> Integer.parseInt(i) - 1).toArray();
        costs = Arrays.stream(read.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        assert f.length == n && costs.length == n;

        int[] inCyc = new int[n];
        Arrays.fill(inCyc, -1);
        for (int i = 0; i < n; i++) {
            if (inCyc[i] != -1) {
                continue;
            }

            List<Integer> path = new ArrayList<>(Collections.singletonList(i));
            int at = i;
            while (inCyc[f[at]] == -1) {
                at = f[at];
                inCyc[at] = -2;
                path.add(at);
            }

            boolean inCycle = false;
            for (int j : path) {
                inCycle = inCycle || j == f[at];
                inCyc[j] = inCycle ? 1 : 0;
            }
        }

        actualKids = new List[n];
        for (int i = 0; i < n; i++) {
            actualKids[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            if (inCyc[i] == 0) {
                actualKids[f[i]].add(i);
            }
        }

        treeCost = new long[n][];
        boolean[] cycVis = new boolean[n];
        long totalCost = 0;
        for (int i = 0; i < n; i++) {
            if (cycVis[i] || inCyc[i] == 0) {
                continue;
            }

            List<Integer> cyc = new ArrayList<>();
            int at = i;
            do {
                cycVis[at] = true;
                cyc.add(at);
                calcTreeCosts(at);
                at = f[at];
            } while (at != i);

            if (cyc.size() == 1) {
                totalCost += treeCost[i][1] - costs[cyc.get(0)];
                continue;
            }

            final int m = cyc.size();  // just a shorthand
            long best = Long.MAX_VALUE;
            for (int save = 0; save < 2; save++) {
                long[][] minCost = new long[m][2];
                minCost[0][1 - save] = Long.MAX_VALUE / 2;  // a "big enough" # lol
                minCost[0][save] = treeCost[cyc.get(0)][save];
                for (int j = 1; j < m; j++) {
                    long[] prev = minCost[j - 1];
                    minCost[j][0] = treeCost[cyc.get(j)][0] + prev[1];
                    minCost[j][1] = treeCost[cyc.get(j)][1] + Math.min(prev[0], prev[1]);
                }

                long dontCare = Math.min(minCost[m - 1][0], minCost[m - 1][1]);
                best = Math.min(best, save == 0 ? minCost[m - 1][1] : dontCare);
            }

            totalCost += best;
        }

        System.out.println(totalCost);
    }

    private static long[] calcTreeCosts(int at) {
        long[] ret = new long[] { 0, costs[at] };
        for (int k : actualKids[at]) {
            long[] kid = calcTreeCosts(k);
            ret[0] += kid[1];
            ret[1] += Math.min(kid[0], kid[1]);
        }
        return treeCost[at] = ret;
    }
}
