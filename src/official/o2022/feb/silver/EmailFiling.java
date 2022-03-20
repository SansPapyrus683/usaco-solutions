package official.o2022.feb.silver;

import java.io.*;
import java.util.*;

// 2022 feb silver (input omitted due to length)
public final class EmailFiling {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            StringTokenizer initial = new StringTokenizer(read.readLine());
            int folderNum = Integer.parseInt(initial.nextToken());
            int emailNum = Integer.parseInt(initial.nextToken());
            int screenHeight = Integer.parseInt(initial.nextToken());
            if (screenHeight > Math.min(folderNum, emailNum)) {
                throw new IllegalArgumentException("invalid value for the screen height");
            }

            int[] fileIn = Arrays.stream(read.readLine().split(" "))
                    .mapToInt(f -> Integer.parseInt(f) - 1).toArray();
            HashSet<Integer>[] emails = new HashSet[folderNum];
            for (int f = 0; f < folderNum; f++) {
                emails[f] = new HashSet<>();
            }
            for (int e = 0; e < emailNum; e++) {
                emails[fileIn[e]].add(e);
            }
            System.out.println(Arrays.toString(fileIn));
            System.out.println(Arrays.toString(emails));

            int eStart = 0;
            boolean[] filed = new boolean[emailNum];
            boolean canFile = true;
            for (int fAt = 0; fAt <= folderNum - screenHeight; fAt++) {
                while (!emails[fAt].isEmpty() && eStart <= emailNum - screenHeight) {
                    int onScreen = 0;
                    for (int e = eStart; e < emailNum && onScreen < screenHeight; e++) {
                        if (filed[e]) {
                            continue;
                        }
                        if (fAt <= fileIn[e] && fileIn[e] < fAt + screenHeight) {
                            filed[e] = true;
                            emails[fileIn[e]].remove(e);
                        } else {
                            onScreen++;
                        }
                    }
                    // piss, we haven't filed all the emails in the first folder
                    if (!emails[fAt].isEmpty()) {
                        eStart++;
                        while (eStart < emailNum && filed[eStart]) {
                            eStart++;
                        }
                    }
                }
                if (!emails[fAt].isEmpty()) {
                    canFile = false;
                    break;
                }
                System.out.println(eStart);
                //break;
            }
            System.out.println(canFile ? "YES" : "NO");
        }
    }
}
