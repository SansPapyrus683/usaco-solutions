package official.o2023.dec.gold;

import java.io.*;
import java.util.BitSet;

// 2023 dec gold
public class FlightRoutes {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cityNum = Integer.parseInt(read.readLine());
        BitSet[] parity = new BitSet[cityNum];
        for (int c = 0; c < cityNum - 1; c++) {
            String parities = read.readLine();
            parity[c] = new BitSet(cityNum);
            for (int i = 0; i < parities.length(); i++) {
                if (parities.charAt(i) == '1') {
                    parity[c].set(c + i + 1);
                }
            }
        }
        parity[cityNum - 1] = new BitSet(cityNum);

        int flightNum = 0;
        for (int c = cityNum - 2; c >= 0; c--) {
            BitSet currParities = new BitSet(cityNum);
            for (int i = 0; i < cityNum - 1 - c; i++) {
                int flyTo = c + i + 1;
                if (currParities.get(flyTo) != parity[c].get(flyTo)) {
                    currParities.flip(flyTo);  // not really necessary, more for debugging purposes
                    currParities.xor(parity[flyTo]);
                    flightNum++;
                }
            }
        }

        System.out.println(flightNum);
    }
}
