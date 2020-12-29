import java.io.*;
import java.util.*;

// most of my sols are structured like this (bare bones)
public class Template {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("test.in"));
        ArrayList<String[]> lines = new ArrayList<>();
        String currLine;
        while ((currLine = read.readLine()) != null) {
            lines.add(currLine.split(" "));
        }

        System.out.printf("bruh why did you run this code that took %d ms lol%n", System.currentTimeMillis() - start);
    }
}
