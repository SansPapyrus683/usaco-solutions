package official.o2017.dec.bronze.billboard;

import java.io.*;
import java.util.*;
import java.awt.Rectangle;

public class Billboard {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("billboard.in"));
        Rectangle[] boards = new Rectangle[3];
        for (int i = 0; i < boards.length; i++) {
            StringTokenizer boardST = new StringTokenizer(read.readLine());
            int x1 = Integer.parseInt(boardST.nextToken());
            int y1 = Integer.parseInt(boardST.nextToken());
            int x2 = Integer.parseInt(boardST.nextToken());
            int y2 = Integer.parseInt(boardST.nextToken());
            boards[i] = new Rectangle(x1, -y2, x2 - x1, y2 - y1);
        }

        int initArea = combArea(boards[0], boards[1]);
        int blockedArea = combArea(
                boards[0].intersection(boards[2]), boards[1].intersection(boards[2])
        );
        int stillVisible = initArea - blockedArea;

        PrintWriter written = new PrintWriter("billboard.out");
        written.println(stillVisible);
        written.close();
    }

    public static int area(Rectangle rect) {
        return Math.max(rect.height, 0) * Math.max(rect.width, 0);
    }

    public static int combArea(Rectangle rect1, Rectangle rect2) {
        return area(rect1) + area(rect2) - area(rect1.intersection(rect2));
    }
}
