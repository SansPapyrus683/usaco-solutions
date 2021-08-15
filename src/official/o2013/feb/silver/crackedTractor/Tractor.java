package official.o2013.feb.silver.crackedTractor;

import java.io.*;
import java.util.*;

// 2013 feb silver
public final class Tractor {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("tractor.in"));
        int width = Integer.parseInt(read.readLine());
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;
        int[][] field = new int[width][width];
        for (int r = 0; r < width; r++) {
            field[r] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int c : field[r]) {
                lowest = Math.min(lowest, c);
                highest = Math.max(highest, c);
            }
        }
        int fieldReq = (int) Math.ceil((double) width * width / 2);

        int lo = 0;
        int hi = highest - lowest;
        int valid = -1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            DisjointSets traversable = new DisjointSets(width * width);
            for (int r = 0; r < width; r++) {
                for (int c = 0; c < width; c++) {
                    if (r > 0 && Math.abs(field[r][c] - field[r - 1][c]) <= mid) {
                        traversable.link(r * width + c, (r - 1) * width + c);
                    }
                    if (c > 0 && Math.abs(field[r][c] - field[r][c - 1]) <= mid) {
                        traversable.link(r * width + c, r * width + (c - 1));
                    }
                    if (r < width - 1 && Math.abs(field[r][c] - field[r + 1][c]) <= mid) {
                        traversable.link(r * width + c, (r + 1) * width + c);
                    }
                    if (c < width - 1 && Math.abs(field[r][c] - field[r][c + 1]) <= mid) {
                        traversable.link(r * width + c, r * width + (c + 1));
                    }
                }
            }
            int maxTraversable = 0;
            for (int r = 0; r < width; r++) {
                for (int c = 0; c < width; c++) {
                    maxTraversable = Math.max(maxTraversable, traversable.size(r * width + c));
                }
            }

            if (maxTraversable >= fieldReq) {
                valid = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }

        PrintWriter written = new PrintWriter("tractor.out");
        written.println(valid);
        written.close();
        System.out.println(valid);
        System.out.printf("%d ms that's it nothing more%n", System.currentTimeMillis() - start);
    }
}

/**
 * based on {@link utils.DisjointSets} and there's also a link explaining it
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

    public int size(int n) {
        return sizes[getUltimate(n)];
    }

    public void link(int e1, int e2) {
        e1 = getUltimate(e1);
        e2 = getUltimate(e2);
        if (e1 == e2) {
            return;
        }
        if (sizes[e2] > sizes[e1]) {
            int temp = e1;
            e1 = e2;
            e2 = temp;
        }
        parents[e2] = e1;
        sizes[e1] += sizes[e2];
    }
}
