package official.o2020.jan.silver.bessieIRS;

import java.io.*;
import java.util.*;

// 2020 jan silver
public final class Loan {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        StringTokenizer initial = new StringTokenizer(new BufferedReader(new FileReader("loan.in")).readLine());
        long milkOwed = Long.parseLong(initial.nextToken());
        long deadline = Long.parseLong(initial.nextToken());
        long dayReq = Long.parseLong(initial.nextToken());
        if (deadline * dayReq >= milkOwed) {
            throw new IllegalArgumentException("invalid input (according to the problem)");
        }

        long validSoFar = -1;
        long lowerBound = 1;
        long upperBound = (long) Math.pow(10, 12);
        while (lowerBound <= upperBound) {
            long toSearch = (lowerBound + upperBound) / 2;
            if (paidBeforeDeadline(milkOwed, deadline, dayReq, toSearch)) {
                lowerBound = toSearch + 1;
                validSoFar = toSearch;
            } else {
                upperBound = toSearch - 1;
            }
        }

        PrintWriter written = new PrintWriter("loan.out");
        written.println(validSoFar);
        written.close();
        System.out.println(validSoFar);
        System.out.printf("for you, it took %d ms... acceptable.%n", System.currentTimeMillis() - start);
    }

    static boolean paidBeforeDeadline(long milkOwed, long deadline, long dayReq, long xVal) {
        long paidSoFar = 0;
        long daysLeft = deadline;
        while (daysLeft > 0 && paidSoFar < milkOwed) {
            long initialPay = (milkOwed - paidSoFar) / xVal;
            if (initialPay <= dayReq) {  // at some point, it'll fall below the dayReq so we can immediately calc that
                return daysLeft * dayReq >= milkOwed - paidSoFar;
            }
            // how many days fj will pay bessie initialPay
            long thisPayDays = ((milkOwed - paidSoFar) - (xVal * initialPay - 1)) / initialPay;
            thisPayDays = Math.min(Math.max(thisPayDays, 1), daysLeft);
            paidSoFar += thisPayDays * initialPay;  // simulate a buncha days at once to make it faster
            daysLeft -= thisPayDays;
        }
        return paidSoFar >= milkOwed;
    }
}
