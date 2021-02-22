package utils.template;

import java.io.*;
import java.util.*;

// this is the same thing as template but it uses stdin instead of file input so yeah
public final class STDInTemplate {
    public static void main(String[] args) throws IOException {

        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("whaddya want me to say? ");
        String input = read.readLine();

        long start = System.currentTimeMillis();
        System.out.println(input);
        System.err.printf("this code that took %d ms is absolutely pointless lol%n", System.currentTimeMillis() - start);
    }
}
