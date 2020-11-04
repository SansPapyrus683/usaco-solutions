import java.io.*;
import java.util.*;

public class Odometer {
    static final long[] powers = new long[19];
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < 19; i++) {
            powers[i] = (long) Math.pow(10, i);
        }

        BufferedReader read = new BufferedReader(new FileReader("odometer.in"));
        StringTokenizer info = new StringTokenizer(read.readLine());
        long start = Long.parseLong(info.nextToken());
        long end = Long.parseLong(info.nextToken());
        assert 100 <= start && start <= end;

        long mooTimes = interestingNum(start, end);
        PrintWriter written = new PrintWriter(new FileOutputStream("odometer.out"));
        written.println(mooTimes);
        written.close();
        System.out.println(mooTimes);
        System.out.printf("fj does everything with his cows lol (%d ms)", System.currentTimeMillis() - timeStart);
    }

    static boolean allZeroes(double n) {  // checks if a # is like N0000000000
        while (n > 9) {
            n /= 10;
        }
        return n == (int) n;
    }

    static long round(long input, int roundTo) {
        long base = (long) Math.pow(10, roundTo);
        return (input + base - 1) / base * base;
    }

    // copied from stackoverflow: https://stackoverflow.com/questions/2201113/combinatoric-n-choose-r-in-java-math
    static long choose(long n, long r) {
        long ans = 1;
        for (int i = 0; i < r; i++) {
            ans = ans * (n - i) / (i + 1);
        }
        return ans;
    }

    static long interestingNum(long start, long end) {  // find all interesting numbers from start to end (inclusive i think)
        if (!allZeroes(end)) {
            long upper = round(end, (int) Math.log10(end));
            long answer = interestingNum(start, upper);
            if (end < upper) {  // if the end is less than the upper bound, subtract the other range
                answer -= interestingNum(end + 1, upper);
            }
            return answer;
        }
        assert start <= end;
        if (start == end) {
            return 1;  // bc it's at least 100, the end is always interesting
        }

        int zeroNum = -1;
        for (int i = 1; i <= 18; i++) {
            if (start % powers[i] != 0) {
                zeroNum = i - 1;
                break;
            }
        }

        long interesting = 0;
        int startDigits = Long.toString(start).length();
        int firstNonZero = Integer.parseInt(Long.toString(start).substring(startDigits - zeroNum - 1, startDigits - zeroNum));
        int requirement = startDigits % 2 == 0 ? startDigits / 2 : startDigits / 2 + 1;

        int[] setInStone = new int[10];  // this[i] = how many digits set in stone for i
        for (int i = 0; i < startDigits - zeroNum - 1; i++) {
            setInStone[Integer.parseInt(Long.toString(start).substring(i, i + 1))]++;
        }

        boolean lastTime = zeroNum == Long.toString(end).length() - 1;
        int limit = lastTime ? Integer.parseInt(Long.toString(end).substring(0, 1)) : 10;
        for (int i = firstNonZero; i < limit; i++) {  // go through all possible numbers for the 1st nonzero digit
            setInStone[i]++;
            for (int majNum = 0; majNum <= 9; majNum++) {
                if (setInStone[majNum] + zeroNum < requirement) {  // check if the majority can actually work
                    continue;
                }
                for (int j = Math.max(requirement - setInStone[majNum], 0); j <= zeroNum; j++) {
                    interesting += choose(zeroNum, j) * (long) Math.pow(9, (zeroNum - j));
                }
            }

            // we might've overcounted numbers like 1199, because in those 1 and 9 are "majorities"
            if (startDigits % 2 == 0) {  // dupes only happen if there's an even # of digits
                ArrayList<int[]> havedNumbers = new ArrayList<>();
                for (int n = 0; n < 10; n++) {
                    if (setInStone[n] != 0) {
                        havedNumbers.add(new int[]{n, setInStone[n]});
                    }
                }
                if (havedNumbers.size() <= 2) {
                    int chooseNum = requirement - havedNumbers.get(0)[1];  // doesn't matter which one we choose
                    interesting -= havedNumbers.size() == 1 ? choose(zeroNum, chooseNum) * 9 : choose(zeroNum, chooseNum);
                }
            }
            setInStone[i]--;  // revert the changes made by that first digit
        }
        // +1 bc it's the last time so we just include end, which will always be interesting
        return lastTime ? interesting + 1 : interesting + interestingNum(round(start, zeroNum + 1), end);
    }
}
