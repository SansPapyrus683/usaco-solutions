package official.o2016.jan.gold.bodyInElectric;

import java.io.*;
import java.util.*;

// 2016 jan gold (for some reason it fails for test case 8, i have no idea why)
public class LightsOut {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("lightsout.in"));
        int vertexNum = Integer.parseInt(read.readLine());
        int[][] vertices = new int[vertexNum][2];
        for (int v = 0; v < vertexNum; v++) {
            vertices[v] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        // each of these is the angle AT the vertex or the distance to the next vertex
        ArrayList<Integer> attributes = new ArrayList<>();
        int[] distToExit = new int[vertexNum];
        int totalDist = 0;
        for (int v = 0; v < vertexNum; v++) {
            int[] prevVertex = vertices[(v - 1 + vertexNum) % vertexNum];
            int[] atVertex = vertices[v];
            int[] nextVertex = vertices[(v + 1) % vertexNum];
            attributes.add(rightTurn(prevVertex, atVertex, nextVertex) ? 1 : 2);  // 1 signals a right turn, 2 a left
            attributes.add(Math.abs(atVertex[0] - nextVertex[0]) + Math.abs(atVertex[1] - nextVertex[1]));
            distToExit[v] = totalDist;
            totalDist += Math.abs(atVertex[0] - nextVertex[0]) + Math.abs(atVertex[1] - nextVertex[1]);
        }
        for (int v = 0; v < vertexNum; v++) {
            distToExit[v] = Math.min(distToExit[v], totalDist - distToExit[v]);
        }
        attributes.set(0, 0);  // do some special stuff for the exit to distinguish them from the rest
        attributes.set(attributes.size() - 1, -attributes.get(attributes.size() - 1));

        HashMap<List<Integer>, Integer> subLengthCounts = new HashMap<>();
        for (int i = 0; i <= attributes.size(); i += 2) {  // += 2 because we only have to account for even lengths (see double add() below)
            for (int j = i + 2; j <= attributes.size(); j += 2) {
                List<Integer> subLength = attributes.subList(i, j);
                subLengthCounts.put(subLength, subLengthCounts.getOrDefault(subLength, 0) + 1);
            }
        }

        int worstDiff = 0;
        for (int v = 1; v < vertexNum; v++) {  // they said we don't calculate if bessie starts @ the exit
            ArrayList<Integer> soFar = new ArrayList<>();
            int pos = v;
            int distTravelled = 0;
            while (true) {
                soFar.add(attributes.get(2 * pos));
                soFar.add(attributes.get(2 * pos + 1));
                distTravelled += Math.abs(attributes.get(2 * pos + 1));
                pos = (pos + 1) % vertexNum;
                if (subLengthCounts.get(soFar) == 1) {  // don't worry, the element is always going to be there
                    worstDiff = Math.max(worstDiff, distTravelled + distToExit[pos] - distToExit[v]);
                    break;
                }
            }
        }

        PrintWriter written = new PrintWriter("lightsout.out");
        written.println(worstDiff);
        written.close();
        System.out.println(worstDiff);
        System.out.printf("bruh %d ms in and i already found a body in electrical smh%n", System.currentTimeMillis() - start);
    }

    static boolean rightTurn(int[] p1, int[] p2, int[] p3) {
        if ((p1[0] != p2[0] && p1[1] != p2[1]) || (p2[0] != p3[0] && p2[1] != p3[1])) {
            throw new IllegalArgumentException("ok uh the points are invalid (they have to be || the x axis)");
        }
        // for the first element, true = y change, false = x change, and the second is true = positive, false = negative
        boolean[][] changes = new boolean[][] {
                {p1[0] == p2[0], (p1[0] - p2[0]) + (p1[1] - p2[1]) < 0}, 
                {p2[0] == p3[0], (p2[0] - p3[0]) + (p2[1] - p3[1]) < 0}
        };
        // hahahaha (there probably is a better way to do this but it works so idc [and tbh it isn't even that bad])
        return Arrays.deepEquals(changes, new boolean[][] {{true, true}, {false, true}}) || Arrays.deepEquals(changes, new boolean[][] {{false, true}, {true, false}}) || 
                Arrays.deepEquals(changes, new boolean[][] {{true, false}, {false, false}}) || Arrays.deepEquals(changes, new boolean[][] {{false, false}, {true, true}});
    }
}
