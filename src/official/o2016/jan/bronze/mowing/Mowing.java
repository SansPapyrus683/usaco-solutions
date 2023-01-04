package official.o2016.jan.bronze.mowing;

import java.io.*;
import java.util.*;

public class Mowing {
    private static class Step {
        char dir;
        int mag;
        public Step(char dir, int mag) {
            this.dir = dir;
            this.mag = mag;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("mowing.in"));
        int stepNum = Integer.parseInt(read.readLine());
        Step[] steps = new Step[stepNum];
        int totalMag = 0;
        for (int s = 0; s < stepNum; s++) {
            StringTokenizer step = new StringTokenizer(read.readLine());
            steps[s] = new Step(step.nextToken().charAt(0), Integer.parseInt(step.nextToken()));
            totalMag += steps[s].mag;
        }

        int maxTime = -1;
        for (int grassT = totalMag + 1; grassT >= 0; grassT--) {
            Point curr = new Point(0, 0);
            int time = 0;
            HashMap<Point, Integer> history = new HashMap<>(Map.of(curr, time));
            boolean valid = true;
            mow:
            for (Step s : steps) {
                for (int i = 0; i < s.mag; i++) {
                    curr = curr.next(s.dir);
                    time++;
                    if (history.containsKey(curr) && time - history.get(curr) < grassT) {
                        valid = false;
                        break mow;
                    }
                    history.put(curr, time);
                }
            }

            if (valid) {
                maxTime = grassT;
                break;
            }
        }

        PrintWriter written = new PrintWriter("mowing.out");
        written.println(maxTime == totalMag + 1 ? -1 : maxTime);
        written.close();
    }
}

class Point {
    int x, y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point next(char dir) {
        switch (dir) {
            case 'N':
                return new Point(x, y + 1);
            case 'S':
                return new Point(x, y - 1);
            case 'E':
                return new Point(x + 1, y);
            case 'W':
                return new Point(x - 1, y);
            default:
                return this;
        }
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Point && x == ((Point) obj).x && y == ((Point) obj).y;
    }
}
