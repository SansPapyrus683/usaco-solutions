import java.io.*;
import java.util.*;

// 2014 us open silver
public class GPSDuel {
    static int intNum;
    static ArrayList<int[]>[] neighbors;
    static ArrayList<int[]>[] reverseNeighbors;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("gpsduel.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        intNum = Integer.parseInt(initial.nextToken());  // intersection num, not like integer num or something
        int roadNum = Integer.parseInt(initial.nextToken());

        neighbors = new ArrayList[intNum];
        reverseNeighbors = new ArrayList[intNum];
        for (int i = 0; i < intNum; i++) {
            neighbors[i] = new ArrayList<>();
            reverseNeighbors[i] = new ArrayList<>();
        }

        for (int r = 0; r < roadNum; r++) {
            StringTokenizer road = new StringTokenizer(read.readLine());
            int from = Integer.parseInt(road.nextToken()) - 1, to = Integer.parseInt(road.nextToken()) - 1;
            int firstDist = Integer.parseInt(road.nextToken());
            int secondDist = Integer.parseInt(road.nextToken());
            neighbors[from].add(new int[] {to, firstDist, secondDist});  // if i try to use a 2d array it's too slow (initialization time)
            reverseNeighbors[to].add(new int[] {from, firstDist, secondDist});
        }

        int[] gps1Dist = fromBarn(true);
        int[] gps2Dist = fromBarn(false);
        int[] complaintNum = new int[intNum];
        PriorityQueue<Integer> frontier = new PriorityQueue<>(Comparator.comparingInt(i -> complaintNum[i]));
        Arrays.fill(complaintNum, Integer.MAX_VALUE);
        complaintNum[0] = 0;
        frontier.add(0);
        while (!frontier.isEmpty()) {  // another dijkstra's lol
            int current = frontier.poll();
            if (current == intNum - 1) {
                break;
            }
            int rnComplaints = complaintNum[current];
            int rnGPS1Dist = gps1Dist[current];
            int rnGPS2Dist = gps2Dist[current];
            for (int[] n : neighbors[current]) {
                int realN = n[0];
                int newComplaints = rnComplaints;
                // add a (karen) complaint if the dist(current, neighbor) + shortestdist(neighbor, end) isn't the supposed optimal
                newComplaints += (rnGPS1Dist < n[1] + gps1Dist[realN] ? 1 : 0) +
                        (rnGPS2Dist < n[2] + gps2Dist[realN] ? 1 : 0);
                if (newComplaints < complaintNum[realN]) {
                    complaintNum[realN] = newComplaints;
                    frontier.add(realN);
                }
            }
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("gpsduel.out"));
        written.println(complaintNum[intNum - 1]);
        written.close();
        System.out.println(complaintNum[intNum - 1]);
        System.out.printf("how does just pressing submit twice get you 2 gps's- it took %d ms anyways%n", System.currentTimeMillis() - start);
    }

    static int[] fromBarn(boolean useGPS1) {  // dijkstra's to find the shortest path from the barn to each of the intersections
        int[] distances = new int[intNum];
        PriorityQueue<Integer> frontier = new PriorityQueue<>(Comparator.comparingInt(i -> distances[i]));
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[intNum - 1] = 0;
        frontier.add(intNum - 1);
        while (!frontier.isEmpty()) {
            int current = frontier.poll();
            int rnCost = distances[current];
            for (int[] n : reverseNeighbors[current]) {
                int newCost = useGPS1 ? rnCost + n[1] : rnCost + n[2];
                if (newCost < distances[n[0]]) {
                    distances[n[0]] = newCost;
                    frontier.add(n[0]);
                }
            }
        }
        return distances;
    }
}
