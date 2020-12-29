package official.o2019.dec.gold.soManyFriends;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2019 gold december (it took two whole weeks lol)
// and even then this is rife of bad practices but it works so screw it
public class MilkVisits {
    private static final long MAX_DEPTH = 100000;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("milkvisits.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int farmNum = Integer.parseInt(initial.nextToken());
        int friendNum = Integer.parseInt(initial.nextToken());
        int[] types = Stream.of(read.readLine().split(" ")).mapToInt(m -> Integer.parseInt(m) - 1).toArray();

        char[] happiness = new char[friendNum];
        Arrays.fill(happiness, '0');
        ArrayList<Integer>[] adj = new ArrayList[farmNum];
        for (int i = 0; i < farmNum; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int i = 0; i < farmNum - 1; i++) {
            StringTokenizer path = new StringTokenizer(read.readLine());
            int firstFarm = Integer.parseInt(path.nextToken()) - 1;  // farms start at 1, let's make that 0
            int secondFarm = Integer.parseInt(path.nextToken()) - 1;
            adj[firstFarm].add(secondFarm);
            adj[secondFarm].add(firstFarm);
        }

        ArrayList<int[]>[] nodeQueries = new ArrayList[farmNum];
        for (int i = 0; i < farmNum; i++) {
            nodeQueries[i] = new ArrayList<>();
        }
        int[][] queryLookup = new int[friendNum][2];
        for (int f = 0; f < friendNum; f++) {  // farmer john has more friends than i ever will
            StringTokenizer query = new StringTokenizer(read.readLine());
            int from = Integer.parseInt(query.nextToken()) - 1;
            int to = Integer.parseInt(query.nextToken()) - 1;
            int milk = Integer.parseInt(query.nextToken()) - 1;  // so the types start at 1 too lol
            nodeQueries[from].add(new int[] {f, milk});
            nodeQueries[to].add(new int[] {f, milk});
            queryLookup[f] = new int[] {from, to};
        }

        boolean[] traversed = new boolean[farmNum];
        int[] inTimes = new int[farmNum];
        int[] outTimes = new int[farmNum];
        int movesTaken = 0;
        ArrayDeque<Integer> timeFrontier = new ArrayDeque<>(Collections.singletonList(0));  // start @ node 0
        while (!timeFrontier.isEmpty()) {  // sauce: https://www.geeksforgeeks.org/query-ancestor-descendant-relationship-tree/
            int curr = timeFrontier.removeFirst();
            if (traversed[curr]) {  // this should be that marker (defined below) i think
                outTimes[curr] = movesTaken;
                continue;
            }
            inTimes[curr] = movesTaken;
            timeFrontier.addFirst(curr);  // set a marker to record the outtime
            traversed[curr] = true;
            for (int n : adj[curr]) {
                if (!traversed[n]) {
                    timeFrontier.addFirst(n);
                }
            }
            movesTaken++;
        }

        Arrays.fill(traversed, false);  // reset the traversed array
        ArrayDeque<int[]> frontier = new ArrayDeque<>(Collections.singletonList(new int[] {0, 0}));

        int[] stack = new int[farmNum];
        ArrayList<Long>[] seenTypes = new ArrayList[farmNum];  // longs for like point compression or smth
        for (int i = 0; i < farmNum; i++) {
            seenTypes[i] = new ArrayList<>();
        }
        int lastDepth = -1;

        while (!frontier.isEmpty()) {
            int[] gotten = frontier.removeFirst();
            int current = gotten[0];
            int depth = gotten[1];

            if (depth != lastDepth + 1) {
                for (int i = depth; i < lastDepth + 1; i++) {
                    seenTypes[types[stack[i]]].remove(seenTypes[types[stack[i]]].size() - 1);
                }
            }
            stack[depth] = current;
            seenTypes[types[current]].add(current * MAX_DEPTH + depth);

            for (int[] q : nodeQueries[current]) {
                int qNum = q[0];
                List<Long> ofType = seenTypes[q[1]];
                if (ofType.size() == 0) {
                    continue;
                }
                long haveGotten = ofType.get(ofType.size() - 1);
                int otherFarm = queryLookup[qNum][0] == current ? queryLookup[qNum][1] : queryLookup[qNum][0];

                // check if the below one is the other's ancestor- if it isn't, then we'll def have to visit that node on the way to the other one
                if (haveGotten / MAX_DEPTH == current ||
                        !fatherTest(stack[(int) (haveGotten % MAX_DEPTH + 1)], otherFarm, inTimes, outTimes)) {
                    happiness[qNum] = '1';
                }
            }

            traversed[current] = true;
            for (int c : adj[current]) {
                if (!traversed[c]) {
                    frontier.addFirst(new int[] {c, depth + 1});
                }
            }
            lastDepth = depth;
        }

        PrintWriter written = new PrintWriter("milkvisits.out");
        written.println(new String(happiness));
        written.close();
        System.out.println(new String(happiness));
        System.out.printf("so we finished: it took something like %s milliseconds%n", System.currentTimeMillis() - start);
    }

    static boolean fatherTest(int possDad, int possKid, int[] inTimes, int[] outTimes) {
        return inTimes[possDad] <= inTimes[possKid] && outTimes[possDad] >= outTimes[possKid];
    }
}
