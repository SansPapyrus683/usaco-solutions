package utils.template;

import java.io.*;
import java.util.*;

// this is the same thing as template but it uses stdin instead of file input so yeah
public class STDInTemplate {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("whaddya want me to say? ");
        String input = read.readLine();
    }
}
