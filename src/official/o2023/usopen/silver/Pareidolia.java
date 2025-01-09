package official.o2023.usopen.silver;

import java.io.*;

/** 2023 us open silver (took a lil peek at edi unfortunately) */
public class Pareidolia {
    private static final String MATCH = "bessie";

    public static void main(String[] args) throws IOException {
        String str = new BufferedReader(new InputStreamReader(System.in)).readLine();

        int[] status = new int[MATCH.length()];
        long total = 0;
        for (int i = 0; i < str.length(); i++) {
            status[0]++;
            int loopBack = 0;
            for (int c = MATCH.length() - 1; c >= 0; c--) {
                if (str.charAt(i) == MATCH.charAt(c)) {
                    if (c == MATCH.length() - 1) {
                        total += (long) status[c] * (str.length() - i);
                        loopBack += status[c];
                    } else {
                        status[c + 1] += status[c];
                    }
                    status[c] = 0;
                }
            }
            status[0] += loopBack;
        }

        System.out.println(total);
    }
}
