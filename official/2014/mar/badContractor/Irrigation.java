import java.io.*;
import java.util.*;

// 2014 mar silver
public class Irrigation {
    static final int maxBound = 1001;
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("irrigation.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int[][] fields = new int[Integer.parseInt(initial.nextToken())][2];
        int pipeReq = Integer.parseInt(initial.nextToken());
        for (int f = 0; f < fields.length; f++) {
            StringTokenizer field = new StringTokenizer(read.readLine());
            fields[f] = new int[] {Integer.parseInt(field.nextToken()), Integer.parseInt(field.nextToken())};
        }

        /*
         * this is just prim's algo
         * the difference is that we can only add "valid" distances (distances which >= pipeReq)
         * invalid distances are represented with MAX_VALUE
         */
        int cost = 0;
        boolean[][] used = new boolean[maxBound][maxBound];  // many of them won't be used, but that's ok
        int[][] distToTree = new int[maxBound][maxBound];

        int[] start = fields[0];
        used[start[0]][start[1]] = true;
        distToTree[start[0]][start[1]] = 0;
        for (int[] f : fields) {
            if (!Arrays.equals(start, f)) {
                distToTree[f[0]][f[1]] = cost(start, f) >= pipeReq ? cost(start, f) : Integer.MAX_VALUE;
            }
        }

        for (int f = 0; f < fields.length - 1; f++) {
            int[] toAdd = null;
            for (int[] fi : fields) {
                // no need to check if it was max val or not bc it's max val, it can't be smaller than anything
                if (!used[fi[0]][fi[1]] && (toAdd == null ||
                        (distToTree[fi[0]][fi[1]] < distToTree[toAdd[0]][toAdd[1]]))) {
                    toAdd = fi;
                }
            }

            if (distToTree[toAdd[0]][toAdd[1]] == Integer.MAX_VALUE) {  // if the only thing we found was invalid, it's -1
                cost = -1;
                break;
            }
            used[toAdd[0]][toAdd[1]] = true;
            cost += distToTree[toAdd[0]][toAdd[1]];
            for (int[] fi : fields) {
                if (!used[fi[0]][fi[1]] && cost(toAdd, fi) >= pipeReq) {
                    distToTree[fi[0]][fi[1]] = Math.min(distToTree[fi[0]][fi[1]], cost(fi, toAdd));
                }
            }
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("irrigation.out"));
        written.println(cost);
        written.close();
        System.out.println(cost);
        System.out.printf("yes, my lord. it took %d ms for the code to run.", System.currentTimeMillis() - timeStart);
    }

    static int cost(int[] f1, int[] f2) {
        return (int) (Math.pow(f1[0] - f2[0], 2) + Math.pow(f1[1] - f2[1], 2));
    }
}
