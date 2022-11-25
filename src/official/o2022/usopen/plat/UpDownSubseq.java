package official.o2022.usopen.plat;

import java.io.*;
import java.util.*;

/**
 * 2022 us open platinum
 * 5
 * 1 5 3 4 2
 * UUDD should output 3
 */
public class UpDownSubseq {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int len = Integer.parseInt(read.readLine());
        int[] arr = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        assert arr.length == len;
        String relStr = read.readLine();

        System.out.println(Arrays.toString(arr));
        System.out.println(relStr);
    }
}
