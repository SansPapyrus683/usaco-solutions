package official.o2013.usopen.silver.cowClownCar;

import java.io.*;
import java.util.*;

// 2013 silver usopen
public class Fuel {
    private static final long start = System.currentTimeMillis();
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("fuel.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int stationNum = Integer.parseInt(initial.nextToken());
        int fuelCap = Integer.parseInt(initial.nextToken());
        int startingFuel = Integer.parseInt(initial.nextToken());
        int dist = Integer.parseInt(initial.nextToken());
        if (startingFuel > fuelCap) {
            throw new IllegalArgumentException(String.format("how can you fit %s amt of fuel into a tank that only stores %s units lol", startingFuel, fuelCap));
        }

        int[][] stations = new int[stationNum][2];  // their position and the amt they charge for a single unit of fuel
        for (int s = 0; s < stationNum; s++) {
            stations[s] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(stations, Comparator.comparingInt(s -> s[0]));

        int[] nextCheaper = new int[stationNum];  // index of the closest next station that's cheaper
        Stack<Integer> costs = new Stack<>();
        for (int s = stationNum - 1; s >= 0; s--) {
            // try to find a cheaper one (if we pop all of them, that means that this one is the cheapest so far)
            while (!costs.isEmpty() && stations[costs.peek()][1] >= stations[s][1]) {
                costs.pop();
            }
            nextCheaper[s] = costs.isEmpty() ? -1 : costs.peek();
            costs.push(s);
        }

        if (stations[0][0] > startingFuel) {
            outputAndDie(-1);  // oof, we can't even reach the first station
        }
        long money = 0;
        int fuel = startingFuel - stations[0][0];
        for (int i = 0; i < stationNum; i++) {
            int pos = stations[i][0];
            int price = stations[i][1];
            int distToTrav = Math.max((i + 1 < stationNum ? stations[i + 1][0] : dist) - pos, 0);
            if (fuelCap < distToTrav) {
                outputAndDie(-1);  // even if we completely filled up our tank, we can't make it
            }
            int betterOne = nextCheaper[i];
            int toAdd;
            if (betterOne == -1 || fuelCap < stations[betterOne][0] - pos) {
                // ok, this is the best one we're going to get for a long time, let's get as much as we can
                toAdd = Math.min(dist - pos, fuelCap) - fuel;
            } else {
                // there's a cheaper one down the line, let's just get enough fuel to putter over there
                toAdd = Math.max(stations[betterOne][0] - pos - fuel, 0);
            }
            money += (long) toAdd * price;
            fuel += toAdd - distToTrav;
        }
        outputAndDie(money);
    }

    private static void outputAndDie(Object output) throws IOException {
        PrintWriter written = new PrintWriter("fuel.out");
        written.println(output);
        written.close();
        System.out.println(output);
        System.out.printf("how do you cram so many gosh darn cows into a single truck: %d ms%n", System.currentTimeMillis() - start);
        System.exit(0);
    }
}
