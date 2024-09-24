package official.o2024.feb.bronze;

import java.io.*;

/** 2024 feb bronze */
public class PalindromeGame {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            String stoneNum = read.readLine();
            // HAHAHAHAAHAHAHAHAHAHAHAHAHAHA
            int lastDig = Character.getNumericValue(stoneNum.charAt(stoneNum.length() - 1));
            System.out.println(lastDig == 0 ? 'E' : 'B');
        }
    }
}
