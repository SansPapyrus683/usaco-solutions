import java.io.*;

public class RestStops {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        Reader read = new Reader("reststops.in");
        read.nextInt();  // lol the trail length is irrelevant
        int restStopNum = read.nextInt();
        int farmerSpeed = read.nextInt();  // NOTE TO SELF: IT'S SECONDS PER METER
        int bessieSpeed = read.nextInt();

        long[] positions = new long[restStopNum];  // we're dealing with huge numbers here boys
        long[] goodGrass = new long[restStopNum];
        for (int i = 0; i < restStopNum; i++) {
            positions[i] = read.nextInt();
            goodGrass[i] = read.nextInt();
        }

        boolean[] goodOnes = new boolean[restStopNum];
        long prevGrass = 0;
        for (int i = restStopNum - 1; i >= 0; i--) {
            if (goodGrass[i] > prevGrass) {
                goodOnes[i] = true;
                prevGrass = goodGrass[i];
            }
        }
        
        long tastiness = 0L;
        long startOff = 0;
        for (int i = 0; i < restStopNum; i++) {  // position, goodgrass (comment for reference)
            if (goodOnes[i]) {
                long rsPos = positions[i];
                long gottenAmt = goodGrass[i];
                long bessiesLead = ((rsPos - startOff) * farmerSpeed) - ((rsPos - startOff) * bessieSpeed);
                tastiness += bessiesLead * gottenAmt;
                startOff = rsPos;
            }
        }

        PrintWriter written = new PrintWriter(new FileWriter(new File("reststops.out")));
        written.println(tastiness);
        written.close();
        System.out.println(tastiness);
        System.out.printf("so it took about %d ms to finish", System.currentTimeMillis() - start);
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

