package official.o2018.feb.gold.bootsGalore;

import java.io.*;
import java.util.*;

// 2018 feb gold
public class GoldBoots {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("snowboots.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        initial.nextToken();
        int bootNum = Integer.parseInt(initial.nextToken());
        int[] tiles = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        assert tiles[0] == 0 && tiles[tiles.length - 1] == 0;
        int[][] boots = new int[bootNum][3];
        for (int b = 0; b < bootNum; b++) {
            StringTokenizer boot = new StringTokenizer(read.readLine());
            boots[b] = new int[] {Integer.parseInt(boot.nextToken()), Integer.parseInt(boot.nextToken()), b};
        }
        Arrays.sort(boots, Comparator.comparingInt(b -> -b[0]));
        PriorityQueue<Integer> sortedTiles = new PriorityQueue<>(Comparator.comparingInt(t -> -tiles[t]));
        int[][] tileIsland = new int[tiles.length][2];
        for (int t = 0; t < tiles.length; t++) {
            sortedTiles.add(t);
            // each "island" consists of a reference to the index of the prev. valid island and the next valid island
            tileIsland[t] = new int[] {Math.max(0, t - 1), Math.min(tiles.length - 1, t + 1)};
        }

        int maximumChasm = 0;
        int[] possible = new int[bootNum];
        for (int[] boot : boots) {  // go through the boots (which we sorted from toughest to least toughest)
            while (tiles[sortedTiles.peek()] > boot[0]) {  // remove the tiles we can't traverse
                int removed = sortedTiles.poll();
                int[] adjacent = tileIsland[removed];
                maximumChasm = Math.max(maximumChasm, adjacent[1] - adjacent[0]);  // update the maximum "jump" we have to make
                tileIsland[adjacent[0]][1] = adjacent[1];  // update adjacent tile references, kinda like a linkedlist
                tileIsland[adjacent[1]][0] = adjacent[0];
                tileIsland[removed] = null;
            }
            if (boot[1] >= maximumChasm) {  // only possible if we can make it across that chasm (with the updated tiles)
                possible[boot[2]] = 1;
            }
        }

        PrintWriter written = new PrintWriter("snowboots.out");
        for (int p : possible) {
            written.println(p);
            System.out.println(p);
        }
        written.close();
        System.out.printf("%d ms is a real :POG: time tho ngl%n", System.currentTimeMillis() - start);
    }
}
