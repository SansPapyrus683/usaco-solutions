package official.o2019.feb.gold.amusementParkEpisode;

import java.util.*;
import java.io.*;

// 2019 feb gold (this one works by PURE CHANCE- sometimes it works, sometimes it doesn't so yeah lol)
public class CowLand {
    private static class MinSegTree {
        private final int[] segtree;
        private final int arrSize;
        private final int size;
        private final Comparator<Integer> cmp;

        public MinSegTree(int len, Comparator<Integer> comp) {
            int size = 1;
            while (size < len) {
                size *= 2;
            }
            this.size = size;
            cmp = comp;
            arrSize = len;
            segtree = new int[size * 2];
        }

        public MinSegTree(int[] arr, Comparator<Integer> comp) {
            this(arr.length, comp);
            for (int i = 0; i < arr.length; i++) {
                set(i, arr[i]);
            }
        }

        public void set(int index, int element) {
            if (index < 0 || index > arrSize) {
                throw new IllegalArgumentException(String.format("%s should be out of bounds lol", index));
            }
            set(index, element, 0, 0, size);
        }

        private void set(int index, int element, int currNode, int left, int right) {
            if (right - left == 1) {
                segtree[currNode] = element;
            } else {
                int mid = (left + right) / 2;
                if (index < mid) {
                    set(index, element, 2 * currNode + 1, left, mid);
                } else {
                    set(index, element, 2 * currNode + 2, mid, right);
                }
                segtree[currNode] = Collections.min(Arrays.asList(segtree[2 * currNode + 1], segtree[2 * currNode + 2]), cmp);
            }
        }

        public int min(int from, int to) {
            if (from < 0 || to > arrSize) {
                throw new IllegalArgumentException(String.format("the bounds %s and %s are out of bounds i think", from, to));
            }
            return min(from, to, 0, 0, size);
        }

        private int min(int from, int to, int currNode, int left, int right) {
            if (right <= from || to <= left) {
                return Integer.MAX_VALUE;
            }
            if (from <= left && right <= to) {
                return segtree[currNode];
            }
            int middle = (left + right) / 2;
            int leftPart = min(from, to, 2 * currNode + 1, left, middle);
            int rightPart = min(from, to, 2 * currNode + 2, middle, right);
            if (leftPart == Integer.MAX_VALUE) {
                return rightPart;
            } else if (rightPart == Integer.MAX_VALUE) {
                return leftPart;
            }
            return cmp.compare(leftPart, rightPart) < 0 ? leftPart : rightPart;
        }
    }

    private static class XORSegTree {
        private final int[] segtree;
        private final int arrSize;
        private final int size;

        public XORSegTree(int len) {  // constructs the thing kinda like an array
            int size = 1;
            while (size < len) {
                size *= 2;
            }
            this.size = size;
            arrSize = len;
            segtree = new int[size * 2];  // we won't necessarily use all of the element but that doesn't really matter
        }

        public void set(int index, int element) {
            if (index < 0 || index > arrSize) {
                throw new IllegalArgumentException(String.format("%s should be out of bounds lol", index));
            }
            set(index, element, 0, 0, size);
        }

        private void set(int index, int element, int currNode, int left, int right) {
            if (right - left == 1) {
                segtree[currNode] = element;
            } else {
                int mid = (left + right) / 2;
                if (index < mid) {
                    set(index, element, 2 * currNode + 1, left, mid);
                } else {
                    set(index, element, 2 * currNode + 2, mid, right);
                }
                segtree[currNode] = segtree[2 * currNode + 1] ^ segtree[2 * currNode + 2];
            }
        }

        // for this one, from and to follow "normal" slicing rules - left bound is inclusive, right bound isn't
        public int xor(int from, int to) {
            if (from < 0 || to > arrSize) {
                throw new IllegalArgumentException(String.format("the bounds %s and %s are out of bounds i think", from, to));
            }
            return xor(from, to, 0, 0, size);
        }

        private int xor(int from, int to, int currNode, int left, int right) {
            if (right <= from || to <= left) {  // oof, out of bounds, so the sum is definitely 0
                return 0;
            }
            if (from <= left && right <= to) {
                return segtree[currNode];
            }
            int middle = (left + right) / 2;
            int leftPartSum = xor(from, to, 2 * currNode + 1, left, middle);
            int rightPartSum = xor(from, to, 2 * currNode + 2, middle, right);
            return leftPartSum ^ rightPartSum;
        }
    }

    private static class AmusementPark {
        private final ArrayList<Integer>[] neighbors;
        private final ArrayList<Integer> eulerTour = new ArrayList<>();
        private final int[] firstOcc;
        private final int[] lastOcc;
        private final int[] parent;
        private final int[] height;
        private final int[] enjoyment;

