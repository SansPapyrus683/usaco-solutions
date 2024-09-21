package official.o2024.feb.silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Moorbles {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            StringTokenizer initial = new StringTokenizer(read.readLine());
            int initMarbles = Integer.parseInt(initial.nextToken());
            int turnNum = Integer.parseInt(initial.nextToken());
            int playNum = Integer.parseInt(initial.nextToken());
            int[][] choices = new int[turnNum][];
            for (int turn = 0; turn < turnNum; turn++) {
                StringTokenizer playST = new StringTokenizer(read.readLine());
                int[] play = new int[playNum];
                for (int p = 0; p < playNum; p++) {
                    play[p] = Integer.parseInt(playST.nextToken());
                    if (play[p] % 2 == 1) {
                        play[p] *= -1;
                    }
                }
                Arrays.sort(play);
                choices[turn] = new int[]{play[0], -play[playNum - 1]};
            }

            // i could probably combine this and the other one into one loop
            long[] suffChanges = new long[turnNum + 1];
            for (int turn = turnNum - 1; turn >= 0; turn--) {
                int meta = Math.max(choices[turn][0], choices[turn][1]);
                suffChanges[turn] = suffChanges[turn + 1] - meta;
            }

            long[] mostDamage = new long[turnNum + 1];
            long currMin = 0;
            for (int turn = turnNum - 1; turn >= 0; turn--) {
                mostDamage[turn] = Math.max(suffChanges[turn] - currMin, 0);
                currMin = Math.min(currMin, suffChanges[turn]);
            }

            if (mostDamage[0] >= initMarbles) {
                System.out.println(-1);
                continue;
            }

            StringBuilder ans = new StringBuilder();
            int marbles = initMarbles;
            for (int turn = 0; turn < turnNum; turn++) {
                if (marbles + choices[turn][0] > mostDamage[turn + 1]) {
                    ans.append("Even");
                    marbles += choices[turn][0];
                } else {
                    ans.append("Odd");
                    marbles += choices[turn][1];
                }
                ans.append(' ');
            }
            ans.deleteCharAt(ans.length() - 1);

            System.out.println(ans);
        }
    }
}
