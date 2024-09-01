package official.o2023.feb.bronze;

import java.io.*;
import java.util.*;

// 2023 feb bronze
public class HungryCow {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int deliveryNum = Integer.parseInt(initial.nextToken());
        long dayNum = Long.parseLong(initial.nextToken());
        long[][] deliveries = new long[deliveryNum + 1][];
        for (int d = 0; d < deliveryNum; d++) {
            StringTokenizer delivery = new StringTokenizer(read.readLine());
            deliveries[d] = new long[]{
                    Long.parseLong(delivery.nextToken()),  // day
                    Long.parseLong(delivery.nextToken())  // number of haybales
            };
        }
        deliveries[deliveryNum] = new long[] {dayNum + 1, 0};
        Arrays.sort(deliveries, Comparator.comparingLong(d -> d[0]));

        long eatNum = 0;
        long baleNum = 0;
        long lastDay = 1;
        for (long[] d : deliveries) {
            long passed = d[0] - lastDay;
            long thisEat = Math.min(passed, baleNum);
            eatNum += thisEat;
            baleNum += d[1] - thisEat;
            lastDay = d[0];
        }

        System.out.println(eatNum);
    }
}
