package official.o2019.feb.gold.cowland;

import java.io.*;
import java.util.*;

// 2019 feb gold
public final class CowLand {
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
        StringBuilder enjoyment = new StringBuilder();  // so io becomes faster
        for (int q = 0; q < queryNum; q++) {
            int[] query = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            switch (query[0]) {
                case 1:
                    park.set(query[1] - 1, query[2]);
                    break;
                case 2:
                    int happiness = park.query(query[1] - 1, query[2] - 1);
                    enjoyment.append(happiness).append('\n');
                    break;
                default:
                    throw new IllegalArgumentException("what kind of query is " + Arrays.toString(query) + " lol");
            }
        }
        PrintWriter written = new PrintWriter("cowland.out");
        written.print(enjoyment);
        written.close();
        System.out.print(enjoyment);
        System.out.printf("WOOOOOASDPOFIJASDFIOJASPODI it took %d ms%n", System.currentTimeMillis() - start);
    }

    private static final class AmusementPark {
        private final List<Integer>[] neighbors;
        private final ArrayList<Integer> eulerTour = new ArrayList<>();
        private final int[] firstOcc;
        private final int[] lastOcc;
        private final int[] height;
        private final int[] enjoyment;
        private final int nodes;

        private final MinSegTree LCATree;
        private final XORBIT queryTree;

        public AmusementPark(List<Integer>[] neighbors, int[] respectiveVals) {
            this.neighbors = neighbors;
            enjoyment = respectiveVals.clone();
            nodes = neighbors.length;
            firstOcc = new int[nodes];
            lastOcc = new int[nodes];
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

            queryTree = new XORBIT(eulerTour.size());
            for (int n = 0; n < nodes; n++) {
                queryTree.set(firstOcc[n], enjoyment[n]);
                queryTree.set(lastOcc[n], enjoyment[n]);
            }
            LCATree = new MinSegTree(eulerTour.stream().mapToInt(i -> i).toArray(), Comparator.comparingInt(n -> height[(int) n]));
        }

        public int query(int n1, int n2) {
            return queryTree.xor(firstOcc[n1]) ^ queryTree.xor(firstOcc[n2]) ^ enjoyment[LCA(n1, n2)];
        }

        public void set(int node, int val) {
            enjoyment[node] = val;
            queryTree.set(firstOcc[node], val);
            queryTree.set(lastOcc[node], val);
        }

        public int LCA(int n1, int n2) {
            int p1 = firstOcc[n1];
            int p2 = firstOcc[n2];
            if (p1 > p2) {  // see which one occurs first because of the minimum thing
                return LCA(n2, n1);
            }
            return LCATree.rangeMin(p1, p2 + 1);  // + 1 because of the bounds of min()
        }

        private void eulerTour() {  // i could just put this in the constructor but this makes it look better
            ArrayDeque<int[]> frontier = new ArrayDeque<>(Collections.singletonList(new int[]{0, -1, 0}));
            HashSet<Integer> visited = new HashSet<>();
            int[] parent = new int[nodes];
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
}

final class MinSegTree {
    private final int[] segtree;
    private final int len;
    private final Comparator<Integer> cmp;
    public MinSegTree(int[] arr, Comparator<Integer> cmp) {  // constructs the thing with initial elements as well
        this.len = arr.length;
        this.cmp = cmp;
        segtree = new int[len * 2];  // note: we won't use index 0
        for (int i = 0; i < arr.length; i++) {
            set(i, arr[i]);
        }
    }

    public void set(int ind, int val) {
        if (ind < 0 || ind >= len) {
            throw new IllegalArgumentException(String.format("the index %d is OOB", ind));
        }
        for (segtree[ind += len] = val; ind > 1; ind >>= 1) {
            segtree[ind >> 1] = min(segtree[ind], segtree[ind ^ 1]);
        }
    }

    public int rangeMin(int from, int to) {  // minimum from [from, to)
        if (from > to || from < 0 || from >= len || to <= 0 || to > len) {
            throw new IllegalArgumentException(String.format("the query [%d, %d) is invalid just sayin'", from, to));
        }
        int min = Integer.MAX_VALUE;
        for (from += len, to += len; from < to; from >>= 1, to >>= 1) {
            if ((from & 1) != 0) {
                min = min(min, segtree[from++]);
            }
            if ((to & 1) != 0) {
                min = min(min, segtree[--to]);
            }
        }
        return min;
    }

    private int min(int x, int y) {
        if (x == Integer.MAX_VALUE) {
            return y;
        } else if (y == Integer.MAX_VALUE) {
            return x;
        }
        return Collections.min(Arrays.asList(x, y), cmp);
    }
}

final class XORBIT {
    private final int[] treeThing;
    private final int[] actualArr;
    private final int size;
    public XORBIT(int size) {
        treeThing = new int[size + 1];  // to make stuff easier we'll just make it 1-indexed
        actualArr = new int[size];
        this.size = size;
    }

    public int get(int ind) {
        return actualArr[ind];
    }

    public void set(int ind, int val) {
        increment(ind, actualArr[ind] ^ val);
    }

    public void increment(int ind, int val) {
        actualArr[ind] ^= val;
        ind++;  // have the driver code not worry about 1-indexing
        // that bitwise thing returns the greatest power of two that's less than i
        for (; ind <= size; ind += ind & -ind) {
            treeThing[ind] ^= val;
        }
    }

    public int xor(int ind) {  // the bound is inclusive i think (returns sum of everything from 0 to ind)
        ind++;
        int sum = 0;
        for (; ind > 0; ind -= ind & -ind) {
            sum ^= treeThing[ind];
        }
        return sum;
    }
}
