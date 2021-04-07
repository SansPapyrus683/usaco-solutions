// package official.o2014.feb.silver.duckingAutocomplete;

import java.io.*;
import java.util.*;

// 2014 feb silver (and yes i realize that duck is bc of autoCORRECT, not autoCOMPLETE)
public final class Auto {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("auto.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int wordNum = Integer.parseInt(initial.nextToken());
        int queryNum = Integer.parseInt(initial.nextToken());
        String[] words = new String[wordNum];
        HashMap<String, Integer> wordInds = new HashMap<>();
        for (int w = 0; w < wordNum; w++) {
            words[w] = read.readLine();
            wordInds.put(words[w], w);
        }
        Arrays.sort(words);

        PrintWriter written = new PrintWriter("auto.out");
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            int placing = Integer.parseInt(query.nextToken()) - 1;
            String prefix = query.nextToken();
            int firstMatch = firstMatch(words, prefix);
            int ans = -1;
            if (firstMatch != -1
                    && firstMatch + placing < wordNum
                    && words[firstMatch + placing].startsWith(prefix)) {
                ans = wordInds.get(words[firstMatch + placing]) + 1;  // the answers are 1-indexed
            }
            written.println(ans);
            System.out.println(ans);
        }
        written.close();
        System.out.printf("you literally can't type with hooves wth: %d ms%n", System.currentTimeMillis() - start);
    }

    // basically bisect_left but with strings
    private static int firstMatch(String[] words, String prefix) {
        // just binsearch for the occurring substring
        int lo = 0;
        int hi = words.length - 1;
        int valid = -1;
        while (lo <= hi) {
            int toSearch = (lo + hi) / 2;
            // only look at the substring up to the length of the partial
            String gotWord = words[toSearch].substring(0, Math.min(words[toSearch].length(), prefix.length()));
            if (gotWord.compareTo(prefix) > 0) {
                hi = toSearch - 1;
            } else if (gotWord.compareTo(prefix) < 0) {
                lo = toSearch + 1;
            } else {
                valid = toSearch;
                hi = toSearch - 1;
            }
        }
        return valid;
    }
}
