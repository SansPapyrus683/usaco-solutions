/*
ID: kevinsh4
TASK: race3
LANG: JAVA
 */
package section4.part3.race3;

import java.io.*;
import java.util.*;

// this is horribly verbose but it works lol
public class Race3 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("race3.in"));
        ArrayList<int[]> neighbors = new ArrayList<>();
        String line;
        while ((line = read.readLine()) != null) {
            if (line.equals("-1")) {
                break;
            }
            int[] adj = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            adj = Arrays.copyOfRange(adj, 0, adj.length - 1);
            neighbors.add(adj);
        }

        int finishPoint = neighbors.size() - 1;
        RaceTrack track = new RaceTrack(neighbors);
        ArrayList<Integer> mustPass = new ArrayList<>();
        for (int p = 1; p < finishPoint; p++) {
            if (track.necessaryPoint(0, finishPoint, p)) {
                mustPass.add(p);
            }
        }

        ArrayList<Integer> splitPoints = new ArrayList<>();
        for (int p : mustPass) {  // i mean, if a point isn't necessary, it definitely can't be a splitting point
            if (track.splitPossible(0, finishPoint, p)) {
                splitPoints.add(p);
            }
        }

        PrintWriter written = new PrintWriter("race3.out");
        written.println(toStringRep(mustPass));
        written.println(toStringRep(splitPoints));
        written.close();
        System.out.println(mustPass);
        System.out.println(splitPoints);
        System.out.printf("vroom vroom %d ms baby%n", System.currentTimeMillis() - start);
    }

    // this is the weirdest output format ever lol
    private static <T> String toStringRep(Collection<T> coll) {
        StringBuilder ret = new StringBuilder();
        ret.append(coll.size()).append(' ');
        for (T p : coll) {
            ret.append(p).append(' ');
        }
        ret.setLength(ret.length() - 1);
        return ret.toString();
    }
}

class RaceTrack {
    private final ArrayList<int[]> neighbors;
    private final HashSet<Integer> allPoints;
    public RaceTrack(ArrayList<int[]> neighbors) {
        this.neighbors = neighbors;
        allPoints = new HashSet<>();
        for (int p = 0; p < neighbors.size(); p++) {
            allPoints.add(p);
        }
    }

    private HashSet<Integer> allVisited(int start, int end) {
        ArrayDeque<Integer> frontier = new ArrayDeque<>(Collections.singletonList(start));
        HashSet<Integer> visited = new HashSet<>(Collections.singletonList(start));
        while (!frontier.isEmpty()) {
            int curr = frontier.poll();
            if (curr == end) {
                return visited;
            }
            if (neighbors.get(curr) == null) {  // it's a deleted point lol don't do anything
                continue;
            }
            for (int n : neighbors.get(curr)) {
                if (!visited.contains(n)) {
                    visited.add(n);
                    frontier.add(n);
                }
            }
        }
        return visited;
    }

    public boolean necessaryPoint(int start, int end, int pt) {
        int[] backup = neighbors.get(pt);
        neighbors.set(pt, null);
        if (!allVisited(start, end).contains(end)) {
            neighbors.set(pt, backup);
            return true;  // oh no, it seems like without this point we can't reach the end
        }
        neighbors.set(pt, backup);
        return false;
    }

    public boolean splitPossible(int start, int end, int pt) {
        int[] backup = neighbors.get(pt);
        neighbors.set(pt, null);
        // all the points that can be visited on the way to the splitting pt
        HashSet<Integer> toSplit = allVisited(start, end);
        neighbors.set(pt, backup);

        HashSet<Integer> splitToEnd = allVisited(pt, end);
        splitToEnd.remove(pt);
        if (!Collections.disjoint(toSplit, splitToEnd)) {  // they have to be mutually exclusive
            return false;
        }
        toSplit.addAll(splitToEnd);
        return toSplit.equals(allPoints);  // and all points have to be reachable from them
    }
}
