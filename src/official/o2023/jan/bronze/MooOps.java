package official.o2023.jan.bronze;

import java.io.*;

/**
 * 2023 jan bronze
 * 3
 * MOMMOM
 * MMO
 * MOO should output 4, -1, and 0, each on a newline
 */
public class MooOps {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int queryNum = Integer.parseInt(read.readLine());
        for (int q = 0; q < queryNum; q++) {
            String str = read.readLine();

            int baseline = str.length() - 3;
            int minOps = Integer.MAX_VALUE;
            for (int i = 0; i < str.length() - 2; i++) {
                String substr = str.substring(i, i + 3);
                if (substr.charAt(1) == 'O') {
                    int needed = (
                            baseline
                                    + (substr.charAt(0) != 'M' ? 1 : 0)
                                    + (substr.charAt(2) != 'O' ? 1 : 0)
                    );
                    minOps = Math.min(minOps, needed);
                }
            }

            System.out.println(minOps == Integer.MAX_VALUE ? -1 : minOps);
        }
    }
}
