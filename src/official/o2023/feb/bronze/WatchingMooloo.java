package official.o2023.feb.bronze;

import java.io.*;
import java.util.*;

// 2023 feb bronze
public class WatchingMooloo {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer inital = new StringTokenizer(read.readLine());
        int dayNum = Integer.parseInt(inital.nextToken());
        int baseCost = Integer.parseInt(inital.nextToken());
        long[] days = Arrays.stream(read.readLine()
                .split(" ")).mapToLong(Long::parseLong).toArray();
        assert days.length == dayNum;
        Arrays.sort(days);

        long lastDay = -1;
        long cost = 0;
        for (long d : days) {
            if (d == days[0]) {
                cost += baseCost + 1;
            } else {
                cost += Math.min(d - lastDay, baseCost + 1);
            }
            lastDay = d;
        }

        System.out.println(cost);
    }
}
