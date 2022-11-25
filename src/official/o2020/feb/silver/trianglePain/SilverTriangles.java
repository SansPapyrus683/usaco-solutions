package official.o2020.feb.silver.trianglePain;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2020 feb silver (as if that wasn't obvious already)
public class SilverTriangles {
    private static final int MOD = (int) Math.pow(10, 9) + 7;

    private static final class Pair {  // underscore so it doesn't clash with that other pair final class
        public long first;
        public long second;
        public Pair(long first, long second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return String.format("Pair_{first=%s, second=%s}", first, second);
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("triangles.in"));
        int postNum = Integer.parseInt(read.readLine());
        int[][] posts = new int[postNum][2];
        HashMap<Integer, ArrayList<Pair>> sameXVals = new HashMap<>();
        HashMap<Integer, ArrayList<Pair>> sameYVals = new HashMap<>();
        for (int p = 0; p < postNum; p++) {
            int[] post = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            posts[p] = post;
            // the second part will be the sum of the distances from this number to the other numbers
            if (!sameXVals.containsKey(post[0])) {
                sameXVals.put(post[0], new ArrayList<>());
            }
            sameXVals.get(post[0]).add(new Pair(post[1], -1));
            if (!sameYVals.containsKey(post[1])) {
                sameYVals.put(post[1], new ArrayList<>());
            }
            sameYVals.get(post[1]).add(new Pair(post[0], -1));
        }

        // here we calculate the sums in linear time from the previous ones, it's some math stuff idc to explain lol
        for (ArrayList<Pair> y : sameXVals.values()) {
            y.sort(Comparator.comparingLong(p -> p.first));
            long firstNum = y.get(0).first;
            y.get(0).second = y.stream().mapToLong(p -> p.first - firstNum).sum();
            for (int p = 1; p < y.size(); p++) {
                y.get(p).second = y.get(p - 1).second + (2L * p - y.size()) * (y.get(p).first - y.get(p - 1).first);
            }
        }

        for (ArrayList<Pair> x : sameYVals.values()) {
            x.sort(Comparator.comparingLong(p -> p.first));
            long firstNum = x.get(0).first;
            x.get(0).second = x.stream().mapToLong(p -> p.first - firstNum).sum();
            for (int p = 1; p < x.size(); p++) {
                x.get(p).second = x.get(p - 1).second + (2L * p - x.size()) * (x.get(p).first - x.get(p - 1).first);
            }
        }

        /*
         * if we have point p as the corner, then all the areas w/ it is going to be the sum of the distances on the x axis
         * time the sum of the distances on the y axis (more math stuff i'm not going to explain lol)
         * */
        long totalSum = 0;
        for (int[] p : posts) {
            long totalXDiff = sameXVals.get(p[0])  // use binary search to get the sums and then multiply them
                    .get(Collections.binarySearch(sameXVals.get(p[0]), new Pair(p[1], -1),
                            Comparator.comparingLong(a -> a.first))).second;
            long totalYDiff = sameYVals
                    .get(p[1]).get(Collections.binarySearch(sameYVals.get(p[1]), new Pair(p[0], -1),
                            Comparator.comparingLong(a -> a.first))).second;
            totalSum = (totalSum + ((totalXDiff % MOD) * (totalYDiff % MOD) % MOD)) % MOD;
        }
        PrintWriter written = new PrintWriter("triangles.out");
        written.println(totalSum);
        written.close();
        System.out.println(totalSum);
        System.out.printf("i say that it took %d ms- the grader might think differently%n", System.currentTimeMillis() - start);
    }
}
