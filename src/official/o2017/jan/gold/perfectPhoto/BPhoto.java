package official.o2017.jan.gold.perfectPhoto;

import java.io.*;
import java.util.*;

// 2017 jan gold
public class BPhoto {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("bphoto.in"));
        int cowNum = Integer.parseInt(read.readLine());

        HashSet<Integer> distinctHeights = new HashSet<>();
        int[] cows = new int[cowNum];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Integer.parseInt(read.readLine());
            distinctHeights.add(cows[c]);
        }
        ArrayList<Integer> heights = new ArrayList<>(distinctHeights);
        heights.sort(Comparator.comparingInt(h -> h));
        HashMap<Integer, Integer> compressedHeights = new HashMap<>();
        int compressed = 0;
        for (int h : heights) {
            compressedHeights.put(h, compressed++);
        }
        for (int c = 0; c < cowNum; c++) {
            cows[c] = compressedHeights.get(cows[c]);
        }

        BITree leftHeights = new BITree(heights.size());
        BITree rightHeights = new BITree(heights.size());
        for (int c : cows) {
            rightHeights.increment(c, 1);
        }
        int unbalanced = 0;
        for (int c : cows) {
            int leftTaller = leftHeights.query(heights.size() - 1) - leftHeights.query(c);
            int rightTaller = rightHeights.query(heights.size() - 1) - rightHeights.query(c);
            if (leftTaller > 2 * rightTaller || rightTaller > 2 * leftTaller) {
                unbalanced++;
            }
            leftHeights.increment(c, 1);
            rightHeights.increment(c, -1);
        }

        PrintWriter written = new PrintWriter("bphoto.out");
        written.println(unbalanced);
        written.close();
        System.out.println(unbalanced);
        System.out.printf("how many photos has fj taken jesus christ: %d ms%n", System.currentTimeMillis() - start);
    }
}

class BITree {
    private final int[] treeThing;
    private final int size;
    public BITree(int size) {
        treeThing = new int[size + 1];
        this.size = size;
    }

    public void increment(int updateAt, long val) {
        updateAt++;  // have the driver code not worry about 1-indexing
        for (; updateAt <= size; updateAt += updateAt & -updateAt) {
            treeThing[updateAt] += val;
        }
    }

    public int query(int ind) {  // the bound is inclusive i think
        ind++;
        int sum = 0;
        for (; ind > 0; ind -= ind & -ind) {
            sum += treeThing[ind];
        }
        return sum;
    }
}
