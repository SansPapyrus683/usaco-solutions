package official.o2024.usopen.bronze;

import java.awt.Point;
import java.io.*;
import java.util.*;
import java.util.function.IntFunction;

/**
 * 2024 us open bronze
 * this is basically just the silver code copy and pasted, idk what to tell you
 */
public class WalkingAlongFence {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int postNum = Integer.parseInt(initial.nextToken());
        Point[] posts = new Point[postNum];
        Map<Integer, List<Integer>> byX = new HashMap<>();
        Map<Integer, List<Integer>> byY = new HashMap<>();
        for (int p = 0; p < postNum; p++) {
            StringTokenizer pt = new StringTokenizer(read.readLine());
            posts[p] =
                    new Point(Integer.parseInt(pt.nextToken()), Integer.parseInt(pt.nextToken()));
            if (!byX.containsKey(posts[p].x)) {
                byX.put(posts[p].x, new ArrayList<>());
            }
            byX.get(posts[p].x).add(p);
            if (!byY.containsKey(posts[p].y)) {
                byY.put(posts[p].y, new ArrayList<>());
            }
            byY.get(posts[p].y).add(p);
        }

        List<Integer>[] neighbors = new ArrayList[postNum];
        for (int p = 0; p < postNum; p++) {
            neighbors[p] = new ArrayList<>();
        }
        for (List<Integer> ySet : byX.values()) {
            ySet.sort(Comparator.comparingInt(p -> posts[p].y));
            for (int i = 0; i < ySet.size(); i += 2) {
                neighbors[ySet.get(i)].add(ySet.get(i + 1));
                neighbors[ySet.get(i + 1)].add(ySet.get(i));
            }
        }
        for (List<Integer> xSet : byY.values()) {
            xSet.sort(Comparator.comparingInt(p -> posts[p].x));
            for (int i = 0; i < xSet.size(); i += 2) {
                neighbors[xSet.get(i)].add(xSet.get(i + 1));
                neighbors[xSet.get(i + 1)].add(xSet.get(i));
            }
        }

        int[] order = new int[postNum]; // order[0] = 0 by default
        long[] distPref = new long[postNum + 1];
        Map<Point, Integer> edgeFirst = new HashMap<>();
        Map<Point, Integer> postMap = new HashMap<>();
        int at = 0;
        for (int p = 0; p < postNum; p++) {
            int moveTo = neighbors[at].get(0);
            if (p > 0 && moveTo == order[p - 1]) {
                moveTo = neighbors[at].get(1);
            }
            order[p] = at;
            postMap.put(posts[at], p);
            edgeFirst.put(new Point(at, moveTo), p);
            edgeFirst.put(new Point(moveTo, at), p);
            distPref[p + 1] = distPref[p] + (int) posts[at].distance(posts[moveTo]);
            at = moveTo;
        }

        for (int c = 0; c < cowNum; c++) {
            StringTokenizer st = new StringTokenizer(read.readLine());
            Point[] pos = new Point[2];
            pos[0] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            pos[1] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            int[][] data = new int[pos.length][];
            for (int i = 0; i < pos.length; i++) {
                if (postMap.containsKey(pos[i])) {
                    data[i] = new int[] {postMap.get(pos[i]), 0};
                    continue;
                }
                if (byX.containsKey(pos[i].x)) {
                    int ind = largestLt(byX.get(pos[i].x), pos[i].y, p -> posts[p].y);
                    if (ind % 2 == 0) {
                        int pt = byX.get(pos[i].x).get(ind);
                        int ptNext = byX.get(pos[i].x).get(ind + 1);
                        int first = edgeFirst.get(new Point(pt, ptNext));
                        data[i] = new int[] {first, (int) pos[i].distance(posts[order[first]])};
                        continue;
                    }
                }
                int ind = largestLt(byY.get(pos[i].y), pos[i].x, p -> posts[p].x);
                int pt = byY.get(pos[i].y).get(ind);
                int ptNext = byY.get(pos[i].y).get(ind + 1);
                int first = edgeFirst.get(new Point(pt, ptNext));
                data[i] = new int[] {first, (int) pos[i].distance(posts[order[first]])};
            }
            if (Arrays.compare(data[0], data[1]) > 0) {
                int[] temp = data[0];
                data[0] = data[1];
                data[1] = temp;
            }

            long dist = distPref[data[1][0]] - distPref[data[0][0]] - data[0][1] + data[1][1];
            System.out.println(Math.min(dist, distPref[postNum] - dist));
        }
    }

    // WHY THE HELL DOESN'T JAVE HAVE LOWER BOUND EVEN PYTHON HAS BISECT
    public static int largestLt(List<Integer> a, int i, IntFunction<Integer> key) {
        int lo = 0;
        int hi = a.size() - 1;
        int valid = -1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (key.apply(a.get(mid)) < i) {
                valid = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return valid;
    }
}
