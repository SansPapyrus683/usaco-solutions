package official.o2022.feb.gold;

import java.io.*;
import java.util.*;

// 2022 feb gold
public class MooNetwork {
    private static final int MAX_X = (int) Math.pow(10, 6);
    private static final int MAX_Y = 10;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int pointNum = Integer.parseInt(read.readLine());
        int[][] points = new int[pointNum][];
        for (int p = 0; p < pointNum; p++) {
            StringTokenizer pt = new StringTokenizer(read.readLine());
            points[p] = new int[] {Integer.parseInt(pt.nextToken()), Integer.parseInt(pt.nextToken())};
            assert 0 <= points[p][0] && points[p][0] <= MAX_X && 0 <= points[p][1] && points[p][1] <= MAX_Y;
        }

        Arrays.sort(points, Comparator.comparingInt(p -> p[0]));
        ArrayList<ArrayList<Integer>> sections = new ArrayList<>();
        ArrayList<Integer> curr = new ArrayList<>(Collections.singletonList(0));
        for (int p = 1; p < pointNum; p++) {
            if (points[p][0] != points[p - 1][0]) {
                sections.add(curr);
                curr = new ArrayList<>();
            }
            curr.add(p);
        }
        sections.add(curr);

        ArrayList<int[]> edges = new ArrayList<>();
        for (int s1 = 0; s1 < sections.size(); s1++) {
            for (int a : sections.get(s1)) {
                // apparently just MAX_Y / 2 works
                for (int s2 = s1; s2 < Math.min(s1 + MAX_Y / 2, sections.size()); s2++) {
                    for (int b : sections.get(s2)) {
                        // we will get edges like {1, 1} but doesn't matter in the long run
                        if (a != b) {
                            edges.add(new int[] {a, b});
                        }
                    }
                }
            }
        }
        edges.sort(Comparator.comparingLong(e -> cost(points[e[0]], points[e[1]])));

        DisjointSets mst = new DisjointSets(pointNum);
        long cost = 0;
        int edgeNum = 0;
        for (int[] e : edges) {
            if (mst.link(e[0], e[1])) {
                cost += cost(points[e[0]], points[e[1]]);
                edgeNum++;
                if (edgeNum == pointNum - 1) {
                    break;
                }
            }
        }
        System.out.println(cost);
    }

    private static long cost(int[] p1, int[] p2) {
        long dx = p1[0] - p2[0];
        long dy = p1[1] - p2[1];
        return dx * dx + dy * dy;
    }
}

/**
 * see {@link utils.DisjointSets} for explanation
 */
class DisjointSets {
    private final int[] parents;
    private final int[] sizes;
    public DisjointSets(int size) {
        parents = new int[size];
        sizes = new int[size];
        for (int i = 0; i < size; i++) {
            parents[i] = i;
            sizes[i] = 1;
        }
    }

    public int getUltimate(int n) {
        return parents[n] == n ? n : (parents[n] = getUltimate(parents[n]));
    }

    public boolean link(int e1, int e2) {
        e1 = getUltimate(e1);
        e2 = getUltimate(e2);
        if (e1 == e2) {
            return false;
        }
        if (sizes[e2] > sizes[e1]) {
            return link(e2, e1);
        }
        parents[e2] = e1;
        sizes[e1] += sizes[e2];
        return true;
    }
}

