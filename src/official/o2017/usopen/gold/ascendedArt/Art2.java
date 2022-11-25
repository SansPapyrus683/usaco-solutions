package official.o2017.usopen.gold.ascendedArt;

import java.io.*;
import java.util.*;

// 2017 us open gold
public class Art2 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("art2.in"));
        int canvasLen = Integer.parseInt(read.readLine());
        int[] canvas = new int[canvasLen];
        HashMap<Integer, int[]> visible = new HashMap<>();
        for (int i = 0; i < canvasLen; i++) {
            canvas[i] = Integer.parseInt(read.readLine());
            int[] bounds;
            if (!visible.containsKey(canvas[i])) {
                visible.put(canvas[i], (bounds = new int[] {i, i}));
            } else {
                bounds = visible.get(canvas[i]);
            }
            bounds[0] = Math.min(bounds[0], i);
            bounds[1] = Math.max(bounds[1], i);
        }
        visible.remove(0);

        // build a prefix sum array to see the maximum layers of paint on a single cell
        int[] starts = new int[canvasLen];
        int[] ends = new int[canvasLen];
        int[] layerNum = new int[canvasLen + 1];  // we'll ignore the last value
        for (Map.Entry<Integer, int[]> color : visible.entrySet()) {
            int[] bounds = color.getValue();
            starts[bounds[0]] = color.getKey();
            ends[bounds[1]] = color.getKey();
            layerNum[bounds[0]]++;
            layerNum[bounds[1] + 1]--;
        }
        for (int i = 1; i < canvasLen; i++) {
            layerNum[i] += layerNum[i - 1];
        }

        // try to restore the painting from the intervals (to see if the painting is authentic)
        int[] restored = new int[canvasLen];
        Stack<Integer> palette = new Stack<>();
        palette.push(0);
        for (int i = 0; i < canvasLen; i++) {
            if (starts[i] != 0) {
                palette.push(starts[i]);
            }
            restored[i] = palette.peek();
            if (ends[i] != 0) {
                palette.pop();
            }
        }

        // i mean it doesn't take much thinking to notice that the answer is just the largest depth
        int minRounds = Arrays.equals(Arrays.copyOfRange(restored, 0, canvasLen), canvas) ? Arrays.stream(layerNum).max().getAsInt() : -1;
        PrintWriter written = new PrintWriter("art2.out");
        written.println(minRounds);
        written.close();
        System.out.println(minRounds);
        System.out.printf("i wonder if picowso was ever depressed: %d ms", System.currentTimeMillis() - start);
    }
}
