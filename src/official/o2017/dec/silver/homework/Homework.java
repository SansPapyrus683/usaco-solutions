package official.o2017.dec.silver.homework;

import java.io.*;
import java.util.*;

// 2017 dec silver
public class Homework {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("homework.in"));
        int problemNum = Integer.parseInt(read.readLine());
        int[] problems = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // this[i] = min problems if i was the closest problem that bessie didn't eat
        int[] rightSideMin = new int[problemNum];
        rightSideMin[problemNum - 1] = problems[problemNum - 1];
        for (int p = problemNum - 2; p >= 0; p--) {
            rightSideMin[p] = Math.min(rightSideMin[p + 1], problems[p]);
        }

        float maxPoints = 0;
        ArrayList<float[]> points = new ArrayList<>();
        int runningTotal = Arrays.stream(problems).sum() - problems[0];  // subtraction bc we alr "processed" the first one
        for (int firstLeft = 1; firstLeft < problemNum; firstLeft++) {
            float pts = (runningTotal - rightSideMin[firstLeft]) / (float) (Math.max(problemNum - firstLeft - 1, 1));
            points.add(new float[] {pts, firstLeft});
            maxPoints = Math.max(maxPoints, pts);
            runningTotal -= problems[firstLeft];  // ok this problem has passed the point of no return, remove it from total
        }

        PrintWriter written = new PrintWriter("homework.out");
        for (float[] p : points) {
            if (p[0] == maxPoints) {
                written.println((int) p[1]);
                System.out.println((int) p[1]);
            }
        }
        written.close();
        System.out.printf("%d ms and we are done. what a chad runtime%n", System.currentTimeMillis() - start);
    }
}
