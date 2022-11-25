package official.o2016.dec.silver.MURICA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

// 2016 dec silver
public class CityState {
    private static final class Pair<T1, T2> {  // maybe i could ask usaco for apache commons?
        public T1 first;
        public T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return String.format("Pair{first=%s, second=%s}", first, second);
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("citystate.in"));
        int cityNum = Integer.parseInt(read.readLine());
        Pair<String, String>[] cityStates = new Pair[cityNum];
        HashMap<String, HashMap<String, Integer>> stateCities = new HashMap<>();
        for (int c = 0; c < cityNum; c++) {
            String[] cityState = read.readLine().split(" ");
            cityStates[c] = new Pair<>(cityState[0], cityState[1]);
            String city = cityState[0].substring(0, 2);
            String state = cityState[1];
            if (!stateCities.containsKey(state)) {  // frick you java for not having a defaultdict
                stateCities.put(state, new HashMap<>());  // but anyways just map the city "codes" to the states
            }
            if (!stateCities.get(state).containsKey(city)) {
                stateCities.get(state).put(city, 0);
            }
            stateCities.get(state).put(city, stateCities.get(state).get(city) + 1);
        }

        int hasReverse = 0;
        HashMap<String, Integer> placeholder = new HashMap<>();
        for (Pair<String, String> cs : cityStates) {
            // they have to come from diff. cities- if the city code == the state code, then all their pairs aren't valid
            if (!cs.first.substring(0, 2).equals(cs.second)) {
                hasReverse += stateCities.getOrDefault(cs.first.substring(0, 2), placeholder).getOrDefault(cs.second, 0);
            }
        }

        int reversedPairs = hasReverse / 2;
        PrintWriter written = new PrintWriter("citystate.out");
        written.println(reversedPairs);
        written.close();
        System.out.println(reversedPairs);
        System.out.printf("your code took %d ms. (beep boop i'm a bot)%n", System.currentTimeMillis() - start);
    }
}
