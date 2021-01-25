package official.o2018.feb.silver.goodBessie;

import java.io.*;
import java.util.StringTokenizer;

// 2018 feb silver
public final class RestStops {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("reststops.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        initial.nextToken(); // lol the trail length is irrelevant
        int restStopNum = Integer.parseInt(initial.nextToken());
        int farmerSpeed = Integer.parseInt(initial.nextToken());  // NOTE TO SELF: IT'S SECONDS PER METER
        int bessieSpeed = Integer.parseInt(initial.nextToken());

        long[] positions = new long[restStopNum];  // we're dealing with huge numbers here boys
        long[] goodGrass = new long[restStopNum];
        for (int i = 0; i < restStopNum; i++) {
            StringTokenizer stop = new StringTokenizer(read.readLine());
            positions[i] = Integer.parseInt(stop.nextToken());
            goodGrass[i] = Integer.parseInt(stop.nextToken());
        }

        boolean[] goodOnes = new boolean[restStopNum];
        long prevGrass = 0;
        for (int i = restStopNum - 1; i >= 0; i--) {
            if (goodGrass[i] > prevGrass) {
                goodOnes[i] = true;
                prevGrass = goodGrass[i];
            }
        }
        
        long tastiness = 0L;
        long startOff = 0;
        for (int i = 0; i < restStopNum; i++) {  // position, goodgrass (comment for reference)
            if (goodOnes[i]) {
                long rsPos = positions[i];
                long gottenAmt = goodGrass[i];
                long bessiesLead = ((rsPos - startOff) * farmerSpeed) - ((rsPos - startOff) * bessieSpeed);
                tastiness += bessiesLead * gottenAmt;
                startOff = rsPos;
            }
        }

        PrintWriter written = new PrintWriter(new FileWriter(new File("reststops.out")));
        written.println(tastiness);
        written.close();
        System.out.println(tastiness);
        System.out.printf("so it took about %d ms to finish", System.currentTimeMillis() - start);
    }
}
