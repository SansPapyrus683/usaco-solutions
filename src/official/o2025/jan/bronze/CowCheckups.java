package official.o2025.jan.bronze;

import java.io.*;
import java.util.*;

public class CowCheckups {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());
        int[] line = Arrays.stream(read.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        int[] check = Arrays.stream(read.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        
        int[] checkOps = new int[cowNum + 1];
        for (int mid = 0; mid < cowNum - 1; mid++) {
            int range = Math.min(mid, cowNum - mid - 2);
            int checked = 0;
            int[] deltas = new int[range + 1];
            for (int c = 0; c < cowNum; c++) {
                checked += line[c] == check[c] ? 1 : 0;
                if (mid - range <= c && c <= mid + 1 + range) {
                    int dist = c <= mid ? mid - c : c - (mid + 1);
                    deltas[dist] -= line[c] == check[c] ? 1 : 0;
                    deltas[dist] += line[mid + (mid - c + 1)] == check[c] ? 1 : 0;
                }
            }
            for (int d : deltas) {
                checked += d;
                checkOps[checked]++;
            }
        }

        for (int mid = 0; mid < cowNum; mid++) {
            int range = Math.min(mid, cowNum - mid - 1);
            int checked = 0;
            int[] deltas = new int[range + 1];
            for (int c = 0; c < cowNum; c++) {
                checked += line[c] == check[c] ? 1 : 0;
                int dist = Math.abs(c - mid);
                if (dist <= range) {
                    deltas[dist] -= line[c] == check[c] ? 1 : 0;
                    deltas[dist] += line[mid + (mid - c)] == check[c] ? 1 : 0;
                }
            }
            for (int d : deltas) {
                checked += d;
                checkOps[checked]++;
            }
        }

        Arrays.stream(checkOps).forEach(System.out::println);
    }
}
