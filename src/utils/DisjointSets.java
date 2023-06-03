package utils;

/**
 * <a href="https://cp-algorithms.com/data_structures/disjoint_set_union.html">sauce</a>
 * i might add a bad explanation™ later if i have the time
 */
public class DisjointSets {
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

    public int getTop(int n) {
        return parents[n] == n ? n : (parents[n] = getTop(parents[n]));
    }

    public boolean link(int e1, int e2) {
        if ((e1 = getTop(e1)) == (e2 = getTop(e2))) {
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
