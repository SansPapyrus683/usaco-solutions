package official.o2022.usopen.silver;

import java.io.*;
import java.util.*;

/**
 * 2022 us open silver
 * 4
 * 2 10
 * 3 20
 * 4 30
 * 1 40 should output 90
 */
public final class Visits {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());
        int[] goTo = new int[cowNum];
        int[] mooNum = new int[cowNum];
        for (int c = 0; c < cowNum; c++) {
            StringTokenizer cow = new StringTokenizer(read.readLine());
            goTo[c] = Integer.parseInt(cow.nextToken()) - 1;
            mooNum[c] = Integer.parseInt(cow.nextToken());
        }

        long maxMoos = 0;
        for (int m : mooNum) {
            maxMoos += m;
        }

        boolean[] visited = new boolean[cowNum];
        for (int c = 0; c < cowNum; c++) {
            if (visited[c]) {
                continue;
            }
            ArrayList<Integer> journey = new ArrayList<>(Collections.singleton(c));
            while (!visited[journey.get(journey.size() - 1)]) {
                int curr = journey.get(journey.size() - 1);
                visited[curr] = true;
                journey.add(goTo[curr]);
            }

            int last = journey.get(journey.size() - 1);
            for (int i = 0; i < journey.size() - 1; i++) {
                if (journey.get(i) == last) {  // ok this is actually a cycle
                    int min = mooNum[journey.get(i)];
                    for (int j = i + 1; j < journey.size() - 1; j++) {
                        min = Math.min(min, mooNum[journey.get(j)]);
                    }
                    maxMoos -= min;
                    break;
                }
            }
        }

        System.out.println(maxMoos);
    }
}
