import java.io.*;
import java.util.*;

// 2017 feb silver
public class HelpCross {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("helpcross.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int[] chickens = new int[Integer.parseInt(initial.nextToken())];
        int[][] cows = new int[Integer.parseInt(initial.nextToken())][2];
        for (int i = 0; i < chickens.length; i++) {
            chickens[i] = Integer.parseInt(read.readLine());
        }
        for (int i = 0; i < cows.length; i++) {
            cows[i] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        Arrays.sort(chickens);
        // kinda like that "choose intervals" problem- we wanna choose the one that ends first, not the one that starts first
        Arrays.sort(cows, (int[] c1, int[] c2) -> c1[1] != c2[1] ? c1[1] - c2[1] : c1[0] - c2[0]);
        int paired = 0;
        for (int ch: chickens) {
            for (int c = 0; c < cows.length; c++) {
                int[] cow = cows[c];
                // if it's not been assigned and the chicken is valid, assign it and increment
                if (cow != null && cow[0] <= ch && ch <= cow[1]) {
                    cows[c] = null;
                    paired++;
                    break;
                }
            }
        }
        PrintWriter written = new PrintWriter("helpcross.out");
        written.println(paired);
        written.close();
        System.out.println(paired);
        System.out.printf("bruh you fricking took %d ms what the heck%n", System.currentTimeMillis() - start);
    }
}
