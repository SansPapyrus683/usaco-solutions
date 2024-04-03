package official.o2023.jan.bronze;

import java.io.*;
import java.util.*;

/**
 * 2023 jan bronze
 * 4
 * GHHG
 * 2 4 3 4 should output 1
 * 3
 * GGH
 * 2 3 3 should output 2
 */
public class Leaders {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());
        String cows = read.readLine();
        int[] upTo = Arrays.stream(read.readLine().split(" "))
                .mapToInt(u -> Integer.parseInt(u) - 1).toArray();
        assert cows.length() == cowNum && upTo.length == cowNum;

        int firstG = cows.indexOf('G'), firstH = cows.indexOf('H');
        int lastG = cows.lastIndexOf('G'), lastH = cows.lastIndexOf('H');

        Map<Integer, Set<Integer>> leaderPairs = new HashMap<>();
        if (upTo[firstG] >= lastG) {
            leaderPairs.put(firstG, new HashSet<>());
            for (int c = 0; c < cowNum; c++) {
                if (cows.charAt(c) == 'H') {
                    leaderPairs.get(firstG).add(c);
                }
            }
        }
        if (upTo[firstH] >= lastH) {
            for (int c = 0; c < cowNum; c++) {
                if (cows.charAt(c) == 'G') {
                    if (!leaderPairs.containsKey(c)) {
                        leaderPairs.put(c, new HashSet<>());
                    }
                    leaderPairs.get(c).add(firstH);
                }
            }
        }

        int leaderNum = 0;
        for (Map.Entry<Integer, Set<Integer>> gh : leaderPairs.entrySet()) {
            int gPos = gh.getKey();
            for (int hPos : gh.getValue()) {
                boolean gValid = (gPos == firstG && upTo[gPos] >= lastG)
                        || (gPos < hPos && hPos <= upTo[gPos]);
                boolean hValid = (hPos == firstH && upTo[hPos] >= lastH)
                        | (hPos < gPos && gPos <= upTo[hPos]);
                leaderNum += gValid && hValid ? 1 : 0;
            }
        }

        System.out.println(leaderNum);
    }
}
