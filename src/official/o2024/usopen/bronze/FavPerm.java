package official.o2024.usopen.bronze;

import java.io.*;
import java.util.*;

/** 2024 us open bronze */
public class FavPerm {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            int permLen = Integer.parseInt(read.readLine());
            int[] hints =
                    Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            assert hints.length == permLen - 1;

            if (hints[hints.length - 1] != 1) {
                System.out.println(-1);
                continue;
            }

            Set<Integer> has = new HashSet<>();
            for (int i = 1; i <= permLen; i++) {
                has.add(i);
            }
            int oneCount = 0;
            for (int h : hints) {
                has.remove(h);
                oneCount += h == 1 ? 1 : 0;
            }

            if (oneCount == 1) {
                if (has.size() > 1) {
                    System.out.println(-1);
                } else {
                    StringBuilder ans = new StringBuilder();
                    for (int i = hints.length - 1; i >= 0; i--) {
                        ans.append(hints[i]).append(' ');
                    }
                    ans.append(has.iterator().next());
                    System.out.println(ans);
                }
                continue;
            }

            if (has.size() > 2 || oneCount > 2) {
                System.out.println(-1);
                continue;
            }

            Iterator<Integer> it = has.iterator();
            int[] arr = new int[permLen];
            arr[0] = it.next();
            arr[permLen - 1] = it.next();
            if (arr[0] > arr[permLen - 1]) {
                int temp = arr[0];
                arr[0] = arr[permLen - 1];
                arr[permLen - 1] = temp;
            }

            int left = 0;
            int right = permLen - 1;
            for (int h : hints) {
                if (arr[left] > arr[right]) {
                    arr[++left] = h;
                } else {
                    arr[--right] = h;
                }
            }

            StringBuilder ans = new StringBuilder();
            for (int i = 0; i < permLen; i++) {
                ans.append(arr[i]).append(i == permLen - 1 ? '\n' : ' ');
            }
            System.out.print(ans);
        }
    }
}
