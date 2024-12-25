package official.o2024.dec.silver;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/** 2024 dec silver */
public class ConveyorBelt {
    private static final Map<Character, int[]> DELTA = new HashMap<>() {{
        put('U', new int[]{-1, 0});
        put('D', new int[]{1, 0});
        put('L', new int[]{0, -1});
        put('R', new int[]{0, 1});
    }};

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int side = Integer.parseInt(initial.nextToken()) + 2;  // padding!!!!
        char[][] grid = new char[side][side];
        for (char[] row : grid) {
            Arrays.fill(row, '?');
        }

        int placeNum = Integer.parseInt(initial.nextToken());
        int[][] conv = new int[placeNum][2];
        for (int p = 0; p < placeNum; p++) {
            StringTokenizer c = new StringTokenizer(read.readLine());
            conv[p][0] = Integer.parseInt(c.nextToken());
            conv[p][1] = Integer.parseInt(c.nextToken());
            grid[conv[p][0]][conv[p][1]] = c.nextToken().charAt(0);
        }

        boolean[][] free = new boolean[side][side];
        Deque<int[]> frontier = new ArrayDeque<>();
        int[] unused = new int[placeNum];
        unused[placeNum - 1] = (side - 2) * (side - 2);
        for (int i = 0; i < side; i++) {
            for (int[] pos : new int[][]{{0, i}, {i, 0}, {side - 1, i}, {i, side - 1}}) {
                free[pos[0]][pos[1]] = true;
                frontier.add(pos);
            }
        }
        while (!frontier.isEmpty()) {
            int[] curr = frontier.poll();
            for (int[] n : neighbors(curr, side)) {
                if (free[n[0]][n[1]]) {
                    continue;
                }
                if (grid[n[0]][n[1]] == '?'
                        || Arrays.equals(add(n, DELTA.get(grid[n[0]][n[1]])), curr)) {
                    free[n[0]][n[1]] = true;
                    unused[placeNum - 1]--;
                    frontier.add(n);
                }
            }
        }

        for (int i = placeNum - 1; i > 0; i--) {
            int[] c = conv[i];
            grid[c[0]][c[1]] = '?';
            unused[i - 1] = unused[i];
            if (free[c[0]][c[1]]) {
                continue;
            }

            frontier = neighbors(c, side).stream()
                    .filter(n -> free[n[0]][n[1]])
                    .collect(Collectors.toCollection(ArrayDeque::new));
            while (!frontier.isEmpty()) {
                int[] curr = frontier.poll();
                for (int[] n : neighbors(curr, side)) {
                    if (free[n[0]][n[1]]) {
                        continue;
                    }
                    if (grid[n[0]][n[1]] == '?'
                            || Arrays.equals(add(n, DELTA.get(grid[n[0]][n[1]])), curr)) {
                        free[n[0]][n[1]] = true;
                        unused[i - 1]--;
                        frontier.add(n);
                    }
                }
            }
        }

        Arrays.stream(unused).forEach(System.out::println);
    }

    private static int[] add(int[] a, int[] b) {
        return new int[]{a[0] + b[0], a[1] + b[1]};
    }

    private static List<int[]> neighbors(int[] at, int side) {
        List<int[]> ret = new ArrayList<>();
        // i don't usually remove braces but i feel like this is necessary
        if (at[0] > 0) ret.add(new int[]{at[0] - 1, at[1]});
        if (at[1] > 0) ret.add(new int[]{at[0], at[1] - 1});
        if (at[0] + 1 < side) ret.add(new int[]{at[0] + 1, at[1]});
        if (at[1] + 1 < side) ret.add(new int[]{at[0], at[1] + 1});
        return ret;
    }
}
