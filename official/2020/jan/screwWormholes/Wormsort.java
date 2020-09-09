import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Wormsort {
    public static HashMap<Integer, Integer> parents = new HashMap<>();
    public static HashMap<Integer, Integer> sizes = new HashMap<>();
    public static HashMap<Integer, Integer> positions = new HashMap<>();

    public static void main(String[] args) throws IOException {
        File file = new File("wormsort.in");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        int lineNumber = 0;  // start from 0
        String[] start = br.readLine().split(" ");

        Integer[] actual = new Integer[Integer.parseInt(start[0])];
        Integer[][] wormholes = new Integer[Integer.parseInt(start[1])][3];

        while ((line = br.readLine()) != null) {
            if (lineNumber == 0) {
                int whichCow = 0;
                for (String i: line.split(" ")) {
                    actual[whichCow] = Integer.parseInt(i);
                    positions.put(Integer.parseInt(i), whichCow);
                    whichCow++;
                }
            } else if (lineNumber >= 1) {
                String[] rawWormhole = line.split(" ");
                Integer[] newWormhole = new Integer[3];
                for (int counter = 0; counter < 3; counter++) {
                    newWormhole[counter] = Integer.parseInt(rawWormhole[counter]);
                }
                wormholes[lineNumber - 1] = newWormhole;
            }
            lineNumber++;
        }
        br.close();

        int lowerBound = Integer.MAX_VALUE;
        int upperBound = Integer.MIN_VALUE;
        for (Integer[] w : wormholes) {
            if (w[2] > upperBound) {
                upperBound = w[2];
            }
            if (w[2] < lowerBound) {
                lowerBound = w[2];
            }
        }
        int cowNum = Integer.MIN_VALUE;
        for (int c : actual) {
            if (c > cowNum) {
                cowNum = c;
            }
        }

        ArrayList<Integer> toSort = new ArrayList<>();
        for (int i = 0; i < cowNum; i++) {
            if (actual[i] != i + 1){
                toSort.add(actual[i]);
            }
        }

        BufferedWriter written = new BufferedWriter(new FileWriter("wormsort.out"));
        boolean sorted = true;
        for (int i = 0; i < actual.length - 1; i++) {
            if (actual[i] >= actual[i+1]) {
                sorted = false;
                break;
            }
        }
        if (sorted) {
            written.write("-1\n");
            written.close();
            System.exit(0);
        }

        while (upperBound - lowerBound > 1) {
            int toSearch = (upperBound + lowerBound) / 2;
            parents.clear();
            sizes.clear();

            for (int i = 1; i <= cowNum; i++) {  // start the tree
                startTree(i);
            }
            for (Integer[] w : wormholes) {
                if (w[2] >= toSearch) {
                    mergeTrees(w[0], w[1]);
                }
            }

            boolean valid = true;  // check if only using wormholes of this width would be valid
            for (int c : toSort) {
                if (getUltimate(positions.get(c) + 1) != getUltimate(c)) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                lowerBound = toSearch;
            } else {
                upperBound = toSearch;
            }
        }
        written.write(String.format("%s\n", upperBound - 1));
        written.close();
    }

    public static void startTree(int p) {
        parents.put(p, p);
        sizes.put(p, 1);
    }

    public static int getUltimate(int s) {
        if (parents.get(s) == s) {
            return s;
        }
        parents.put(s, getUltimate(parents.get(s)));
        return parents.get(s);
    }

    public static void mergeTrees(int p1, int p2) {
        int tree1 = getUltimate(p1);
        int tree2 = getUltimate(p2);
        if (tree1 != tree2) {
            if (sizes.get(tree1) >= sizes.get(tree2)) {
                parents.put(tree2, tree1); // merge the trees through the root nodes
                sizes.put(tree2, sizes.get(tree1) + sizes.get(tree2));
            } else {
                parents.put(tree1, tree2);
                sizes.put(tree1, sizes.get(tree1) + sizes.get(tree2));
            }
        }
    }
}
