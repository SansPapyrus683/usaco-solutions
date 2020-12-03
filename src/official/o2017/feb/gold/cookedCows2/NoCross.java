package official.o2017.feb.gold.cookedCows2;

import java.io.*;
import java.util.*;

// 2017 feb gold
public class NoCross {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("nocross.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int fieldNum = Integer.parseInt(initial.nextToken());
        int[] firstFields = new int[fieldNum];
        int[] secondIDToPos = new int[fieldNum];  // tbh we don't really need the second field values
        for (int i = 0; i < fieldNum; i++) {
            firstFields[i] = Integer.parseInt(read.readLine()) - 1;
        }
        for (int i = 0; i < fieldNum; i++) {
            secondIDToPos[Integer.parseInt(read.readLine()) - 1] = i;
        }

        // an arraylist the connections drawn and the last taken second pos given the last thing we connect
        int max = 0;
        ArrayList<int[]>[] connSoFar = new ArrayList[fieldNum + 1];
        connSoFar[0] = new ArrayList<>(Collections.singletonList(new int[] {0, -1}));
        for (int f = 1; f <= fieldNum; f++) {
            // the indices we can link the current field to
            ArrayList<Integer> options = new ArrayList<>();
            for (int canLink = Math.max(0, firstFields[f - 1] - 4); canLink <= Math.min(firstFields[f - 1] + 4, fieldNum - 1); canLink++) {
                options.add(secondIDToPos[canLink]);
            }
            options.sort(Comparator.comparingInt(i -> i));

            int[] connNumToLastConn = new int[fieldNum + 1];
            Arrays.fill(connNumToLastConn, Integer.MAX_VALUE);
            for (int i = 0; i < f; i++) {
                for (int[] prevState : connSoFar[i]) {
                    for (int o : options) {
                        if (o > prevState[1]) {
                            connNumToLastConn[prevState[0] + 1] = Math.min(connNumToLastConn[prevState[0] + 1], o);
                            break;  // options was sorted, and why in frick would we choose something higher? just break it
                        }
                    }
                }
            }
            ArrayList<int[]> thisStates = new ArrayList<>();
            for (int i = 0; i <= fieldNum; i++) {  // actually make the states and pass them on to the storage
                if (connNumToLastConn[i] != Integer.MAX_VALUE) {
                    thisStates.add(new int[] {i, connNumToLastConn[i]});
                    max = Math.max(max, i);
                }
            }
            connSoFar[f] = thisStates;
        }

        PrintWriter written = new PrintWriter("nocross.out");
        written.println(max);
        written.close();
        System.out.println(max);
        System.out.printf("%d ms and we are DONE FRICK YEA%n", System.currentTimeMillis() - start);
    }
}
