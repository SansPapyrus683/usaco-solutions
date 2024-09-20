package official.o2024.feb.silver;

import java.io.*;
import java.util.*;

public class TargetPracticeII {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            StringTokenizer initial = new StringTokenizer(read.readLine());
            int rectNum = Integer.parseInt(initial.nextToken());
            int rectX = Integer.parseInt(initial.nextToken());
            int[][] rects = new int[rectNum][3];
            int[] allY = new int[rectNum * 2];
            for (int r = 0; r < rectNum; r++) {
                StringTokenizer rect = new StringTokenizer(read.readLine());
                for (int p = 0; p < rects[r].length; p++) {
                    rects[r][p] = Integer.parseInt(rect.nextToken());
                }
                allY[2 * r] = rects[r][0];
                allY[2 * r + 1] = rects[r][1];
            }

            // apparently there's always going to be an angle, no straight lines
            int[] cows =
                    Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            assert cows.length == 4 * rectNum;

            int[] pos = Arrays.stream(cows).filter(c -> c > 0).sorted().toArray();
            int[] neg = Arrays.stream(cows).filter(c -> c < 0).sorted().toArray();
            if (pos.length < rectNum || neg.length < rectNum) {
                System.out.println(-1);
                continue;
            }

            long minY = Long.MAX_VALUE;
            long lo = Long.MIN_VALUE / 2;
            long hi = Long.MAX_VALUE / 2;
            TreeMap<Long, Integer> canTake = new TreeMap<>();
            Arrays.stream(Arrays.copyOfRange(pos, 0, rectNum))
                    .asLongStream()
                    .forEach(i -> canTake.put(i, canTake.getOrDefault(i, 0) + 1));
            while (lo <= hi) {
                long mid = (lo + hi) / 2;
                TreeMap<Long, Integer> temp = new TreeMap<>(canTake);
                boolean valid = true;
                for (int[] r : rects) {
                    long atMost = Math.floorDiv(r[0] - mid,  r[2]);
                    Map.Entry<Long, Integer> take = temp.floorEntry(atMost);
                    if (take == null) {
                        valid = false;
                        break;
                    }
                    if (take.getValue() == 1) {
                        temp.remove(take.getKey());
                    } else {
                        temp.put(take.getKey(), take.getValue() - 1);
                    }
                }
                if (valid) {
                    minY = mid;
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }

            long maxY = Long.MIN_VALUE;
            lo = Long.MIN_VALUE / 2;
            hi = Long.MAX_VALUE / 2;
            canTake.clear();
            Arrays.stream(Arrays.copyOfRange(neg, neg.length - rectNum - 1, neg.length))
                    .asLongStream()
                    .forEach(i -> canTake.put(i, canTake.getOrDefault(i, 0) + 1));
            // yes, this is basically the same binary search all over again
            // yes, i wish i could combine these two into a single one.
            while (lo <= hi) {
                long mid = (lo + hi) / 2;
                TreeMap<Long, Integer> temp = new TreeMap<>(canTake);
                boolean valid = true;
                for (int[] r : rects) {
                    long atLeast = Math.floorDiv(r[1] - mid + r[2] - 1, r[2]);
                    Map.Entry<Long, Integer> take = temp.ceilingEntry(atLeast);
                    if (take == null) {
                        valid = false;
                        break;
                    }
                    if (take.getValue() == 1) {
                        temp.remove(take.getKey());
                    } else {
                        temp.put(take.getKey(), take.getValue() - 1);
                    }
                }
                if (valid) {
                    maxY = mid;
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }

            int[] remaining = new int[2 * rectNum];
            int at = 0;
            for (int r = rectNum; r < pos.length; r++) {
                remaining[at++] = pos[r];
            }
            for (int r = 0; r < neg.length - rectNum; r++) {
                remaining[at++] = neg[r];
            }
            Arrays.sort(allY);
            Arrays.sort(remaining);
            for (int i = 0; i < remaining.length; i++) {
                long y = allY[i] - (long) remaining[i] * rectX;
                minY = Math.min(minY, y);
                maxY = Math.max(maxY, y);
            }

            System.out.println(maxY - minY);
        }
    }
}
