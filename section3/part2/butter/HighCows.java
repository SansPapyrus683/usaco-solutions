/*
ID: kevinsh4
TASK: butter
LANG: JAVA
*/
import java.io.*;
import java.util.*;

public class HighCows {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("butter.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int pastNum = Integer.parseInt(initial.nextToken());
        int connectionNum = Integer.parseInt(initial.nextToken());

        ArrayList<Integer> cows = new ArrayList<>();
        ArrayList<Integer>[] neighbors = new ArrayList[pastNum];
        int[][] distances = new int[pastNum][pastNum];
        for (int c = 0; c < cowNum; c++) {
            cows.add(Integer.parseInt(read.readLine()) - 1);
        }
        for (int p = 0; p < pastNum; p++) {
            neighbors[p] = new ArrayList<>();
        }
        for (int c = 0; c < connectionNum; c++) {
            StringTokenizer path = new StringTokenizer(read.readLine());
            int p1 = Integer.parseInt(path.nextToken()) - 1, p2 = Integer.parseInt(path.nextToken()) - 1;
            int distance = Integer.parseInt(path.nextToken());
            distances[p1][p2] = distance;  // too bad no multiple assignment (add both bc bidirectional paths)
            distances[p2][p1] = distance;
            neighbors[p1].add(p2);
            neighbors[p2].add(p1);
        }

        int[][] pairwiseDistances = allFieldDist(neighbors, distances);
        int minButterDist = Integer.MAX_VALUE;
        for (int p = 0; p < pastNum; p++) {  // go through all possible paths
            int thisButterDist = 0;
            for (int c : cows) {
                thisButterDist += pairwiseDistances[p][c];
            }
            minButterDist = Math.min(minButterDist, thisButterDist);
        }
        PrintWriter written = new PrintWriter("butter.out");
        written.println(minButterDist);
        written.close();
        System.out.println(minButterDist);
    }

    // just an ez application of the floyd warshall algo
    static int[][] allFieldDist(ArrayList<Integer>[] neighbors, int[][] distances) {
        int pastNum = distances.length;
        int[][] pairwise = new int[pastNum][pastNum];
        for (int p = 0; p < pastNum; p++) {
            Arrays.fill(pairwise[p], 1234);  // number to prevent integer overflow
            pairwise[p][p] = 0;
            for (int n : neighbors[p]) {
                pairwise[p][n] = distances[p][n];
            }
        }

        for (int i = 0; i < pastNum; i++) {
            for (int j = 0; j < pastNum; j++) {
                for (int k = 0; k < pastNum; k++) {
                    pairwise[j][k] = Math.min(pairwise[j][k], pairwise[j][i] + pairwise[i][k]);
                }
            }
        }
        return pairwise;
    }
}
