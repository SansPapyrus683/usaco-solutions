package official.o2016.jan.bronze.promote;

import java.io.*;
import java.util.*;

public class Promote {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("promote.in"));
        // cursed 100
        int[] bronze = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] silver = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] gold = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] plat = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int toPlat = plat[1] - plat[0];
        int toGold = gold[1] - gold[0] + toPlat;
        int toSilver = silver[1] - silver[0] + toGold;

        PrintWriter written = new PrintWriter("promote.out");
        written.printf("%d%n%d%n%d%n", toSilver, toGold, toPlat);
        written.close();
    }
}
