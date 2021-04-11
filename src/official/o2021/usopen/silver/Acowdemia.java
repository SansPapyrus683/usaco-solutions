package official.o2021.usopen.silver;

import java.io.*;
import java.util.*;

/**
 * 2021 usopen silver
 * 4 4 1
 * 1 100 1 1 should output 3
 */
public final class Acowdemia {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int paperNum = Integer.parseInt(initial.nextToken());
        int surveyNum = Integer.parseInt(initial.nextToken());
        int surveyCitations = Integer.parseInt(initial.nextToken());
        int[] papers = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        if (papers.length != paperNum) {
            throw new IllegalArgumentException("the amount of papers you gave me is inconsistent frick you");
        }
        int lo = 0;
        int hi = paperNum;
        int valid = 0;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (scoreReachable(papers, mid, surveyNum, surveyCitations)) {
                valid = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        System.out.println(valid);
        System.err.printf("%d milli-AACHHOOO-sorry- seconds%n", System.currentTimeMillis() - start);
    }

    private static boolean scoreReachable(int[] papers, int score, int surveyNum, int surveyCitations) {
        List<Integer> belowScore = new ArrayList<>();
        int papersLeft = score;  // the amount of papers that we still need to meet the score threshold
        for (int p : papers) {
            if (p >= score) {
                papersLeft--;
            } else {
                belowScore.add(score - p);
            }
        }
        if (papersLeft <= 0) {
            return true;
        }
        belowScore.sort(Comparator.comparingInt(p -> p));

        long neededCites = 0;
        for (int p = 0; p < papersLeft; p++) {
            neededCites += belowScore.get(p);
        }
        /*
         * we 1. need enough citations to actually cover all the papers and
         * 2. need to have enough surveys to get the largest hole up to par since we can only cite it once per survey
         */
        return ((long) Math.min(surveyCitations, papersLeft) * surveyNum >= neededCites
                && belowScore.get(papersLeft - 1) <= surveyNum);
    }
}
