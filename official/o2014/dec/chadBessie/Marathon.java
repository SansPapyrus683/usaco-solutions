import java.io.*;
import java.util.*;

// 2014 dec silver
public class Marathon {

    static int[][] distances;  // shamelessly copied from the official solution
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        CheatingReader read = new CheatingReader("marathon.in");
        int checkpointNum = read.nextInt();
        ArrayList<int[]> checkpoints = new ArrayList<>();
        int badnessLevel = read.nextInt();
        for (int i = 0; i < checkpointNum; i++) {
            checkpoints.add(new int[] {read.nextInt(), read.nextInt()});
        }
        distances = new int[checkpoints.size()][checkpoints.size()];
        for (int p1 = 0; p1 < checkpoints.size(); p1++) {  // we probably won't even end up using half the things here lol
            for (int p2 = 0; p2 < checkpoints.size(); p2++) {  // but it takes like no time so idk and idc really
                distances[p1][p2] = pointDist(checkpoints.get(p1), checkpoints.get(p2));
            }
        }

        // this array[n][k] is the min distance it takes to get to point n (0-indexed) having skipped EXACTLY k points
        int[][] minMarathonDistances = new int[checkpointNum][badnessLevel + 1];
        for (int[] asdf : minMarathonDistances) {
            Arrays.fill(asdf, Integer.MAX_VALUE);
        }
        minMarathonDistances[0][0] = 0;

        int atRn = 0;
        for (int[] p : minMarathonDistances) {
            int alrSkipped = 0;
            for (int value : p) {
                if (value == Integer.MAX_VALUE) {  // stop for no integer overflow
                    break;
                }
                for (int i = 0; i <= badnessLevel; i++) {
                    // make sure she skips within her limits
                    if (alrSkipped + i > badnessLevel || atRn + i + 1 > checkpointNum - 1) {
                        break;
                    }
                    minMarathonDistances[atRn + i + 1][alrSkipped + i] = Math.min(minMarathonDistances[atRn + i + 1][alrSkipped + i],
                                                                                  value + distances[atRn][atRn + i + 1]);
                }
                alrSkipped++;
            }
            atRn++;
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("marathon.out"));
        written.println(minMarathonDistances[checkpointNum - 1][badnessLevel]);
        written.close();
        System.out.println(minMarathonDistances[checkpointNum - 1][badnessLevel]);
        System.out.printf("took about %d ms idk man%n", System.currentTimeMillis() - start);
    }

    static int pointDist(int[] p1, int[] p2) {
        return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
    }
}

class CheatingReader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;

    public CheatingReader(String fileName) throws IOException {
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

