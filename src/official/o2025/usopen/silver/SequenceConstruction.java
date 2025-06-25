package official.o2025.usopen.silver;

import java.io.*;
import java.util.*;

public class SequenceConstruction {
    private static final int WHAT = 5;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            StringTokenizer data = new StringTokenizer(read.readLine());
            int sum = Integer.parseInt(data.nextToken());
            int popXor = Integer.parseInt(data.nextToken());
            assert (0 < popXor && popXor < (1 << WHAT));

            List<Integer> out = new ArrayList<>();
            for (int i = 0; i < WHAT; i++) {
                if ((popXor & (1 << i)) != 0) {
                    int bitNum = 1 << i;
                    int toAdd = (1 << bitNum) - 1;
                    out.add(toAdd);
                    sum -= toAdd;
                }
            }

            if (sum < 0) {
                System.out.println(-1);
                continue;
            }
            if (sum == 1) {
                if (out.remove((Integer) 1)) {
                    out.add(2);
                    sum -= 1;
                } else {
                    System.out.println(-1);
                    continue;
                }
            }

            List<Integer> toSet = new ArrayList<>();
            for (int i = 0; i < 32; i++) {
                if ((sum & (1 << i)) != 0) {
                    toSet.add(i);
                }
            }

            for (int i = 0; i < toSet.size(); i++) {
                int curr = toSet.get(i);
                if (curr == 0) {
                    out.add(1 << curr);
                    out.add(1 << (toSet.get(i + 1)));
                    i++;
                } else {
                    out.add(1 << (curr - 1));
                    out.add(1 << (curr - 1));
                }
            }

            System.out.println(out.size());
            for (int i = 0; i < out.size(); i++) {
                System.out.printf("%d%s", out.get(i), i == out.size() - 1 ? '\n' : ' ');
            }
        }
    }
}
