package official.o2014.feb.bronze.hackerCows;

import java.io.*;
import java.util.*;

// 2014 feb silver
public final class SCode {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String encrypted =  new BufferedReader(new FileReader("scode.in")).readLine();

        // this code is literally the same as the silver version (only thing that changed is the validFrom code)
        PriorityQueue<String> frontier = new PriorityQueue<>(Comparator.comparingInt(s -> -s.length()));
        HashMap<String, Integer> waysToGet = new HashMap<>();
        frontier.add(encrypted);
        waysToGet.put(encrypted, 1);
        int opWays = 0;
        while (!frontier.isEmpty()) {
            String current = frontier.poll();
            int currMultiplier = waysToGet.get(current);
            for (String n : validFrom(current)) {
                opWays += currMultiplier;
                if (waysToGet.containsKey(n)) {
                    waysToGet.put(n, waysToGet.get(n) + currMultiplier);
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

    private static ArrayList<String> validFrom(String result) {
        if (result.length() % 2 == 0) {
            return new ArrayList<>();
        }
        ArrayList<String> valid = new ArrayList<>();
        String shorter = result.substring(0, result.length() / 2);
        String longer = result.substring(result.length() / 2);
        if (longer.startsWith(shorter)) {
            valid.add(longer);
        }
        if (longer.endsWith(shorter)) {
            valid.add(longer);
        }

        shorter = result.substring(result.length() / 2 + 1);
        longer = result.substring(0, result.length() / 2 + 1);
        if (longer.startsWith(shorter)) {
            valid.add(longer);
        }
        if (longer.endsWith(shorter)) {
            valid.add(longer);
        }
        return valid;
    }
}
