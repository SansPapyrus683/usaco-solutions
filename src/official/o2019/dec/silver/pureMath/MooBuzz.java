package official.o2019.dec.silver.pureMath;

import java.io.*;

// 2019 dec silver (i swear this problem is just pure math)
public class MooBuzz {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        int toFind = Integer.parseInt(new BufferedReader(new FileReader("moobuzz.in")).readLine());
        int lowerBound = 0;
        int upperBound = Integer.MAX_VALUE;
        while (lowerBound < upperBound) {  // simple bin search (but for first occurrence, copied from SO)
            int toSearch = lowerBound / 2 + upperBound / 2;  // individually as to prevent integer overflow
            int searchedValue = numbersSaid(toSearch);
            if (searchedValue < toFind) {
                lowerBound = toSearch + 1;
            } else if (searchedValue > toFind) {
                upperBound = toSearch - 1;
            } else if (lowerBound != toSearch) {
                upperBound = toSearch;
            } else {
                break;
            }
        }

        PrintWriter written = new PrintWriter("moobuzz.out");
        written.println(lowerBound);
        written.close();
        System.out.println(lowerBound);
        System.out.printf("the fizz is mooing and the buzz is mooing: %d ms%n", System.currentTimeMillis() - start);
    }

    private static int numbersSaid(int upTo) {  // upTo is inclusive
        return upTo - upTo / 3 - upTo / 5 + upTo / 15;
    }
}
