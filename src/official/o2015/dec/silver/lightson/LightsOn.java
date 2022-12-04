package official.o2015.dec.silver.lightson;

import java.io.*;
import java.util.*;

// 2015 dec silver
public class LightsOn {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("lightson.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int sideLen = Integer.parseInt(initial.nextToken());
        int switchNum = Integer.parseInt(initial.nextToken());

        ArrayList<Pos>[][] switches = new ArrayList[sideLen][sideLen];
        for (int i = 0; i < sideLen; i++) {
            for (int j = 0; j < sideLen; j++) {
                switches[i][j] = new ArrayList<>();
            }
        }

        for (int s = 0; s < switchNum; s++) {
            StringTokenizer light = new StringTokenizer(read.readLine());
            int sr = Integer.parseInt(light.nextToken()) - 1;
            int sc = Integer.parseInt(light.nextToken()) - 1;
            int lr = Integer.parseInt(light.nextToken()) - 1;
            int lc = Integer.parseInt(light.nextToken()) - 1;
            switches[sr][sc].add(new Pos(lr, lc));
        }

        Pos startPos = new Pos(0, 0);
        boolean[][] lit = new boolean[sideLen][sideLen];
        boolean[][] visited = new boolean[sideLen][sideLen];
        ArrayDeque<Pos> frontier = new ArrayDeque<>();
        frontier.add(startPos);
        lit[startPos.r][startPos.c] = visited[startPos.r][startPos.c] = true;
        while (!frontier.isEmpty()) {
            Pos curr = frontier.poll();

            // turn on all the lights in this room
            for (Pos light : switches[curr.r][curr.c]) {
                lit[light.r][light.c] = true;
                if (!visited[light.r][light.c]) {
                    // have we been to any adjacent square of this newly lit room?
                    for (Pos ln : neighbors(light, sideLen)) {
                        if (visited[ln.r][ln.c]) {
                            // start the search again at this position
                            frontier.add(light);
                            visited[light.r][light.c] = true;
                            break;
                        }
                    }
                }
            }

            // normal grid floodfill
            for (Pos n : neighbors(curr, sideLen)) {
                if (lit[n.r][n.c] && !visited[n.r][n.c]) {
                    frontier.add(n);
                    visited[n.r][n.c] = true;
                }
            }
        }

        int litNum = 0;
        for (boolean[] row : lit) {
            for (boolean p : row) {
                litNum += p ? 1 : 0;
            }
        }

        PrintWriter written = new PrintWriter("lightson.out");
        written.println(litNum);
        written.close();
        System.out.println(litNum);
        System.out.printf("time: %d ms%n", System.currentTimeMillis() - start);
    }

    static ArrayList<Pos> neighbors(Pos curr, int sideLen) {
        ArrayList<Pos> ret = new ArrayList<>();
        if (curr.r - 1 >= 0) {
            ret.add(new Pos(curr.r - 1, curr.c));
        }
        if (curr.r + 1 < sideLen) {
            ret.add(new Pos(curr.r + 1, curr.c));
        }
        if (curr.c - 1 >= 0) {
            ret.add(new Pos(curr.r, curr.c - 1));
        }
        if (curr.c + 1 < sideLen) {
            ret.add(new Pos(curr.r, curr.c + 1));
        }
        return ret;
    }
}

class Pos {
    int r, c;
    public Pos(int r, int c) {
        this.r = r;
        this.c = c;
    }
}
