package official.o2024.feb.gold;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

/** 2024 feb gold */
public class MilkExchange {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int bucketNum = Integer.parseInt(read.readLine());
        int[] buckets =
                Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] bucketInds =
                IntStream.range(0, bucketNum)
                        .boxed()
                        .sorted(Comparator.comparing(i -> -buckets[i]))
                        .mapToInt(i -> i)
                        .toArray();

        long[] deltas = new long[bucketNum + 1];
        ReallySpecificDSU dsu = new ReallySpecificDSU(buckets);
        for (int i = 0; i < bucketNum - 1; i++) {
            int ind = bucketInds[i];
            int next = dsu.getNext(ind);
            int decrease = dsu.getMin(ind) - dsu.getMin(next);
            int start = dsu.getSize(next);
            int len = dsu.getSize(ind);
            deltas[start] += decrease;
            deltas[start + len] -= decrease;
            dsu.link(ind, next);
        }

        long milk = Arrays.stream(buckets).mapToLong(Integer::valueOf).sum();
        long currChange = 0;
        for (int t = 1; t <= bucketNum; t++) {
            currChange += deltas[t];
            milk -= currChange;
            System.out.println(milk);
        }
    }
}

class ReallySpecificDSU {
    private final int[] parents;
    private final int[] sizes;
    private final int[] next;
    private final int[] vals;

    public ReallySpecificDSU(int[] vals) {
        parents = new int[vals.length];
        sizes = new int[vals.length];
        next = new int[vals.length];
        this.vals = vals.clone();
        for (int i = 0; i < vals.length; i++) {
            parents[i] = i;
            sizes[i] = 1;
            next[i] = (i + 1) % vals.length;
        }
    }

    public int getTop(int n) {
        return parents[n] == n ? n : (parents[n] = getTop(parents[n]));
    }

    public int getSize(int n) {
        return sizes[getTop(n)];
    }

    public int getMin(int n) {
        return vals[getTop(n)];
    }

    public int getNext(int n) {
        return getTop(next[getTop(n)]);
    }

    public boolean link(int e1, int e2) {
        if ((e1 = getTop(e1)) == (e2 = getTop(e2))) {
            return false;
        }
        if (sizes[e2] > sizes[e1]) {
            return link(e2, e1);
        }
        if (getNext(e1) != e2 && getNext(e2) != e1) {
            throw new IllegalArgumentException("i can only link consecutive components, sorry");
        }
        sizes[e1] += sizes[e2];
        vals[e1] = Math.min(vals[e1], vals[e2]);
        next[e1] = getNext(e1) == e2 ? getNext(e2) : getNext(e1);
        parents[e2] = e1;  // this has to be at the end!!
        return true;
    }
}
