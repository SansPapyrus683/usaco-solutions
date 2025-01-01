package official.o2022.dec.bronze;

import java.io.*;
import java.util.*;

/** 2022 dec bronze */
public class FeedingTheCows {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            StringTokenizer initial = new StringTokenizer(read.readLine());
            int cowNum = Integer.parseInt(initial.nextToken());
            int maxDist = Integer.parseInt(initial.nextToken());
            String cows = read.readLine();
            List<Integer> guernseys = new ArrayList<>();
            List<Integer> holsteins = new ArrayList<>();
            for (int c = 0; c < cowNum; c++) {
                if (cows.charAt(c) == 'G') {
                    guernseys.add(c);
                } else if (cows.charAt(c) == 'H') {
                    holsteins.add(c);
                }
            }

            char[] config = new char[cowNum];
            Arrays.fill(config, '.');
            int gLast = guernseys.isEmpty() ? -1 : guernseys.get(0);
            int hLast = holsteins.isEmpty() ? -1 : holsteins.get(0);
            for (int c = 0; c < cowNum; c++) {
                if (cows.charAt(c) == 'G') {
                    if (c > gLast + 2 * maxDist) {
                        config[gLast + maxDist] = 'G';
                        gLast = c;
                    }
                } else if (cows.charAt(c) == 'H') {
                    if (c > hLast + 2 * maxDist) {
                        config[hLast + maxDist] = 'H';
                        hLast = c;
                    }
                }
            }

            // if you remove these two if statements half the crap breaks
            // like ik why but it's insane that so many tcs have all Gs or all Hs
            if (!guernseys.isEmpty()) {
                int gFinal = Math.min(cowNum - 1, gLast + maxDist);
                config[gFinal] = 'G';
            }
            if (!holsteins.isEmpty()) {
                int hFinal = Math.min(cowNum - 1, hLast + maxDist);
                while (config[hFinal] == 'G') {
                    hFinal--;
                }
                config[hFinal] = 'H';
            }

            int totalPatches = 0;
            for (char c : config) {
                totalPatches += c == '.' ? 0 : 1;
            }

            System.out.println(totalPatches);
            System.out.println(new String(config));
        }
    }
}
