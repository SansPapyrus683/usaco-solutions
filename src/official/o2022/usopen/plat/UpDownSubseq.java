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
        // just gonna assume this is a permutation
        int[] arr = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        String relStr = read.readLine();
        assert arr.length == len && relStr.length() == len - 1;

        int[] best = new int[len];
        Arrays.fill(best, -1);
        best[0] = arr[0];
        for (int i = 1; i < len; i++) {
            for (int j = len - 1; j >= 0; j--) {
                if (j > 0) {
                    char req = relStr.charAt(j - 1);
                    if (best[j - 1] == -1
                            || (req == 'U' && arr[i] < best[j - 1])
                            || (req == 'D' && arr[i] > best[j - 1])) {
                        continue;
                    }
                }

                if (j < len - 1) {
                    if (relStr.charAt(j) == 'U') {
                        best[j] = best[j] == -1 ? arr[i] : Math.min(best[j], arr[i]);
                    } else if (relStr.charAt(j) == 'D') {
                        best[j] = best[j] == -1 ? arr[i] : Math.max(best[j], arr[i]);
                    }
                } else {
                    best[j] = arr[i];
                }
            }
        }

        // output the longest sequence that has been made
        for (int i = len - 1; i >= 0; i--) {
            if (best[i] != -1) {
                System.out.println(i);
                break;
            }
        }
    }
}
