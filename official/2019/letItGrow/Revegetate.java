import java.util.*;
import java.io.*;

public class Revegetate {
    static ArrayList<int[]>[] specifications;
    static int[] hypothetical;
    static boolean[] usedUp;
    static PrintWriter written;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("revegetate.in"));
        String[] startInfo = read.readLine().split(" ");
        int fieldNum = Integer.parseInt(startInfo[0]);
        int cowNum = Integer.parseInt(startInfo[1]);

        specifications = new ArrayList[fieldNum + 1];
        for (int i = 1; i < specifications.length; i++) {
            specifications[i] = new ArrayList<>();
        }
        for (int i = 0; i < cowNum; i++) {
            String[] rawCow = read.readLine().split(" ");
            int[] processedCow = new int[3];
            processedCow[0] = rawCow[0].equals("S") ? 1 : 0;  // 1 means same, 0 means different (just making it explicit)
            processedCow[1] = Integer.parseInt(rawCow[1]);
            processedCow[2] = Integer.parseInt(rawCow[2]);
            specifications[processedCow[1]].add(new int[] {processedCow[2], processedCow[0]});
            specifications[processedCow[2]].add(new int[] {processedCow[1], processedCow[0]});
        }
        read.close();

        written = new PrintWriter(new FileWriter(new File("revegetate.out")));
        hypothetical = new int[fieldNum + 1];  // no use 0 index (seriously usaco)
        Arrays.fill(hypothetical, 69);
        usedUp = new boolean[fieldNum + 1];
        int sectorNum = 0;  // we can split the fields into "sectors"- sectors are never dependent on each other
        
        for (int i = 1; i <= fieldNum; i++) {  // the actual program
            if (!usedUp[i]) {
                hypothetical[i] = 0;  // it doesn't matter
                expandField(i);
                sectorNum++;
            }
        }

        written.write("1");  // i can always count on the answer being a power of 2
        for (int i = 0; i < sectorNum; i++) {
            written.write("0");
        }
        written.write("\n");
        written.close();
        System.out.println(sectorNum);
        System.out.printf("so it took about %d milliseconds to finish", System.currentTimeMillis() - start);
    }

    static void expandField(int start) {
        usedUp[start] = true;
        ArrayDeque<Integer> frontier = new ArrayDeque<Integer>();
        frontier.add(start);

        while (!frontier.isEmpty()) {
            int current = frontier.poll();
            for (int[] n : specifications[current]) {  // the field, same/different
                int supposedType = (n[1] == 1 ? hypothetical[current] : (hypothetical[current] == 0 ? 1 : 0));
                if (!usedUp[n[0]]) {
                    usedUp[n[0]] = true;
                    hypothetical[n[0]] = supposedType;
                    frontier.add(n[0]);
                }
                else if (hypothetical[n[0]] != supposedType) {  // if it's used up, check for a contradiction
                    System.out.println("farmer john you absolute dummy");
                    written.println("0");
                    written.close();
                    System.exit(0);
                }
            }
        }
    }
}
