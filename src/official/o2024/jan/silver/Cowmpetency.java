package official.o2024.jan.silver;

import java.io.*;
import java.util.*;

/**
 * 2024 jan silver
 * 1
 * 7 3 5
 * 1 0 2 3 0 4 0
 * 1 2
 * 3 4
 * 4 5 should output
 * 1 2 2 3 4 4 1
 */
public class Cowmpetency {
    enum LastRel { SAME, GT, IDK }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            StringTokenizer initial = new StringTokenizer(read.readLine());
            int cowNum = Integer.parseInt(initial.nextToken());
            int memNum = Integer.parseInt(initial.nextToken());
            int maxScore = Integer.parseInt(initial.nextToken());  // min is 1 btw
            int[] scores = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int[][] mems = new int[memNum][];
            for (int m = 0; m < memNum; m++) {
                mems[m] = Arrays.stream(read.readLine().split(" "))
                        .mapToInt(i -> Integer.parseInt(i) - 1).toArray();
            }

            LastRel[] rels = new LastRel[cowNum];
            Arrays.fill(rels, LastRel.IDK);
            boolean valid = true;
            for (int[] m : mems) {
                for (int i = m[0] + 1; i < m[1]; i++) {
                    if (rels[i] == LastRel.GT) {
                        valid = false;  // can't be bothered to include break statements here
                    }
                    rels[i] = LastRel.SAME;
                }
                if (rels[m[1]] == LastRel.SAME) {
                    valid = false;
                }
                rels[m[1]] = LastRel.GT;
            }
            if (!valid) {
                System.out.println(-1);
                continue;
            }

            int changeable = -1;
            int currMax = 1;
            fillUp:
            for (int c = 0; c < cowNum; c++) {
                if (scores[c] == 0) {
                    switch (rels[c]) {
                        case SAME:
                            scores[c] = 1;
                            break ;
                        case IDK:
                            scores[c] = 1;
                            changeable = c;
                            break;
                        case GT:
                            scores[c] = currMax + 1;
                            changeable = c;
                            break;
                    }
                } else {
                    switch (rels[c]) {
                        case SAME:
                            if (scores[c] <= currMax) {
                                break;
                            }
                            if (changeable == -1) {
                                valid = false;
                                break fillUp;
                            }
                            scores[changeable] = scores[c];
                            break;
                        case GT:
                            if (scores[c] <= currMax) {
                                valid = false;
                                break fillUp;
                            }
                            changeable = -1;
                            break;
                    }
                }
                currMax = Math.max(currMax, scores[c]);
            }

            valid = valid && currMax <= maxScore;
            if (valid) {
                StringBuilder ans = new StringBuilder();
                for (int s : scores) {
                    ans.append(s).append(' ');
                }
                ans.setLength(ans.length() - 1);
                System.out.println(ans);
            } else {
                System.out.println(-1);
            }
        }
    }
}
