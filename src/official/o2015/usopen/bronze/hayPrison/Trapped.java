package official.o2015.usopen.bronze.hayPrison;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

// 2015 us open gold
public class Trapped {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("trapped.in"));
        int baleNum = Integer.parseInt(read.readLine());
        int[][] bales = new int[baleNum][2];
        for (int b = 0; b < baleNum; b++) {
            bales[b] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        // for some reason they give the size first then the position
        Arrays.sort(bales, Comparator.comparingInt(b -> b[1]));

        int trappedRegions = 0;
        for (int b = 0; b < baleNum - 1; b++) {
            int leftBale = b;
            int rightBale = b + 1;
            while (leftBale != -1 && rightBale != baleNum) {
                int rammingDist = bales[rightBale][1] - bales[leftBale][1];
                if (rammingDist > bales[leftBale][0]) {
                    leftBale--;
                } else if (rammingDist > bales[rightBale][0]) {
                    rightBale++;
                } else {
                    trappedRegions += bales[b + 1][1] - bales[b][1];
                    break;
                }
            }
        }
        PrintWriter written = new PrintWriter("trapped.out");
        written.println(trappedRegions);
        written.close();
        System.out.println(trappedRegions);
        System.out.printf("the real question is why does fj have haybales of size a billion: %d ms%n", System.currentTimeMillis() - start);
    }
}
