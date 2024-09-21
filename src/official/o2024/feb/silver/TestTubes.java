package official.o2024.feb.silver;

import java.io.*;
import java.util.*;

public class TestTubes {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        StringBuilder totalAns = new StringBuilder();
        for (int t = 0; t < testNum; t++) {
            StringTokenizer initial = new StringTokenizer(read.readLine());
            initial.nextToken();
            int queryType = Integer.parseInt(initial.nextToken());

            Tube first = new Tube(1, new ArrayList<>());
            for (char c : read.readLine().toCharArray()) {
                first.add(c == '2');
            }
            Tube second = new Tube(2, new ArrayList<>());
            for (char c : read.readLine().toCharArray()) {
                second.add(c == '2');
            }

            List<int[]> bestMoves = null;
            for (int i = 0; i < 4; i++) {
                boolean color = i % 2 == 0;
                List<int[]> res =
                        i < 2
                                ? minMoves(first.copy(), second.copy(), color)
                                : minMoves(second.copy(), first.copy(), color);
                if (bestMoves == null || res.size() < bestMoves.size()) {
                    bestMoves = res;
                }
            }

            totalAns.append(bestMoves.size()).append('\n');
            if (queryType == 2 || queryType == 3) {
                for (int[] m : bestMoves) {
                    totalAns.append(m[0]).append(' ').append(m[1]).append('\n');
                }
            }
        }

        System.out.print(totalAns);
    }

    private static List<int[]> minMoves(Tube from, Tube to, boolean beakerColor) {
        List<int[]> moves = new ArrayList<>();

        Tube beaker = new Tube(3, new ArrayList<>());
        if (to.top() == beakerColor) {
            to.pourInto(beaker, moves);
        }
        while (from.tube.size() > 1) {
            from.pourInto(from.top() == beakerColor ? beaker : to, moves);
        }
        if (to.tube.size() == 1) {
            if (from.top() == to.top()) {
                from.pourInto(to, moves);
            }
            beaker.pourInto(from, moves);
            return moves;
        } else if (from.top() == beakerColor) {
            from.pourInto(beaker, moves);
        }

        while (to.tube.size() > 1) {
            to.pourInto(to.top() == beakerColor ? beaker : from, moves);
        }
        if (!to.tube.isEmpty() && to.top() != beakerColor) {
            to.pourInto(from, moves);
        }
        beaker.pourInto(to, moves);

        return moves;
    }
}

class Tube {
    public final int id;
    public final List<Boolean> tube;

    public Tube(int id, List<Boolean> tube) {
        this.id = id;
        this.tube = tube;
    }

    public Tube copy() {
        return new Tube(id, new ArrayList<>(tube));
    }

    public boolean top() {
        return tube.get(tube.size() - 1);
    }

    public void add(boolean liquid) {
        if (tube.isEmpty() || top() != liquid) {
            tube.add(liquid);
        }
    }

    public void pourInto(Tube other, List<int[]> moveLog) {
        if (tube.isEmpty()) {
            return;
        }
        other.add(tube.remove(tube.size() - 1));
        moveLog.add(new int[] {id, other.id});
    }
}
