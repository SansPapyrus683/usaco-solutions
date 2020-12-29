package utils;

import java.util.*;

/**
 * tbh this class doesn't really do anything<br>
 * it just has some little algorithms that i think might(?) help<br>
 * it includes an iterative euler tour which i couldn't find anywhere on the internet so...
 */
public class Tree {
    private static class TourInfo {
        private final ArrayList<Integer> toured;
        private final int[] parent;
        private final int[] height;
        private final int[] firstOcc;
        private final int[] lastOcc;

        // because of you java i have to make this god-awful TourInfo class
        public TourInfo(ArrayList<Integer> toured, int[] parent, int[] height) {
            if (parent.length != height.length) {
                throw new IllegalArgumentException("your parent and height stuff is kinda wonky ngl");
            }
            int nodes = parent.length;  // or height.length, doesn't matter lol
            this.toured = toured;
            this.height = height;
            this.parent = parent;
            firstOcc = new int[nodes];
            lastOcc = new int[nodes];
            boolean[] seen = new boolean[nodes];
            // get the first & last occurrence of each of the nodes in the tour
            for (int i = 0; i < toured.size(); i++) {
                int n = toured.get(i);  // forgive me for this variable name (stands for node)
                if (!seen[n]) {
                    firstOcc[n] = i;
                    seen[n] = true;
                }
            }
            Arrays.fill(seen, false);  // reset some stuff, idk
            for (int i = toured.size() - 1; i >= 0; i--) {
                int n = toured.get(i);
                if (!seen[n]) {
                    lastOcc[n] = i;
                    seen[n] = true;
                }
            }
        }

        public ArrayList<Integer> getToured() {
            return toured;
        }

        public int[] getHeight() {
            return height;
        }

        public int[] getParent() {
            return parent;
        }

        public int[] getFirstOcc() {
            return firstOcc;
        }

        public int[] getLastOcc() {
            return lastOcc;
        }
    }

    private final List<Integer>[] neighbors;
    private final int nodes;
    private final TourInfo inOutTour;
    private final CompSegmentTree LCATree;  // it's in that other file, you should go look at it lol

    public Tree(List<Integer>[] neighbors) {
        this.neighbors = neighbors;
        nodes = neighbors.length;
        inOutTour = genInOutTour();
        TourInfo eulerTour = genEulerTour();
        LCATree = new CompSegmentTree(eulerTour.toured.stream().mapToInt(i -> i).toArray(),
                CompSegmentTree.OpType.MIN, Comparator.comparingInt(n -> eulerTour.height[n]));
    }

    public int LCA(int n1, int n2) {  // basically copied from https://cp-algorithms.com/graph/lca.html
        int p1 = inOutTour.firstOcc[n1];
        int p2 = inOutTour.firstOcc[n2];
        if (p1 > p2) {
            int temp = p1;
            p1 = p2;
            p2 = temp;
        }
        return LCATree.calc(p1, p2 + 1);
    }

    // sauce: https://www.geeksforgeeks.org/query-ancestor-descendant-relationship-tree/
    public boolean fatherTest(int possDad, int possKid) {
        return inOutTour.firstOcc[possDad] <= inOutTour.firstOcc[possKid] && inOutTour.lastOcc[possDad] >= inOutTour.lastOcc[possKid];
    }

    private TourInfo genEulerTour() {
        int[] parent = new int[nodes];
        int[] height = new int[nodes];
        ArrayList<Integer> eulerTour = new ArrayList<>();
        // current node, it's parent, and it's depth
        ArrayDeque<int[]> frontier = new ArrayDeque<>(Collections.singletonList(new int[] {0, 0, 0}));
        boolean[] visited = new boolean[nodes];
        while (!frontier.isEmpty()) {  // because i hate recursion, heere's an iterative euler tour
            int[] curr = frontier.removeFirst();
            eulerTour.add(curr[0]);
            if (curr[0] == -1 || visited[curr[0]]) {
                continue;
            }
            visited[curr[0]] = true;
            height[curr[0]] = curr[2];
            if (curr[0] != 0) {
                frontier.addFirst(new int[] {curr[1], parent[curr[1]], curr[2] - 1});
            }
            for (int n : neighbors[curr[0]]) {
                if (n != curr[1]) {  // visit all children (don't process the parent)
                    parent[n] = curr[0];
                    frontier.addFirst(new int[]{n, curr[0], curr[2] + 1});
                }
            }
        }
        return new TourInfo(eulerTour, parent, height);
    }

    private TourInfo genInOutTour() {
        ArrayList<Integer> tour = new ArrayList<>();
        int[] parent = new int[nodes];
        int[] height = new int[nodes];

        // again, node, parent, and height
        ArrayDeque<int[]> frontier = new ArrayDeque<>(Collections.singletonList(new int[] {0, 0, 0}));
        boolean[] visited = new boolean[nodes];
        while (!frontier.isEmpty()) {
            int[] curr = frontier.removeFirst();
            tour.add(curr[0]);
            if (visited[curr[0]]) {
                continue;
            }
            frontier.addFirst(curr);
            visited[curr[0]] = true;
            height[curr[0]] = curr[2];
            for (int n : neighbors[curr[0]]) {
                if (n != curr[1]) {
                    parent[n] = curr[0];
                    frontier.addFirst(new int[] {n, curr[0], curr[2] + 1});
                }
            }
        }
        return new TourInfo(tour, parent, height);
    }
}
