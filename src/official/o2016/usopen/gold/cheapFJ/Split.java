package official.o2016.usopen.gold.cheapFJ;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2016 usopen gold
public final class Split {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("split.in"));
        int cowNum = Integer.parseInt(read.readLine());

        int[][] cows = new int[cowNum][2];
        HashSet<Integer> unsortedXVals = new HashSet<>();
        HashSet<Integer> unsortedYVals = new HashSet<>();
        int minX = Integer.MAX_VALUE, maxX = 0, minY = Integer.MAX_VALUE, maxY = 0;
        for (int c = 0; c < cowNum; c++) {
            int[] cow = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            cows[c] = cow;
            unsortedXVals.add(cow[0]);
            unsortedYVals.add(cow[1]);
            minX = Math.min(minX, cow[0]);
            maxX = Math.max(maxX, cow[0]);
            minY = Math.min(minY, cow[1]);
            maxY = Math.max(maxY, cow[1]);
        }
        ArrayList<Integer> xVals = new ArrayList<>(unsortedXVals);
        ArrayList<Integer> yVals = new ArrayList<>(unsortedYVals);
        xVals.sort(Comparator.comparingInt(i -> i));
        yVals.sort(Comparator.comparingInt(i -> i));
        
        long bigArea = ((long) (maxX - minX)) * (maxY - minY);
        long maxSaved = 0;

        Arrays.sort(cows, Comparator.comparingInt(c -> c[0]));
        MinMaxStack rightSideCows = new MinMaxStack();
        for (int c = cowNum - 1; c >= 0; c--) {  // bc it's a stack, add the elements in reverse
            rightSideCows.add(cows[c][1]);
        }
        rightSideCows.poll();
        minY = cows[0][1];  // we only need to keep track of the y bounds for iterating through the x vals
        maxY = cows[0][1];
        int atCow = 0;
        for (int x : xVals.subList(0, xVals.size() - 1)) {  // try and split the field horizontally first
            while (cows[atCow + 1][0] <= x) {  // move the pointer and pop the thing until it matches the coo
                atCow++;
                rightSideCows.poll();
                int[] cow = cows[atCow];
                minY = Math.min(minY, cow[1]);
                maxY = Math.max(maxY, cow[1]);
            }
            long firstArea = ((long) (x - xVals.get(0))) * (maxY - minY);
            long secondArea = ((long) (xVals.get(xVals.size() - 1) - cows[atCow + 1][0])) * (rightSideCows.max() - rightSideCows.min());
            maxSaved = Math.max(maxSaved, bigArea - firstArea - secondArea);
        }

        Arrays.sort(cows, Comparator.comparingInt(c -> c[1]));  // try it vertically now
        MinMaxStack aboveCows = new MinMaxStack();
        for (int c = cowNum - 1; c >= 0; c--) {
            aboveCows.add(cows[c][0]);
        }
        aboveCows.poll();
        minX = cows[0][0];
        maxX = cows[0][0];
        atCow = 0;
        for (int y : yVals.subList(0, yVals.size() - 1)) {
            while (cows[atCow + 1][1] <= y) {
                atCow++;
                aboveCows.poll();
                int[] cow = cows[atCow];
                minX = Math.min(minX, cow[0]);
                maxX = Math.max(maxX, cow[0]);
            }
            long firstArea = ((long) (y - yVals.get(0))) * (maxX - minX);
            long secondArea = ((long) (yVals.get(yVals.size() - 1) - cows[atCow + 1][1])) * (aboveCows.max() - aboveCows.min());
            maxSaved = Math.max(maxSaved, bigArea - firstArea - secondArea);
        }

        PrintWriter written = new PrintWriter("split.out");
        written.println(maxSaved);
        written.close();
        System.out.println(maxSaved);
        System.out.printf("i blame the lag for the runtime of %d ms%n", System.currentTimeMillis() - start);
    }
}

// sauce: // copied from: https://cp-algorithms.com/data_structures/stack_queue_modification.html
class MinMaxStack {
    private final Stack<int[]> stack = new Stack<>();
    public void add(int toAdd) {
        if (stack.isEmpty()) {
            stack.add(new int[] {toAdd, toAdd, toAdd});
            return;
        }
        stack.add(new int[] {toAdd, Math.min(toAdd, min()), Math.max(toAdd, max())});
    }

    public int poll() {
        return stack.pop()[0];
    }

    public int min() {
        return stack.peek()[1];
    }

    public int max() {
        return stack.peek()[2];
    }
}
