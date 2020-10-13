import java.util.*;
import java.io.*;

// 2019 feb silver
public class MooTube {
    static int vidNum;
    static ArrayList<int[]>[] neighbors;
    static long[] times = new long[3];

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        YTReader read = new YTReader("mootube.in");
        vidNum  = read.nextInt();
        int queryNum = read.nextInt();
        neighbors = new ArrayList[vidNum + 1];  // INDEX 0 WILL NOT BE USED
        for (int i = 1; i <= vidNum; i++) {
            neighbors[i] = new ArrayList<>();
        }

        for (int i = 0; i < vidNum - 1; i++) {
            int[] relationship  = new int[] {read.nextInt(), read.nextInt(), read.nextInt()};
            neighbors[relationship[0]].add(new int[] {relationship[1], relationship[2]});
            neighbors[relationship[1]].add(new int[] {relationship[0], relationship[2]});
        }
        int[][] queries  = new int[queryNum][2];
        for (int i = 0; i < queryNum; i++) {
            queries[i] = new int[] {read.nextInt(), read.nextInt()};
        }

        int[] answers = new int[queryNum];
        int pointer = 0;
        for (int[] q : queries) {
            answers[pointer] = ytRecommendations(q[0], q[1]);
            pointer++;
        }

        PrintWriter written = new PrintWriter(new FileWriter(new File("mootube.out")));  // so i don't need that buffered writer; neat
        for (int a : answers) {
            written.println(a);
        }
        written.close();
        System.out.println(Arrays.toString(answers));
        System.out.printf("ok so it took around %s milliseconds%n", System.currentTimeMillis() - start);
    }

    static int ytRecommendations(int threshold, int start) {  // ngl yt's recommendations are actually pretty good
        int vidsRecced = 0;
        ArrayDeque<Integer> frontier = new ArrayDeque<>();
        boolean[] traversed = new boolean[vidNum + 1];  // again, index 1 won't be used
        frontier.add(start);
        traversed[start] = true;

        while (!frontier.isEmpty()) {
            int current = frontier.poll();
            for (int[] n : neighbors[current]) {
                if (n[1] >= threshold && !traversed[n[0]]) {  // i mean, farms past this will never pass the threshold...
                    vidsRecced++;
                    frontier.add(n[0]);
                    traversed[n[0]] = true;
                }
            }
        }
        return vidsRecced;
    }
}

class YTReader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;

    public YTReader(String file_name) throws IOException {
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
