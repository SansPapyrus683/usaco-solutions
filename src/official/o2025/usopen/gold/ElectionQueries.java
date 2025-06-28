package official.o2025.usopen.gold;

import java.io.*;
import java.util.*;

/** 2025 us open gold (damn actually had to pull out kattio what) */
public class ElectionQueries {
    public static void main(String[] args) throws IOException {
        Kattio io = new Kattio();
        int cowNum = io.nextInt();
        int queryNum = io.nextInt();

        int[] voteFor = new int[cowNum];
        int[] freq = new int[cowNum];
        for (int c = 0; c < cowNum; c++) {
            voteFor[c] = io.nextInt() - 1;
            freq[voteFor[c]]++;
        }

        Map<Integer, TreeSet<Integer>> byFreq = new HashMap<>();
        for (int c = 0; c < cowNum; c++) {
            if (!byFreq.containsKey(freq[c])) {
                byFreq.put(freq[c], new TreeSet<>());
            }
            byFreq.get(freq[c]).add(c);
        }
        TreeMap<Integer, int[]> ends = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Integer, TreeSet<Integer>> f : byFreq.entrySet()) {
            ends.put(f.getKey(), new int[] { f.getValue().first(), f.getValue().last() });
        }

        StringBuilder ans = new StringBuilder();
        for (int q = 0; q < queryNum; q++) {
            int cow = io.nextInt() - 1;
            int newCow = io.nextInt() - 1;
            int oldCow = voteFor[cow];
            voteFor[cow] = newCow;

            Set<Integer> toUpd = new HashSet<>(Arrays.asList(
                    freq[oldCow], freq[oldCow] - 1, freq[newCow], freq[newCow] + 1));
            byFreq.get(freq[oldCow]).remove(oldCow);
            byFreq.get(freq[newCow]).remove(newCow);
            freq[oldCow]--;
            freq[newCow]++;
            byFreq.computeIfAbsent(freq[oldCow], k -> new TreeSet<>()).add(oldCow);
            byFreq.computeIfAbsent(freq[newCow], k -> new TreeSet<>()).add(newCow);

            for (int u : toUpd) {
                if (byFreq.getOrDefault(u, new TreeSet<>()).isEmpty()) {
                    ends.remove(u);
                    byFreq.remove(u);
                } else {
                    TreeSet<Integer> cows = byFreq.get(u);
                    ends.put(u, new int[] { cows.first(), cows.last() });
                }
            }

            int[] freqs = new int[byFreq.size()];
            int[] suffMin = new int[byFreq.size()];
            int[] suffMax = new int[byFreq.size()];
            int at = byFreq.size() - 1;
            for (Map.Entry<Integer, int[]> f : ends.entrySet()) {
                freqs[at] = f.getKey();
                suffMin[at] = f.getValue()[0];
                suffMax[at] = f.getValue()[1];
                at--;
            }
            for (int i = byFreq.size() - 2; i >= 0; i--) {
                suffMin[i] = Math.min(suffMin[i], suffMin[i + 1]);
                suffMax[i] = Math.max(suffMax[i], suffMax[i + 1]);
            }

            int right = byFreq.size() - 1;
            int best = 0;
            for (int left = 1; left < byFreq.size(); left++) {
                while (right > 1 && freqs[left] + freqs[right - 1] >= freqs[byFreq.size() - 1]) {
                    right--;
                }
                best = Math.max(best, Math.abs(suffMax[left] - suffMin[right]));
            }

            ans.append(best).append('\n');
        }

        io.print(ans);
        io.close();
    }
}

/** sauce: https://usaco.guide/general/input-output?lang=java#method-3---io-template */
class Kattio extends PrintWriter {
    private BufferedReader r;
    private StringTokenizer st;

    public Kattio() {
        this(System.in, System.out);
    }

    public Kattio(InputStream i, OutputStream o) {
        super(o);
        r = new BufferedReader(new InputStreamReader(i));
    }

    public String next() {
        try {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(r.readLine());
            return st.nextToken();
        } catch (Exception e) {
        }
        return null;
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }
}
