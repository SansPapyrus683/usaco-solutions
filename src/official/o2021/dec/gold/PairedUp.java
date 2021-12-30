package official.o2021.dec.gold;

import java.io.*;
import java.util.*;

/**
 * 2021 dec gold
 * (input ommitted due to length)
 */
public final class PairedUp {
    private static class Cow implements Comparable<Cow> {
        public int pos;
        public int weight;
        public Cow(int pos, int weight) {
            this.pos = pos;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return String.format("(%d, %d)", pos, weight);
        }

        @Override
        public int compareTo(Cow o) {
            return pos - o.pos;
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int type = Integer.parseInt(initial.nextToken());
        int cowNum = Integer.parseInt(initial.nextToken());
        int distReq = Integer.parseInt(initial.nextToken());
        Cow[] cows = new Cow[cowNum];
        for (int c = 0; c < cowNum; c++) {
            StringTokenizer cow = new StringTokenizer(read.readLine());
            cows[c] = new Cow(Integer.parseInt(cow.nextToken()), Integer.parseInt(cow.nextToken()));
        }

        if (type == 1) {
            System.out.println(minUnpaired(cows, distReq));
        } else if (type == 2) {
            System.out.println(maxUnpaired(cows, distReq));
        }
        System.out.printf("%d ms sadge%n", System.currentTimeMillis() - start);
    }

    private static int minUnpaired(Cow[] cows, int distReq) {
        Arrays.sort(cows);
        int[] minWasted = new int[cows.length + 1];
        minWasted[1] = cows[0].weight;
        for (int c = 2; c <= cows.length; c++) {
            Cow curr = cows[c - 1];
            minWasted[c] = minWasted[c - 1] + curr.weight;
            // pair with prev or one before prev
            if (curr.pos - cows[c - 2].pos <= distReq) {
                minWasted[c] = Math.min(
                        minWasted[c],
                        minWasted[c - 2]
                );
                if (c >= 3 && curr.pos - cows[c - 3].pos <= distReq) {
                    minWasted[c] = Math.min(
                            minWasted[c],
                            minWasted[c - 3] + cows[c - 2].weight
                    );
                }
            }
        }
        return minWasted[cows.length];
    }

    private static int maxUnpaired(Cow[] cows, int distReq) {
        Arrays.sort(cows);
        ArrayList<ArrayList<Cow>> consecCows = new ArrayList<>();
        ArrayList<Cow> curr = new ArrayList<>();
        int prevPos = cows[0].pos;
        for (Cow c : cows) {
            if (c.pos - prevPos > distReq) {
                consecCows.add(curr);
                curr = new ArrayList<>();
            }
            curr.add(c);
            prevPos = c.pos;
        }
        consecCows.add(curr);

        int total = 0;
        for (ArrayList<Cow> cc : consecCows) {
            System.out.println(cc);
            int[][] maxUnpaired = new int[cc.size() + 1][2];
            int prev = 0;
            for (int c = 0; c < cc.size(); c++) {
                while (cc.get(c).pos - cc.get(prev + 1).pos > distReq) {
                    prev++;
                }
                System.out.println(cc.get(c) + " " + cc.get(prev));
            }
        }
        return total;
    }
}
