package official.o2015.usopen.silver.hayPrison;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2015 us open silver (solution shamelessly copied)
public final class Trapped {
    private static class Bale {  // makes reading the code much more easier
        int size;
        int pos;
        public Bale(int size, int pos) {
            this.size = size;
            this.pos = pos;
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("trapped.in"));
        String[] initInfo = read.readLine().split(" ");
        Bale[] bales = new Bale[Integer.parseInt(initInfo[0])];
        int bessiePos = Integer.parseInt(initInfo[1]);
        for (int i = 0; i < bales.length; i++) {
            int[] raw = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            bales[i] = new Bale(raw[0], raw[1]);
        }
        Arrays.sort(bales, Comparator.comparingInt(h -> h.pos));  // sort by position

        int insertPos = -1;  // the position we would hypothetically insert bessie to preserve order among the road
        if (bessiePos < bales[0].pos) {
            insertPos = 0;
        } else if (bessiePos > bales[bales.length - 1].pos) {
            insertPos = bales.length;
        } else {
            for (int b = 0; b < bales.length; b++) {
                if (bessiePos < bales[b].pos) {
                    insertPos = b;
                    break;
                }
            }
        }

        int best = Integer.MAX_VALUE;
        int otherSide = insertPos;
        for (int i = insertPos - 1; i >= 0; i--) {
            // while our bale at i holds strong, see what the repair costs for the other sides are
            while (otherSide < bales.length && bales[otherSide].pos - bales[i].pos <= bales[i].size) {
                // if it can, then we calculate the "repair costs"- the cost to make it so bes can't break through
                best = Math.min(best, bales[otherSide].pos - bales[i].pos - bales[otherSide].size);
                otherSide++;
            }
        }

        // do the same thing but in reverse
        otherSide = insertPos - 1;
        for (int i = insertPos; i < bales.length; i++) {
            // the ineq is the same but in reverse
            while (otherSide >= 0 && bales[i].size >= bales[i].pos - bales[otherSide].pos) {
                best = Math.min(best, bales[i].pos - bales[otherSide].size - bales[otherSide].pos);
                otherSide--;
            }
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("trapped.out"));
        if (best == Integer.MAX_VALUE) {
            written.println(-1);
            System.out.println(-1);
        } else {
            written.println(Math.max(best, 0));  // bc best might be negative when we don't need to anything & bessie's alr trapped
            System.out.println(Math.max(best, 0));
        }
        written.close();
        System.out.printf("oh no it took %d ms we're all doomed%n", System.currentTimeMillis() - start);
    }
}
