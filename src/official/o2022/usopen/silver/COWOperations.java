package official.o2022.usopen.silver;

import java.io.*;
import java.util.*;

/**
 * 2022 us open silver
 * COW
 * 6
 * 1 1
 * 1 2
 * 1 3
 * 2 2
 * 2 3
 * 3 3 should output YNNNYN
 */
public final class COWOperations {
    private static final HashMap<Character, Integer> code = new HashMap<>() {{
        put('C', 0);
        put('O', 1);
        put('W', 2);
    }};

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        String str = read.readLine();
        if (!str.replaceAll("[COW]", "").isEmpty()) {
            throw new IllegalArgumentException("only COWs please");
        }

        int[][] charPrefs = new int[str.length() + 1][code.size()];
        for (int c = 0; c < str.length(); c++) {
            charPrefs[c + 1] = charPrefs[c].clone();
            charPrefs[c + 1][code.get(str.charAt(c))]++;
        }

        int queryNum = Integer.parseInt(read.readLine());
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            int start = Integer.parseInt(query.nextToken()) - 1;
            int end = Integer.parseInt(query.nextToken()) - 1;

            boolean haveC = (charPrefs[end + 1][code.get('C')] - charPrefs[start][code.get('C')]) % 2 != 0;
            boolean haveO = (charPrefs[end + 1][code.get('O')] - charPrefs[start][code.get('O')]) % 2 != 0;
            boolean haveW = (charPrefs[end + 1][code.get('W')] - charPrefs[start][code.get('W')]) % 2 != 0;

            if (haveC != haveO && haveO == haveW) {
                System.out.print('Y');
            } else{
                System.out.print('N');
            }
        }
        System.out.println();
    }
}
