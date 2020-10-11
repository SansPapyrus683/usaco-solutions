import java.util.*;
import java.io.*;

public class Wormsort {
    static int[] cows;
    static int[] parents;
    static int[] sizes;
    static int[][] wormholes;
    public static void main(String[] args) throws IOException {
        var start = System.currentTimeMillis();
        Reader read = new Reader("wormsort.in");
        PrintWriter written = new PrintWriter(new FileWriter(new File("wormsort.out")));
        int cowNum = read.nextInt();
        int wormholeNum = read.nextInt();
        
        cows = new int[cowNum + 1];  // stupid 1-based indexing
        parents = new int[cowNum + 1];  // index 0 will never be used just so you know
        sizes = new int[cowNum + 1];
        ArrayList<int[]> toSort = new ArrayList<>();
        boolean alrSorted = true;
        for (int i = 1; i <= cowNum; i++) {
            cows[i] = read.nextInt();
            if (cows[i] != i) {
                alrSorted = false;
                toSort.add(new int[] {cows[i], i});
            }
        }
        if (alrSorted) {
            System.out.println("bruh this is already sorted");
            written.println("-1");
            written.close();
            System.exit(0);
        }

        wormholes = new int[wormholeNum][3];
        for (int i = 0; i < wormholeNum; i++) {
            wormholes[i] = new int[] {read.nextInt(), read.nextInt(), read.nextInt()};
        }

        int lowerBound = 1;
        int upperBound = Integer.MIN_VALUE;
        for (int[] w : wormholes) {
            upperBound = Math.max(upperBound, w[2] + 1);  // +1 because it isn't inclusive
        }
        while (upperBound - lowerBound > 1) {  // binary search the possible lower bound for wormhole width
            int toSearch = (upperBound + lowerBound) / 2;
            for (int i = 1; i <= cowNum; i++) {  // reset parents, sizes, & the cached parents
                parents[i] = i;
            }
            Arrays.fill(sizes, 1);

            boolean goodToGo = true;
            for (int[] w : wormholes) {
                if (w[2] >= toSearch) {
                    merge(w[0], w[1]);
                }
            }

            for (int[] ts: toSort) {
                if (getUltimate(ts[0]) != getUltimate(ts[1])) {  // test if this cow can still be sorted
                    goodToGo = false;
                    break;
                }
            }

            if (goodToGo) {
                lowerBound = toSearch;
            }
            else {
                upperBound = toSearch;
            }
        }
        
        System.out.println(lowerBound);
        written.printf("%s%n", lowerBound);
        written.close();
        System.out.printf("so it took about this many ms: %s%n", System.currentTimeMillis() - start);
    }

    static int getUltimate(int start) {
        if (parents[start] == start) {
            return start;  // i mean the start is its own parent so it doesn't really matter
        }
        return parents[start] = getUltimate(parents[start]);
    }

    static void merge(int nodeA, int nodeB) {
        nodeA = getUltimate(nodeA);  // always merge roots, not leaves
        nodeB = getUltimate(nodeB);
        if (sizes[nodeB] > sizes[nodeA]) {  // make sure that a is always larger than b
            int temp = nodeA;
            nodeA = nodeB;
            nodeB = temp;
        }
        parents[nodeB] = nodeA;
        sizes[nodeA] += sizes[nodeB];
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
