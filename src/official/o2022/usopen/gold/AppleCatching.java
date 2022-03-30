package official.o2022.usopen.gold;

import java.io.*;
import java.util.*;

// 2022 us open gold (sample input omitted due to length)
public final class AppleCatching {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int eventNum = Integer.parseInt(read.readLine());
        ArrayList<Event> cows = new ArrayList<>();
        ArrayList<Event> apples = new ArrayList<>();
        for (int e = 0; e < eventNum; e++) {
            StringTokenizer event = new StringTokenizer(read.readLine());
            boolean cow = Integer.parseInt(event.nextToken()) == 1;
            int time = Integer.parseInt(event.nextToken());
            int loc = Integer.parseInt(event.nextToken());
            double[] rotated = rotate45(time, loc);

            (cow ? cows : apples).add(new Event(
                    (int) Math.round(rotated[0] * Math.sqrt(2)),
                    (int) Math.round(rotated[1] * Math.sqrt(2)),
                    Integer.parseInt(event.nextToken())
            ));
        }
        Collections.sort(cows);
        Collections.reverse(cows);
        Collections.sort(apples);

        int canEat = 0;
        TreeSet<Event> available = new TreeSet<>((a1, a2) -> a1.loc != a2.loc ? a1.loc - a2.loc : a1.time - a2.time);
        int appleAt = apples.size() - 1;
        for (Event c : cows) {
            while (appleAt >= 0 && apples.get(appleAt).time >= c.time) {
                available.add(apples.get(appleAt));
                appleAt--;
            }
            Event a;
            while ((a = available.ceiling(c)) != null && c.amt > 0) {
                int ate = Math.min(c.amt, a.amt);
                a.amt -= ate;
                c.amt -= ate;
                canEat += ate;
                if (a.amt == 0) {
                    available.remove(a);
                }
            }
        }
        System.out.println(canEat);
    }

    // sauce: https://danceswithcode.net/engineeringnotes/rotations_in_2d/rotations_in_2d.html
    public static double[] rotate45(int x, int y) {
        final double val = Math.sqrt(2) / 2;  // sin(45) & cos(45)
        return new double[] {x * val - y * val, x * val + y * val};
    }
}

class Event implements Comparable<Event> {
    public int time;
    public int loc;
    public int amt;

    public Event(int time, int loc, int amt) {
        this.time = time;
        this.loc = loc;
        this.amt = amt;
    }

    @Override
    public String toString() {
        return String.format("(%d %d %d)", time, loc, amt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, loc, amt);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != getClass()) {
            return false;
        }
        Event o = (Event) obj;
        return time == o.time && loc == o.loc && amt == o.amt;
    }

    @Override
    public int compareTo(Event o) {
        return o.time != time ? (time - o.time) : (loc - o.loc);
    }
}
