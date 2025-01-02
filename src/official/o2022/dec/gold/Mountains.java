package official.o2022.dec.gold;

import java.io.*;
import java.util.*;

/** 2022 dec gold */
public class Mountains {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int mountainNum = Integer.parseInt(read.readLine());
        int[] heights = Arrays.stream(read.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        assert heights.length == mountainNum;

        TreeSet<Integer>[] points = new TreeSet[mountainNum];
        for (int i = 0; i < mountainNum; i++) {
            points[i] = toRight(heights, i);
        }

        int queryNum = Integer.parseInt(read.readLine());
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            int pos = Integer.parseInt(query.nextToken()) - 1;
            int inc = Integer.parseInt(query.nextToken());
            heights[pos] += inc;

            points[pos] = toRight(heights, pos);
            for (int i = 0; i < pos; i++) {
                points[i].remove(pos);
                SortedSet<Integer> relevant = points[i].tailSet(pos);
                Integer prev = points[i].lower(pos);
                if (prev == null || !blocks(heights, i, pos, prev)) {
                    while (!relevant.isEmpty() && blocks(heights, i, relevant.first(), pos)) {
                        relevant.remove(relevant.first());
                    }
                    points[i].add(pos);
                }
            }

            int seeable = Arrays.stream(points).mapToInt(TreeSet::size).sum();
            System.out.println(seeable);
        }
    }

    private static TreeSet<Integer> toRight(int[] heights, int pos) {
        TreeSet<Integer> ret = new TreeSet<>();
        for (int i = pos + 1; i < heights.length; i++) {
            if (ret.isEmpty() || !blocks(heights, pos, i, ret.last())) {
                ret.add(i);
            }
        }
        return ret;
    }

    private static boolean blocks(int[] heights, int from, int to, int block) {
        long dx = to - from, dy = heights[to] - heights[from];
        long bdx = block - from, bdy = heights[block] - heights[from];
        return bdx * dy < dx * bdy;
    }
}
