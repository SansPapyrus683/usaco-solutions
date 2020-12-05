package official.o2016.feb.whyCirclesTho;

import java.io.*;
import java.util.*;

// 2016 feb silver (works for gold too lol)
public class CBarn {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cbarn.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[][] barn = new int[cowNum][2];
        for (int i = 0; i < cowNum; i++) {
            // the second i stores the rooms we've travelled already in case
            // a cow has to travel even further in another iteration around the circle
            barn[i] = new int[] {Integer.parseInt(read.readLine()), 0};
        }

        
        long energy = 0;  // long for gold accommodation
        while (Arrays.stream(barn).anyMatch(i -> i[0] == 0)) {
            // keep on going around the barn counterclockwise until we get no 0's (which means every room's filled)
            for (int i = barn.length - 1; i >= 0; i--) {
                if (barn[i][0] != 0) {
                    continue;
                }
                int pos = i;
                while (barn[pos][0] == 0) {  // find the nearest nonzero room
                    pos--;
                    pos += pos < 0 ? barn.length : 0;
                }
                int distance = i - pos < 0 ? i - pos + barn.length : i - pos;
                energy = energy - (int) Math.pow(barn[pos][1], 2) + (int) Math.pow(barn[pos][1] + distance, 2);
                barn[pos][0]--;
                barn[i] = new int[] {1, distance};  // remember the distance travelled
            }
        }

        PrintWriter written = new PrintWriter("cbarn.out");
        written.println(energy);
        written.close();
        System.out.println(energy);
        System.out.printf("%d ms- you should be ashamed of yourself for writing such horrid code%n", System.currentTimeMillis() - start);
    }
}
