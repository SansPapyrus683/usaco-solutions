package official.o2018.jan.silver.businessIsBoomin;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

// 2018 jan silver
public final class Rental {
    private static int[] cows;
    private static int[][] stores;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("rental.in"));
        String[] initialInfo = read.readLine().split(" ");
        cows = new int[Integer.parseInt(initialInfo[0])];
        stores = new int[Integer.parseInt(initialInfo[1])][2];
        int[] rentals = new int[Integer.parseInt(initialInfo[2])];
        for (int i = 0; i < cows.length; i++) {
            cows[i] = Integer.parseInt(read.readLine());
        }
        for (int i = 0; i < stores.length; i++) {
            String[] unparsed = read.readLine().split(" ");  // amt a day, followed by offered price
            stores[i] = new int[] {Integer.parseInt(unparsed[0]), Integer.parseInt(unparsed[1])};
        }
        Arrays.sort(cows);
        Arrays.sort(stores, Comparator.comparingInt(s -> -s[1]));
        for (int i = 0; i < rentals.length; i++) {
            rentals[i] = Integer.parseInt(read.readLine());
        }
        Arrays.sort(rentals);
        for (int i = 0; i < rentals.length / 2; i++) {  // reverse to the best ones are @ the front
            int temp = rentals[i];  // middleman for exchange
            rentals[i] = rentals[rentals.length - i - 1];
            rentals[rentals.length - i - 1] = temp;
        }

        long best = 0;
        long rentalAmt = 0;
        long milkAmt = IntStream.of(cows).sum();
        HashMap<Long, Long> milkingIncome = calcMilkIncome();
        for (int cutoff = 0; cutoff <= cows.length; cutoff++) {  // to the left = rent, to the right = milk
            if (cutoff > 0) {
                rentalAmt += rentals[cutoff - 1];
                milkAmt -= cows[cutoff - 1];
            }
            long income = rentalAmt + milkingIncome.get(milkAmt);
            if (income < best) {  // so here's the thing- if i'm right the amount of money is kinda like a mountain, going up then down
                break;
            }
            best = income;  // we eliminated all the ones that are less than so...
            if (cutoff >= rentals.length) {  // extremely hacky implementation but better than nothing
                break;  // prevents a dumb index out of bounds error
            }
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("rental.out"));
        written.println(best);
        written.close();
        System.out.println(best);
        System.out.printf("ok it took %d ms now go away%n", System.currentTimeMillis() - start);
    }

    private static HashMap<Long, Long> calcMilkIncome() {
        HashMap<Long, Long> costs = new HashMap<>();
        long currMoney = 0;
        long currMilk = 0;
        int storePointer = 0;  // something signifying which store we're at
        int milkLeft = stores[storePointer][0];
        for (int i = cows.length - 1; i >= 0; i--) {  // calculate the prices we're going to need
            int c = cows[i];
            currMilk += c;
            do {
                if (c <= milkLeft) {
                    currMoney += (long) c * stores[storePointer][1];
                    milkLeft -= c;
                    c = 0;  // used up all the milk lol
                } else {
                    currMoney += (long) milkLeft * stores[storePointer][1];
                    if (storePointer + 1 >= stores.length) {
                        milkLeft = 0;
                        break;
                    }
                    storePointer++;
                    c -= milkLeft;
                    milkLeft = stores[storePointer][0];
                }
            } while (c > 0);
            costs.put(currMilk, currMoney);
        }
        return costs;
    }
}
