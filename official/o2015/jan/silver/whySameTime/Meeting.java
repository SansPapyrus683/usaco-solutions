import java.io.*;
import java.util.*;

// 2015 jan silver
public class Meeting {
    static ArrayList<Integer>[] neighbors;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("meeting.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int fieldNum = Integer.parseInt(initial.nextToken());
        int pathNum = Integer.parseInt(initial.nextToken());
        neighbors = new ArrayList[fieldNum];
        int[][] bessieTimes = new int[fieldNum][fieldNum];
        int[][] elsieTimes = new int[fieldNum][fieldNum];
        for (int f = 0; f < fieldNum; f++) {
            neighbors[f] = new ArrayList<>();
        }

        for (int p = 0; p < pathNum; p++) {
            StringTokenizer path = new StringTokenizer(read.readLine());
            int from = Integer.parseInt(path.nextToken()) - 1;
            int to = Integer.parseInt(path.nextToken()) - 1;
            neighbors[from].add(to);
            bessieTimes[from][to] = Integer.parseInt(path.nextToken());
            elsieTimes[from][to] = Integer.parseInt(path.nextToken());
        }

        ArrayList<Integer> bessiePoss = allFieldTimes(bessieTimes);
        HashSet<Integer> elsiePoss = new HashSet<>(allFieldTimes(elsieTimes));
        Collections.sort(bessiePoss);
        String answer = "IMPOSSIBLE";
        for (int t : bessiePoss) {
            if (elsiePoss.contains(t)) {
                answer = Integer.toString(t);
                break;
            }
        }

        PrintWriter written = new PrintWriter("meeting.out");
        written.println(answer);
        written.close();
        System.out.println(answer);
        System.out.printf("well crap it took %s ms%n", System.currentTimeMillis() - start);
    }

    static ArrayList<Integer> allFieldTimes(int[][] edgeTimes) {
        ArrayDeque<int[]> frontier = new ArrayDeque<>();
        HashSet<Integer>[] allTimes = new HashSet[edgeTimes.length];
        frontier.add(new int[]{0, 0});  // first is position, second is time
        for (int t = 0; t < edgeTimes.length; t++) {
            allTimes[t] = new HashSet<>();
        }

        while (!frontier.isEmpty()) {
            int[] current = frontier.poll();
            int rnPos = current[0];
            int rnTime = current[1];
            for (int n : neighbors[rnPos]) {
                if (!allTimes[n].contains(rnTime + edgeTimes[rnPos][n])) {
                    allTimes[n].add(rnTime + edgeTimes[rnPos][n]);
                    frontier.add(new int[]{n, rnTime + edgeTimes[rnPos][n]});
                }
            }
        }

        return new ArrayList<>(allTimes[edgeTimes.length - 1]);
    }
}
