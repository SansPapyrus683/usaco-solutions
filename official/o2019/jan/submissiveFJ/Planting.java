import java.io.*;
import java.util.*;

// 2019 jan silver (all the fields form a tree if you didn't know bc of the n-1 edges)
public class Planting {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("planting.in"));
        int fieldNum = Integer.parseInt(read.readLine());
        ArrayList<Integer>[] neighbors = new ArrayList[fieldNum];
        for (int i = 0; i < fieldNum; i++) {
            neighbors[i] = new ArrayList<>();
        }

        for (int i = 0; i < fieldNum - 1; i++) {
            StringTokenizer path = new StringTokenizer(read.readLine());
            int f1 = Integer.parseInt(path.nextToken()) - 1, f2 = Integer.parseInt(path.nextToken()) - 1;
            neighbors[f1].add(f2);
            neighbors[f2].add(f1);
        }

        int leastTypes = 0;
        for (int f = 0; f < fieldNum; f++) {  // go through each of the fields and set them as the "center"
          // at least this many, bc of the intermediate fields and the center all have to be different
            int atLeastTypes = neighbors[f].size() + 1;
            for (int n : neighbors[f]) {  // for the extended neighbors it's the same logic (except the immediate n is the center)
                atLeastTypes = Math.max(atLeastTypes, neighbors[n].size() + 1);
            }
            leastTypes = Math.max(leastTypes, atLeastTypes);
        }
        PrintWriter written = new PrintWriter("planting.out");
        written.println(leastTypes);
        written.close();
        System.out.println(leastTypes);
        System.out.printf("oh my god it took %d ms *clapping*%n", System.currentTimeMillis() - start);
    }
}
