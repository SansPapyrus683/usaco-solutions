import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2018 feb silver
public class SnowBoots {
    private static final int INVALID = 420696969;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("snowboots.in"));
        StringTokenizer intial = new StringTokenizer(read.readLine());
        intial.nextToken();
        int bootNum = Integer.parseInt(intial.nextToken());
        int[][] boots = new int[bootNum][2];
        int[] tiles = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int tileLength = tiles.length;
        for (int b = 0; b < bootNum; b++) {
            // 0th is snow amt, 1st is step amt
            boots[b] = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        int[] bestTileStates = new int[tileLength];  // this[i] is the min discarded
        Arrays.fill(bestTileStates, INVALID);
        bestTileStates[0] = 0;  // he starts off w/ 0 boots wasted and wearing the first pair of boots

        // this takes advantage of that the # of boots he's discarded is also the index of the boot he's wearing right now
        for (int t = 0; t < tileLength; t++) {
            int tileBest = bestTileStates[t];
            if (tileBest == INVALID) {
                continue;
            }
            int tileDepth = tiles[t];
            // go through all the boots that are left (including the one he's wearing rn)
            for (int nb = tileBest; nb < boots.length; nb++) {
                int[] nextBoot = boots[nb];
                if (nextBoot[0] < tileDepth) {  // if he can switch to that boot on this tile
                    continue;
                }
                for (int toT = t + 1; toT <= Math.min(t + nextBoot[1], tileLength - 1); toT++) {
                    // see where he can go with those switched boots
                    if (nextBoot[0] >= tiles[toT]) {
                        bestTileStates[toT] = Math.min(bestTileStates[toT], nb);
                    }
                }
            }
        }

        // don't worry, the input is always guaranteed to have fj reach the farm
        PrintWriter written = new PrintWriter("snowboots.out");
        written.println(bestTileStates[tileLength - 1]);
        written.close();
        System.out.println(bestTileStates[tileLength - 1]);
        System.out.printf("while farmer john was prepping for his journey, it took %d ms%n", System.currentTimeMillis() - start);
    }
}
