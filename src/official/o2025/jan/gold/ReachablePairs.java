package official.o2025.jan.gold;

import java.io.*;
import java.util.*;

/** 2025 jan gold */
public class ReachablePairs {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int nodeNum = Integer.parseInt(initial.nextToken());
        int edgeNum = Integer.parseInt(initial.nextToken());
        String removals = read.readLine();
        List<Integer>[] neighbors = new List[nodeNum];
        List<Integer>[] newNeighbors = new List[nodeNum];
        for (int n = 0; n < nodeNum; n++) {
            neighbors[n] = new ArrayList<>();
            newNeighbors[n] = new ArrayList<>();
        }
        for (int e = 0; e < edgeNum; e++) {
            StringTokenizer edge = new StringTokenizer(read.readLine());
            int from = Integer.parseInt(edge.nextToken()) - 1;
            int to = Integer.parseInt(edge.nextToken()) - 1;
            neighbors[from].add(to);
            neighbors[to].add(from);
        }

        boolean[] visited = new boolean[nodeNum];
        for (int n = 0; n < nodeNum; n++) {
            if (visited[n] || removals.charAt(n) == '0') {
                continue;
            }
            visited[n] = true;
            Set<Integer> conn = new HashSet<>(neighbors[n]);
            Deque<Integer> frontier = new ArrayDeque<>();
            frontier.push(n);
            while (!frontier.isEmpty()) {
                int curr = frontier.poll();
                for (int next : neighbors[curr]) {
                    if (!visited[next] && removals.charAt(next) == '1') {
                        frontier.push(next);
                        visited[next] = true;
                        conn.addAll(neighbors[next]);
                    }
                }
            }

            if (!conn.isEmpty()) {
                int max = conn.stream().max(Integer::compareTo).get();
                for (int i : conn) {
                    if (i != max) {
                        newNeighbors[i].add(max);
                    }
                }
            }
        }
        for (int n = 0; n < nodeNum; n++) {
            neighbors[n].addAll(newNeighbors[n]);
        }

        long reachablePairs = 0;
        DisjointSets dsu = new DisjointSets(nodeNum);
        List<Long> rawAns = new ArrayList<>();
        for (int add = nodeNum - 1; add >= 0; add--) {
            for (int n : neighbors[add]) {
                long nSize = dsu.getSize(n);  // avoid stupid casting down there
                long mySize = dsu.getSize(add);
                if (n > add && dsu.link(n, add)) {
                    reachablePairs -= nSize * (nSize - 1) / 2 + mySize * (mySize - 1) / 2;
                    long newSize = dsu.getSize(add);
                    reachablePairs += newSize * (newSize - 1) / 2;
                }
            }
            rawAns.add(reachablePairs);
        }

        StringBuilder ans = new StringBuilder();
        Collections.reverse(rawAns);
        rawAns.forEach(i -> ans.append(i).append('\n'));
        System.out.print(ans);
    }
}

class DisjointSets {
    private final int[] parents;
    private final int[] sizes;

    public DisjointSets(int size) {
        parents = new int[size];
        sizes = new int[size];
        for (int i = 0; i < size; i++) {
            parents[i] = i;
            sizes[i] = 1;
        }
    }

    public int getSize(int n) {
        return sizes[getTop(n)];
    }

    public int getTop(int n) {
        return parents[n] == n ? n : (parents[n] = getTop(parents[n]));
    }

    public boolean link(int e1, int e2) {
        if ((e1 = getTop(e1)) == (e2 = getTop(e2))) {
            return false;
        }
        if (sizes[e2] > sizes[e1]) {
            return link(e2, e1);
        }
        parents[e2] = e1;
        sizes[e1] += sizes[e2];
        return true;
    }
}
