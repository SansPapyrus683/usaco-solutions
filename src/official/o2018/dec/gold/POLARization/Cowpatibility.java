package official.o2018.dec.gold.POLARization;

import java.io.*;
import java.util.*;

// 2018 dec gold
public final class Cowpatibility {
    // sauce: https://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
    private static final class Combo {
        private static ArrayList<int[]> combos = new ArrayList<>();
        private static void combos(int[] arr, int[] soFar, int start, int end, int at, int n) {
            if (at == n) {
                combos.add(soFar.clone());
                return;
            }
            for (int i = start; i <= end && end - i + 1 >= n - at; i++) {
                soFar[at] = arr[i];
                combos(arr, soFar, i + 1, end, at + 1, n);
            }
        }

        public static ArrayList<int[]> combos(int[] arr, int n) {
            combos = new ArrayList<>();
            combos(arr, new int[n], 0, arr.length - 1, 0, n);
            return combos;
        }
    }

    private static class Liked {  // lmao this is just an array with a hashcode method
        public int[] iceCream;
        public Liked(int[] like) {
            this.iceCream = like;
        }

        @Override
        public String toString() {
            return String.format("Liked{like=%s}", Arrays.toString(iceCream));
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(iceCream);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj.getClass() != getClass()) {
                return false;
            }
            return Arrays.equals(iceCream, ((Liked) obj).iceCream);
        }
    }

    private static final int FAVE_NUM = 5;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cowpatibility.in"));
        int cowNum = Integer.parseInt(read.readLine());

        HashMap<Liked, Integer>[] faveSubsets = new HashMap[FAVE_NUM];
        for (int i = 0; i < FAVE_NUM; i++) {
            faveSubsets[i] = new HashMap<>();
        }
        for (int c = 0; c < cowNum; c++) {
            int[] cow = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            if (cow.length != FAVE_NUM) {
                throw new IllegalArgumentException("blasphemy- each cow should like only 5 ice creams");
            }
            Arrays.sort(cow);
            for (int ics = 1; ics <= FAVE_NUM; ics++) {  // ics stands for "ice cream size"
                // add all the subsets to the liked subset
                for (int[] likedSubset : Combo.combos(cow, ics)) {
                    Liked processed = new Liked(likedSubset);
                    faveSubsets[ics - 1].put(processed, faveSubsets[ics - 1].getOrDefault(processed, 0) + 1);
                }
            }
        }

        long compatible = 0;  // screw it, not taking any changes with overflow
        for (int i = 0; i < FAVE_NUM; i++) {
            long validPairings = 0;
            for (int shared : faveSubsets[i].values()) {
                validPairings += pairings(shared);
            }
            /*
             * when we counted all the cows that had, say, 1 ice cream in common, we overcounted
             * so we then adjust by subtracting all the cows that had 2 ice creams in common
             * but then we undercounted (make a venn diagram or smth to understand why)
             * so then we add all the cows that have 3 ice creams in common, and so on and so forth
             */
            compatible += Math.pow(-1, i) * validPairings;
        }

        long nonCompatible = pairings(cowNum) - compatible;
        PrintWriter written = new PrintWriter("cowpatibility.out");
        written.println(nonCompatible);
        written.close();
        System.out.println(nonCompatible);
        System.out.printf("haha do you get the package name? because ice cream, polar, haha... (%d ms)%n", System.currentTimeMillis() - start);
    }

    private static long pairings(int n) {  // given n things, how many ways can we choose 2 things from it?
        return n * ((long) n - 1) / 2;
    }
}
