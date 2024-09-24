package official.o2024.feb.gold;

import java.io.*;
import java.util.*;

public class QuantumMoochanics {
    private static class CollideTime implements Comparable<CollideTime> {
        public long time;
        public int a, b;

        public CollideTime(long time, int a, int b) {
            this.time = time;
            this.a = a;
            this.b = b;
        }

        @Override
        public int compareTo(CollideTime o) {
            return Long.compare(time, o.time);
        }

        @Override
        public String toString() {
            return String.format("(%d, %d, %d)", time, a, b);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            int particleNum = Integer.parseInt(read.readLine());
            // assuming sorted order screw you
            long[] pos =
                    Arrays.stream(read.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
            int[] speed =
                    Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            assert pos.length == particleNum && speed.length == particleNum;

            PriorityQueue<CollideTime> willCollide = new PriorityQueue<>();
            TreeSet<Integer> stillAlive = new TreeSet<>();
            for (int i = 0; i < particleNum; i++) {
                stillAlive.add(i);
                if (i < particleNum - 1) {
                    long time = collideTime(i, i + 1, pos, speed);
                    willCollide.add(new CollideTime(time, i, i + 1));
                }
            }

            long[] collideTime = new long[particleNum];
            while (!stillAlive.isEmpty()) {
                List<CollideTime> thisTime = new ArrayList<>();
                long time = willCollide.peek().time;
                while (!willCollide.isEmpty() && willCollide.peek().time == time) {
                    thisTime.add(willCollide.poll());
                }

                for (CollideTime ct : thisTime) {
                    if (!stillAlive.contains(ct.a) || !stillAlive.contains(ct.b)) {
                        continue;
                    }
                    stillAlive.remove(ct.a);
                    stillAlive.remove(ct.b);
                    collideTime[ct.a] = collideTime[ct.b] = time;

                    assert stillAlive
                            .headSet(Math.max(ct.a, ct.b))
                            .tailSet(Math.min(ct.a, ct.b))
                            .isEmpty();
                    Integer lo = stillAlive.lower(ct.a);
                    Integer hi = stillAlive.higher(ct.a);
                    if (lo != null && hi != null) {
                        willCollide.add(new CollideTime(collideTime(lo, hi, pos, speed), lo, hi));
                    }
                }
            }

            StringBuilder ans = new StringBuilder();
            for (int i = 0; i < particleNum; i++) {
                ans.append(collideTime[i]).append(i == particleNum - 1 ? '\n' : ' ');
            }
            System.out.print(ans);
        }
    }

    private static long collideTime(int a, int b, long[] pos, int[] speed) {
        assert a % 2 != b % 2;
        boolean priority = a % 2 == 0 && a < b || a % 2 == 1 && b < a;
        long units = ceilDiv(Math.abs(pos[a] - pos[b]), speed[a] + speed[b]);
        return 2 * units - (priority ? 1 : 0);
    }

    private static long ceilDiv(long a, long b) {
        return Math.floorDiv(a + b - 1, b);
    }
}
