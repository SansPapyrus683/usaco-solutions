package official.o2013.jan.silver.cowHangover;

import java.io.*;
import java.util.*;

// 2013 jan silver
public class Invite {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("invite.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int groupNum = Integer.parseInt(initial.nextToken());
        ArrayList<Integer>[] cowToGroupNum = new ArrayList[cowNum];
        HashSet<Integer>[] groups = new HashSet[groupNum];
        for (int c = 0; c < cowNum; c++) {
            cowToGroupNum[c] = new ArrayList<>();
        }

        for (int g = 0; g < groupNum; g++) {
            // make cows 0-indexed so they line up w/ the array indices
            int[] members = Arrays.stream(read.readLine().split(" ")).mapToInt(c -> Integer.parseInt(c) - 1).toArray();
            groups[g] = new HashSet<>();
            for (int i = 1; i < members.length; i++) {  // exclude the first number (the group size)
                int c = members[i];
                groups[g].add(c);
                cowToGroupNum[c].add(g);
            }
        }

        ArrayDeque<Integer> frontier = new ArrayDeque<>(Collections.singletonList(0));
        boolean[] invited = new boolean[cowNum];
        invited[0] = true;
        while (!frontier.isEmpty()) {  // while there's still cows we have to invite
            int curr = frontier.poll();
            for (int g : cowToGroupNum[curr]) {
                groups[g].remove(curr);
                if (groups[g].size() == 1) {
                    int lastOne = groups[g].iterator().next();
                    if (!invited[lastOne]) {
                        frontier.add(lastOne);
                        invited[lastOne] = true;
                    }
                }
            }
        }

        int totalInvited = 0;
        for (boolean c : invited) {
            if (c) {
                totalInvited++;
            }
        }
        PrintWriter written = new PrintWriter("invite.out");
        written.println(totalInvited);
        written.close();
        System.out.println(totalInvited);
        System.out.printf("i'm having a wonderful time picturing a drunk cow: %d ms%n", System.currentTimeMillis() - start);
    }
}
