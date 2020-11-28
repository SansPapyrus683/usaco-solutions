import java.io.*;
import java.util.*;

// 2014 feb silver
public class SCode {
    private static final int MOD = 2014;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String encrypted =  new BufferedReader(new FileReader("scode.in")).readLine();
        // go through the strings by decreasing length (so we don't process any dupes at all)
        PriorityQueue<String> frontier = new PriorityQueue<>(Comparator.comparingInt(s -> -s.length()));
        HashMap<String, Integer> waysToGet = new HashMap<>();  // keep track of the ways to decrypt to the hashmap key

        frontier.add(encrypted);
        waysToGet.put(encrypted, 1);  // yea only 1 way to get from the encrypted to the encrypted lol
        int opWays = 0;
        while (!frontier.isEmpty()) {
            String current = frontier.poll();
            int currMultiplier = waysToGet.get(current);
            for (String n : validFrom(current)) {
                opWays = (opWays + currMultiplier) % MOD;
                if (waysToGet.containsKey(n)) {
                    waysToGet.put(n, (waysToGet.get(n) + currMultiplier) % MOD);
                } else {
                    waysToGet.put(n, currMultiplier);  // multipliers should carry on
                    frontier.add(n);
                }
            }
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("scode.out"));
        written.println(opWays);
        written.close();
        System.out.println(opWays);
        System.out.printf("how do we even decrypt fj's message (it took %d ms ok?)", System.currentTimeMillis() - start);
    }

    public static ArrayList<String> validFrom(String result) {
        ArrayList<String> valid = new ArrayList<>();
        for (int i = 1; i < result.length(); i++) {
            if (i == (float) result.length() / 2) {  // NOOO!! YOU CAN'T JUST DUPLICATE A STRING AS AN OPERATION!!!
                continue;
            }
            // decide which part would it make sense to cutoff (head or tail?)
            String toCutoff = i <= result.length() / 2 ? result.substring(0, i) : result.substring(i);
            String cutoffResult = i <= result.length() / 2 ? result.substring(i) : result.substring(0, i);
            if (cutoffResult.startsWith(toCutoff)) {  // two if statements (it can either be the start or the end)
                valid.add(cutoffResult);
            }
            if (cutoffResult.endsWith(toCutoff)) {
                valid.add(cutoffResult);
            }
        }
        return valid;
    }
}
