package official.o2015.usopen.gold.friendPath;

import java.io.*;

// 2015 usopen gold
public final class PalPath {
    private static final int MOD = (int) Math.pow(10, 9) + 7;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("palpath.in"));
        int width = Integer.parseInt(read.readLine());
        char[][] farm = new char[width][width];
        for (int r = 0; r < width; r++) {
            farm[r] = read.readLine().toUpperCase().toCharArray();
        }

        // this[i][j] for ans between row i and row j (the distance walked is determined in loop below)
        long[][] subsquareAmts = new long[width][width];
        for (int i = 0; i < width; i++) {
            subsquareAmts[i][i] = 1;
        }
        for (int halfPath = 1; halfPath < width; halfPath++) {
            long[][] biggerSubsquares = new long[width][width];
            for (int topRow = 0; topRow < width - halfPath; topRow++) {
                int leftCol = (width - 1 - halfPath) - topRow;
                int at = 0;  // a counter for how many rows we've gone through
                // go through the points on the other row that are a halfPath's distance away from the middle
                for (int botRow = halfPath; botRow < width; botRow++) {
                    int rightCol = width - 1 - at++;
                    // check if the 2 points are valid and if we can even form a palindrome
                    if ((botRow - topRow) + (rightCol - leftCol) != halfPath * 2
                            || farm[topRow][leftCol] != farm[botRow][rightCol]) {
                        continue;
                    }
                    /*
                     * so for a previous state, we traversed 2 less squares
                     * so now we need to distribute them for the new state- 1 on the top side, 1 on the bottom side
                     * we can do this by expanding a previous column or row amt
                     * but keep in mind that they have to be distributed evenly to preserve palindrome-ness
                     */
                    biggerSubsquares[topRow][botRow] = subsquareAmts[topRow][botRow];
                    biggerSubsquares[topRow][botRow] += subsquareAmts[topRow + 1][botRow - 1];
                    biggerSubsquares[topRow][botRow] += subsquareAmts[topRow + 1][botRow];
                    biggerSubsquares[topRow][botRow] += subsquareAmts[topRow][botRow - 1];
                    biggerSubsquares[topRow][botRow] %= MOD;
                }
            }
            subsquareAmts = biggerSubsquares;
        }

        PrintWriter written = new PrintWriter("palpath.out");
        written.println(subsquareAmts[0][width - 1]);
        written.close();
        System.out.println(subsquareAmts[0][width - 1]);
        System.out.printf("the path is your pal buddy: %d ms%n", System.currentTimeMillis() - start);
    }
}