        private final MinSegTree LCATree;
        private final XORSegTree queryTree;

        public AmusementPark(ArrayList<Integer>[] neighbors, int[] respectiveVals) {
            this.neighbors = neighbors;
            enjoyment = respectiveVals.clone();
            int nodes = neighbors.length;
            firstOcc = new int[nodes];
            lastOcc = new int[nodes];
            parent = new int[nodes];
            height = new int[nodes];

            eulerTour();
            boolean[] seen = new boolean[nodes];
            for (int i = eulerTour.size() - 1; i >= 0; i--) {
                int n = eulerTour.get(i);
                if (!seen[n]) {
                    lastOcc[n] = i;
                    seen[n] = true;
                }
            }
            queryTree = new XORSegTree(eulerTour.size());
            for (int n = 0; n < nodes; n++) {
                queryTree.set(firstOcc[n], enjoyment[n]);
                queryTree.set(lastOcc[n], enjoyment[n]);
            }
            LCATree = new MinSegTree(eulerTour.stream().mapToInt(i -> i).toArray(), Comparator.comparingInt(n -> height[n]));
        }

        public int query(int n1, int n2) {
            return queryTree.xor(0, firstOcc[n1] + 1) ^ queryTree.xor(0, firstOcc[n2] + 1) ^ enjoyment[LCA(n1, n2)];
        }

        public void set(int node, int val) {
            enjoyment[node] = val;
            queryTree.set(firstOcc[node], val);
            queryTree.set(lastOcc[node], val);
        }

        public int LCA(int n1, int n2) {
            int p1 = firstOcc[n1];
            int p2 = firstOcc[n2];
            if (p1 > p2) {
                int temp = p1;
                p1 = p2;
                p2 = temp;
            }
            return LCATree.min(p1, p2 + 1);  // + 1 because of the bounds of min()
        }

        private void eulerTour() {  // i could just put this in the constructor but this makes it look better
            ArrayDeque<int[]> frontier = new ArrayDeque<>(Collections.singletonList(new int[]{0, -1, 0}));
            HashSet<Integer> visited = new HashSet<>();
            while (!frontier.isEmpty()) {
                int[] curr = frontier.removeFirst();
                eulerTour.add(curr[0]);
                if (curr[0] == -1 || visited.contains(curr[0])) {
                    continue;
                }
                visited.add(curr[0]);
                height[curr[0]] = curr[2];
                firstOcc[curr[0]] = eulerTour.size() - 1;  // -1 because of that .add(curr[0]) above
                if (curr[0] != 0) {
                    frontier.addFirst(new int[]{curr[1], parent[curr[1]], curr[2] - 1});
                }
                boolean isLeaf = true;  // i know i could like check the amt of neighbors but this is somehow faster
                for (int n : neighbors[curr[0]]) {
                    if (n != curr[1]) {  // visit all children (don't process the parent)
                        parent[n] = curr[0];
                        frontier.addFirst(new int[]{n, curr[0], curr[2] + 1});
                        isLeaf = false;
                    }
                }
                if (isLeaf) {
                    eulerTour.add(curr[0]);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cowland.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int placeNum = Integer.parseInt(initial.nextToken());
        int queryNum = Integer.parseInt(initial.nextToken());
        int[] initialVals = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        ArrayList<Integer>[] neighbors = new ArrayList[placeNum];
        for (int p = 0; p < placeNum; p++) {
            neighbors[p] = new ArrayList<>();
        }
        for (int i = 0; i < placeNum - 1; i++) {
            int[] route = Arrays.stream(read.readLine().split(" ")).mapToInt(j -> Integer.parseInt(j) - 1).toArray();
            neighbors[route[0]].add(route[1]);
            neighbors[route[1]].add(route[0]);
        }
        AmusementPark park = new AmusementPark(neighbors, initialVals);
        PrintWriter written = new PrintWriter("cowland.out");
        for (int q = 0; q < queryNum; q++) {
            int[] query = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            switch (query[0]) {
                case 1:
                    park.set(query[1] - 1, query[2]);
                    break;
                case 2:
                    int happiness = park.query(query[1] - 1, query[2] - 1);
                    // System.out.println(happiness);  // <- adding this makes the thing too slow lol
                    written.println(happiness);
                    break;
                default:
                    throw new IllegalArgumentException("what kind of query is " + Arrays.toString(query) + " lol");
            }
        }
        written.close();
        System.out.printf("WOOOOOASDPOFIJASDFIOJASPODI it took %d ms%n", System.currentTimeMillis() - start);
    }
}
