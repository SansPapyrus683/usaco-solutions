package official.o2025.feb.silver;

import java.io.*;
import java.util.*;

public class VocabQuiz {
    // stupid java. stupid recursion. stupid trees.
    private static List<Integer>[] kids;
    private static Set<Integer>[] actualKids;
    private static int[] branchAbove;
    private static int[] depth;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int wordNum = Integer.parseInt(read.readLine());

        int[] parents = new int[wordNum + 2];
        kids = new List[wordNum + 2];
        parents[1] = 0;
        for (int w = 0; w < kids.length; w++) {
            kids[w] = new ArrayList<>();
        }
        kids[0].add(1);
        StringTokenizer words = new StringTokenizer(read.readLine());
        for (int w = 2; w <= wordNum + 1; w++) {
            int par = Integer.parseInt(words.nextToken()) + 1;
            parents[w] = par;
            kids[par].add(w);
        }

        branchAbove = new int[kids.length];
        depth = new int[kids.length];
        actualKids = new HashSet[kids.length];
        for (int w = 0; w < kids.length; w++) {
            actualKids[w] = new HashSet<>();
        }
        depth[1] = 1;
        findBranches(1, 0);

        StringBuilder ans = new StringBuilder();
        while (read.ready()) {
            int word = Integer.parseInt(read.readLine()) + 1;
            int above = branchAbove[word];
            ans.append(depth[above]).append('\n');

            actualKids[above].remove(word);
            if (actualKids[above].size() == 1) {
                int orphan = actualKids[above].iterator().next();
                int newAbove = branchAbove[above];
                branchAbove[orphan] = newAbove;
                actualKids[newAbove].add(orphan);
                actualKids[newAbove].remove(above);
            }
        }

        System.out.print(ans);
    }

    private static void findBranches(int at, int lastBranch) {
        branchAbove[at] = lastBranch;
        if (kids[at].size() != 1) {
            actualKids[lastBranch].add(at);
            lastBranch = at;
        }
        for (int k : kids[at]) {
            depth[k] = depth[at] + 1;
            findBranches(k, lastBranch);
        }
    }
}
