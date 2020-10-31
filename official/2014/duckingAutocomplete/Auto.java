import java.io.*;
import java.util.*;

// 2014 feb silver (and yes i realize that duck is bc of autoCORRECT, not autoCOMPLETE)
public class Auto {
    static Pair<Integer, String>[] words;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("auto.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        // words[] is a pair so i can keep track of the original index in the dictionary
        words = new Pair[Integer.parseInt(initial.nextToken())];
        Pair<Integer, String>[] queries = new Pair[Integer.parseInt(initial.nextToken())];
        for (int i = 1; i <= words.length; i++) {
            words[i-1] = new Pair<>(i, read.readLine());
        }
        for (int i = 0; i < queries.length; i++) {
            StringTokenizer unparsed = new StringTokenizer(read.readLine());
            queries[i] = new Pair<>(Integer.parseInt(unparsed.nextToken()), unparsed.nextToken());
        }
        read.close();
        Arrays.sort(words, Comparator.comparing(p -> p.second));  // note: i don't need to sort the queries

        int queryAt = 0;
        int[] answers = new int[queries.length];
        for (Pair<Integer, String> q : queries) {
            // binary search for the first & last occurrence of the starting substring
            int firstMatch = findIndex(q.second), lastMatch = findIndex(q.second, false);
            // if there's enough matches (&& we actually found some stuff), give an answer or just -1
            answers[queryAt] = lastMatch - firstMatch + 1 >= q.first && firstMatch != -1 ?
                    words[firstMatch + q.first - 1].first : -1;
            queryAt++;
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("auto.out"));
        for (int a : answers) {
            written.println(a);
            System.out.println(a);
        }
        written.close();
        System.out.printf("it fricking took %d ms to calculate fj's autocomplete app%n", System.currentTimeMillis() - start);
    }

    static int findIndex(String partial) {
        return findIndex(partial, true);
    }

    static int findIndex(String partial, boolean first) {  // true = find first, false = find last
        // just binsearch for the occurring substring
        int lower = 0, upper = words.length - 1;  // idk why i made the upper bound inclusive, but it works
        int toReturn = -1;
        while (lower <= upper) {
            int toSearch = (lower + upper) / 2;
            // only look at the substring up to the point where we want it (that is, the length of the partial)
            String gotWord = words[toSearch].second.substring(0, Math.min(words[toSearch].second.length(), partial.length()));
            if (gotWord.compareTo(partial) > 0) {
                upper = toSearch - 1;
            } else if (gotWord.compareTo(partial) < 0) {
                lower = toSearch + 1;
            } else {
                toReturn = toSearch;  // a sorta backup? but anyways let's keep on searching
                if (first) {
                    upper = toSearch - 1;
                } else {
                    lower = toSearch + 1;
                }
            }
        }
        return toReturn;
    }
}

class Pair<T1, T2> {  // seriously wish java had it's own pair class
    public T1 first;
    public T2 second;
    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("Pair{first: %s, second: %s}", first, second);
    }
}
