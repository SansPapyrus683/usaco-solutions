package official.o2013.jan.silver.cowLabor;

import java.io.*;
import java.util.*;

// 2013 jan silver
public class Paint {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("paint.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int moveNum = Integer.parseInt(initial.nextToken());
        int paintReq = Integer.parseInt(initial.nextToken());

        int pos = 0;
        TreeMap<Integer, Integer> fenceStartEnds = new TreeMap<>();
        for (int m = 0; m < moveNum; m++) {
            StringTokenizer move = new StringTokenizer(read.readLine());
            int magnitude = Integer.parseInt(move.nextToken());
            assert magnitude >= 0;
            String dir = move.nextToken().toUpperCase();
            int from = pos;
            if (dir.equals("L")) {
                pos -= magnitude;
                fenceStartEnds.put(pos, fenceStartEnds.getOrDefault(pos, 0) + 1);
                fenceStartEnds.put(from, fenceStartEnds.getOrDefault(from, 0) - 1);
            } else if (dir.equals("R")) {
                pos += magnitude;
                fenceStartEnds.put(from, fenceStartEnds.getOrDefault(from, 0) + 1);
                fenceStartEnds.put(pos, fenceStartEnds.getOrDefault(pos, 0) - 1);
            }
        }

        int covered = 0;
        int paintSoFar = 0;
        int last = -1;
        for (Map.Entry<Integer, Integer> point : fenceStartEnds.entrySet()) {
            if (paintSoFar >= paintReq) {
                covered += point.getKey() - last;
            }
            last = point.getKey();
            paintSoFar += point.getValue();
        }

        PrintWriter written = new PrintWriter("paint.out");
        written.println(covered);
        written.close();
        System.out.println(covered);
        System.out.printf("i feel bad for bessie: %d ms%n", System.currentTimeMillis() - start);
    }
}
