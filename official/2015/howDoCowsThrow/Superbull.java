import java.io.*;
import java.util.*;

/**
 * 2015 feb silver (seriously how do cows throw like they have frickin hooves)
 * ok so anyways we have each team as sorta a "node" and we know all the pairwise distances (=score when they play)
 * what we wanna do is make a "maximum spanning tree" so that max score's achieved
 * node's parent = what they lose to, so a node beats all it's children
 * so then prim's ez clap
 * */
public class Superbull {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("superbull.in"));
        int[] teams = new int[Integer.parseInt(read.readLine())];
        for (int i = 0; i < teams.length; i++) {
            teams[i] = Integer.parseInt(read.readLine());
        }
        Arrays.sort(teams);  // probably doesn't change anything, it makes my life easier though

        long weightSoFar = 0;
        HashMap<Integer, Boolean> played = new HashMap<>();
        HashMap<Integer, Integer> distToTree = new HashMap<>();
        for (int t : teams) {
            distToTree.put(t, Integer.MAX_VALUE);
            played.put(t, false);
        }
        int nodeStart = teams[0];
        distToTree.put(nodeStart, 0);
        played.put(nodeStart, true);

        for (int t : teams) {
            if (t != nodeStart) {
                distToTree.put(t, nodeStart ^ t);
            }
        }

        for (int i = 0; i < teams.length - 1; i++) {
            int nodeToAdd = -1;
            for (int t : teams) {
                // if we haven't explored this & it's a better node than the prev one we got
                if (!played.get(t) && (nodeToAdd == -1 || distToTree.get(t) > distToTree.get(nodeToAdd))) {
                    nodeToAdd = t;
                }
            }

            played.put(nodeToAdd, true);  // ok add to tree & update total distance
            weightSoFar += distToTree.get(nodeToAdd);
            for (int t : teams) {
                if (!played.get(t)) {  // update distances to tree
                    distToTree.put(t, Math.max(distToTree.get(t), t ^ nodeToAdd));
                }
            }
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("superbull.out"));
        written.println(weightSoFar);
        written.close();
        System.out.println(weightSoFar);
        System.out.printf("AND IT TOOK %d MS WOOOO *screams in pain*", System.currentTimeMillis() - start);
    }
}
