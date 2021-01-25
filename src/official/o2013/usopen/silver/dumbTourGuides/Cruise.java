package official.o2013.usopen.silver.dumbTourGuides;

import java.io.*;
import java.util.*;

// 2013 usopen silver
public final class Cruise {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cruise.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int portNum = Integer.parseInt(initial.nextToken());
        int repeatSeq = Integer.parseInt(initial.nextToken());
        long moveTimes = (long) Integer.parseInt(initial.nextToken()) * repeatSeq;

        int[][] neighbors = new int[portNum][2];
        for (int i = 0; i < portNum; i++) {
            neighbors[i] = Arrays.stream(read.readLine().split(" ")).mapToInt(p -> Integer.parseInt(p) - 1).toArray();
        }
        String[] dirs = read.readLine().toUpperCase().split(" ");  // screw it, i'm too lazy to parse it to a char[]
        if (dirs.length != repeatSeq) {
            throw new IllegalArgumentException("uh the length for the amount of directions is inconsistent");
        }

        int at = 0;
        int dirAt = 0;
        int movesTaken = 0;
        int[][] seenStates = new int[portNum][dirs.length];
        for (int p = 0; p < portNum; p++) {
            Arrays.fill(seenStates[p], -1);
        }
        ArrayList<Integer> states = new ArrayList<>();  // the ports we go through
        while (seenStates[at][dirAt] == -1) {
            states.add(at);
            seenStates[at][dirAt] = movesTaken++;
            if (dirs[dirAt].equals("L")) {
                at = neighbors[at][0];
            } else if (dirs[dirAt].equals("R")) {
                at = neighbors[at][1];
            } else {
                throw new IllegalArgumentException(String.format("what kind of direction is %s i mean...", dirs[dirAt]));
            }
            dirAt = (dirAt + 1) % dirs.length;
        }

        int offset = seenStates[at][dirAt];  // it's possible to just go 1 -> 2 -> 3 -> 2 -> 3.....
        int cycleLen = movesTaken - offset;  // so because of that we subtract the offset to calculate the cycle length
        moveTimes = (moveTimes - offset) % cycleLen;
        int finalState = states.get((int) moveTimes + offset);  // apply the offset again to get the final state

        PrintWriter written = new PrintWriter("cruise.out");
        written.println(finalState + 1);  // +1 because remember that 0-indexing?
        written.close();
        System.out.println(finalState + 1);
        System.out.printf("i am writing this during covid so... (%d ms ecks dee)%n", System.currentTimeMillis() - start);
    }
}

