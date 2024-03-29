package official.o2019.dec.silver.stupidCows;

import java.io.*;
import java.util.*;

// 2019 dec silver
public class Meetings {
    private static class Cow {
        int weight;
        int pos;
        int speed;
        public Cow(int weight, int pos, int speed) {
            this.weight = weight;
            this.pos = pos;
            this.speed = speed;
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("meetings.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int otherBarn = Integer.parseInt(initial.nextToken());
        Cow[] cows = new Cow[cowNum];
        int totalWeight = 0;
        for (int c = 0; c < cowNum; c++) {  // weight, pos, speed
            StringTokenizer cow = new StringTokenizer(read.readLine());
            cows[c] = new Cow(Integer.parseInt(cow.nextToken()),
                    Integer.parseInt(cow.nextToken()), Integer.parseInt(cow.nextToken()));
            totalWeight += cows[c].weight;
        }
        Arrays.sort(cows, Comparator.comparingInt(c -> c.pos));

        // so the # of -1's is also the # of cows that meet the left barn, and some for right barn
        ArrayList<Cow> toTheLeft = new ArrayList<>();
        ArrayList<Cow> toTheRight = new ArrayList<>();
        ArrayList<int[]> weightTimes = new ArrayList<>();
        for (Cow c : cows) {
            if (c.speed == -1) {
                toTheLeft.add(c);
            } else if (c.speed == 1) {
                toTheRight.add(c);
            }
        }
        /*
         * calculate each of the times when the cows meet the end
         * the leftmost cows get all of the -1 cow's positions as their times,
         * and basically the same for the rightmost ones (just subtract them from the end)
         */
        for (int c = 0; c < toTheLeft.size(); c++) {
            weightTimes.add(new int[] {toTheLeft.get(c).pos, cows[c].weight});
        }
        for (int c = 0; c < toTheRight.size(); c++) {
            weightTimes.add(new int[] {otherBarn - toTheRight.get(c).pos, cows[toTheLeft.size() + c].weight});
        }
        weightTimes.sort(Comparator.comparingInt(t -> t[0]));  // sort them by their occurrence

        int endTime = -1;
        for (int[] barnMeeting : weightTimes) {
            totalWeight -= 2 * barnMeeting[1];
            if (totalWeight <= 0) {
                endTime = barnMeeting[0];
                break;
            }
        }

        // now we know what time the sim ends, so now we can just ignore the meetings (since the weights don't matter)
        int meetingNum = 0;
        Queue<Integer> stillOnLeft = new ArrayDeque<>();
        for (int c = 0; c < cowNum; c++) {
            if (cows[c].speed == 1) {
                stillOnLeft.add(cows[c].pos);
            } else if (cows[c].speed == -1) {
                while (!stillOnLeft.isEmpty() && stillOnLeft.peek() + 2 * endTime < cows[c].pos) {
                    stillOnLeft.poll();
                }
                meetingNum += stillOnLeft.size();
            }
        }
        PrintWriter written = new PrintWriter("meetings.out");
        written.println(meetingNum);
        written.close();
        System.out.println(meetingNum);
        System.out.printf("it took %d ms, kinda a bruh moment", System.currentTimeMillis() - start);
    }
}
