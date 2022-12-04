package official.o2016.dec.gold.lasers;

import java.io.*;
import java.util.*;

// 2016 dec gold
public class Lasers {
    // 0 means that the beam is coming from left or right, and 1 means it's coming from up or down
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("lasers.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int laserNum = Integer.parseInt(initial.nextToken());
        int[] lightPos = new int[]{Integer.parseInt(initial.nextToken()), Integer.parseInt(initial.nextToken())};
        int[] farmPos = new int[]{Integer.parseInt(initial.nextToken()), Integer.parseInt(initial.nextToken())};
        HashMap<Integer, ArrayList<Integer>> xPoints = new HashMap<>();
        HashMap<Integer, ArrayList<Integer>> yPoints = new HashMap<>();
        for (int i = 0; i < laserNum + 1; i++) {
            // for the last ieration, include the farm as a fence so the search can detect
            int[] fence = i < laserNum ? Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray() : farmPos;
            if (!xPoints.containsKey(fence[0])) {
                xPoints.put(fence[0], new ArrayList<>());
            }
            if (!yPoints.containsKey(fence[1])) {
                yPoints.put(fence[1], new ArrayList<>());
            }
            xPoints.get(fence[0]).add(fence[1]);
            yPoints.get(fence[1]).add(fence[0]);
        }

        // i apologize for these lines that are longer than a fricking broadsword
        HashSet<LaserState> visited = new HashSet<>(Arrays.asList(new LaserState(lightPos[0], lightPos[1], 0), new LaserState(lightPos[0], lightPos[1], 1)));
        ArrayList<LaserState> frontier = new ArrayList<>(Arrays.asList(new LaserState(lightPos[0], lightPos[1], 0), new LaserState(lightPos[0], lightPos[1], 1)));
        int mirrorsPlaced = 0;
        boolean reachable = false;
        searching:
        while (!frontier.isEmpty()) {  // just a bfs to see if we can reach the farm
            ArrayList<LaserState> inLine = new ArrayList<>();
            for (LaserState s : frontier) {
                for (LaserState n : neighbors(s, xPoints, yPoints)) {
                    // because of how mirrorsPlaced is incremented, do the destination check within the neighbor processing
                    if (n.x == farmPos[0] && n.y == farmPos[1]) {
                        reachable = true;
                        break searching;
                    }
                    if (!visited.contains(n)) {
                        visited.add(n);
                        inLine.add(n);
                    }
                }
            }
            frontier = inLine;
            mirrorsPlaced++;
        }
        PrintWriter written = new PrintWriter("lasers.out");
        written.println(reachable ? mirrorsPlaced : -1);
        written.close();
        System.out.println(reachable ? mirrorsPlaced : -1);
        System.out.printf("VI UNDRAR ÄR NI REDO ATT VARA ME- oh sorry, it took %d ms%n", System.currentTimeMillis() - start);
    }

    private static ArrayList<LaserState> neighbors(
            LaserState rn,
            HashMap<Integer, ArrayList<Integer>> xPoints,
            HashMap<Integer, ArrayList<Integer>> yPoints) {
        ArrayList<LaserState> valid = new ArrayList<>();
        if (rn.dir == 0) {  // we can reflect it up or down
            for (int y : xPoints.getOrDefault(rn.x, new ArrayList<>())) {
                if (y != rn.y) {
                    valid.add(new LaserState(rn.x, y, 1));
                }
            }
        } else {  // we can reflect it left or right
            for (int x : yPoints.getOrDefault(rn.y, new ArrayList<>())) {
                if (x != rn.x) {
                    valid.add(new LaserState(x, rn.y, 0));
                }
            }
        }
        return valid;
    }
}

class LaserState {
    public int x;
    public int y;
    public int dir;

    public LaserState(int x, int y, int dir) {
        assert dir == 0 || dir == 1;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, dir);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == this.getClass()) {
            return false;
        }
        LaserState obj_ = (LaserState) obj;
        return x == obj_.x && y == obj_.y && dir == obj_.dir;
    }

    @Override
    public String toString() {
        return String.format("LaserState{x=%s, y=%s, dir=%s}", x, y, dir);
    }
}
