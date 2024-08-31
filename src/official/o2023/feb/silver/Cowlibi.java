package official.o2023.feb.silver;

import java.io.*;
import java.util.*;

public class Cowlibi {
    private static class Loc implements Comparable<Loc> {
        public int x;
        public int y;
        public int time;

        public Loc(int x, int y, int time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }

        @Override
        public String toString() {
            return String.format("(%d,%d,%d)", x, y, time);
        }

        @Override
        public int compareTo(Loc loc) {
            return time - loc.time;
        }

        public boolean canReach(Loc other) {
            long dx = x - other.x;
            long dy = y - other.y;
            long dt = time - other.time;
            return dx * dx + dy * dy <= dt * dt;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int gardenNum = Integer.parseInt(initial.nextToken());
        int cowNum = Integer.parseInt(initial.nextToken());
        TreeSet<Loc> gardens = new TreeSet<>();
        for (int g = 0; g < gardenNum; g++) {
            StringTokenizer garden = new StringTokenizer(read.readLine());
            gardens.add(new Loc(
                    Integer.parseInt(garden.nextToken()),
                    Integer.parseInt(garden.nextToken()),
                    Integer.parseInt(garden.nextToken())
            ));
        }

        int innocentNum = 0;
        for (int c = 0; c < cowNum; c++) {
            StringTokenizer cow = new StringTokenizer(read.readLine());
            Loc alibi = new Loc(
                    Integer.parseInt(cow.nextToken()),
                    Integer.parseInt(cow.nextToken()),
                    Integer.parseInt(cow.nextToken())
            );
            Loc before = gardens.floor(alibi);
            Loc after = gardens.ceiling(alibi);
            boolean beforeBad = before != null && !before.canReach(alibi);
            boolean afterBad = after != null && !after.canReach(alibi);
            if (beforeBad || afterBad) {
                innocentNum++;
            }
        }

        System.out.println(innocentNum);
    }
}
