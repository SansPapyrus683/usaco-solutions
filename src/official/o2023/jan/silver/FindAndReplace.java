package official.o2023.jan.silver;

import java.io.*;
import java.util.*;

/** 2023 jan silver (input omitted due to length */
public class FindAndReplace {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            int[] from = read.readLine().chars().map(FindAndReplace::codeToNum).toArray();
            int[] to = read.readLine().chars().map(FindAndReplace::codeToNum).toArray();
            assert from.length == to.length;

            int[] mappings = new int[52];  // i think this 52 should be fine
            Arrays.fill(mappings, -1);
            boolean valid = true;
            for (int i = 0; i < from.length; i++) {
                if (mappings[from[i]] != -1 && mappings[from[i]] != to[i]) {
                    valid = false;
                    break;
                }
                mappings[from[i]] = to[i];
            }
            for (int i = 0; i < mappings.length; i++) {
                if (mappings[i] == -1) {
                    mappings[i] = i;
                }
            }
            if (!valid) {
                System.out.println(-1);
                continue;
            }

            int[] cycId = new int[mappings.length];
            Arrays.fill(cycId, -1);
            List<Integer> cycSize = new ArrayList<>();
            for (int i = 0; i < mappings.length; i++) {
                if (cycId[i] != -1) {
                    continue;
                }

                int at = i;
                Map<Integer, Integer> visited = new HashMap<>();
                List<Integer> visOrder = new ArrayList<>();
                do {
                    visited.put(at, visited.size());
                    visOrder.add(at);
                    cycId[at] = -2;
                    at = mappings[at];
                } while (cycId[at] == -1);

                if (cycId[at] == -2) {
                    for (int j : visOrder) {
                        cycId[j] = cycSize.size();  // new id!
                    }
                    cycSize.add(visited.size() - visited.get(at));
                } else {
                    for (int j : visOrder) {
                        cycId[j] = cycId[at];
                    }
                }
            }

            int[] compSize = new int[cycSize.size()];
            for (int id : cycId) {
                compSize[id]++;
            }

            int minReplaces = 0;
            boolean pureCycle = false;
            boolean cycleWithEnds = false;
            for (int i = 0; i < cycSize.size(); i++) {
                if (cycSize.get(i) > 1) {
                    if (compSize[i] == cycSize.get(i)) {
                        pureCycle = true;
                        minReplaces += cycSize.get(i) + 1;
                    } else {
                        cycleWithEnds = true;
                        minReplaces += compSize[i];
                    }
                } else {
                    minReplaces += compSize[i] - 1;
                }
            }

            Set<Integer> fromChars = new HashSet<>();
            for (int c : from) {
                fromChars.add(c);
            }
            valid = fromChars.size() < mappings.length || !pureCycle || cycleWithEnds;
            System.out.println(valid ? minReplaces : -1);
        }
    }

    private static int codeToNum(int c) {
        if ('a' <= c && c <= 'z') {
            return c - 'a';
        } else if ('A' <= c && c <= 'Z') {
            return c - 'A' + 26;
        }
        throw new IllegalArgumentException(String.format("bad char %c", c));
    }
}
