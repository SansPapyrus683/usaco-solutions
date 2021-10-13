package official.o2018.usopen.silver.mooRegions;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2018 us open silver
public final class MultiMoo {
    private static  final int[] rChanges = new int[] {1, -1, 0, 0};
    private static  final int[] cChanges = new int[] {0, 0, 1, -1};
    private static  int side;
    private static  int currID = 0;
    private static  int[][] board;
    private static  int[][] ids;  // these ids are different from cow ids
    private static  ArrayList<Integer> regionSizes = new ArrayList<>();
    private static  ArrayList<HashSet<ArrayList<Integer>>> adjPoints = new ArrayList<>();
    private static  boolean[][] visited;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("multimoo.in"));
        side = Integer.parseInt(read.readLine());
        board = new int[side][side];
        ids = new int[side][side];
        visited = new boolean[side][side];
        for (int r = 0; r < side; r++) {
            board[r] = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        for (int r = 0; r < side; r++) {
            for (int c = 0; c < side; c++) {
                if (!visited[r][c]) {
                    expand(r, c);
                }
            }
        }

        int maxDoubleSize = 0;
        bruteForce:
        for (int r = 0; r < side; r++) {
            for (int c = 0; c < side; c++) {
                for (int[] n : neighbors(r, c)) {
                    if (board[r][c] != board[n[0]][n[1]]) {
                        maxDoubleSize = Math.max(maxDoubleSize, twoRegionSize(new int[] {r, c}, n));
                        // this optimization is essentially worthless but it makes it pass the test cases so what the hell
                        if (maxDoubleSize == (int) Math.pow(side, 2)) {
                            break bruteForce;
                        }
                    }
                }
            }
        }

        PrintWriter written = new PrintWriter("multimoo.out");
        written.printf("%s%n%s%n", Collections.max(regionSizes), maxDoubleSize);
        written.close();
        System.out.printf("%s%n%s%n", Collections.max(regionSizes), maxDoubleSize);
        System.out.printf("This is the greatest moo game of All Time: %d ms%n", System.currentTimeMillis() - start);
    }

    static ArrayList<int[]> neighbors(int r, int c) {
        ArrayList<int[]> valid = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int newR = r + rChanges[i];
            int newC = c + cChanges[i];
            if (0 <= newR && newR < side && 0 <= newC && newC < side) {
                valid.add(new int[] {newR, newC});
            }
        }
        return valid;
    }

    static void expand(int r, int c) {
        int cowID = board[r][c];
        ArrayDeque<int[]> frontier = new ArrayDeque<>(Collections.singletonList(new int[]{r, c}));
        HashSet<ArrayList<Integer>> thisAdj = new HashSet<>();
        int size = 1;
        visited[r][c] = true;
        ids[r][c] = currID;

        while (!frontier.isEmpty()) {
            int[] current = frontier.poll();
            int currR = current[0], currC = current[1];
            for (int[] n : neighbors(currR, currC)) {
                // if it's next to the region but isn't a PART of the region, add it to the "neighbors"
                if (board[n[0]][n[1]] != cowID) {
                    thisAdj.add(new ArrayList<>(Arrays.asList(n[0], n[1])));  // arrays aren't hashable so...
                } else if (!visited[n[0]][n[1]] && board[n[0]][n[1]] == cowID) {
                    frontier.add(n);
                    visited[n[0]][n[1]] = true;
                    ids[n[0]][n[1]] = currID;
                    size++;
                }
            }
        }
        adjPoints.add(thisAdj);  // for this id, record it's size and all the adjacent points
        regionSizes.add(size);
        currID++;
    }

    static int twoRegionSize(int[] first, int[] second) {
        int firstID = ids[first[0]][first[1]];
        int secondID = ids[second[0]][second[1]];
        int type1 = board[first[0]][first[1]];
        int type2 = board[second[0]][second[1]];
        HashSet<Integer> doneIDs = new HashSet<>(Arrays.asList(firstID, secondID));
        ArrayDeque<Integer> frontier = new ArrayDeque<>(Arrays.asList(firstID, secondID));
        int totalSize = regionSizes.get(firstID) + regionSizes.get(secondID);

        while (!frontier.isEmpty()) {
            int current = frontier.poll();
            for (ArrayList<Integer> n : adjPoints.get(current)) {
                int neighborID = ids[n.get(0)][n.get(1)];
                if (!doneIDs.contains(neighborID) &&
                        (board[n.get(0)][n.get(1)] == type1 || board[n.get(0)][n.get(1)] == type2)) {
                    doneIDs.add(neighborID);
                    frontier.add(neighborID);
                    totalSize += regionSizes.get(neighborID);
                }
            }
        }
        return totalSize;
    }
}
