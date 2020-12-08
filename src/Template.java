import java.io.*;
import java.util.*;

// most of my sols are structured like this (bare bones)
public class Template {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("README.md"));
        StringTokenizer initial = new StringTokenizer(read.readLine());  // some random placeholder code
        read.close();
        while (initial.hasMoreTokens()) {
            System.out.println(initial.nextToken());
        }
        System.out.printf("bruh why did you run this code that took %d ms lol%n", System.currentTimeMillis() - start);
    }
}
