package official.o2020.feb.bronze.billNyeFam;

import java.io.*;

public class BreedFlip {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("breedflip.in"));
        int cowNum = Integer.parseInt(read.readLine());
        String needed = read.readLine();
        String haveRn = read.readLine();
        assert needed.length() == cowNum && haveRn.length() == cowNum;

        boolean[] isDiff = new boolean[cowNum];
        for (int c = 0; c < cowNum; c++) {
            isDiff[c] = needed.charAt(c) != haveRn.charAt(c);
        }

        int consecNum = 0;
        for (int i = 1; i < cowNum; i++) {
            if (!isDiff[i] && isDiff[i - 1]) {
                consecNum++;
            }
        }
        consecNum += isDiff[cowNum - 1] ? 1 : 0;

        PrintWriter written = new PrintWriter("breedflip.out");
        written.println(consecNum);
        written.close();
    }
}
