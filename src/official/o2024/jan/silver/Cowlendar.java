package official.o2024.jan.silver;

import java.io.*;
import java.util.*;

/**
 * 2024 jan silver
 * 12
 * 31 28 31 30 31 30 31 31 30 31 30 31 should output 28
 */
public class Cowlendar {
    private static final int MIN_WEEKS = 4;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int monthNum = Integer.parseInt(read.readLine());
        TreeSet<Long> months = new TreeSet<>();
        StringTokenizer monthST = new StringTokenizer(read.readLine());
        for (int m = 0; m < monthNum; m++) {
            months.add(Long.parseLong(monthST.nextToken()));
        }

        long maxWeek = months.first() / MIN_WEEKS;
        if (months.size() <= 3) {
            System.out.println(maxWeek * (maxWeek + 1) / 2);
            return;
        }

        List<Long> firstFour = new ArrayList<>();
        TreeSet<Long> candidates = new TreeSet<>();
        for (long m : months) {
            firstFour.add(m);
            if (firstFour.size() == 4) {
                break;
            }
        }
        for (int i = 0; i < firstFour.size(); i++) {
            for (int j = i + 1; j < firstFour.size(); j++) {
                candidates.addAll(divisors(firstFour.get(j) - firstFour.get(i)));
            }
        }

        long validSum = 0;
        for (long week : candidates) {
            if (week > maxWeek) {
                break;  // since i used a treeset instead of a hashset i can do this
            }
            Set<Long> mods = new HashSet<>();
            for (long m : months) {
                mods.add(m % week);
                if (mods.size() > 3) {
                    break;
                }
            }
            validSum += mods.size() > 3 ? 0 : week;
        }

        System.out.println(validSum);
    }

    private static List<Long> divisors(long n) {
        List<Long> ret = new ArrayList<>();
        for (long i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                ret.add(i);
                if (i * i != n) {
                    ret.add(n / i);
                }
            }
        }
        return ret;
    }
}
