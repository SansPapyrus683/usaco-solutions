package official.o2022.usopen.bronze;

import java.io.*;
import java.util.*;

/**
 * 2022 us open bronze
 * 2
 * G 3
 * L 5 should output 0
 * 2
 * G 3
 * L 2 should output 1
 */
public class Liars {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());

        List<Integer> greater = new ArrayList<>();
        List<Integer> lesser = new ArrayList<>();
        Set<Integer> allPos = new HashSet<>();
        for (int c = 0; c < cowNum; c++) {
            StringTokenizer cow = new StringTokenizer(read.readLine());
            char type = cow.nextToken().charAt(0);
            int pos = Integer.parseInt(cow.nextToken());
            if (type == 'G') {
                greater.add(pos);
            } else {
                lesser.add(pos);
            }
            allPos.add(pos);
        }

        int minLying = Integer.MAX_VALUE;
        for (int p : allPos) {
            int lying = 0;
            for (int g : greater) {
                lying += p < g ? 1 : 0;
            }
            for (int l : lesser) {
                lying += p > l ? 1 : 0;
            }
            minLying = Math.min(minLying, lying);
        }
        System.out.println(minLying);
    }
}
