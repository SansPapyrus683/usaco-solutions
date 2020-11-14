import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// an alternate implementation with arrays (a bit faster- maybe like 10 to 20%)
public class ArrayTime {
    private static final int MOD = (int) Math.pow(10, 9) + 7;
    private static final int LEN = (int) Math.pow(10, 4);

    static class Pair {
        public long first;
        public long second;
        public Pair(long first, long second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return String.format("Pair{first=%s, second=%s}", first, second);
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("triangles.in"));
        int postNum = Integer.parseInt(read.readLine());
        int[][] posts = new int[postNum][2];
        // bc we use arrays, shift the entire friggin coordinate plane up by 10^4 so all the coordinates will be positive
        ArrayList<Pair>[] sameXVals = new ArrayList[2 * LEN + 1];
        ArrayList<Pair>[] sameYVals = new ArrayList[2 * LEN + 1];
        for (int i = 0; i < 2 * LEN + 1; i++) {
            sameXVals[i] = new ArrayList<>();
            sameYVals[i] = new ArrayList<>();
        }
        for (int p = 0; p < postNum; p++) {
            int[] post = Stream.of(read.readLine().split(" ")).mapToLong(Long::parseLong).mapToInt(a -> (int) (a + LEN)).toArray();
            posts[p] = post;
            sameXVals[post[0]].add(new Pair(post[1], -1));
            sameYVals[post[1]].add(new Pair(post[0], -1));
        }

        for (ArrayList<Pair> y : sameXVals) {
            if (y.isEmpty()) {
                continue;
            }
            y.sort(Comparator.comparingLong(p -> p.first));
            long firstNum = y.get(0).first;
            y.get(0).second = y.stream().mapToLong(p -> p.first - firstNum).sum();
            for (int p = 1; p < y.size(); p++) {
                y.get(p).second = y.get(p - 1).second + (2 * p - y.size()) * (y.get(p).first - y.get(p - 1).first);
            }
        }

        for (ArrayList<Pair> x : sameYVals) {
            if (x.isEmpty()) {
                continue;
            }
            x.sort(Comparator.comparingLong(p -> p.first));
            long firstNum = x.get(0).first;
            x.get(0).second = x.stream().mapToLong(p -> p.first - firstNum).sum();
            for (int p = 1; p < x.size(); p++) {
                x.get(p).second = x.get(p - 1).second + (2 * p - x.size()) * (x.get(p).first - x.get(p - 1).first);
            }
        }

        long totalSum = 0;
        for (int[] p : posts) {
            long totalXDiff = sameXVals[p[0]]
                    .get(Collections.binarySearch(sameXVals[p[0]], new Pair(p[1], -1),
                            Comparator.comparingLong(a -> a.first))).second;
            long totalYDiff = sameYVals[p[1]]
                    .get(Collections.binarySearch(sameYVals[p[1]], new Pair(p[0], -1),
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
