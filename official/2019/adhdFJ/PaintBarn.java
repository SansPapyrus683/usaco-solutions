import java.io.*;

public class PaintBarn {
    static final int BARN_WIDTH = 1000;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        PaintReader read = new PaintReader("paintbarn.in");
        int rectNum = read.nextInt();
        int requirement = read.nextInt();
        int[][] startEnds = new int[BARN_WIDTH][BARN_WIDTH];
        for (int i = 0; i < rectNum; i++) {
            int[][] rect = new int[][] {{read.nextInt(), read.nextInt()}, {read.nextInt(), read.nextInt()}};
            int startX = rect[0][0];
            int endX = rect[1][0];
            for (int y = rect[0][1]; y < rect[1][1]; y++) {
                startEnds[y][startX]++;  // the start
                startEnds[y][endX]--;  // the end
            }
        }

        int kAreaAmt = 0;
        for (int r = 0; r < BARN_WIDTH; r++) {
            int currIncrement = 0;
            int[] increments = startEnds[r];
            for (int c = 0; c < BARN_WIDTH; c++) {
                if (currIncrement == requirement) {
                    // System.out.printf("%s %s%n", r, c);
                    kAreaAmt++;
                }
                currIncrement += increments[c];
            }
        }

        PrintWriter written = new PrintWriter(new FileWriter(new File("paintbarn.out")));
        written.println(kAreaAmt);
        written.close();
        System.out.println(kAreaAmt);
        System.out.printf("took around %d milliseconds", System.currentTimeMillis() - start);
    }
}

class PaintReader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;

    public PaintReader(String file_name) throws IOException {
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
