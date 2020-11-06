import java.io.*;
import java.util.*;

// 2014 jan silver
public class SlowDown {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("slowdown.in"));
        int eventNum = Integer.parseInt(read.readLine());
        ArrayList<Integer> times = new ArrayList<>();
        ArrayList<Integer> distances = new ArrayList<>();
        for (int i = 0; i < eventNum; i++) {
            String[] rawEvent = read.readLine().split(" ");
            if (rawEvent[0].equals("T")) {
                times.add(Integer.parseInt(rawEvent[1]));
            } else if (rawEvent[0].equals("D")) {
                distances.add(Integer.parseInt(rawEvent[1]));
            }
        }
        times.sort(Comparator.comparingInt(a -> a));
        distances.sort(Comparator.comparingInt(a -> a));
        distances.add(1000);  // a marker for the end

        double at = 0;  // somehow when i use floats the whole thing fricking breaks
        double totalTime = 0;
        int timePerM = 1;
        int posIndex = -1, timeIndex = -1;  // this index = the last point of interest
        while (at < 1000) {
            double nextTime = timeIndex + 1 < times.size() ? times.get(timeIndex + 1) - totalTime : Double.MAX_VALUE;
            double nextPosTime = posIndex + 1 < distances.size() ? (distances.get(posIndex + 1) - at) * timePerM : Double.MAX_VALUE;
            if (nextTime < nextPosTime) {  // the time "landmark" occurs first
                at += nextTime / timePerM;
                totalTime += nextTime;
                timeIndex++;
                timePerM++;
            } else if (nextPosTime < nextTime) {  // the position "landmark" occurs first
                at = distances.get(posIndex + 1);
                totalTime += nextPosTime;
                posIndex++;
                timePerM++;
            } else {
                at = distances.get(posIndex + 1);  // it's the same if we use the time marker anyways
                totalTime += nextPosTime;
                posIndex++;
                timeIndex++;
                timePerM += 2;
            }
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("slowdown.out"));
        written.println(Math.round(totalTime));
        written.close();
        System.out.println(Math.round(totalTime));
        System.out.printf("it took %d ms, and let's see- yep bessie hasn't even passed 100 m lol%n", System.currentTimeMillis() - start);
    }
}
