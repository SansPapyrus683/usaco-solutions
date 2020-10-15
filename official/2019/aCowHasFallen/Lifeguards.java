import java.util.*;
import java.io.*;

// 2018 silver jan
public class Lifeguards {
    // A COW HAS FALLEN INTO THE POOL IN FARMER JOHN'S FARM!
    static int[][] shifts;
    static int initialWatchTime;
    static SortedMap<Integer, ArrayList<Integer>> startEnds = new TreeMap<>();

    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        LifeReader read = new LifeReader("lifeguards.in");
        shifts = new int[read.nextInt()][2];

        for (int i = 0; i < shifts.length; i++) {
            int start = read.nextInt();
            int end = read.nextInt();
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

        initialWatchTime = calcWatchLength();

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
                }
                else {
                    watchedOver.add(c);
                }
            }
            lastKey = i;
        }
        
        PrintWriter written = new PrintWriter(new FileOutputStream("lifeguards.out"));
        written.println(initialWatchTime - minIntArray(onlyThem));
        written.close();
        System.out.printf("so it took like %d milliseconds to finish", System.currentTimeMillis() - timeStart);
    }

    static int calcWatchLength() {
        int startTime = -1;
        int endTime = -1;
        int amtWatched = 0;
        for (int[] s : shifts) {
            if (s[0] > endTime) {
                amtWatched += endTime - startTime;
                startTime = s[0];
                endTime = s[1];
            }
            else {
                endTime = Math.max(endTime, s[1]);
            }
        }
        amtWatched += endTime - startTime;
        return amtWatched;
    }

    static int minIntArray(int[] array) {
        int min = Integer.MAX_VALUE;
        for (int i : array) {
            min = Math.min(i, min);
        }
        return min;
    }
}

class LifeReader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;

    public LifeReader(String file_name) throws IOException {
        din = new DataInputStream(new FileInputStream(file_name));
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }

    public int nextInt() throws IOException {
        int ret = 0;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');

        if (neg)
            return -ret;
        return ret;
    }

    private void fillBuffer() throws IOException {
        bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
        if (bytesRead == -1)
            buffer[0] = -1;
    }

    private byte read() throws IOException {
        if (bufferPointer == bytesRead)
            fillBuffer();
        return buffer[bufferPointer++];
    }

    public void close() throws IOException {
        if (din == null)
            return;
        din.close();
    }
}

