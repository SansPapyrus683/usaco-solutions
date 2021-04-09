package official.o2021.feb.bronze;

import java.io.*;
import java.util.*;

// 2021 feb bronze (input omitted due to length)
public final class ComfyCows {
    private static final int COMFORT_THRESHOLD = 3;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());
        Point[] cows = new Point[cowNum];
        for (int c = 0; c < cowNum; c++) {
            StringTokenizer info = new StringTokenizer(read.readLine());
            cows[c] = new Point(Integer.parseInt(info.nextToken()), Integer.parseInt(info.nextToken()));
        }

        StringBuilder ans = new StringBuilder();
        HashSet<Point> occupied = new HashSet<>();
        int comfy = 0;
        for (Point c : cows) {
            int beforeComfy = 0;
            for (Point n : neighbors(c)) {
                beforeComfy += comfy(occupied, n) ? 1 : 0;
            }
            occupied.add(c);
            int afterComfy = comfy(occupied, c) ? 1 : 0;
            for (Point n : neighbors(c)) {
                afterComfy += comfy(occupied, n) ? 1 : 0;
            }
            comfy = comfy - beforeComfy + afterComfy;
            ans.append(comfy).append('\n');
        }
        System.out.print(ans);
        System.err.printf("farmer nohj is so evil omg: %d ms%n", System.currentTimeMillis() - start);
    }

    private static ArrayList<Point> neighbors(Point pos) {
        return new ArrayList<>(Arrays.asList(
                new Point(pos.x - 1, pos.y),
                new Point(pos.x, pos.y - 1),
                new Point(pos.x + 1, pos.y),
                new Point(pos.x, pos.y + 1)
        ));
    }

    private static boolean comfy(Set<Point> cows, Point pos) {
        if (!cows.contains(pos)) {
            return false;
        }
        int surrounded = 0;
        for (Point n : neighbors(pos)) {
            surrounded += cows.contains(n) ? 1 : 0;
        }
        return surrounded == COMFORT_THRESHOLD;
    }
}

// i hate java for making me write 20 whole lines just for a lousy point class
class Point {
    int x;
    int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == getClass() && x == ((Point) obj).x && y == ((Point) obj).y;
    }
}
