import java.util.*;
import java.io.*;

public class Lemonade {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        LemonReader read = new LemonReader("lemonade.in");
        int[] cows = new int[read.nextInt()];
        for (int i = 0; i < cows.length; i++) {
            cows[i] = read.nextInt();
        }
        Arrays.sort(cows);
        for (int i = 0; i < cows.length / 2; i++) {
            int temp = cows[i];
            cows[i] = cows[cows.length - i - 1];
            cows[cows.length - i - 1] = temp;
        }
        
        int cowsInLine = 0;
        int happyCows = 0;
        for (int c : cows) {
            if (c >= cowsInLine) {
                happyCows++;
                cowsInLine++;
            }
            else {
                break;  // i mean, don't bother going farther
            }
        }
        PrintWriter written = new PrintWriter(new BufferedWriter(new FileWriter(new File("lemonade.out"))));
        written.println(happyCows);
        written.close();
        System.out.println(happyCows);
        System.out.printf("so it took like %s ms to finish%n", System.currentTimeMillis() - start);
    }
}

class LemonReader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;

    public LemonReader(String file_name) throws IOException {
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

