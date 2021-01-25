package official.o2014.jan.silver.fjGetTheCamera;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2014 jan silver
public final class Recording {
    private static final class PArrangement {  // i could just make a generic Pair<> final class but screw it
        public ArrayList<Integer> first;
        public ArrayList<Integer> second;
        public PArrangement(ArrayList<Integer> first, ArrayList<Integer> second) {
            this.first = first;
            this.second = second;
        }

        public PArrangement(PArrangement toCopy) {
            this.first = new ArrayList<>(toCopy.first);
            this.second = new ArrayList<>(toCopy.second);
        }

        @Override
        public String toString() {
            return String.format("PArrangement{A: %s, B: %s}", first, second);
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("recording.in"));
        int[][] events = new int[Integer.parseInt(read.readLine()) + 1][2];
        for (int i = 1; i < events.length; i++) {
            events[i] = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(events, (a, b) -> {
            if (a[1] != b[1]) return a[1] - b[1];  // compare ending times first then compare starting
            return a[0] - b[0];
        });
        events[0] = null;  // just making it explicit here (events start at 1 to accommodate the dp array)

        PArrangement[] optimal = new PArrangement[events.length];
        optimal[0] = new PArrangement(new ArrayList<>(), new ArrayList<>());  // at 0 there's nothing i mean
        for (int i = 1; i < events.length; i++) {
            int[] toAdd = events[i];
            int best = 0;
            PArrangement soFar = null;  // the best arrangement so far
            for (int p = 0; p < i; p++) {
                PArrangement prevOptimal = optimal[p];
                ArrayList<Integer> theirFirst = prevOptimal.first;
                ArrayList<Integer> theirSecond = prevOptimal.second;
                PArrangement ours = new PArrangement(prevOptimal);  // make a copy so we don't change the original

                if ((theirFirst.isEmpty() || events[theirFirst.get(theirFirst.size() - 1)][1] <= toAdd[0]) ||
                        (theirSecond.isEmpty() || events[theirSecond.get(theirSecond.size() - 1)][1] <= toAdd[0])) {

                    // if it's empty, make it max because it's possible- just don't do it if we can still add to the occupied one
                    int firstAhead = !theirFirst.isEmpty() ? toAdd[0] - events[theirFirst.get(theirFirst.size() - 1)][1] : Integer.MAX_VALUE;
                    int secondAhead = !theirSecond.isEmpty() ? toAdd[0] - events[theirSecond.get(theirSecond.size() - 1)][1] : Integer.MAX_VALUE;
                    if (firstAhead < 0) {  // i mean if one isn't valid we have to add to the other one
                        ours.second.add(i);
                    } else if (secondAhead < 0) {
                        ours.first.add(i);
                    } else {
                        if (firstAhead < secondAhead) {  // make use of space as efficiently as possible
                            ours.first.add(i);
                        } else {
                            ours.second.add(i);
                        }
                    }
                }

                int recorded = ours.first.size() + ours.second.size();
                if (recorded > best) {
                    best = recorded;
                    soFar = ours;
                } else if (recorded == best) {  // if it's the same but it makes more efficient use of space, replace the old one
                    assert soFar != null;
                    int ourOtherEnd = events[ours.first.get(ours.first.size() - 1)][1] != toAdd[1] ? events[ours.first.get(ours.first.size() - 1)][1]
                            : events[ours.second.get(ours.second.size() - 1)][1];
                    int theirOtherEnd = events[soFar.first.get(soFar.first.size() - 1)][1] != toAdd[1] ? events[soFar.first.get(soFar.first.size() - 1)][1]
                            : events[soFar.second.get(soFar.second.size() - 1)][1];
                    soFar = ourOtherEnd < theirOtherEnd ? ours : soFar;
                }
            }
            optimal[i] = soFar;
        }

        // all the best will always accumulate in the end
        int answer = optimal[optimal.length - 1].first.size() + optimal[optimal.length - 1].second.size();
        PrintWriter written = new PrintWriter(new FileOutputStream("recording.out"));
        written.println(answer);
        written.close();
        System.out.println(answer);
        System.out.printf("IT TOOK %d MS AND THE WORLD RECORD HAS- wait it's still intact nvm%n", System.currentTimeMillis() - start);
    }
}
