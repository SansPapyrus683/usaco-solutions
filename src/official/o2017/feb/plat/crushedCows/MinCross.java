package official.o2017.feb.plat.crushedCows;

import java.io.*;

// 2017 feb plat
public class MinCross {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("mincross.in"));
        int pastureNum = Integer.parseInt(read.readLine());
        int[] oneSide = new int[pastureNum];
        int[] otherSide = new int[pastureNum];
        for (int p = 0; p < pastureNum; p++) {
            oneSide[p] = Integer.parseInt(read.readLine()) - 1;
        }
        for (int p = 0; p < pastureNum; p++) {
            otherSide[p] = Integer.parseInt(read.readLine()) - 1;
        }

        long shiftFirstMin = leftShiftMinCross(oneSide, otherSide);
        long shiftSecondMin = leftShiftMinCross(otherSide, oneSide);
        long totalMin = Math.min(shiftFirstMin, shiftSecondMin);

        PrintWriter written = new PrintWriter("mincross.out");
        written.println(totalMin);
        written.close();
        System.out.println(totalMin);
        System.out.printf("i seriously wish i took usaco when i was younger: %d ms%n", System.currentTimeMillis() - start);
    }

    private static long leftShiftMinCross(int[] toShift, int[] otherSide) {
        assert toShift.length == otherSide.length;

        int pastureNum = toShift.length;
        int[] idToInd = new int[pastureNum];
        for (int i = 0; i < pastureNum; i++) {
            idToInd[otherSide[i]] = i;
        }
        int[] inversionPastures = new int[pastureNum];
        for (int i = 0; i < pastureNum; i++) {
            inversionPastures[i] = idToInd[toShift[i]];
        }

        long inversions = 0;
        BITree seenPastures = new BITree(pastureNum);
        for (int p = 0; p < pastureNum; p++) {
            inversions += seenPastures.query(pastureNum - 1) - seenPastures.query(inversionPastures[p]);
            seenPastures.increment(inversionPastures[p], 1);
        }
        long minInversions = inversions;
        // keep on shifting the array to the left aka take elements from the right and slap them on to the left
        for (int p = pastureNum - 1; p >= 0; p--) {
            // all the inversions that came from the elements greater than this one are gone
            inversions -= pastureNum - inversionPastures[p] - 1;
            // but oof, it also added some new inversions by being greater than some elements as well
            inversions += inversionPastures[p];
            minInversions = Math.min(minInversions, inversions);
        }
        return minInversions;
    }
}

class BITree {
    private final int[] treeThing;
    private final int size;
    public BITree(int size) {
        treeThing = new int[size + 1];  // to make stuff easier we'll just make it 1-indexed
        this.size = size;
    }

    public void increment(int ind, int val) {
        ind++;  // have the driver code not worry about 1-indexing
        for (; ind <= size; ind += ind & -ind) {
            treeThing[ind] += val;
        }
    }

    public int query(int ind) {  // the bound is inclusive i think (returns sum of everything from 0 to ind)
        ind++;
        int sum = 0;
        for (; ind > 0; ind -= ind & -ind) {
            sum += treeThing[ind];
        }
        return sum;
    }
}
