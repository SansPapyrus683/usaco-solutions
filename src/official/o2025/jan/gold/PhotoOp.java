package official.o2025.jan.gold;

import java.io.*;
import java.util.*;

/** 2025 jan gold (awful solution it is what it is) */
public class PhotoOp {
    private static class Region {
        public int sx, sy;
        public int ex, ey;

        public Region(int sx, int sy, int ex, int ey) {
            this.sx = sx;
            this.sy = sy;
            this.ex = ex;
            this.ey = ey;
        }

        public long dist(int x, int y) {
            long start = Math.max(sx, Math.min(ex, x));
            long end = Math.max(sy, Math.min(ey, y));
            long stupid = Math.abs(start - x) + Math.abs(end - y);
            return stupid + (long) Math.sqrt(start * start + end * end);
        }

        @Override
        public String toString() {
            return String.format("(%d, %d) to (%d, %d)", sx, sy, ex, ey);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer line = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(line.nextToken());
        int time = Integer.parseInt(line.nextToken());
        line = new StringTokenizer(read.readLine());
        int x = Integer.parseInt(line.nextToken());
        int y = Integer.parseInt(line.nextToken());

        int[][] cows = new int[cowNum][3];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" "))
                    .mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(cows, Arrays::compare);

        TreeSet<Region> ranges = new TreeSet<>(Comparator.comparingInt(l -> l.sx));
        TreeMap<Long, Integer> minDists = new TreeMap<>();
        Region initial = new Region(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
        ranges.add(initial);
        minDists.put(initial.dist(x, y), 1);
        int cowAt = 0;
        for (int t = 0; t < time; t++) {
            while (cowAt < cowNum && cows[cowAt][0] <= t) {
                int[] cow = cows[cowAt++];
                int cx = cow[1], cy = cow[2];  // shorthand
                // thank GOD all the points are distinct
                Region ref = ranges.lower(new Region(cx, 0, 0, 0));
                if (cx < ref.ex && ref.sy < cy && cy < ref.ey) {
                    change(minDists, ref.dist(x, y), -1);
                    Region add = new Region(cx, cy, ref.ex, ref.ey);
                    ref.ex = cx;
                    ref.ey = cy;
                    change(minDists, ref.dist(x, y), 1);
                    change(minDists, add.dist(x, y), 1);
                    ranges.add(add);
                } else if (cx < ref.ex) {
                    // ok so we're at least in the range of lower
                    if (cy < ref.sy) {
                        Region pastThat = ranges.lower(ref);
                        while (cy < pastThat.sy) {
                            change(minDists, pastThat.dist(x, y), -1);
                            ranges.remove(pastThat);
                            pastThat = ranges.lower(ref);
                        }

                        change(minDists, ref.dist(x, y), -1);
                        ref.sx = cx;
                        change(minDists, ref.dist(x, y), 1);

                        change(minDists, pastThat.dist(x, y), -1);
                        pastThat.ey = Math.min(pastThat.ey, cy);
                        change(minDists, pastThat.dist(x, y), 1);
                    } else if (cy > ref.ey) {
                        Region pastThat = ranges.higher(ref);
                        while (cy > pastThat.ey) {
                            change(minDists, pastThat.dist(x, y), -1);
                            ranges.remove(pastThat);
                            pastThat = ranges.higher(ref);
                        }

                        change(minDists, ref.dist(x, y), -1);
                        ref.ex = cx;
                        change(minDists, ref.dist(x, y), 1);

                        change(minDists, pastThat.dist(x, y), -1);
                        pastThat.sy = Math.max(pastThat.sy, cy);
                        change(minDists, pastThat.dist(x, y), 1);
                    }
                } else {
                    // lmao we're not even in the range of that
                    if (cy < ref.ey) {
                        Region pastThat = ref;
                        while (cy < pastThat.sy) {
                            change(minDists, pastThat.dist(x, y), -1);
                            ranges.remove(pastThat);
                            pastThat = ranges.lower(ref);
                        }

                        change(minDists, pastThat.dist(x, y), -1);
                        pastThat.ey = Math.min(pastThat.ey, cy);
                        change(minDists, pastThat.dist(x, y), 1);
                    } else {
                        Region pastThat = ranges.higher(ref);
                        while (cy > pastThat.ey) {
                            change(minDists, pastThat.dist(x, y), -1);
                            ranges.remove(pastThat);
                            pastThat = ranges.higher(ref);
                        }

                        change(minDists, pastThat.dist(x, y), -1);
                        pastThat.sy = Math.max(pastThat.sy, cy);
                        change(minDists, pastThat.dist(x, y), 1);
                    }
                }
            }

            System.out.println(minDists.firstKey());
        }
    }

    private static <T> void change(TreeMap<T, Integer> map, T key, int amt) {
        int freq = map.getOrDefault(key, 0);
        if (freq + amt <= 0) {
            map.remove(key);
        } else {
            map.put(key, freq + amt);
        }
    }
}
