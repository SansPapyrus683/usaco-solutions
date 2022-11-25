package official.o2015.usopen.silver.birthday;

import java.io.*;
import java.util.*;

// 2015 us open (i regret nothing for using epic as a var name)
public class Buffet {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("buffet.in"));
        String[] initialInfo = read.readLine().split(" ");
        int patchNum = Integer.parseInt(initialInfo[0]);
        int energy = Integer.parseInt(initialInfo[1]);
        ArrayList<Integer>[] neighbors = new ArrayList[patchNum];
        int[] epicness = new int[patchNum];
        for (int i = 0; i < patchNum; i++) {
            String[] raw = read.readLine().split(" ");
            epicness[i] = Integer.parseInt(raw[0]);
            neighbors[i] = new ArrayList<>();
            for (int n = 0; n < Integer.parseInt(raw[1]); n++) {
                neighbors[i].add(Integer.parseInt(raw[n + 2]) - 1);
            }
        }

        Integer[] indexBySize = new Integer[patchNum];  // I HATE YOU JAVA
        for (int i = 0; i < patchNum; i++) {
            indexBySize[i] = i;
        }
        Arrays.sort(indexBySize, Comparator.comparingInt(a -> epicness[a]));  // sort by decreasing size

        int bestEnergy = 0;
        int[] maxEndEnergy = new int[patchNum];  // this[i] = max energy if we end @ patch i
        for (int i = patchNum - 1; i >= 0; i--) {
            int patch = indexBySize[i];
            int[] distances = new int[patchNum];
            ArrayList<Integer> frontier = new ArrayList<>();
            boolean[] visited = new boolean[patchNum];
            int movesTaken = 0;
            Arrays.fill(distances, Integer.MAX_VALUE);
            frontier.add(patch);
            visited[patch] = true;
            distances[patch] = 0;
            while (!frontier.isEmpty()) {
                ArrayList<Integer> inLine = new ArrayList<>();
                for (int p : frontier) {
                    distances[p] = movesTaken;
                    for (int n : neighbors[p]) {
                        if (!visited[n]) {
                            inLine.add(n);
                            visited[n] = true;
                        }
                    }
                }
                movesTaken++;
                frontier = inLine;
            }

            int thisMax = epicness[patch];
            for (int other = 0; other < patchNum; other++) {
                // size checking not necessary bc we iterate through them by size anyways (so they'll be 0)
                if (distances[other] != Integer.MAX_VALUE) {
                    thisMax = Math.max(thisMax, epicness[patch] + maxEndEnergy[other] - energy * distances[other]);
                }
            }
            maxEndEnergy[patch] = thisMax;
            bestEnergy = Math.max(bestEnergy, thisMax);
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("buffet.out"));
        written.println(bestEnergy);
        written.close();
        System.out.println(bestEnergy);
        System.out.printf("happy birthday. it took %d ms. now frick off.%n", System.currentTimeMillis() - start);
    }
}
