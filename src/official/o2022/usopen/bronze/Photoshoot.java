package official.o2022.usopen.bronze;

import java.io.*;

/**
 * 2022 us open bronze
 * 14
 * GGGHGHHGHHHGHG should output 1
 */
public final class Photoshoot {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());
        String cows = read.readLine();
        if (cows.length() != cowNum || !cows.replaceAll("[GH]", "").isEmpty() || cowNum % 2 != 0) {
            throw new IllegalArgumentException("invalid input");
        }

        int flips = 0;
        for (int c = cowNum - 2; c >= 0; c -= 2) {
            String sub = cows.substring(c, c + 2);
            if (sub.charAt(0) == sub.charAt(1)) {
                continue;
            }
            if ((sub.equals("GH") && flips % 2 == 0)
                    || (sub.equals("HG") && flips % 2 == 1)) {
                flips++;
            }
        }

        System.out.println(flips);
    }
}
