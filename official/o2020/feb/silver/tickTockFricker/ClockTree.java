import java.io.*;
import java.util.*;

// 202 feb silver (gave up and copied from sol oof)
public class ClockTree {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("clocktree.in"));
        int roomNum = Integer.parseInt(read.readLine());
        int[] roomClocks = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        ArrayList<Integer>[] neighbors = new ArrayList[roomNum];
        for (int r = 0; r < roomNum; r++) {
            neighbors[r] = new ArrayList<>();
        }
        for (int r = 0; r < roomNum - 1; r++) {
            StringTokenizer corridor = new StringTokenizer(read.readLine());
            int r1 = Integer.parseInt(corridor.nextToken()) - 1;
            int r2 = Integer.parseInt(corridor.nextToken()) - 1;
            neighbors[r1].add(r2);
            neighbors[r2].add(r1);
        }

        ArrayDeque<Integer> frontier = new ArrayDeque<>(Collections.singletonList(0));  // assume 0 is ultimate node lol
        boolean[] visited = new boolean[roomNum];
        visited[0] = true;
        int oddDistSum = 0;  // all these distances are based on the distance to the ultimate node
        int evenDistSum = 0;  // these two are the sum of the clocks
        int oddDistRooms = 0;
        int evenDistRooms = 0;  // these two are the actual amount of rooms that are even/odd away
        int movesTaken = 0;
        while (!frontier.isEmpty()) {
            ArrayDeque<Integer> inLine = new ArrayDeque<>();
            for (int r : frontier) {
                if (movesTaken % 2 == 0) {
                    evenDistSum += roomClocks[r];
                    evenDistRooms++;
                } else {
                    oddDistSum += roomClocks[r];
                    oddDistRooms++;
                }
                for (int n : neighbors[r]) {
                    if (!visited[n]) {
                        inLine.add(n);
                        visited[n] = true;
                    }
                }
            }
            movesTaken++;
            frontier = inLine;
        }

        /* (copied straight from official sol)
         * so apparently a node is only valid (every node, not just the ultimate node)
         * if and only if it's even distances (mod 12) - its odd distances (mod 12)
         * are either 0 or 1 (not -1)
         * */
        PrintWriter written = new PrintWriter("clocktree.out");
        if (evenDistSum % 12 == oddDistSum % 12) {
            written.println(oddDistRooms + evenDistRooms);
            System.out.println(oddDistRooms + evenDistRooms);
        } else if ((evenDistSum + 1) % 12 == oddDistSum % 12) {
            written.println(oddDistRooms);
            System.out.println(oddDistRooms);
        } else if ((oddDistSum + 1) % 12 == evenDistSum % 12) {
            written.println(evenDistRooms);
            System.out.println(evenDistRooms);
        }else {
            written.println(0);
            System.out.println(0);
        }
        written.close();
        System.out.printf("tick tock tick tock the clock went \"oh it took %d ms\"%n", System.currentTimeMillis() - start);
    }
}
