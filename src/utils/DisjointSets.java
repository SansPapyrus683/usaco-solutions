package utils;

/**
 * https://cp-algorithms.com/data_structures/disjoint_set_union.html#toc-tgt-3
 * i might add a bad explanation™ later if i have the time
 */
public final class DisjointSets {
    private final int[] parents;
    private final int[] sizes;
    private final boolean compressPaths;
    public DisjointSets(int size, boolean compressPaths) {
        parents = new int[size];
        sizes = new int[size];
        this.compressPaths = compressPaths;
        for (int i = 0; i < size; i++) {
            parents[i] = i;
            sizes[i] = 1;
        }
    }

    public boolean sameSet(int n1, int n2) {
        return getUltimate(n1) == getUltimate(n2);
    }

    public int getUltimate(int n) {
        if (parents[n] == n) {
            return n;
        }
        if (compressPaths) {
            return parents[n] = getUltimate(parents[n]);
        } else {
            return getUltimate(parents[n]);
        }
    }

    public void link(int e1, int e2) {
        if (sameSet(e1, e2)) {
            return;
        }
        e1 = getUltimate(e1);
        e2 = getUltimate(e2);
        if (sizes[e2] > sizes[e1]) {
            int temp = e1;
            e1 = e2;
            e2 = temp;
        }
        parents[e2] = e1;
        sizes[e1] += sizes[e2];
    }
}
