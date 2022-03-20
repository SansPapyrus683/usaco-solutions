package utils;

/**
 * https://cp-algorithms.com/data_structures/disjoint_set_union.html#toc-tgt-3
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
