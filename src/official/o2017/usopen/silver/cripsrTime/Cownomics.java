package official.o2017.usopen.silver.cripsrTime;

import java.io.*;
import java.util.*;

// 2017 us open silver
public final class Cownomics {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cownomics.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int genomeLen = Integer.parseInt(initial.nextToken());
        String[] spotty = new String[cowNum];
        String[] plain = new String[cowNum];
        for (int c = 0; c < cowNum; c++) {
            spotty[c] = read.readLine().toUpperCase();
        }
        for (int c = 0; c < cowNum; c++) {
            plain[c] = read.readLine().toUpperCase();
        }
        int[] allIndices = new int[genomeLen];
        for (int g = 0; g < genomeLen; g++) {
            allIndices[g] = g;
        }
        // just brute force all indices lol (seriously this problem is way too ez for usopen)
        int distinguishableGenomeNum = 0;
        for (int[] indexSet : combinations3(allIndices)) {
            if (distinguishable(spotty, plain, indexSet)) {
                distinguishableGenomeNum++;
            }
        }

        PrintWriter written = new PrintWriter("cownomics.out");
        written.println(distinguishableGenomeNum);
        written.close();
        System.out.println(distinguishableGenomeNum);
        System.out.printf("%d ms: the bruh momento%n", System.currentTimeMillis() - start);
    }

    // copied from: https://stackoverflow.com/questions/29910312/algorithm-to-get-all-the-combinations-of-size-n-from-an-array-java
    static ArrayList<int[]> combinations3(int[] arr){
        ArrayList<int[]> toReturn = new ArrayList<>();
        for(int i = 0; i < arr.length - 2; i++) {
            for (int j = i + 1; j < arr.length - 1; j++) {
                for (int k = j + 1; k < arr.length; k++) {
                    toReturn.add(new int[]{arr[i], arr[j], arr[k]});
                }
            }
        }
        return toReturn;
    }

    static boolean distinguishable(String[] spotty, String[] plain, int[] indices) {
        if (indices.length != 3) {
            throw new IllegalArgumentException("yeah the problem said only 3 genomes");
        }
        HashSet<String> spottySet = new HashSet<>();
        for (String s : spotty) {
            spottySet.add(String.valueOf(s.charAt(indices[0])) + s.charAt(indices[1]) + s.charAt(indices[2]));
        }
        // check if the plain cow set and the spotty cow set are mutually exclusive
        for (String p : plain) {
            if (spottySet.contains(String.valueOf(p.charAt(indices[0])) + p.charAt(indices[1]) + p.charAt(indices[2]))) {
                return false;
            }
        }
        return true;
    }
}
