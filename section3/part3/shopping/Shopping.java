/*
ID: kevinsh4
LANG: JAVA
TASK: test
*/

import java.io.*;
import java.util.*;

public class Shopping {
    static int numToBuy;
    static HashMap<ArrayList<Integer>, Integer> cached;
    static HashMap<ArrayList<Integer>, Integer> allOffers = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("shopping.in"));

        int offerNum = Integer.parseInt(read.readLine());
        HashMap<ArrayList<int[]>, Integer> offers = new HashMap<>();
        for (int i = 0; i < offerNum; i++) {
            int[] rawOffer = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            ArrayList<int[]> packaged = new ArrayList<>();
            for (int n = 0; n < rawOffer[0]; n++) {
                packaged.add(Arrays.copyOfRange(rawOffer, 2*n+1, 2*n+3));
            }
            offers.put(packaged, rawOffer[rawOffer.length - 1]);
        }

        numToBuy = Integer.parseInt(read.readLine());
        int[][] toBuy = new int[numToBuy][3];
        HashMap<Integer, Integer> encoding = new HashMap<>();
        for (int i = 0; i < numToBuy; i++) {
            toBuy[i] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            encoding.put(toBuy[i][0], i);
        }

        Integer[] actualShoppingList = new Integer[numToBuy];  // Integer for casting later on
        for (int i = 0; i < numToBuy; i++) {
            Integer[] singleOffer = new Integer[numToBuy];
            Arrays.fill(singleOffer, 0);
            singleOffer[i] = 1;
            actualShoppingList[i] = toBuy[i][1];
            allOffers.put(new ArrayList<>(Arrays.asList(singleOffer)), toBuy[i][2]);
        }
        for (ArrayList<int[]> o : offers.keySet()) {
            Integer[] newOffer = new Integer[numToBuy];
            Arrays.fill(newOffer, 0);
            for (int[] t : o) {
                newOffer[encoding.get(t[0])] = t[1];
            }
            allOffers.put(new ArrayList<>(Arrays.asList(newOffer)), offers.get(o));
        }
        cached = new HashMap<>(allOffers);

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("shopping.out")));
        out.println(String.format("%s", findLowestCost(new ArrayList<>(Arrays.asList(actualShoppingList)))));
        out.close();  // in java closing the file is 100% needed
    }

    static int findLowestCost(ArrayList<Integer> shoppingList) {
        if (cached.containsKey(shoppingList)) {
            return cached.get(shoppingList);
        }
        if (empty(shoppingList)) {
            return 0;
        }

        int best  = Integer.MAX_VALUE;
        for (ArrayList<Integer> o : allOffers.keySet()) {
            ArrayList<Integer> after = subtract(shoppingList, o);
            if (valid(after)) {
                int gottenOutput = findLowestCost(after);
                cached.put(after, gottenOutput);
                if (gottenOutput + allOffers.get(o) < best) {
                    best = gottenOutput + allOffers.get(o);
                }
            }
        }
        if (best == Integer.MAX_VALUE) {
            return 0;
        }
        return best;
    }

    static ArrayList<Integer> subtract(ArrayList<Integer> rn, ArrayList<Integer> deal) {
        ArrayList<Integer> processed = new ArrayList<>();
        for (int i = 0; i < numToBuy; i++) {
            processed.add(rn.get(i) - deal.get(i));
        }
        return processed;
    }

    static boolean valid(ArrayList<Integer> shoppingList) {
        for (int i : shoppingList) {
            if (i < 0) {
                return false;
            }
        }
        return true;
    }

    static boolean empty(ArrayList<Integer> shoppingList) {
        for (int i : shoppingList) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }
}
