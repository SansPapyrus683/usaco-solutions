package official.o2025.jan.silver;

import java.io.*;
import java.util.*;

/** 2025 jan silver */
public class CowCheckups {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());
        int[] line = Arrays.stream(read.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        int[] check = Arrays.stream(read.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        Map<Integer, List<Integer>> lineBreeds = new HashMap<>();
        Map<Integer, Set<Integer>> checkBreeds = new HashMap<>();
        for (int c = 0; c < cowNum; c++) {
            if (!lineBreeds.containsKey(line[c])) {
                lineBreeds.put(line[c], new ArrayList<>());
            }
            lineBreeds.get(line[c]).add(c);
            if (!checkBreeds.containsKey(check[c])) {
                checkBreeds.put(check[c], new HashSet<>());
            }
            checkBreeds.get(check[c]).add(c);
        }

        long checkSum = 0;
        for (Map.Entry<Integer, List<Integer>> b : lineBreeds.entrySet()) {
            final Set<Integer> vet = checkBreeds.getOrDefault(b.getKey(), new HashSet<>());
            int[] vetDists = new int[vet.size()];
            int at = 0;
            for (int pos : vet) {
                vetDists[at++] = Math.min(pos + 1, cowNum - pos);
            }

            Arrays.sort(vetDists);
            long[] prefDists = new long[vetDists.length + 1];
            for (int i = 0; i < vetDists.length; i++) {
                prefDists[i + 1] = prefDists[i] + vetDists[i];
            }

            final List<Integer> fj = b.getValue();
            for (int pos : fj) {
                final int dist = Math.min(pos + 1, cowNum - pos);
                final int ind = largestLt(vetDists, dist);
                checkSum += (long) dist * (vetDists.length - ind - 1) + prefDists[ind + 1];
                if (vet.contains(pos)) {
                    final long left = (long) pos * (pos + 1) / 2;
                    final long right = (long) (cowNum - pos - 1) * (cowNum - pos) / 2;
                    checkSum += left + right;
                }
            }
        }

        System.out.println(checkSum);
    }

    /** copied from painting fence */
    public static int largestLt(int[] a, int i) {
        int lo = 0;
        int hi = a.length - 1;
        int valid = -1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (a[mid] < i) {
                valid = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return valid;
    }
}
