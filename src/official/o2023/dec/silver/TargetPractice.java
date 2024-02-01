package official.o2023.dec.silver;

import java.io.*;
import java.util.*;

/**
 * 2023 dec silver
 * 3 7
 * 0 -1 1
 * LFFRFRR should output 3
 */
public class TargetPractice {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(read.readLine());
        int targetNum = Integer.parseInt(st.nextToken());
        int cmdNum = Integer.parseInt(st.nextToken());

        Set<Integer> targets = new HashSet<>();
        st = new StringTokenizer(read.readLine());
        for (int t = 0; t < targetNum; t++) {
            targets.add(Integer.parseInt(st.nextToken()));
        }

        String cmd = read.readLine();
        assert cmdNum == cmd.length();

        Map<Integer, Map<Integer, Integer>> dHits = new HashMap<>();
        for (int d = -2; d <= 2; d++) {
            dHits.put(d, new HashMap<>());
        }
        int pos = 0;
        // calculate the targets we'd hit if only bessie were offset by an amount
        for (char c : cmd.toCharArray()) {
            if (c == 'L') { pos--; }
            else if (c == 'R') { pos++; }
            else if (c == 'F') {
                for (int dpos = pos - 2; dpos <= pos + 2; dpos++) {
                    if (targets.contains(dpos)) {
                        Map<Integer, Integer> m = dHits.get(dpos - pos);
                        m.put(dpos, m.getOrDefault(dpos, 0) + 1);
                    }
                }
            }
        }

        pos = 0;
        int maxHit = 0;
        Set<Integer> hitAlr = new HashSet<>();
        // go through changing the commands
        for (char c : cmd.toCharArray()) {
            boolean inPrev = hitAlr.contains(pos);
            if (c == 'L' || c == 'R') {
                // if this command is an R, we can only offset to the left and vice versa
                int sign = c == 'R' ? -1 : 1;
                // if this L or R were changed to an F, would it it something?
                boolean hit = targets.contains(pos) && !inPrev && !dHits.get(sign).containsKey(pos);
                maxHit = Math.max(
                        maxHit,
                        Math.max(
                                hitAlr.size() + dHits.get(sign).size() + (hit ? 1 : 0),
                                hitAlr.size() + dHits.get(sign * 2).size()
                        )
                );
            } else if (c == 'F') {
                // would this F have hit anything important?
                boolean rMiss = dHits.get(1).getOrDefault(pos + 1, 0) == 1;
                boolean lMiss = dHits.get(-1).getOrDefault(pos - 1, 0) == 1;
                maxHit = Math.max(
                        maxHit,
                        Math.max(
                                hitAlr.size() + dHits.get(1).size() - (!inPrev && rMiss ? 1 : 0),
                                hitAlr.size() + dHits.get(-1).size() - (!inPrev && lMiss ? 1 : 0)
                        )
                );
            }

            // update our maps and whatnot
            if (c == 'L') { pos--; }
            else if (c == 'R') { pos++; }
            else if (c == 'F') {
                // if a target has been hit once it can't be hit again- let's remove it
                if (targets.contains(pos)) {
                    hitAlr.add(pos);
                    for (Map.Entry<Integer, Map<Integer, Integer>> h : dHits.entrySet()) {
                        h.getValue().remove(pos);
                    }
                }
                // this F has been passed- it's not relevant anymore
                for (int dpos = pos - 2; dpos <= pos + 2; dpos++) {
                    if (targets.contains(dpos)) {
                        Map<Integer, Integer> m = dHits.get(dpos - pos);
                        m.put(dpos, m.getOrDefault(dpos, 0) - 1);
                        if (m.get(dpos) <= 0) {
                            m.remove(dpos);
                        }
                    }
                }
            }
        }

        System.out.println(Math.max(maxHit, hitAlr.size()));
    }
}
