package official.o2022.dec.bronze;

import java.io.*;
import java.util.Arrays;

/** 2022 dec bronze */
public class CowCollege {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());
        int[] cows = Arrays.stream(read.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        assert cows.length == cowNum;

        Arrays.sort(cows);
        long maxMoney = 0;
        int tuition = 0;
        for (int i = 0; i < cowNum; i++) {
            long currMoney = (long) (cowNum - i) * cows[i];
            if (currMoney > maxMoney) {
                maxMoney = currMoney;
                tuition = cows[i];
            }
        }

        System.out.println(maxMoney + " " + tuition);
    }
}
