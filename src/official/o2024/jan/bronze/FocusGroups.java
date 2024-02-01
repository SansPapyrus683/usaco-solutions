package official.o2024.jan.bronze;

import java.io.*;
import java.util.*;

/**
 * 2024 jan bronze
 * 5
 * 5
 * 1 2 2 2 3
 * 6
 * 1 2 3 1 2 3
 * 6
 * 1 1 1 2 2 2
 * 3
 * 3 2 3
 * 2
 * 2 1 should output 2, -1, 1 2, 3, and -1, each on a new line
 */
public class FocusGroups {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            int cowNum = Integer.parseInt(read.readLine());
            Map<Integer, List<Integer>> hayInds = new TreeMap<>();
            StringTokenizer cowST = new StringTokenizer(read.readLine());
            for (int c = 0; c < cowNum; c++) {
                int hay = Integer.parseInt(cowST.nextToken());
                if (!hayInds.containsKey(hay)) {
                    hayInds.put(hay, new ArrayList<>());
                }
                hayInds.get(hay).add(c);
            }

            StringBuilder likeable = new StringBuilder();
            for (Map.Entry<Integer, List<Integer>> h : hayInds.entrySet()) {
                List<Integer> inds = h.getValue();
                for (int i = 1; i < inds.size(); i++) {
                    if (inds.get(i - 1) + 2 >= inds.get(i)) {
                        likeable.append(h.getKey()).append(' ');
                        break;
                    }
                }
            }

            if (likeable.isEmpty()) {
                System.out.println(-1);
            } else {
                likeable.setLength(likeable.length() - 1);
                System.out.println(likeable);
            }
        }
    }
}
