package official.o2015.usopen.gold.hayPrison;

import java.io.*;
import java.util.*;

// 2015 us open gold
public final class Trapped {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("trapped.in"));
        int baleNum = Integer.parseInt(read.readLine());
        int[][] bales = new int[baleNum][2];
        for (int b = 0; b < baleNum; b++) {
            bales[b] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(bales, Comparator.comparingInt(b -> -b[0]));  // sort by size descending

        TreeSet<int[]> road = new TreeSet<>(Comparator.comparingInt(b -> b[1]));
        ArrayList<int[]> trapped = new ArrayList<>();
        for (int[] b : bales) {
            int[] right = road.higher(b);
            int[] left = road.lower(b);
            System.out.println(Arrays.toString(left) + " " + Arrays.toString(b) + " " + Arrays.toString(right));
            // if bessie can get trapped between this haybale and the other one, she'll definitely be trapped
            if (right != null && right[1] - b[1] <= Math.min(b[0], right[0])) {
                trapped.add(new int[] {b[1], right[1]});
            }
            if (left != null && b[1] - left[1] <= Math.min(b[0], left[0])) {
                trapped.add(new int[] {left[1], b[1]});
            }
            road.add(b);
        }

        trapped.sort(Comparator.comparingInt(i -> i[0]));
        int trappedArea = 0;
        if (!trapped.isEmpty()) {
            int begin = trapped.get(0)[0];
            int end = trapped.get(0)[1];
            for (int i = 1; i < trapped.size(); i++) {
                if (trapped.get(i)[0] > end) {
                    trappedArea += end - begin;
                    begin = trapped.get(i)[0];
                }
                end = Math.max(end, trapped.get(i)[1]);
            }
            trappedArea += end - begin;
        }
        PrintWriter written = new PrintWriter("trapped.out");
        written.println(trappedArea);
        written.close();
        System.out.println(trappedArea);
        System.out.printf("the real question is why does fj have haybales of size a billion: %d ms%n", System.currentTimeMillis() - start);
    }
}
