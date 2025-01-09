package official.o2023.usopen.bronze;

import java.io.*;
import java.util.*;

/** 2023 us open bronze */
public class FEB {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int len = Integer.parseInt(read.readLine());
        String msg = "X" + read.readLine() + "X";
        assert msg.length() == len;

        int base = 0;
        for (int i = 1; i < len; i++) {
            char curr = msg.charAt(i);
            base += (curr != 'F' && curr == msg.charAt(i + 1)) ? 1 : 0;
        }

        int evenTotal = 0;
        int allTotal = 0;
        for (int i = 1; i < msg.length(); i++) {
            if (msg.charAt(i) != 'F') {
                continue;
            }
            int rangeStart = i;
            while (msg.charAt(i) == 'F') {
                i++;
            }

            char b4 = msg.charAt(rangeStart - 1);
            char after = msg.charAt(i);
            int rangeLen = i - rangeStart;
            if (b4 == 'X' || after == 'X') {
                allTotal += rangeLen - (b4 == 'X' && after == 'X' ? 1 : 0);
            } else if ((b4 == after) != (rangeLen % 2 == 0)) {
                evenTotal += rangeLen + rangeLen % 2;
            } else {
                base++;
                evenTotal += rangeLen - rangeLen % 2;
            }
        }

        List<Integer> possible = new ArrayList<>();
        int inc = allTotal == 0 ? 2 : 1;
        for (int i = 0; i <= evenTotal + allTotal; i += inc) {
            possible.add(base + i);
        }
        System.out.println(possible.size());
        possible.forEach(System.out::println);
    }
}
