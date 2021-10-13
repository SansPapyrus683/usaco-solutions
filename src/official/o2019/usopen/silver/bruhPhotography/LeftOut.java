package official.o2019.usopen.silver.bruhPhotography;

import java.io.*;
import java.util.*;

// 2019 us open silver
public final class LeftOut {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("leftout.in"));
        int side = Integer.parseInt(read.readLine());
        // with 1000x1000, we don't need to use arrays lol (nor CAN we i think)
        HashMap<ArrayList<Boolean>, Integer> rowOccurrences = new HashMap<>();
        ArrayList<Boolean>[] cows = new ArrayList[side];
        for (int i = 0; i < side; i++) {
            char[] row = read.readLine().toCharArray();
            ArrayList<Boolean> actualRow = new ArrayList<>();
            for (char c : row) {
                actualRow.add(c == 'R');
            }
            rowOccurrences.put(actualRow, rowOccurrences.getOrDefault(actualRow, 0) + 1);
            cows[i] = actualRow;
        }

        /*
         * logic:
         * ok so the only way we can accomplish the photo by shouting is if all the rows are "inversions" of each other
         * (think it'd work for columns too but whatever) and by inversions i mean you can go from any row to any
         * other row by inverting it, because then you can make them all the same, then invert the columns
         */
        String leftOut = "-1";
        if (rowOccurrences.size() != 3) {  // if != 3, it's either already possible or not possible even if we change a cow
            assert true;
        } else {
            int rowAt = 1;  // the answer is by 1-based indexing, might as well
            searching:
            for (ArrayList<Boolean> row : cows) {  // find the cow by going through rows, then by columns
                if (rowOccurrences.get(row) == 1) {
                    ArrayList<Boolean> tryingRow = new ArrayList<>(row);
                    for (int i = 0; i < side; i++) {
                        tryingRow.set(i, !tryingRow.get(i));
                        if (rowOccurrences.containsKey(tryingRow)) {
                            leftOut = String.format("%s %s", rowAt, i + 1);
                            break searching;
                        }
                        tryingRow.set(i, !tryingRow.get(i));  // revert the change
                    }
                }
                rowAt++;
            }
        }

        PrintWriter written = new PrintWriter("leftout.out");
        written.println(leftOut);
        written.close();
        System.out.println(leftOut);
        System.out.printf("%d ms. need i say more?%n", System.currentTimeMillis() - start);
    }
}
