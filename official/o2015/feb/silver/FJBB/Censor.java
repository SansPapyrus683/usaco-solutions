import java.io.*;
import java.util.*;

// 2015 feb silver
public class Censor {
    private static final long MOD = (long) (Math.pow(10, 9)) + 9;
    private static final long POWER = 31;  // some website told me to do this
    static long[] hashPowers = new long[1000000];

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        long nowPower = 1;
        for (int i = 0; i < 1000000; i++) {  // precompute power (takes surprisingly little time)
            hashPowers[i] = nowPower;
            nowPower = (nowPower * POWER) % MOD;
        }

        BufferedReader read = new BufferedReader(new FileReader("censor.in"));
        String toCensor = read.readLine();  // remember kids, nothing happened on june 4th 1989
        String badWord = read.readLine();

        PrintWriter written = new PrintWriter(new FileOutputStream("censor.out"));
        String after = clean(toCensor, badWord);
        written.println(after);
        written.close();
        System.out.println(after);
        System.out.printf("our great leader fj says it took %d ms so it did%n", System.currentTimeMillis() - start);
    }

    static long hashConcat(long rnHashVal, int nowStrLen, long toAdd) {
        return (rnHashVal + toAdd * hashPowers[nowStrLen]) % MOD;
    }

    static String clean(String toCensor, String badWord) {
        long badHashCode = 0;  // hashcode for the badword to censor
        for (int i = 0; i < badWord.length(); i++) {
            // - 'a' + 1 just translates a to 1, b to 2, etc...
            badHashCode = hashConcat(badHashCode, i, badWord.charAt(i) - 'a' + 1);
        }

        char[] all = new char[toCensor.length()];
        long[] prevHashCodes = new long[toCensor.length() + 1];  // + 1 for the empty string, which has a has of 0
        int index = 0;
        for (char c : toCensor.toCharArray()) {
            all[index++] = c;
            
            int headIndex = Math.max(index - badWord.length(), 0);
            prevHashCodes[index] = hashConcat(prevHashCodes[index - 1], index - 1, c - 'a' + 1);
            long currViewHash = (prevHashCodes[index] - prevHashCodes[headIndex]) % MOD;
            currViewHash += currViewHash >= 0 ? 0 : MOD;  // java's modulus function sucks butt
            if (currViewHash == (badHashCode * hashPowers[headIndex]) % MOD) {  // no divisibility issues now ha
                index -= badWord.length();  // reset the pointer back to "checkpoint"? idk
            }
        }
        return new String(Arrays.copyOfRange(all, 0, index));
    }
}
