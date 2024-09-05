package official.o2023.dec.silver;

import java.io.*;
import java.util.*;

// 2023 dec silver
public class CycleCorrespondence {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int barnNum = Integer.parseInt(initial.nextToken());
        int connNum = Integer.parseInt(initial.nextToken());
        int[] anna = Arrays.stream(read.readLine().split(" "))
                .mapToInt(b -> Integer.parseInt(b) - 1).toArray();
        int[] bessie = Arrays.stream(read.readLine().split(" "))
                .mapToInt(b -> Integer.parseInt(b) - 1).toArray();
        assert anna.length == connNum && bessie.length == connNum;

        Set<Integer> notInAny = new HashSet<>();
        for (int b = 0; b < barnNum; b++) {
            notInAny.add(b);
        }
        for (int i = 0; i < connNum; i++) {
            notInAny.remove(anna[i]);
        }
        for (int b : bessie) {
            notInAny.remove(b);
        }

        int maxMatch = maxShiftEq(anna, bessie);
        for (int i = 0; i < connNum / 2; i++) {
            int temp = bessie[i];
            bessie[i] = bessie[connNum - 1 - i];
            bessie[connNum - 1 - i] = temp;
        }
        maxMatch = Math.max(maxMatch, maxShiftEq(anna, bessie));

        System.out.println(notInAny.size() + maxMatch);
    }

    private static int maxShiftEq(int[] a, int[] b) {
        assert a.length == b.length;
        Map<Integer, Integer> aPos = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            aPos.put(a[i], i);
        }
        Map<Integer, Integer> deltas = new HashMap<>();
        for (int i = 0; i < b.length; i++) {
            if (!aPos.containsKey(b[i])) {
                continue;
            }
            int aMatch = aPos.get(b[i]);
            int delta;
            if (i < aMatch) {
                delta = aMatch - i;
            } else {
                delta = b.length - (i - aMatch);
            }
            deltas.put(delta, deltas.getOrDefault(delta, 0) + 1);
        }
        return deltas.values().stream().max(Integer::compareTo).orElse(0);
    }
}
