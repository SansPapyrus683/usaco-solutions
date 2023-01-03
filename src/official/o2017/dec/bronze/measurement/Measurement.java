package official.o2017.dec.bronze.measurement;

import java.io.*;
import java.util.*;

public class Measurement {
    private static class Update {
        public int date;
        public String cow;
        public int change;

        public Update(int date, String cow, int change) {
            this.date = date;
            this.cow = cow;
            this.change = change;
        }

        @Override
        public String toString() {
            return String.format("Update{date=%d, cow=%s, change=%d}", date, cow, change);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("measurement.in"));
        int updateNum = Integer.parseInt(read.readLine());
        Update[] updates = new Update[updateNum];
        for (int u = 0; u < updateNum; u++) {
            StringTokenizer update = new StringTokenizer(read.readLine());
            updates[u] = new Update(
                    Integer.parseInt(update.nextToken()),
                    update.nextToken().toLowerCase(),
                    Integer.parseInt(update.nextToken())
            );
        }
        Arrays.sort(updates, Comparator.comparingInt(u -> u.date));

        HashMap<String, Integer> cows = new HashMap<>() {{
            put("bessie", 7);
            put("elsie", 7);
            put("mildred", 7);
        }};
        Set<String> best = bestCows(cows);
        int changeNum = 0;
        for (Update u : updates) {
            cows.put(u.cow, cows.get(u.cow) + u.change);
            Set<String> newBest = bestCows(cows);
            if (!newBest.equals(best)) {
                changeNum++;
            }
            best = newBest;
        }

        PrintWriter written = new PrintWriter("measurement.out");
        written.println(changeNum);
        written.close();
    }

    public static Set<String> bestCows(Map<String, Integer> cows) {
        HashSet<String> res = new HashSet<>();
        int mostMilk = cows.values().stream().max(Comparator.comparingInt(i -> i)).get();
        for (Map.Entry<String, Integer> c : cows.entrySet()) {
            if (c.getValue() == mostMilk) {
                res.add(c.getKey());
            }
        }
        return res;
    }
}
