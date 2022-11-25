package official.o2016.feb.gold.noRegrets;

import java.io.*;
import java.util.*;

// 2016 feb gold (goddamn this solution is extremely convoluted)
public class CBarn2 {
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cbarn2.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int roomNum = Integer.parseInt(initial.nextToken());
        int doorNum = Integer.parseInt(initial.nextToken());
        int[] roomCows = new int[roomNum];
        for (int r = 0; r < roomNum; r++) {
            roomCows[r] = Integer.parseInt(read.readLine());
        }

        long minDist = Integer.MAX_VALUE;
        for (int start = 0; start < roomNum; start++) {
            minDist = Math.min(minDist, minDist(roomCows, doorNum, start));
        }

        PrintWriter written = new PrintWriter("cbarn2.out");
        written.println(minDist);
        written.close();
        System.out.println(minDist);
        System.out.printf("fj have you learned ANYTHING from the last problem? (%d ms)%n", System.currentTimeMillis() - timeStart);
    }

    private static long minDist(int[] roomCows, int doorNum, int startingRoom) {
        int roomNum = roomCows.length;
        roomCows = leftRotArr(roomCows, startingRoom);
        ConsecQueries prefCows = new ConsecQueries(roomCows);
        // this[i][j] = minimum distance if we only consider the first i rooms and only use j doors
        long[][] minDist = new long[roomNum + 1][doorNum + 1];
        for (int r = 0; r <= roomNum; r++) {
            Arrays.fill(minDist[r], Long.MAX_VALUE);
        }
        minDist[0] = null;
        for (int r = 1; r <= roomNum; r++) {
            long[] subCircleDist = minDist[r];
            // these 2 indices are here because of stupid special cases
            subCircleDist[1] = prefCows.query(1, r - 1);
            if (doorNum < 2) {
                continue;
            }
            subCircleDist[2] = prefCows.query(1, r - 2);
            for (int d = 3; d <= Math.min(r, doorNum); d++) {
                for (int prevR = r - 1; prevR > 0; prevR--) {
                    if (minDist[prevR][d - 1] == Long.MAX_VALUE) {
                        continue;
                    }
                    subCircleDist[d] = Math.min(
                            subCircleDist[d],
                            minDist[prevR][d - 1] + prefCows.query(prevR, (r - 1) - 1)
                    );
                }
            }
        }
        long overallMin = minDist[roomNum][doorNum];
        if (doorNum > 1) {
            for (int r = doorNum; r < roomNum; r++) {
                overallMin = Math.min(overallMin, minDist[r][doorNum] + prefCows.query(r, roomNum - 1));
            }
        }
        return overallMin;
    }

    private static int[] leftRotArr(int[] arr, int dist) {
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[(i + dist) % arr.length];
        }
        return result;
    }
}

class ConsecQueries {
    private long[] prefArr;
    private long[] consecPrefArr;

    public ConsecQueries(int[] arr) {
        prefArr = new long[arr.length + 1];
        consecPrefArr = new long[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            prefArr[i + 1] = prefArr[i] + arr[i];
            consecPrefArr[i + 1] = consecPrefArr[i] + (long) (i + 1) * arr[i];
        }
    }

    public long query(int start, int end) {  // bounds'll be inclusive
        return (consecPrefArr[end + 1] - consecPrefArr[start]) - start * (prefArr[end + 1] - prefArr[start]);
    }
}
