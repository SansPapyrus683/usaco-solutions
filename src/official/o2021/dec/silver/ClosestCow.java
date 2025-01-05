package official.o2021.dec.silver;

import java.io.*;
import java.util.*;

/** 2021 dec silver */
public class ClosestCow {
    private static class Thing {
        public long pos;  // uh yeah long just in case
        public boolean isPatch;
        public int taste;

        public Thing(long pos, boolean isPatch, int taste) {
            this.pos = pos;
            this.isPatch = isPatch;
            this.taste = taste;
        }

        @Override
        public String toString() {
            return isPatch ? String.format("%d @ %d", taste, pos) : Long.toString(pos);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int patchNum = Integer.parseInt(initial.nextToken());
        int nohjNum = Integer.parseInt(initial.nextToken());
        int johnNum = Integer.parseInt(initial.nextToken());
        List<Thing> things = new ArrayList<>();
        for (int p = 0; p < patchNum; p++) {
            StringTokenizer patch = new StringTokenizer(read.readLine());
            things.add(new Thing(
                    Integer.parseInt(patch.nextToken()),
                    true,
                    Integer.parseInt(patch.nextToken())
            ));
        }
        for (int c = 0; c < nohjNum; c++) {
            things.add(new Thing(Integer.parseInt(read.readLine()), false, -1));
        }
        // look man i just need a BIG number
        things.add(new Thing(Long.MAX_VALUE / 2, false, -1));
        things.sort(Comparator.comparingLong(t -> t.pos));

        long lastCow = Long.MIN_VALUE / 2;
        List<Thing> patches = new ArrayList<>();
        List<Long> tastiness = new ArrayList<>();
        for (Thing t : things) {
            if (t.isPatch) {
                patches.add(t);
                continue;
            }
            if (!patches.isEmpty()) {
                long[] sides = new long[3];
                for (Thing p : patches) {
                    final long left = p.pos - lastCow;
                    final long right = t.pos - p.pos;
                    sides[Long.compare(left, right) + 1] += p.taste;
                }
                tastiness.add(Math.max(sides[0], sides[2]) + sides[1]);
                tastiness.add(Math.min(sides[0], sides[2]));
            }
            lastCow = t.pos;
            patches = new ArrayList<>();
        }
        tastiness.sort(Comparator.reverseOrder());

        long best = tastiness.stream().limit(johnNum).mapToLong(Long::valueOf).sum();
        System.out.println(best);
    }
}
