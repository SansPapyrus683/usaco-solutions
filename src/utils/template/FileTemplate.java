package utils.template;

import java.io.*;
import java.util.*;

public class FileTemplate {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("test.in"));
        ArrayList<String[]> lines = new ArrayList<>();
        String currLine;
        while ((currLine = read.readLine()) != null) {
            lines.add(currLine.split(" "));
        }
    }
}
