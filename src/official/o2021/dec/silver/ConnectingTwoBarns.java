package official.o2021.dec.silver;

import java.io.*;
import java.util.*;

/** 2022 dec silver */
public class ConnectingTwoBarns {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            StringTokenizer initial = new StringTokenizer(read.readLine());
            int fieldNum = Integer.parseInt(initial.nextToken());
            int pathNum = Integer.parseInt(initial.nextToken());
            List<Integer>[] neighbors = new ArrayList[fieldNum];
            for (int f = 0; f < fieldNum; f++) {
                neighbors[f] = new ArrayList<>();
            }
            for (int p = 0; p < pathNum; p++) {
                StringTokenizer path = new StringTokenizer(read.readLine());
                int from = Integer.parseInt(path.nextToken()) - 1;
                int to = Integer.parseInt(path.nextToken()) - 1;
                neighbors[from].add(to);
                neighbors[to].add(from);
            }

            int[] comp = new int[fieldNum];
            Arrays.fill(comp, -1);
            int compNUm = 0;
            for (int f = 0; f < fieldNum; f++) {
                if (comp[f] != -1) {
                    continue;
                }
                comp[f] = compNUm;
                Deque<Integer> frontier = new ArrayDeque<>();
                frontier.add(f);
                while (!frontier.isEmpty()) {
                    int curr = frontier.poll();
                    for (int n : neighbors[curr]) {
                        if (comp[n] == -1) {
                            comp[n] = compNUm;
                            frontier.add(n);
                        }
                    }
                }
                compNUm++;
            }

            long[] start = new long[compNUm];
            long[] end = new long[compNUm];
            int last0 = -1;
            int lastN = -1;
            Arrays.fill(start, Long.MAX_VALUE);
            Arrays.fill(end, Long.MAX_VALUE);

            // lol this is so cursed (go from 0 to fieldNum - 1 and then in reverse)
            List<Integer> order = new ArrayList<>();
            for (int f = 0; f < fieldNum; f++) {
                order.add(f);
            }
            for (int f = fieldNum - 1; f >= 0; f--) {
                order.add(f);
            }
            for (int f : order) {
                if (comp[f] == comp[0]) {
                    last0 = f;
                }
                if (comp[f] == comp[fieldNum - 1]) {
                    lastN = f;
                }
                start[comp[f]] = Math.min(start[comp[f]], (long) (f - last0) * (f - last0));
                if (lastN != -1) {
                    end[comp[f]] = Math.min(
                            end[comp[f]], (long) (f - lastN) * (f - lastN)
                    );
                }
            }

            long best = Long.MAX_VALUE;
            for (int c = 0; c < compNUm; c++) {
                best = Math.min(best, start[c] + end[c]);
            }
            System.out.println(best);
        }
    }
}
