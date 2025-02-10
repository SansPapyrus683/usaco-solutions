package official.o2025.jan.silver;

import java.io.*;
import java.util.*;

/** 2025 jan silver */
public class TableRecovery {
    private static class Pos implements Comparable<Pos> {
        public int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public String toString() {
            return String.format("(%d, %d)", r, c);
        }

        @Override
        public int compareTo(Pos other) {
            return r != other.r ? r - other.r : c - other.c;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int side = Integer.parseInt(read.readLine());
        List<Pos>[] freq = new List[side * 2 + 1];
        for (int i = 0; i < freq.length; i++) {
            freq[i] = new ArrayList<>();
        }
        for (int r = 0; r < side; r++) {
            StringTokenizer row = new StringTokenizer(read.readLine());
            for (int c = 0; c < side; c++) {
                freq[Integer.parseInt(row.nextToken())].add(new Pos(r, c));
            }
        }

        List<Pos>[][] classes = new List[side + 1][2];
        for (List<Pos> set : freq) {
            set.sort(Pos::compareTo);
            List<Pos>[] curr = classes[set.size()];
            curr[curr[0] != null ? 1 : 0] = set;
        }

        int[][] first = buildOrig(classes, side, false);
        int[][] second = buildOrig(classes, side, true);
        int[][] arr = Arrays.compare(first[0], second[0]) < 0 ? first : second;

        StringBuilder gridStr = new StringBuilder();
        for (int r = 0; r < side; r++) {
            for (int c = 0; c < side; c++) {
                gridStr.append(arr[r][c]).append(c == side - 1 ? '\n' : ' ');
            }
        }
        System.out.print(gridStr);
    }

    private static int[][] buildOrig(List<Pos>[][] classes, int side, boolean swap) {
        int zeroRow = -1;
        int[][] arr = new int[side][side];
        for (int i = 1; i < classes.length - 1; i++) {
            List<Pos> first = classes[i][0], second = classes[i][1];
            if (i == 1) {
                if (swap) {
                    List<Pos> temp = first;
                    first = second;
                    second = temp;

                }
                zeroRow = first.get(0).r;
            } else {
                final int stupidJava = zeroRow;
                boolean hasFirst = first.stream().anyMatch(p -> p.r == stupidJava);
                if (!hasFirst) {
                    List<Pos> temp = first;
                    first = second;
                    second = temp;
                }
            }

            final int stupidJava = i + 1;
            first.forEach(p -> arr[p.r][p.c] = stupidJava);
            final int broWhat = side * 2 + 1 - i;
            second.forEach(p -> arr[p.r][p.c] = broWhat);
        }
        classes[side][0].forEach(p -> arr[p.r][p.c] = side + 1);
        return arr;
    }
}
