package official.o2023.dec.bronze;

import java.io.*;
import java.util.*;

public class Cowntact {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

        int cowNum = Integer.parseInt(read.readLine());
        String cows = read.readLine();
        assert cowNum == cows.length();

        String[] sickRuns = cows.split("0");
        if (cows.endsWith("0")) {
            sickRuns = Arrays.copyOf(sickRuns, sickRuns.length + 1);
            sickRuns[sickRuns.length - 1] = "";
        }

        int maxDays = Integer.MAX_VALUE;
        for (int i = 0; i < sickRuns.length; i++) {
            if (sickRuns[i].isEmpty()) {
                continue;
            }

            if (i == 0 || i == sickRuns.length - 1) {
                maxDays = Math.min(maxDays, sickRuns[i].length() - 1);
            } else {
                maxDays = Math.min(maxDays, (sickRuns[i].length() - 1) / 2);
            }
        }

        int minSick = 0;
        for (String sickRun : sickRuns) {
            if (sickRun.isEmpty()) {
                continue;
            }
            minSick += (int) Math.ceil((double) sickRun.length() / (2 * maxDays + 1));
        }

        System.out.println(minSick);
    }
}
