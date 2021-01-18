package official.o2017.usopen.bronze.lost;

import java.io.*;
import java.util.*;

// 2017 usopen bronze (just decided to do a java translation for fun)
public class LostCow {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("lostcow.in"));
        StringTokenizer info = new StringTokenizer(read.readLine());
        int farmerPos = Integer.parseInt(info.nextToken());
        int bessiePos = Integer.parseInt(info.nextToken());

        int travelled = 0;
        int lastAt = farmerPos;
        int magnitude = 1;
        while (true) {
            int target = farmerPos + magnitude;
            if (Math.min(lastAt, target) <= bessiePos && bessiePos <= Math.max(lastAt, target)) {
                travelled += Math.abs(lastAt - bessiePos);
                break;
            } else {
                travelled += Math.abs(lastAt - target);
            }
            lastAt = target;
            magnitude *= -2;
        }

        PrintWriter written = new PrintWriter("lostcow.out");
        written.println(travelled);
        written.close();
        System.out.println(travelled);
        System.out.printf("bruh i thought the usopen was supposed to be hard: %d ms", System.currentTimeMillis() - start);
    }
}
