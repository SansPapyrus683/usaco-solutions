package official.o2025.usopen.bronze;

import java.io.*;
import java.util.*;

/** 2025 us open bronze */
public class MoreCowPhotos {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            int cowNum = Integer.parseInt(read.readLine());
            StringTokenizer cows = new StringTokenizer(read.readLine());
            Map<Integer, Integer> freq = new HashMap<>();
            int maxHeight = 0;
            for (int c = 0; c < cowNum; c++) {
                int height = Integer.parseInt(cows.nextToken());
                freq.put(height, freq.getOrDefault(height, 0) + 1);
                maxHeight = Math.max(maxHeight, height);
            }

            int photoLen = 1;
            for (Map.Entry<Integer, Integer> c : freq.entrySet()) {
                if (c.getKey() < maxHeight && c.getValue() >= 2) {
                    photoLen += 2;
                }
            }
            System.out.println(photoLen);
        }
    }
}
