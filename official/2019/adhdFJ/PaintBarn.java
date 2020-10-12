import java.util.*;
import java.io.*;

public class PaintBarn {
    static int[][][] rectangles;
    static int requirement;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        Reader read = new Reader("paintbarn.in");
        rectangles = new int[read.nextInt()][2][2];
        requirement = read.nextInt();
        for (int i = 0; i < rectangles.length; i++) {
            rectangles[i] = new int[][] {{read.nextInt(), read.nextInt()}, {read.nextInt(), read.nextInt()}};
        }
        System.out.printf("took around %d milliseconds", System.currentTimeMillis() - start);
    }
}

class Reader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;

    public Reader(String file_name) throws IOException {
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
