import java.util.*;
import java.io.*;

// 2015 jan silver
// for some reason with i name it Stampede.java the whole thing just frickin breaks
public class CowFundraising {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader(new File("stampede.in")));
        int cowNum = Integer.parseInt(read.readLine());
        Cow[] cows = new Cow[cowNum];
        for (int i = 0; i < cows.length; i++) {
            String[] unparsed = read.readLine().split(" "); // x, y, speed (time per unit)
            cows[i] = new Cow(Integer.parseInt(unparsed[0]), Integer.parseInt(unparsed[1]), Integer.parseInt(unparsed[2]));
        }
        Arrays.sort(cows, Comparator.comparingInt(c -> c.y));
        ArrayList<Cow> seen = new ArrayList<>();
        ArrayList<int[]> coveredIntervals = new ArrayList<>();
        for (Cow c : cows) {
            int[] inFrontTimes = new int[] {(-c.x - 1) * c.speed, -c.x * c.speed};  // x is always negative dw
            //arrayListArray(coveredIntervals);
            //System.out.println(Arrays.toString(inFrontTimes));
            //System.out.println(c);
            boolean blocked = false;
            for (int[] i : coveredIntervals) {  // assumes that intervals are all at least like 1 "unit" apart
                if (i[0] <= inFrontTimes[0] && inFrontTimes[1] <= i[1]) {
                    //System.out.println("blocked");
                    blocked = true;
                    break;
                }
            }
            if (!blocked) {
                seen.add(c);
                insertIntoIntervals(coveredIntervals, inFrontTimes);
            }
            //System.out.println();
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("stampede.out"));
        written.println(seen.size());
        written.close();
        System.out.println(seen.size());
        System.out.printf("AOIJDSAPAPAPAPA took %d ms", System.currentTimeMillis() - start);
    }

    static void arrayListArray(List<int[]> toPrint) {
        if (toPrint.size() == 0) {
            System.out.println("[]");
            return;
        }
        for (int[] asdf : toPrint) {
            System.out.printf("%s ", Arrays.toString(asdf));
        }
        System.out.println();
    }

    static boolean overlap(int[] a, int[] b) {
        return Math.min(a[1], b[1]) >= Math.max(a[0], b[0]);
    }

    static void insertIntoIntervals(List<int[]> insertTo, int[] toInsert) {
        if (insertTo.size() <= 0) {
            insertTo.add(toInsert);
            return;
        }

        if (toInsert[1] < insertTo.get(0)[0]) {
            insertTo.add(0, toInsert);
        } else if (toInsert[0] > insertTo.get(insertTo.size() - 1)[1]) {
            insertTo.add(toInsert);
        } else if (toInsert[0] < insertTo.get(0)[0] && toInsert[1] > insertTo.get(insertTo.size() - 1)[1]) {
            insertTo.clear();
            insertTo.add(toInsert);
        } else {
            ArrayList<int[]> newIntervals = new ArrayList<>();
            for (int i = 0; i < insertTo.size(); i++) {
                int[] currInterval = insertTo.get(i);
                boolean overlapExisting = overlap(currInterval, toInsert);
                if (!overlapExisting) {
                    newIntervals.add(currInterval);
                    if (currInterval[1] < toInsert[0] && toInsert[1] < insertTo.get(i + 1)[0]) {
                        newIntervals.add(toInsert);
                    }
                    continue;
                }

                int[] middleman = new int[] {Math.min(currInterval[0], toInsert[0]), Integer.MAX_VALUE};
                while (i < insertTo.size() && overlapExisting) {
                    middleman[1] = Math.max(insertTo.get(i)[1], toInsert[1]);
                    // simplified version of: i == allIntervals.size() - 1 ? false : overlap(allIntervals.get(i + 1), toInsert);
                    if (i == insertTo.size() - 1) {
                        overlapExisting = false;
                    } else {
                        overlapExisting = overlap(insertTo.get(i + 1), toInsert);
                    }
                    i++;
                }
                newIntervals.add(middleman);
                i--;
            }
            insertTo.clear();
            insertTo.addAll(newIntervals);
        }
    }
}

class Cow {
    public int speed;
    public int x;
    public int y;

    public Cow(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    @Override
    public String toString() {  // just for debugging purposes
        return String.format("Cow{speed=%s, x=%s, y=%s}", speed, x, y);
    }
}
