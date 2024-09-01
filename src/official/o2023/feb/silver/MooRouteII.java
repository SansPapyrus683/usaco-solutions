package official.o2023.feb.silver;

import java.io.*;
import java.util.*;

// 2023 feb silver
public class MooRouteII {
    private static class Pair<T1, T2> {
        public T1 first;
        public T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        @Override
        public String toString() {
            return String.format("(%s, %s)", first, second);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int airportNum = Integer.parseInt(initial.nextToken());
        int flightNum = Integer.parseInt(initial.nextToken());
        Map<Integer, TreeMap<Integer, List<Pair<Integer, Integer>>>> flights = new HashMap<>();
        for (int f = 0; f < flightNum; f++) {
            StringTokenizer flight = new StringTokenizer(read.readLine());
            int from = Integer.parseInt(flight.nextToken()) - 1;
            int fromTime = Integer.parseInt(flight.nextToken());
            int to = Integer.parseInt(flight.nextToken()) - 1;
            int toTime = Integer.parseInt(flight.nextToken());
            if (!flights.containsKey(from)) {
                flights.put(from, new TreeMap<>());
            }
            if (!flights.get(from).containsKey(fromTime)) {
                flights.get(from).put(fromTime, new ArrayList<>());
            }
            flights.get(from).get(fromTime).add(new Pair<>(to, toTime));
        }
        int[] layovers = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        assert layovers.length == airportNum;

        final int start = 0;
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        ArrayDeque<Pair<Integer, Integer>> queue = new ArrayDeque<>();
        queue.add(new Pair<>(start, -layovers[start]));  // hack to get past the no initial layover thing
        while (!queue.isEmpty()) {
            Pair<Integer, Integer> curr = queue.poll();
            int at = curr.first;
            int time = curr.second;
            // lol.
            Map<Integer, List<Pair<Integer, Integer>>> avail = flights
                    .getOrDefault(at, new TreeMap<>())
                    .tailMap(time + layovers[at], true);
            for (List<Pair<Integer, Integer>> fLists : avail.values()) {
                for (Pair<Integer, Integer> f : fLists) {
                    if (!visited.contains(f)) {
                        visited.add(f);
                        queue.add(f);
                    }
                }
            }
            // well, no point in taking these flights again
            avail.clear();
        }

        int[] earliest = new int[airportNum];
        Arrays.fill(earliest, Integer.MAX_VALUE);
        earliest[start] = 0;  // yeah, it's kinda stupid
        for (Pair<Integer, Integer> pos : visited) {
            earliest[pos.first] = Math.min(earliest[pos.first], pos.second);
        }

        Arrays.stream(earliest).forEach(t -> System.out.println(t == Integer.MAX_VALUE ? -1 : t));
    }
}
