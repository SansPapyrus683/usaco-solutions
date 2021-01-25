package official.o2018.usopen.silver.lemonade;

import java.util.*;
import java.io.*;

// 2018 us open silver
public final class Lemonade {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("lemonade.in"));
        read.readLine();
        int[] cows = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        Arrays.sort(cows);
        for (int i = 0; i < cows.length / 2; i++) {  // sort the cows in reverse
            int temp = cows[i];
            cows[i] = cows[cows.length - i - 1];
            cows[cows.length - i - 1] = temp;
        }
        
        int cowsInLine = 0;
        int happyCows = 0;
        for (int c : cows) {
            if (c >= cowsInLine) {
                happyCows++;
                cowsInLine++;
            }
            else {
                break;  // i mean, don't bother going farther
            }
        }
        PrintWriter written = new PrintWriter(new BufferedWriter(new FileWriter(new File("lemonade.out"))));
        written.println(happyCows);
        written.close();
        System.out.println(happyCows);
        System.out.printf("so it took like %s ms to finish%n", System.currentTimeMillis() - start);
    }
}
