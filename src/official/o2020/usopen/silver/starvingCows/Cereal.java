package official.o2020.usopen.silver.starvingCows;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/** 2020 us open silver */
public class Cereal {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cereal.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int cerealTypes = Integer.parseInt(initial.nextToken());
        int[][] choices = new int[cowNum][2];
        for (int c = 0; c < cowNum; c++) {
            choices[c] = Stream.of(read.readLine().split(" ")).mapToInt(a -> Integer.parseInt(a) - 1).toArray();
        }

        int[] okCowNum = new int[cowNum];
        int[] usedChoice = new int[cowNum];  // 0 means no cereal, 1 means 1st choice, 2 means 2nd choice
        int[] takenCereals = new int[cerealTypes];  // this[i] gives the cow that took cereal i
        Arrays.fill(takenCereals, -1);
        okCowNum[cowNum - 1] = 1;  // with 1 cow in the line, it'll always be satisfied
        usedChoice[cowNum - 1] = 1;
        takenCereals[choices[cowNum - 1][0]] = cowNum - 1;
        for (int c = cowNum - 2; c >= 0; c--) {  // add each cow and calculate the cows that get cereal
            okCowNum[c] = okCowNum[c + 1] + 1;
            usedChoice[c] = 1;
            int wantedCereal = choices[c][0];
            int thief = c;
            boolean stoleCereal = true;
            while (takenCereals[wantedCereal] != -1) {  // each cow steals from it's predecessors
                int victim = takenCereals[wantedCereal];
                if (thief < victim && usedChoice[victim] == 1) {
                    usedChoice[victim] = 2;
                    takenCereals[wantedCereal] = thief;
                    thief = victim;
                    wantedCereal = choices[victim][1];
                } else if (thief < victim && usedChoice[victim] == 2) {
                    usedChoice[victim] = 0;
                    okCowNum[c]--;
                    break;
                } else {  // oof the thief can't be satisfied, frick everything
                    stoleCereal = false;
                    usedChoice[thief] = 0;
                    okCowNum[c]--;
                    break;
                }
            }
            if (stoleCereal) {
                takenCereals[wantedCereal] = thief;
            }
        }

        PrintWriter written = new PrintWriter("cereal.out");
        System.out.println(Arrays.toString(okCowNum));
        Arrays.stream(okCowNum).forEach(written::println);  // just discovered this, epic
        written.close();
        System.out.printf("wait a ms- how and WHY did this finish after frickin' %d ms%n", System.currentTimeMillis() - start);
    }
}
