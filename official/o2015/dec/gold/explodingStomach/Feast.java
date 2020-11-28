import java.io.*;
import java.util.*;

// 2015 dec gold
public class Feast {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("feast.in"));
        StringTokenizer info = new StringTokenizer(read.readLine());  // some random placeholder code
        int capacity = Integer.parseInt(info.nextToken());
        int orange = Integer.parseInt(info.nextToken());
        int lemon = Integer.parseInt(info.nextToken());

        int maxCapacity = 0;
        boolean[] withoutWaterPossible = new boolean[capacity + 1];
        boolean[] afterWaterPossible = new boolean[capacity + 1];
        withoutWaterPossible[0] = true;
        for (int c = 0; c <= capacity; c++) {
            if (withoutWaterPossible[c]) {
                maxCapacity = Math.max(maxCapacity, c);
                afterWaterPossible[c / 2] = true;
                if (c + orange <= capacity) {
                    withoutWaterPossible[c + orange] = true;
                }
                if (c + lemon <= capacity) {
                    withoutWaterPossible[c + lemon] = true;
                }
            }
        }
        for (int c = 0; c <= capacity; c++) {
            if (afterWaterPossible[c]) {
                maxCapacity = Math.max(maxCapacity, c);
                if (c + orange <= capacity) {
                    afterWaterPossible[c + orange] = true;
                }
                if (c + lemon <= capacity) {
                    afterWaterPossible[c + lemon] = true;
                }
            }
        }

        PrintWriter written = new PrintWriter("feast.out");
        written.println(maxCapacity);
        written.close();
        System.out.println(maxCapacity);
        System.out.printf("%d ms (i may or many not have committed 500 instances of tax fraud)%n", System.currentTimeMillis() - start);
    }
}
