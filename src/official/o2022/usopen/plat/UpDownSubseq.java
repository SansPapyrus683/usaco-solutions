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
        // just gonna assume this is a permutation of 0, 1, 2, 3 ... len - 1
        int[] arr = Arrays.stream(read.readLine().split(" "))
                .mapToInt(i -> Integer.parseInt(i) - 1).toArray();
        String relStr = read.readLine();
        assert arr.length == len && relStr.length() == len - 1;

        int[] best = new int[len];
        Arrays.fill(best, -1);
        best[0] = arr[0];

        MaxSegTree greater = new MaxSegTree(len);
        MaxSegTree lesser = new MaxSegTree(len);
        for (int i = 0; i < len; i++) {
            greater.set(i, -1);
            lesser.set(i, -1);
        }
        if (relStr.charAt(0) == 'U') {
            greater.set(arr[0], 0);
        } else if (relStr.charAt(0) == 'D') {
            lesser.set(arr[0], 0);
        }

        for (int i = 1; i < len; i++) {
            int lesserMax = lesser.rangeMax(arr[i] + 1, len);
            int greaterMax = greater.rangeMax(0, arr[i] + 1);
            int ind = Math.max(lesserMax, greaterMax);
            if (ind < len - 2) {
                char target = relStr.charAt(ind + 1);
                if (target == 'U') {
                    best[ind + 1] = best[ind + 1] == -1 ? arr[i]
                            : Math.min(best[ind + 1], arr[i]);
                    greater.set(best[ind + 1], ind + 1);
                } else if (target == 'D') {
                    best[ind + 1] = best[ind + 1] == -1 ? arr[i]
                            : Math.max(best[ind + 1], arr[i]);
                    lesser.set(best[ind + 1], ind + 1);
                }
            } else {
                best[ind + 1] = arr[i];
                break;
            }
        }

        for (int i = len - 1; i >= 0; i--) {
            if (best[i] != -1) {
                System.out.println(i);
                break;
            }
        }
    }
}

class MaxSegTree {
    private final int[] segtree;
    private final int len;
    public MaxSegTree(int len) {  // constructs the thing kinda like an array
        this.len = len;
        segtree = new int[len * 2];  // note: we won't use index 0
    }

    public void set(int ind, int val) {
        assert 0 <= ind && ind < len;
        for (segtree[ind += len] = val; ind > 1; ind >>= 1) {
            segtree[ind >> 1] = Math.max(segtree[ind], segtree[ind ^ 1]);
        }
    }

    int rangeMax(int from, int to) {  // minimum from [from, to)
        assert from <= to && 0 <= from && from <= len && 0 < to && to <= len;
        int max = Integer.MIN_VALUE;
        for (from += len, to += len; from < to; from >>= 1, to >>= 1) {
            if ((from & 1) != 0) {
                max = Math.max(max, segtree[from++]);
            }
            if ((to & 1) != 0) {
                max = Math.max(max, segtree[--to]);
            }
        }
        return max;
    }
}
