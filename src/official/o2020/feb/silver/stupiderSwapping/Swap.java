package official.o2020.feb.silver.stupiderSwapping;

import java.io.*;
import java.util.*;

public class Swap {
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("swap.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int reverseNum = Integer.parseInt(initial.nextToken());
        int repeatTimes = Integer.parseInt(initial.nextToken());
        int[][] reverseParts = new int[reverseNum][2];
        for (int r = 0; r < reverseNum; r++) {
            reverseParts[r] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        // there's only 10^5 positions, so precalculate all the positions after applying 1 total reversal
        int[] afterReversing = new int[cowNum + 1];  // no use index 0, makes life easier
        for (int c = 1; c <= cowNum; c++) {
            int pos = c;
            for (int[] r : reverseParts) {
                if (pos < r[0] || pos > r[1]) {
                    continue;
                }
                pos = r[0] + (r[1] - pos);
            }
            afterReversing[c] = pos;
        }

        // assumes that all points will at some point cycle back to their original position, which should (?) be true
        int[] finalPos = new int[cowNum + 1];
        HashMap<Integer, Pair<int[], Boolean>> cycled = new HashMap<>();
        for (int c = 1; c <= cowNum; c++) {
            if (afterReversing[c] == c) {  // if it isn't affected by the reversals, it'll still be the same at the end
                finalPos[c] = c;
            }
            /* if we haven't processed this thing's cycle, go through it and process all the other cycles
             * that we come upon as well (faster than processing each position at a time)
             */
            else if (!cycled.containsKey(c)) {
                ArrayList<Integer> previousPositions = new ArrayList<>(Collections.singletonList(c));
                int appliedTimes = 0;
                // it's first occurrence, it's position in previousPositions, and whether it's had it's cycle yet
                cycled.put(c, new Pair<>(new int[] {appliedTimes, 0}, false));
                int leftToCycle = 1;
                int pos = c;
                while (leftToCycle > 0) {
                    appliedTimes++;
                    pos = afterReversing[pos];
                    previousPositions.add(pos);
                    if (!cycled.containsKey(pos)) {  // oh, it's a new thing
                        cycled.put(pos, new Pair<>(new int[] {appliedTimes, previousPositions.size() - 1}, false));
                        leftToCycle++;
                    } else if (!cycled.get(pos).second) {  // we've completed this position's cycle
                        cycled.get(pos).second = true;
                        int cycleTimes = appliedTimes - cycled.get(pos).first[0];
                        finalPos[previousPositions.get(cycled.get(pos).first[1] + repeatTimes % cycleTimes)] = pos;
                        leftToCycle--;
                    }
                }
            }
        }

        int[] actualPos = Arrays.copyOfRange(finalPos, 1, finalPos.length);
        PrintWriter written = new PrintWriter("swap.out");
        for (int p : actualPos) {
            written.println(p);
        }
        written.close();
        System.out.println(Arrays.toString(actualPos));
        System.out.printf("what kind of exercise routine takes a measly %d ms lol%n", System.currentTimeMillis() - timeStart);
    }
}

class Pair <T1, T2> {  // frick you java for not having a pair class
    public T1 first;
    public T2 second;
    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("Pair{first=%s, second=%s}", first, second);
    }
}
