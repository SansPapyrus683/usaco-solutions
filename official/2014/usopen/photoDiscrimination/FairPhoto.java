import java.io.*;
import java.util.*;

// 2014 usopen silver (pretty much copied from official sol)
public class FairPhoto {
    static int[][] cows;
    static ArrayList<Integer>[] relCowStarts = new ArrayList[] {new ArrayList(), new ArrayList()};
    static ArrayList<Integer>[] relCowPrefixes = new ArrayList[] {new ArrayList(), new ArrayList()};
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("fairphoto.in"));
        int cowNum = Integer.parseInt(read.readLine());
        cows = new int[cowNum][2];
        for (int c = 0; c < cowNum; c++) {
            StringTokenizer unparsed = new StringTokenizer(read.readLine());
            // white = -1, spotted = 1
            cows[c] = new int[] {Integer.parseInt(unparsed.nextToken()), unparsed.nextToken().equals("W") ? -1 : 1};
        }
        Arrays.sort(cows, Comparator.comparingInt(c -> c[0]));

        int cumulative = 0;
        int longest = 0;
        for (int c = 0; c < cowNum; c++) {
            int ind = cumulative & 1;  // actually works better than % 2 (because of negative numbers)
            /*
             *  keep a steady decreasing sequence
             *  that's because if a cow is to the right of another and >= the other's prefix sum, we can always use
             *  that's because if a cow is to the right of another and >= the other's prefix sum, we can always use
             *  that other cow instead of the cow to the right, and it'll be more optimal as well
             */
            if (relCowStarts[ind].isEmpty() || cumulative > relCowPrefixes[ind].get(relCowPrefixes[ind].size() - 1)) {
                relCowStarts[ind].add(cows[c][0]);
                relCowPrefixes[ind].add(cumulative);
            }
            cumulative += cows[c][1];
            ind = cumulative & 1;  // switch which arraylist to use bc photos have to be of even length
            // if it isn't empty and the current sum has even the slightest chance of matching up
            if (!relCowPrefixes[ind].isEmpty() && cumulative <= relCowPrefixes[ind].get(relCowPrefixes[ind].size() - 1)) {
                // because that seq is decreasing, we can just use binsearch to find the max length
                longest = Math.max(longest, cows[c][0] - relCowStarts[ind]
                        .get(bisectLeft(relCowPrefixes[ind], cumulative)));
            }
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("fairphoto.out"));
        written.println(longest);
        written.close();
        System.out.println(longest);
        System.out.printf("*click* it took something like %d ms%n", System.currentTimeMillis() - start);
    }

    // binsearch that finds the leftmost position that x can be inserted in so that the list is still sorted
    static int bisectLeft(ArrayList<Integer> toSearch, int x) {
        int lowerBound = 0;
        int upperBound = toSearch.size();
        if (x < toSearch.get(lowerBound)) {
            return lowerBound;
        }
        if (x > toSearch.get(upperBound - 1)) {
            return upperBound;
        }
        while (true) {
            if (lowerBound + 1 == upperBound) {
                return x == toSearch.get(lowerBound) ? lowerBound : (lowerBound + 1);
            }
            int mid = (upperBound + lowerBound) / 2;
            if (x <= toSearch.get(mid)) {
                upperBound = mid;
            } else {
                lowerBound = mid;
            }
        }
    }
}
