package official.o2021.feb.silver;

import java.io.*;
import java.util.*;

// 2021 feb silver (input omitted due to length)
public final class Comfort {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());
        int[][] cows = new int[cowNum][2];
        int maxPos = 0;
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            maxPos = Math.max(maxPos, Math.max(cows[c][0], cows[c][1]));
        }

        StringBuilder ans = new StringBuilder();
        boolean[][] occupied = new boolean[maxPos * 3][maxPos * 3];
        int added = 0;
        for (int[] c : cows) {
            c[0] += maxPos;
            c[1] += maxPos;
            ArrayDeque<int[]> notAdded = new ArrayDeque<>();
            notAdded.add(c);
            added--;  // because this new cow doesn't count as an extra cow
            ArrayList<int[]> temp;
            while (!notAdded.isEmpty()) {
                int[] toAdd = notAdded.poll();
                if (occupied[toAdd[0]][toAdd[1]]) {  // make sure we haven't added this cow already
                    continue;
                }
                occupied[toAdd[0]][toAdd[1]] = true;
                added++;
                // check if the added cow itself is comfortable is not
                if ((temp = empty(occupied, toAdd)).size() == 1) {
                    notAdded.add(temp.get(0));
                }
                for (int[] n : neighbors(toAdd, occupied.length, occupied[0].length)) {
                    // if the neighbor actually has a cow and that cow is comfortable, well, make it uncomfortable
                    if (occupied[n[0]][n[1]] && (temp = empty(occupied, n)).size() == 1) {
                        notAdded.add(temp.get(0));
                    }
                }
            }
            ans.append(added).append('\n');
        }
        System.out.print(ans);
        System.err.printf("farmer nohj is so evil omg: %d ms%n", System.currentTimeMillis() - start);
    }

    private static ArrayList<int[]> neighbors(int[] pos, int rowNum, int colNum) {
        ArrayList<int[]> neighbors = new ArrayList<>();
        if (pos[0] > 0) {
            neighbors.add(new int[] {pos[0] - 1, pos[1]});
        }
        if (pos[1] > 0) {
            neighbors.add(new int[] {pos[0], pos[1] - 1});
        }
        if (pos[0] < rowNum) {
            neighbors.add(new int[] {pos[0] + 1, pos[1]});
        }
        if (pos[1] < colNum) {
            neighbors.add(new int[] {pos[0], pos[1] + 1});
        }
        return neighbors;
    }

    private static ArrayList<int[]> empty(boolean[][] cows, int[] pos) {
        ArrayList<int[]> empty = new ArrayList<>();
        for (int[] n : neighbors(pos, cows.length, cows[0].length)) {
            if (!cows[n[0]][n[1]]) {
                empty.add(n);
            }
        }
        return empty;
    }
}
