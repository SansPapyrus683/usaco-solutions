package official.o2019.dec.silver.silverFriends;

import java.io.*;
import java.util.*;

// 2019 dec silver
public final class SilverVisits {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("milkvisits.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int farmNum = Integer.parseInt(initial.nextToken());
        int friendNum = Integer.parseInt(initial.nextToken());
        int[] types = read.readLine().chars().map(t -> t - 71).toArray();
        ArrayList<Integer>[] neighbors = new ArrayList[farmNum];
        for (int f = 0; f < farmNum; f++) {
            neighbors[f] = new ArrayList<>();
        }
        for (int n = 0; n < farmNum - 1; n++) {
            int[] path = Arrays.stream(read.readLine().split(" ")).mapToInt(f -> Integer.parseInt(f) - 1).toArray();
            neighbors[path[0]].add(path[1]);
            neighbors[path[1]].add(path[0]);
        }

        int[] componentID = new int[farmNum];
        int soFarID = 1;
        for (int f = 0; f < farmNum; f++) {
            if (componentID[f] != 0) {
                continue;
            }
            componentID[f] = soFarID;
            int type = types[f];
            ArrayDeque<Integer> frontier = new ArrayDeque<>(Collections.singletonList(f));
            while (!frontier.isEmpty()) {
                int curr = frontier.poll();
                for (int n : neighbors[curr]) {
                    if (componentID[n] == 0 && types[n] == type) {
                        frontier.add(n);
                        componentID[n] = soFarID;
                    }
                }
            }
            soFarID++;
        }

        PrintWriter written = new PrintWriter("milkvisits.out");
        for (int f = 0; f < friendNum; f++) {
            StringTokenizer rawQuery = new StringTokenizer(read.readLine());
            int farm1 = Integer.parseInt(rawQuery.nextToken()) - 1, farm2 = Integer.parseInt(rawQuery.nextToken()) - 1;
            int milk = rawQuery.nextToken().charAt(0) - 71;
            if ((types[farm1] == milk || types[farm2] == milk)
                    || (componentID[farm1] != componentID[farm2])) {
                written.write("1");  // have to input it as a string or else java thinks its a char code lol
                System.out.print(1);
            } else {
                written.write("0");
                System.out.print(0);
            }
        }
        written.println();
        written.close();
        System.out.println();
        System.out.printf("%d ms- bruh moment%n", System.currentTimeMillis() - start);
    }
}
