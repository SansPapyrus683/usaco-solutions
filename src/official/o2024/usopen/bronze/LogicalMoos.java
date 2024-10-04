package official.o2024.usopen.bronze;

import java.io.*;
import java.util.*;

/** 2024 us open bronze */
public class LogicalMoos {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int stmtLen = Integer.parseInt(initial.nextToken());
        int queryNum = Integer.parseInt(initial.nextToken());

        List<List<Boolean>> stmt = new ArrayList<>();
        List<Boolean> curr = new ArrayList<>();
        StringTokenizer stmtST = new StringTokenizer(read.readLine());
        int[][] indToPos = new int[stmtLen][];
        for (int s = 0; s < stmtLen; s++) {
            String token = stmtST.nextToken();
            indToPos[s] = new int[] {stmt.size(), curr.size()};
            switch (token) {
                case "false":
                    curr.add(false);
                    break;
                case "true":
                    curr.add(true);
                    break;
                case "or":
                    stmt.add(curr);
                    curr = new ArrayList<>();
                    break;
            }
        }
        stmt.add(curr);

        int[] pref = new int[stmt.size() + 1];
        int[][] subPref = new int[stmt.size()][];
        for (int i = 0; i < stmt.size(); i++) {
            subPref[i] = new int[stmt.get(i).size() + 1];
            boolean hasAll = true;
            for (int j = 0; j < stmt.get(i).size(); j++) {
                hasAll = hasAll && stmt.get(i).get(j);
                subPref[i][j + 1] = subPref[i][j] + (stmt.get(i).get(j) ? 1 : 0);
            }
            pref[i + 1] = pref[i] + (hasAll ? 0 : 1);
        }

        StringBuilder queryAns = new StringBuilder();
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            int[] from = indToPos[Integer.parseInt(query.nextToken()) - 1];
            int[] to = indToPos[Integer.parseInt(query.nextToken()) - 1];
            boolean evalTo = query.nextToken().equals("true");

            int suffTrues = subPref[to[0]][subPref[to[0]].length - 1] - subPref[to[0]][to[1] + 1];
            int expectTrues = subPref[to[0]].length - 1 - (to[1] + 1);
            boolean canMakeTrue = subPref[from[0]][from[1]] == from[1] && suffTrues == expectTrues;

            int suffFalses = pref[pref.length - 1] - pref[to[0] + 1];
            int expectedFalses = pref.length - 1 - (to[0] + 1);
            boolean canMakeFalse = pref[from[0]] == from[0] && suffFalses == expectedFalses;

            if (evalTo) {
                queryAns.append(canMakeTrue || !canMakeFalse ? 'Y' : 'N');
            } else {
                queryAns.append(canMakeFalse ? 'Y' : 'N');
            }
        }

        System.out.println(queryAns);
    }
}
