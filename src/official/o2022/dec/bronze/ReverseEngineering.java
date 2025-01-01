package official.o2022.dec.bronze;

import java.io.*;
import java.util.*;

/** 2022 dec bronze */
public class ReverseEngineering {
    private static class Input {
        public boolean[] vars;
        public boolean output;

        public Input(boolean[] vars, boolean output) {
            this.vars = vars;
            this.output = output;
        }

        @Override
        public String toString() {
            return String.format("%s -> %s", Arrays.toString(vars), output);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            read.readLine();  // ?????
            StringTokenizer initial = new StringTokenizer(read.readLine());
            int varNum = Integer.parseInt(initial.nextToken());
            int inpNum = Integer.parseInt(initial.nextToken());
            List<Input> inputs = new ArrayList<>();
            for (int i = 0; i < inpNum; i++) {
                StringTokenizer input = new StringTokenizer(read.readLine());
                boolean[] vars = new boolean[varNum];
                String raw = input.nextToken();
                for (int v = 0; v < varNum; v++) {
                    vars[v] = raw.charAt(v) == '1';
                }
                inputs.add(new Input(vars, input.nextToken().equals("1")));
            }

            boolean ok = true;
            while (!allSame(inputs)) {
                boolean found = false;
                for (int diff = 0; diff < varNum; diff++) {
                    List<Input> first = new ArrayList<>();
                    List<Input> second = new ArrayList<>();
                    for (Input i : inputs) {
                        (i.vars[diff] ? first : second).add(i);
                    }
                    if (first.isEmpty() || second.isEmpty()) {
                        continue;
                    }

                    boolean firstGood = allSame(first);
                    if (firstGood || allSame(second)) {
                        inputs = firstGood ? second : first;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    ok = false;
                    break;
                }
            }

            System.out.println(ok ? "OK" : "LIE");
        }
    }

    private static boolean allSame(Collection<Input> inputs) {
        boolean[] set = new boolean[2];
        for (Input i : inputs) {
            set[i.output ? 1 : 0] = true;
        }
        return !(set[0] && set[1]);
    }
}
