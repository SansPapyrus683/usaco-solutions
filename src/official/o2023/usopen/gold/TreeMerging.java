package official.o2023.usopen.gold;

import java.io.*;
import java.util.*;

/** 2023 us open gold (read editorial screw this) */
public class TreeMerging {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            int n = Integer.parseInt(read.readLine());

            Set<Integer> root = new HashSet<>();
            List<Integer>[] kids = new List[n];
            List<Integer>[] newKids = new List[n];
            for (int i = 0; i < n; i++) {
                kids[i] = new ArrayList<>();
                newKids[i] = new ArrayList<>();
                root.add(i);
            }
            for (int e = 0; e < n - 1; e++) {
                StringTokenizer edge = new StringTokenizer(read.readLine());
                int kid = Integer.parseInt(edge.nextToken()) - 1;
                int parent = Integer.parseInt(edge.nextToken()) - 1;
                kids[parent].add(kid);
                root.remove(kid);
            }

            boolean[] preserved = new boolean[n];
            int newN = Integer.parseInt(read.readLine());
            for (int e = 0; e < newN - 1; e++) {
                StringTokenizer edge = new StringTokenizer(read.readLine());
                int kid = Integer.parseInt(edge.nextToken()) - 1;
                int parent = Integer.parseInt(edge.nextToken()) - 1;
                newKids[parent].add(kid);
                preserved[parent] = preserved[kid] = true;
            }

            List<List<Integer>> levels = new ArrayList<>();
            List<Integer> curr = new ArrayList<>(List.of(root.iterator().next()));
            while (!curr.isEmpty()) {
                levels.add(curr);
                List<Integer> next = new ArrayList<>();
                for (int i : curr) {
                    next.addAll(kids[i]);
                }
                curr = next;
            }

            boolean[][] shouldMerge = new boolean[n][n];
            Collections.reverse(levels);
            for (List<Integer> l : levels) {
                for (int into : l) {
                    if (!preserved[into]) {
                        continue;
                    }
                    shouldMerge[into][into] = true;
                    for (int bad : l) {
                        if (preserved[bad] || bad > into) {
                            continue;
                        }

                        shouldMerge[bad][into] = true;
                        for (int bk : kids[bad]) {
                            boolean coexist = false;
                            for (int ik : newKids[into]) {
                                if (shouldMerge[bk][ik]) {
                                    coexist = true;
                                    break;
                                }
                            }
                            if (!coexist) {
                                shouldMerge[bad][into] = false;
                                break;
                            }
                        }
                    }
                }
            }

            System.out.println(n - newN);
            List<Set<Integer>> merging = new ArrayList<>();
            merging.add(new HashSet<>(levels.get(levels.size() - 1)));
            while (!merging.isEmpty()) {
                List<Set<Integer>> next = new ArrayList<>();
                for (Set<Integer> set : merging) {
                    Set<Integer> dead = new HashSet<>();
                    for (int i : set) {
                        if (!preserved[i]) {
                            continue;
                        }
                        Set<Integer> newSet = new HashSet<>(kids[i]);
                        for (int j : set) {
                            if (shouldMerge[j][i] && !dead.contains(j)) {
                                if (i != j) {
                                    System.out.printf("%d %d%n", j + 1, i + 1);
                                }
                                dead.add(j);
                                newSet.addAll(kids[j]);
                            }
                        }
                        next.add(newSet);
                    }
                }
                merging = next;
            }
        }
    }
}
