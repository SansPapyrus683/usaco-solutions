package official.o2020.usopen.gold.cyanBestColor;

import java.io.*;
import java.util.*;

// 2020 us open gold
public final class FColor {
    /**
     * this is based off {@link utils.DisjointSets}, go there for the link that explains this bc i sure can't<br>
     * so this final class represents all the cows- when merging two cows, not only does it put them into the same set,
     * but it also merges the stans into the top-level cow
     */
    private static final class DisjointCows {
        private final int[] parents;
        private final int[] sizes;
        private final ArrayList<Integer>[] stans;
        public DisjointCows(ArrayList<Integer>[] stans) {
            int size = stans.length;
            parents = new int[size];
            sizes = new int[size];
            this.stans = new ArrayList[size];
            for (int i = 0; i < size; i++) {
                parents[i] = i;
                sizes[i] = 1;
                this.stans[i] = new ArrayList<>(stans[i]);
            }
        }

        public int getUltimate(int c) {
            return parents[c] = (parents[c] == c ? c : getUltimate(parents[c]));
        }

        public void link(int c1, int c2) {
            if (getUltimate(c1) == getUltimate(c2)) {
                return;
            }
            c1 = getUltimate(c1);
            c2 = getUltimate(c2);
            if (sizes[c2] > sizes[c1]) {  // make it so c1's set is always going to be the larger one
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }
            parents[c2] = c1;
            sizes[c1] += sizes[c2];
            stans[c1].addAll(stans[c2]);
            stans[c2] = new ArrayList<>();  // clear the stans of the second cow
        }

        public ArrayList<Integer>[] getStans() {
            return stans;
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("fcolor.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int relationshipNum = Integer.parseInt(initial.nextToken());

        ArrayList<Integer>[] stans = new ArrayList[cowNum];
        for (int c = 0; c < cowNum; c++) {
            stans[c] = new ArrayList<>();
        }
        for (int r = 0; r < relationshipNum; r++) {
            StringTokenizer relationship = new StringTokenizer(read.readLine());
            stans[Integer.parseInt(relationship.nextToken()) - 1].add(Integer.parseInt(relationship.nextToken()) - 1);
        }

        ArrayDeque<Integer> stansToBeMerged = new ArrayDeque<>();
        for (int c = 0; c < cowNum; c++) {
            if (stans[c].size() >= 2) {
                stansToBeMerged.add(c);
            }
        }

        DisjointCows mergingCows = new DisjointCows(stans);
        // keep on merging stans until there's no more stans to be merged
        while (!stansToBeMerged.isEmpty()) {
            int curr = stansToBeMerged.poll();
            int prevOne = -1;
            boolean allMergedAlr = true;
            // the copy is for concurrently modifying the list (because a cow might be narcissist and stan themself)
            ArrayList<Integer> currStans = new ArrayList<>(mergingCows.getStans()[curr]);
            for (int s : currStans) {
                if (prevOne != -1 && mergingCows.getUltimate(s) != mergingCows.getUltimate(prevOne)) {
                    allMergedAlr = false;
                    mergingCows.link(prevOne, s);
                }
                prevOne = s;
            }
            if (!allMergedAlr) {  // if not all of the stans were already the same, we add the "representative" of the stans
                stansToBeMerged.add(mergingCows.getUltimate(prevOne));
            }
        }

        HashMap<Integer, Integer> starsToColor = new HashMap<>();
        int rnColor = 1;
        StringBuilder allAssigned = new StringBuilder();  // StringBuilder so printing isn't slow
        for (int c = 0; c < cowNum; c++) {
            int ultimateParent = mergingCows.getUltimate(c);
            if (!starsToColor.containsKey(ultimateParent)) {
                starsToColor.put(ultimateParent, rnColor++);
            }
            allAssigned.append(starsToColor.get(ultimateParent)).append('\n');
        }
        PrintWriter written = new PrintWriter("fcolor.out");
        written.print(allAssigned);  // print not println because the stringbuilder alr has a \n @ the end
        written.close();
        System.out.print(allAssigned);
        System.out.printf("oof %d ms that's alot of time%n", System.currentTimeMillis() - start);
    }
}
