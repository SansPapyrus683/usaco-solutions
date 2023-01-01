package official.o2022.jan.bronze;

import java.io.*;
import java.util.*;

public class Herdle {
    private static final int ROW_NUM = 3;
    private static final int COL_NUM = 3;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

        String[] ans = new String[ROW_NUM];
        for (int r = 0; r < ROW_NUM; r++) {
            ans[r] = read.readLine();
            assert ans[r].length() == COL_NUM;
        }
        String[] guess = new String[ROW_NUM];
        for (int r = 0; r < ROW_NUM; r++) {
            guess[r] = read.readLine();
            assert guess[r].length() == COL_NUM;
        }

        int green = 0;
        HashMap<Character, Integer> ansOcc = new HashMap<>();
        HashMap<Character, Integer> guessOcc = new HashMap<>();
        for (int r = 0; r < ROW_NUM; r++) {
            for (int c = 0; c < COL_NUM; c++) {
                char ansChar = ans[r].charAt(c);
                char guessChar = guess[r].charAt(c);
                if (ansChar == guessChar) {
                    green++;
                } else {
                    ansOcc.put(ansChar, ansOcc.getOrDefault(ansChar, 0) + 1);
                    guessOcc.put(guessChar, guessOcc.getOrDefault(guessChar, 0) + 1);
                }
            }
        }

        int yellow = 0;
        for (Map.Entry<Character, Integer> e : ansOcc.entrySet()) {
            yellow += Math.min(e.getValue(), guessOcc.getOrDefault(e.getKey(), 0));
        }

        System.out.println(green);
        System.out.println(yellow);
    }
}
