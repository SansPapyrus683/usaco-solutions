import java.io.*;
import java.util.*;

// 2018 dec silver
public class Convention {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("convention.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int busNum = Integer.parseInt(initial.nextToken());
        int capacity = Integer.parseInt(initial.nextToken());
        assert busNum * capacity >= cowNum;
        int[] cows = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(cows);

        int validWait = -1;
        int lowerBound = 0;
        int upperBound = (int) Math.pow(10, 9);
        while (lowerBound <= upperBound) {  // ngl binsearch is getting kinda boring at this point
            int toSearch = (upperBound + lowerBound) / 2;
            if (waitTimePossibility(cows, busNum, capacity, toSearch)) {
                upperBound = toSearch - 1;
                validWait = toSearch;
            } else {
                lowerBound = toSearch + 1;
            }
        }

        PrintWriter written = new PrintWriter("convention.out");
        written.println(validWait);
        written.close();
        System.out.println(validWait);
        System.out.printf("i hate you and your dumb code that ran for %d ms%n", System.currentTimeMillis() - start);
    }

    // assumes arrivals is sorted which was kinda obvi tbh
    static boolean waitTimePossibility(int[] arrivals, int busNum, int capacity, int time) {
        assert capacity * busNum >= arrivals.length;
        int lastWaitingCow = 0;
        for (int b = 0; b < busNum; b++) {  // squeeze as many cows that fit in the bus and the waiting time
            int firstCow = arrivals[lastWaitingCow];
            int cowsTaken = 1;
            lastWaitingCow++;
            while (cowsTaken < capacity && arrivals[lastWaitingCow] - firstCow <= time) {  // god i hate while loops
                if (lastWaitingCow == arrivals.length - 1) {  // we've gotten all the cows
                    return true;
                }
                lastWaitingCow++;
                cowsTaken++;
            }
        }
        return false;  // well frick, couldn't fit in all the cows
    }
}
