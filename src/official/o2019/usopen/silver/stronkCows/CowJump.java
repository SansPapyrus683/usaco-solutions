package official.o2019.usopen.silver.stronkCows;

import java.io.*;
import java.util.*;

// 2019 us open silver (my initial solution for this exploited the test data, so)
public final class CowJump {
    private static int xAt;  // god forgive me for using a global
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cowjump.in"));
        int segmentNum = Integer.parseInt(read.readLine());
        IDSeg[] segments = new IDSeg[segmentNum];
        IDPoint[] endpoints = new IDPoint[segmentNum * 2];
        for (int s = 0; s < segmentNum; s++) {
            int[] rawSegment = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            segments[s] = new IDSeg(new IDPoint(rawSegment[0], rawSegment[1], s), new IDPoint(rawSegment[2], rawSegment[3], s), s);
            endpoints[2 * s] = segments[s].start;
            endpoints[2 * s + 1] = segments[s].end;
        }
        Arrays.sort(endpoints, Comparator.comparingInt(p -> p.x));

        // we just have to check for one intersecting pair, since all others are like non-intersecting
        IDSeg firstBad = null;
        IDSeg secondBad = null;
        /*
         * the segments that are sorted by y value
         * now you might be thinking, "wait aren't the value of the segments constantly changing, possibly resulting in a loss of order"?
         * that's what i thought
         * then i asked someone
         * the reason is because the only possible way for order to change is for an intersection to occur
         * and by then we would've broken out of the loop alr
         */
        TreeSet<IDSeg> relevantSegs = new TreeSet<>(Comparator.comparingDouble(IDSeg::currY));
        for (int i = 0; i < segmentNum * 2; i++) {
            xAt = endpoints[i].x;
            firstBad = segments[endpoints[i].id];
            if (relevantSegs.contains(firstBad)) {  // wait, this is the end, let's check and then remove
                relevantSegs.remove(firstBad);
                IDSeg below = relevantSegs.lower(firstBad);  // only possibility is for segs directly above and below anyways
                IDSeg above = relevantSegs.ceiling(firstBad);
                if (below != null && above != null && below.intersecting(above)) {
                    firstBad = below;
                    secondBad = above;
                }
            } else {
                // like above, it only makes sense to check the segments that are directly above and below it
                IDSeg below = relevantSegs.lower(firstBad);
                IDSeg above = relevantSegs.ceiling(firstBad);
                if (below != null && firstBad.intersecting(below)) {
                    secondBad = below;
                    break;
                } else if (above != null && firstBad.intersecting(above)) {
                    secondBad = above;
                    break;
                }
                relevantSegs.add(firstBad);
            }
        }

        int toRemove;
        if (firstBad == null || secondBad == null) {
            toRemove = -1;
        } else {
            int firstIntersecting = 0;
            int secondIntersecting = 0;
            for (IDSeg s : segments) {
                if (s.id != firstBad.id && s.intersecting(firstBad)) {
                    firstIntersecting++;
                }
                if (s.id != secondBad.id && s.intersecting(secondBad)) {
                    secondIntersecting++;
                }
            }

            if (firstIntersecting > secondIntersecting) {  // the first one has committed more crimes, let's remove that one
                toRemove = firstBad.id;
            } else if (secondIntersecting > firstIntersecting) {  // oh it's the second one?
                toRemove = secondBad.id;
            } else {  // if they're the same, take the one with the earlier index
                toRemove = Math.min(firstBad.id, secondBad.id);
            }
        }
        PrintWriter written = new PrintWriter("cowjump.out");
        written.println(toRemove + 1);
        written.close();
        System.out.println(toRemove + 1);
        System.out.printf("%d ms is an ok time? you could've done better lol%n", System.currentTimeMillis() - start);
    }

    // equality for these 2 final classes aren't based on their actual info, but id numbers they're given
    private static class IDPoint {
        public int x;
        public int y;
        public int id = 0;
        public IDPoint(int x, int y, int id) {
            this.x = x;
            this.y = y;
            this.id = id;
        }

        @Override
        public boolean equals(Object obj) {
            return obj.getClass() == getClass() && id == ((IDPoint) obj).id;
        }

        @Override
        public String toString() {
            return String.format("(%s, %s)", x, y);
        }
    }

    private static class IDSeg {
        public IDPoint start;
        public IDPoint end;
        public int id = 0;
        public IDSeg(IDPoint start, IDPoint end, int id) {
            this.start = start;
            this.end = end;
            this.id = id;
        }

        @Override
        public boolean equals(Object obj) {
            return obj.getClass() == getClass() && id == ((IDSeg) obj).id;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public String toString() {
            return String.format("Segment{start=%s,end=%s}", start, end);
        }

        // copied from: https://darrenyao.com/usacobook/java.pdf#page=84
        public boolean intersecting(IDSeg other) {
            return intersecting(other.start, other.end);
        }

        public boolean intersecting(IDPoint otherStart, IDPoint otherEnd) {
            int[] signs = {
                    sign(start, otherStart, otherEnd), sign(end, otherStart, otherEnd),
                    sign(otherStart, start, end), sign(otherEnd, start, end)
            };

            if (Arrays.stream(signs).allMatch(Integer.valueOf(0)::equals)) {
                return between(start, otherStart, otherEnd) || between(end, otherStart, otherEnd) || between(otherStart, start, end);
            }
            return signs[0] != signs[1] && signs[2] != signs[3];
        }

        public boolean between(IDPoint p, IDPoint x, IDPoint y) {  // returns if point p is between point x and y
            return ((x.x <= p.x && p.x <= y.x) || (y.x <= p.x && p.x <= x.x))
                    && ((x.y <= p.y && p.y <= y.y) || (y.y <= p.y && p.y <= x.y));
        }

        // i think this returns the sign of the area of like the 3 points (through like cross product or something)
        public int sign(IDPoint a, IDPoint b, IDPoint c) {
            return Integer.compare((b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y), 0);
        }

        public double currY() {  // gives the y-coordinate of the segment at the current x coordinate
            return start.x == end.x ? start.y : start.y + (end.y - start.y) * (xAt - start.x) / (double) (end.x - start.x);
        }
    }
}
