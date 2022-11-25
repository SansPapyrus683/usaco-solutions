package official.o2014.jan.silver.getTheCamera;

import java.io.*;
import java.util.*;

// 2014 jan silver (this one uses the greedy approach also described in the sol)
public class RecordingGreedy {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("recording.in"));
        int[][] events = new int[Integer.parseInt(read.readLine())][2];
        for (int i = 0; i < events.length; i++) {
            events[i] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(events, (a, b) -> a[1] != b[1] ? a[1] - b[1] : a[0] - b[0]);

        int firstCamTime = -1;
        int secondCamTime = -1;
        int totalRecorded = events.length;
        for (int[] e : events) {
            if (e[0] >= firstCamTime && e[0] >= secondCamTime) {
                // choose the one that makes more efficient use of time
                if (e[0] - firstCamTime < e[0] - secondCamTime) {
                    firstCamTime = e[1];
                } else {
                    secondCamTime = e[1];
                }
            } else if (e[0] >= firstCamTime) {  // or it only fits in the first
                firstCamTime = e[1];
            } else if (e[0] >= secondCamTime) {  // or second one, depends
                secondCamTime = e[1];
            } else {
                totalRecorded--;  // frick so it turns out we can't record this event
            }
        }

        PrintWriter written = new PrintWriter("recording.out");
        written.println(totalRecorded);
        written.close();
        System.out.println(totalRecorded);
        System.out.printf("it took %d ms \"no cap\" that's how i use that right%n", System.currentTimeMillis() - start);
    }
}
