package official.o2024.feb.bronze;

import java.io.*;
import java.util.*;

/** 2024 feb bronze */
public class MaxProductivity {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int farmNum = Integer.parseInt(initial.nextToken());
        int queryNum = Integer.parseInt(initial.nextToken());

        int[] farms =
                Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        StringTokenizer times = new StringTokenizer(read.readLine());
        for (int f = 0; f < farmNum; f++) {
            farms[f] -= Integer.parseInt(times.nextToken());
        }
        Arrays.sort(farms);

        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            int visitReq = Integer.parseInt(query.nextToken());
            int wakeup = Integer.parseInt(query.nextToken());
            int lt = farms[farms.length - visitReq];
            System.out.println(wakeup < lt ? "YES" : "NO");
        }
    }
}
