package official.o2019.usopen.gold.interference;

import java.io.*;
import java.util.*;

// 2019 us open gold
public class Balance {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("balance.in"));
        int boardLen = Integer.parseInt(read.readLine());
        int[] rawBoard = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        assert rawBoard.length == boardLen * 2;

        boolean[] board = new boolean[boardLen * 2];
        for (int i = 0; i < boardLen * 2; i++) {
            board[i] = rawBoard[i] != 0;  // going with standard int -> bool truthiness
        }
        boolean[] bess = new boolean[boardLen];  // bess will control the first half, elsi the other
        boolean[] elsi = new boolean[boardLen];
        for (int i = 0; i < boardLen * 2; i++) {
            if (i < boardLen) {
                bess[i] = board[i];
            } else {
                elsi[i - boardLen] = board[i];
            }
        }
        long minMoves = Math.min(minMovesWithMidSwap(bess, elsi, false), minMovesWithMidSwap(bess, elsi, true));
        PrintWriter written = new PrintWriter("balance.out");
        written.println(minMoves);
        written.close();
        System.out.println(minMoves);
        System.out.printf("pain is all i know: %d ms%n", System.currentTimeMillis() - start);
    }

    // changeBessTo is the state we change the edge of bessie's board to before swapping (and we change elsie's to the opposite)
    private static long minMovesWithMidSwap(boolean[] bess, boolean[] elsi, boolean changeBessTo) {
        long bessScore = 0;  // i despise this repetitive code, but it gets the job done
        long elsiScore = 0;
        int bessTrue = 0;
        int elsiTrue = 0;
        for (boolean b : bess) {
            if (b) {
                bessTrue++;
            } else {
                bessScore += bessTrue;
            }
        }
        for (boolean b : elsi) {
            if (b) {
                elsiTrue++;
            } else {
                elsiScore += elsiTrue;
            }
        }
        int elsiFalse = elsi.length - elsiTrue;

        /*
         * ok so here's the thing (solution explains it better imo just look at it)
         * the only "big" moves we can make are in the middle
         * all others just either decrease or increase the amount of inversions on a side by 1
         * so we can like try all the amounts of "big" moves we can make and see how many moves it'd take in total
         * 1 | 0
         * 1 1 | 0 0 and so on until we've exhausted the capacity
         */
        long best = Math.abs(bessScore - elsiScore);  // start is just the difference between the 2 scores
        int swappable = changeBessTo ? Math.min(bessTrue, elsiFalse) : Math.min(bess.length - bessTrue, elsiTrue);
        int bessClosest = bess.length - 1;
        int elsiClosest = 0;
        int bessPrepare = 0;
        int elsiPrepare = 0;
        for (int i = 1; i <= swappable; i++) {
            for (; bessClosest >= 0 && bess[bessClosest] != changeBessTo; bessClosest--);
            for (; elsiClosest < elsi.length && elsi[elsiClosest] != !changeBessTo; elsiClosest++);

            bessPrepare += bess.length - bessClosest - i;
            elsiPrepare += elsiClosest - i + 1;
            long newBessScore = bessScore + (changeBessTo ? -bessPrepare : bessPrepare);
            long newElsiScore = elsiScore + (changeBessTo ? -elsiPrepare : elsiPrepare);

            int bessLeftTrue = changeBessTo ? bessTrue - i : bessTrue;
            int elsiLeftFalse = changeBessTo ? elsiFalse - i : elsiFalse;
            newBessScore += changeBessTo ? (long) i * bessLeftTrue : (long) -i * bessLeftTrue;
            newElsiScore += changeBessTo ? (long) i * elsiLeftFalse : (long) -i * elsiLeftFalse;

            // initMoves is the amount of moves we need to prepare for the big moves + the big moves themselves
            // apparently if you put the Math.pow at the end it overflows for no reason
            long initMoves = (long) Math.pow(i, 2) + bessPrepare + elsiPrepare;
            best = Math.min(best, initMoves + Math.abs(newBessScore - newElsiScore));
            bessClosest--;  // tell the algorithm to go to the next true/false (depends)
            elsiClosest++;
        }
        return best;
    }
}
