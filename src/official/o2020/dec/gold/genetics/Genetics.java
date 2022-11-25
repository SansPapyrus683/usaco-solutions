package official.o2020.dec.gold.genetics;

import java.io.*;
import java.util.*;

/**
 * 2020 dec gold
 * ? should output 4
 * GAT?GTT should output 3
 */
public class Genetics {
    private static final HashMap<Character, Integer> GENOME_MAPPING = new HashMap<Character, Integer>() {{
        put('?', -1);
        put('A', 0);
        put('T', 1);
        put('C', 2);
        put('G', 3);
    }};
    private static final int TYPE_NUM = GENOME_MAPPING.size() - 1;
    private static final int MOD = (int) Math.pow(10, 9) + 7;

    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        int[] corrupted = new BufferedReader(new InputStreamReader(System.in))
                .readLine().toUpperCase()
                .chars().map(i -> GENOME_MAPPING.get((char) i)).toArray();

        /*
         * this[i][lastStart][start][end]
         * i is the index we're splitting up to
         * lastStart is the type of the beginning of that last split (not the current one)
         * start is the type of the beginning of the current split
         * end is the type of the END of the current split
         * note that the last split doesn't have to be valid for it to be counted
         * but we only transition to a new split if the last one is possibly valid
         */
        long[][][][] splitsPossible = new long[corrupted.length][TYPE_NUM][TYPE_NUM][TYPE_NUM];
        for (int i = 0; i < TYPE_NUM; i++) {
            for (int first = 0; first < TYPE_NUM; first++) {
                if (corrupted[0] == -1 || corrupted[0] == first) {
                    // initialization- the lastStart can be anything in this case
                    splitsPossible[0][i][first][first] = 1;
                }
            }
        }
        for (int i = 1; i < corrupted.length; i++) {
            for (int newT = 0; newT < TYPE_NUM; newT++) {
                if (corrupted[i] != newT && corrupted[i] != -1) {
                    continue;
                }
                for (int lastStart = 0; lastStart < TYPE_NUM; lastStart++) {
                    for (int start = 0; start < TYPE_NUM; start++) {
                        for (int end = 0; end < TYPE_NUM; end++) {
                            if (newT != end) {
                                // we can't have 2 consec types in a split, so extend the previous streak only if it isn't the same
                                splitsPossible[i][lastStart][start][newT] += splitsPossible[i - 1][lastStart][start][end];
                                splitsPossible[i][lastStart][start][newT] %= MOD;
                            }
                            if (end == lastStart) {
                                /*
                                 * transition to a new streak only if the streak we have rn is valid
                                 * aka if we reversed it, the streak rn and the prev one meet with the same elements
                                 * so that fj could have conceivably split them at that point
                                 */
                                splitsPossible[i][start][newT][newT] += splitsPossible[i - 1][lastStart][start][end];
                                splitsPossible[i][start][newT][newT] %= MOD;
                            }
                        }
                    }
                }
            }
        }
        long possOriginalNum = 0;
        for (int t1 = 0; t1 < TYPE_NUM; t1++) {
            for (int t2 = 0; t2 < TYPE_NUM; t2++) {
                possOriginalNum += splitsPossible[corrupted.length - 1][t1][t2][t1];
            }
        }
        possOriginalNum %= MOD;
        System.out.println(possOriginalNum);
        System.err.printf("fj how many times are you going to sequence your cows' genomes: %d ms%n", System.currentTimeMillis() - timeStart);
    }
}
