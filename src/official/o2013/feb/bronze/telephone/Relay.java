package official.o2013.feb.bronze.telephone;

import java.io.*;
import java.util.*;

// 2013 feb bronze
public final class Relay {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("relay.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[] goTo = new int[cowNum];
        for (int c = 0; c < cowNum; c++) {
            // make the messages 0-indexed (so now -1 is the invalid value)
            goTo[c] = Integer.parseInt(read.readLine()) - 1;
        }

        int loopyNum = 0;
        for (int c = 0; c < cowNum; c++) {
            HashSet<Integer> visited = new HashSet<>();
            int at = c;
            boolean loopy = true;
            while (!visited.contains(at)) {
                visited.add(at);
                if (goTo[at] == -1) {
                    loopy = false;
                    break;
                }
                at = goTo[at];
            }
            loopyNum += loopy ? 0 : 1;
        }

        PrintWriter written = new PrintWriter("relay.out");
        written.println(loopyNum);
        written.close();
        System.out.println(loopyNum);
        System.out.printf("bruh why did you run this code that took %d ms lol%n", System.currentTimeMillis() - start);
    }
}
