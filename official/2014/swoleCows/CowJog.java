import java.util.*;
import java.io.*;

// 2014 silver dec contest
// somehow even though this should take smth like n^2 operations it still worked
// w h a t
public class CowJog {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        JoggingReader read = new JoggingReader("cowjog.in");
        int[][] cows = new int[read.nextInt()][2];
        int time = read.nextInt();
        for (int i = 0; i < cows.length; i++) {
            cows[i] = new int[] {read.nextInt(), read.nextInt()};  // they're alr sorted, just so you know
        }

        int[][] simulationCows = new int[cows.length][2];  // stupid pass-by-reference
        for (int i = 0; i < cows.length; i++) {
            simulationCows[i] = cows[i].clone();
        }
        ArrayList<int[]> groups = new ArrayList<>(Arrays.asList(simulationCows.clone()));  // why the heck not

        int[] catchupTimes = new int[cows.length];
        int timeLeft = time;
        while (true) {
            Arrays.fill(catchupTimes, Integer.MAX_VALUE);  // reset the stuff
            int nextCatchupTime = Integer.MAX_VALUE;
            for (int c = 0; c < groups.size() - 1; c++) {  // haha c++
                int[] behindCow = groups.get(c);
                int[] cowInFront = groups.get(c + 1);
                if (behindCow[1] > groups.get(c + 1)[1]) {
                    int theoreticalTime = (cowInFront[0] - behindCow[0]) / (behindCow[1] - cowInFront[1]);
                    nextCatchupTime = Math.min(nextCatchupTime, theoreticalTime);
                    catchupTimes[c] = theoreticalTime;
                }
            }

            if (nextCatchupTime == Integer.MAX_VALUE || timeLeft - nextCatchupTime < 0) {
                break;  // either all the groups have been done or we've run out of time before another merge
            }

            for (int c = 0; c < groups.size(); c++) {
                if (catchupTimes[c] > nextCatchupTime) {
                    groups.get(c)[0] += groups.get(c)[1] * nextCatchupTime;
                }
                else {
                    groups.set(c, null);
                }
            }
            groups.removeAll(Collections.singletonList(null));
            timeLeft -= nextCatchupTime;
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("cowjog.out"));
        written.println(groups.size());
        written.close();
        System.out.println(groups.size());
        System.out.printf("ok so it took around %d ms%n", System.currentTimeMillis() - start);
    }
}

class JoggingReader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;

    public JoggingReader(String fileName) throws IOException {
        din = new DataInputStream(new FileInputStream(fileName));
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
