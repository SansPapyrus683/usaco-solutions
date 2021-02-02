package official.o2020.jan.gold.boingBoing;

import java.io.*;
import java.util.*;

// 2020 jan gold
public final class Boards {
    private static class MinMap {
        private final TreeMap<Integer, Integer> map = new TreeMap<>();
        public void put(int key, int val) {
            if (map.isEmpty()) {
                map.put(key, val);
                return;
            }

            // try to keep a steadily decreasing list of values
            // if this one is greater than a previous one, there's no point in putting it in
            if (key >= firstKey() && val > minLower(key)) {
                return;
            }
            // ok, let's see how many other values this eliminates (curse you java for not having iterators)
            map.put(key, val);
            int nextUp;
            while (key != map.lastKey() && map.get(nextUp = map.higherKey(key)) >= val) {
                map.remove(nextUp);
            }
        }

        // returns the minimum value whose corresponding key is <= threshold
        public int minLower(int threshold) {
            return map.get(map.floorKey(threshold));
        }

        public int firstKey() {
            return map.firstKey();
        }

        public boolean isEmpty() {
            return map.isEmpty();
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("boards.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int end = Integer.parseInt(initial.nextToken());
        int boardNum = Integer.parseInt(initial.nextToken());
        int[][] boardPoints = new int[boardNum * 2][3];
        for (int b = 0; b < boardNum; b++) {
            int[] board = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            boardPoints[b * 2] = new int[] {board[0], board[1], b, 1};  // 1 means start, 0 means end
            boardPoints[b * 2 + 1] = new int[] {board[2], board[3], b, 0};
        }
        Arrays.sort(boardPoints, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

        MinMap discounts = new MinMap();
        int[] startCosts = new int[boardNum];  // not sure if longs are needed but i ain't taking any chances
        for (int i = 0; i < boardNum * 2; i++) {
            int[] bp = boardPoints[i];
            if (bp[3] == 1) {  // start, so let's calculate the min start cost
                startCosts[bp[2]] = bp[0] + bp[1]
                        + (discounts.isEmpty() || bp[1] < discounts.firstKey() ? 0 : discounts.minLower(bp[1]));
            } else {  // end, so we can record that discount (other springboards can use this one now)
                // we have to walk to that springboard, but we can jump right to bp[0] - bp[1]
                discounts.put(bp[1], startCosts[bp[2]] - bp[0] - bp[1]);
            }
        }

        long minMovement = end + end + discounts.minLower(end);
        PrintWriter written = new PrintWriter("boards.out");
        written.println(minMovement);
        written.close();
        System.out.println(minMovement);
        System.out.printf("%d ms :)%n", System.currentTimeMillis() - start);
    }
}
