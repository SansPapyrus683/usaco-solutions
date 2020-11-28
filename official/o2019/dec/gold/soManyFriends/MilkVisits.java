import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2019 gold december (it took two whole weeks lol)
// and even then this is rife of bad practices but it works so screw it
public class MilkVisits {
    private static final long MAX_DEPTH = 100000;
    static int farmNum;
    static int friendNum;
    static int[] types;
    static ArrayList<Integer>[] adj;
    static int[] inTimes;
    static int[] outTimes;
    static ArrayList<int[]>[] nodeQueries;
    static int[][] queryLookup;
    static char[] happiness;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("milkvisits.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        farmNum = Integer.parseInt(initial.nextToken());
        friendNum = Integer.parseInt(initial.nextToken());
        types = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        happiness = new char[friendNum];
        Arrays.fill(happiness, '0');
        adj = new ArrayList[farmNum + 1];
        for (int i = 1; i < farmNum + 1; i++) {  // we won't use index 0 because the farms start @ 1
            adj[i] = new ArrayList<>();
        }
        inTimes = new int[farmNum + 1];  // still, we won't use the first index lol
        outTimes = new int[farmNum + 1];

        for (int i = 0; i < farmNum - 1; i++) {
            StringTokenizer path = new StringTokenizer(read.readLine());
            int firstFarm = Integer.parseInt(path.nextToken());
            int secondFarm = Integer.parseInt(path.nextToken());
            adj[firstFarm].add(secondFarm);
            adj[secondFarm].add(firstFarm);
        }

        nodeQueries = new ArrayList[farmNum + 1];
        for (int i = 1; i < farmNum + 1; i++) {
            nodeQueries[i] = new ArrayList<>();
        }
        queryLookup = new int[friendNum][2];
        for (int fn = 0; fn < friendNum; fn++) {  // farmer john has more friends than i ever will
            StringTokenizer query = new StringTokenizer(read.readLine());
            int from = Integer.parseInt(query.nextToken());
            int to = Integer.parseInt(query.nextToken());
            int milk = Integer.parseInt(query.nextToken());
            nodeQueries[from].add(new int[] {fn, milk});
            nodeQueries[to].add(new int[] {fn, milk});
            queryLookup[fn] = new int[] {from, to};
        }
        traverseRecordTimes(1);

        actualSol(1);
        PrintWriter written = new PrintWriter("milkvisits.out");
        written.printf("%s%n", new String(happiness));
        written.close();
        System.out.println(new String(happiness));
        System.out.printf("so we finished: it took something like %s milliseconds %n", System.currentTimeMillis() - start);
    }

    private static void traverseRecordTimes(int startNode) {
        int movesTaken = 0;
        HashSet<Integer> traversed = new HashSet<>();
        LinkedList<Integer> frontier = new LinkedList<>();  // use it for its queue capability
        frontier.add(startNode);

        while (!frontier.isEmpty()) {
            int current = frontier.removeFirst();
            if (traversed.contains(current)) {  // this should be that marker i think
                outTimes[current] = movesTaken;
                continue;
            }

            inTimes[current] = movesTaken;
            frontier.addFirst(current);  // set a marker to record the outtime
            traversed.add(current);
            for (int c : adj[current]) {
                if (!traversed.contains(c)) {
                    frontier.addFirst(c);
                }
            }
            movesTaken++;
        }
    }

    private static boolean fatherTest(int possDad, int possKid) {
        return inTimes[possDad] <= inTimes[possKid] && outTimes[possDad] >= outTimes[possKid];
    }

    private static void actualSol(int startNode) {
        HashSet<Integer> traversed = new HashSet<>();
        LinkedList<int[]> frontier = new LinkedList<>();
        frontier.add(new int[] {startNode, 0});

        int[] stack = new int[farmNum];
        ArrayList<Long>[] seenTypes = new ArrayList[farmNum + 1];
        for (int i = 0; i < farmNum + 1; i++) {
            seenTypes[i] = new ArrayList<>();
        }
        int lastDepth = -1;

        while (!frontier.isEmpty()) {
            int[] gotten = frontier.removeFirst();
            int current = gotten[0];
            int depth = gotten[1];

            if (depth != lastDepth + 1) {
                for (int i = depth; i < lastDepth + 1; i++) {
                    seenTypes[types[stack[i] - 1]].remove(seenTypes[types[stack[i] - 1]].size() - 1);
                }
            }
            stack[depth] = current;
            seenTypes[types[current - 1]].add(current * MAX_DEPTH + depth);

            for (int[] got : nodeQueries[current]) {
                int qNum = got[0];
                List<Long> theGot = seenTypes[got[1]];
                if (theGot.size() > 0) {
                    long haveGotten = theGot.get(theGot.size() - 1);  // see the seenTypes.get()... above for info
                    int otherFarm = queryLookup[qNum][0] == current ? queryLookup[qNum][1] : queryLookup[qNum][0];
                    // check if the below one is the other's ancestor- if it isn't, then we'll def have to visit that node on the way to the other one
                    if (haveGotten / MAX_DEPTH == current || !fatherTest(stack[(int) (haveGotten % MAX_DEPTH + 1)], otherFarm)) {
                        happiness[qNum] = '1';
                    }
                }
            }

            traversed.add(current);
            for (int c : adj[current]) {
                if (!traversed.contains(c)) {
                    frontier.addFirst(new int[] {c, depth + 1});
                }
            }
            lastDepth = depth;
        }
    }
}
