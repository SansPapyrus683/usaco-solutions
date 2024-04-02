package official.o2024.jan.silver;

import java.io.*;
import java.util.*;

/**
 * 2024 jan silver
 * 5
 * 5 4 3 2 1
 * 1 2
 * 1 3
 * 3 4
 * 3 5 should output 2
 */
public class Potions {
    // yeah yeah, unfortunately recursion forces me to have global variables
    private static List<Integer>[] neighbors;
    private static int[] roomPots;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int roomNum = Integer.parseInt(read.readLine());
        int[] potOrder = Arrays.stream(read.readLine().split(" "))
                .mapToInt(p -> Integer.parseInt(p) - 1).toArray();

        neighbors = new ArrayList[roomNum];
        for (int r = 0; r < roomNum; r++) {
            neighbors[r] = new ArrayList<>();
        }
        for (int e = 0; e < roomNum - 1; e++) {
            StringTokenizer edge = new StringTokenizer(read.readLine());
            int a = Integer.parseInt(edge.nextToken()) - 1;
            int b = Integer.parseInt(edge.nextToken()) - 1;
            neighbors[a].add(b);
            neighbors[b].add(a);
        }

        final int root = 0;
        int leafNum = leafNum(root, root);  // i hate having to code up two dfs's
        roomPots = new int[roomNum];
        for (int p = 0; p < leafNum; p++) {
            roomPots[potOrder[p]]++;
        }

        int maxPots = maxPotions(root, root)[0];
        System.out.println(maxPots);
    }

    private static int leafNum(int at, int prev) {
        int tot = 0;
        for (int n : neighbors[at]) {
            tot += n == prev ? 0 : leafNum(n, at);
        }
        return tot == 0 ? 1 : tot;
    }

    private static int[] maxPotions(int at, int prev) {
        int[] ret = new int[]{0, 0};
        boolean isLeaf = true;
        for (int n : neighbors[at]) {
            if (n != prev) {
                isLeaf = false;
                int[] kidRes = maxPotions(n, at);
                ret[0] += kidRes[0];
                ret[1] += kidRes[1];
            }
        }
        if (isLeaf) {
            return new int[]{roomPots[at] > 0 ? 1 : 0, 1};
        }

        int leavesLeft = ret[1] - ret[0];
        ret[0] += Math.min(leavesLeft, roomPots[at]);
        return ret;
    }
}
