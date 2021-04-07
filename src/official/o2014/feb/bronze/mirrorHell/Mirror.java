// package official.o2014.feb.bronze.mirrorHell;

import java.io.*;
import java.util.*;

// 2014 feb bronze
public final class Mirror {
    private enum Dir { UP, DOWN, LEFT, RIGHT }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("mirror.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int rowNum = Integer.parseInt(initial.nextToken());
        int colNum = Integer.parseInt(initial.nextToken());
        boolean[][] mirrors = new boolean[rowNum][colNum];  // true is a /, false is a \
        for (int r = 0; r < rowNum; r++) {
            String row = read.readLine();
            for (int c = 0; c < colNum; c++) {
                mirrors[r][c] = row.charAt(c) == '/';
            }
        }

        int maxReflected = 0;
        for (int r = 0; r < rowNum; r++) {
            maxReflected = Math.max(maxReflected, reflectedAmt(mirrors, new int[] {r, 0}, Dir.LEFT));
            maxReflected = Math.max(maxReflected, reflectedAmt(mirrors, new int[] {r, colNum - 1}, Dir.RIGHT));
        }
        for (int c = 0; c < colNum; c++) {
            maxReflected = Math.max(maxReflected, reflectedAmt(mirrors, new int[] {0, c}, Dir.DOWN));
            maxReflected = Math.max(maxReflected, reflectedAmt(mirrors, new int[] {rowNum - 1, c}, Dir.UP));
        }
        PrintWriter written = new PrintWriter("mirror.out");
        written.println(maxReflected);
        written.close();
        System.out.println(maxReflected);
        System.out.printf("where do you get all those mirrors lol: %d ms%n", System.currentTimeMillis() - start);
    }

    private static int reflectedAmt(boolean[][] mirrors, int[] pos, Dir dir) {
        int times = 0;
        while (0 <= pos[0] && pos[0] < mirrors.length && 0 <= pos[1] && pos[1] < mirrors[0].length) {
            boolean mirror = mirrors[pos[0]][pos[1]];
            if (mirror) {  // the mirror is a "/"
                switch (dir) {  // switch based on the incoming direction
                    case UP:
                        dir = Dir.LEFT;
                        pos[1]++;
                        break;
                    case DOWN:
                        dir = Dir.RIGHT;
                        pos[1]--;
                        break;
                    case LEFT:
                        dir = Dir.UP;
                        pos[0]--;
                        break;
                    case RIGHT:
                        dir = Dir.DOWN;
                        pos[0]++;
                        break;
                }
            } else {  // the mirror is a "\"
                switch (dir) {
                    case UP:
                        dir = Dir.RIGHT;
                        pos[1]--;
                        break;
                    case DOWN:
                        dir = Dir.LEFT;
                        pos[1]++;
                        break;
                    case LEFT:
                        dir = Dir.DOWN;
                        pos[0]++;
                        break;
                    case RIGHT:
                        dir = Dir.UP;
                        pos[0]--;
                        break;
                }
            }
            times++;
        }
        return times;
    }
}
