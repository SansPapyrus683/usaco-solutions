package official.o2018.jan.silver.aCowHasFallen;

import java.io.*;
import java.util.*;

// 2018 silver jan
public class Lifeguards {
    // A COW HAS FALLEN INTO THE POOL IN FARMER JOHN'S FARM!
    private static int[][] shifts;
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("lifeguards.in"));
        shifts = new int[Integer.parseInt(read.readLine())][2];
        SortedMap<Integer, ArrayList<Integer>> startEnds = new TreeMap<>();
        for (int i = 0; i < shifts.length; i++) {
            StringTokenizer shift = new StringTokenizer(read.readLine());
            int start = Integer.parseInt(shift.nextToken());
            int end = Integer.parseInt(shift.nextToken());
            shifts[i] = new int[] {start, end};
            if (!startEnds.containsKey(start)) {
                startEnds.put(start, new ArrayList<>());
            }
            if (!startEnds.containsKey(end)) {
                startEnds.put(end, new ArrayList<>());
            }
            startEnds.get(start).add(i);
            startEnds.get(end).add(i);
        }
        Arrays.sort(shifts, Comparator.comparingInt(o -> o[0]));

        int startTime = -1;
        int endTime = -1;
        int initialWatchTime = 0;
        for (int[] s : shifts) {
            if (s[0] > endTime) {
                initialWatchTime += endTime - startTime;
                startTime = s[0];
                endTime = s[1];
            } else {
                endTime = Math.max(endTime, s[1]);
            }
        }
        initialWatchTime += endTime - startTime;

        int[] onlyThem = new int[shifts.length];
        HashSet<Integer> watchedOver = new HashSet<>();
        int lastKey = 0;
        for (int i : startEnds.keySet()) {
            if (watchedOver.size() == 1) {
                onlyThem[(int) watchedOver.toArray()[0]] += i - lastKey;
            }
            for (int c : startEnds.get(i)) {
                if (watchedOver.contains(c)) {
                    watchedOver.remove(c);
                } else {
                    watchedOver.add(c);
                }
            }
            lastKey = i;
        }
        
        PrintWriter written = new PrintWriter(new FileOutputStream("lifeguards.out"));
        written.println(initialWatchTime - Arrays.stream(onlyThem).min().getAsInt());
        written.close();
        System.out.printf("so it took like %d milliseconds to finish", System.currentTimeMillis() - timeStart);
    }
}
