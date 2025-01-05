package official.o2022.dec.gold;

import java.io.*;
import java.util.*;

/** 2022 dec gold (had to read edi unlucky) */
public class BribingFriends {
    private static class Friend {
        public int pop;  // popularity
        public int money;  // amount of money needed to bribe
        public int cones;  // amount of ice cream cones needed for a discount

        public Friend(int pop, int money, int cones) {
            this.pop = pop;
            this.money = money;
            this.cones = cones;
        }

        @Override
        public String toString() {
            return String.format("(P=%d, C=%d, X=%d)", pop, money, cones);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int friendNum = Integer.parseInt(initial.nextToken());
        int cash = Integer.parseInt(initial.nextToken());
        int cones = Integer.parseInt(initial.nextToken());
        Friend[] friends = new Friend[friendNum];
        for (int f = 0; f < friendNum; f++) {
            StringTokenizer friend = new StringTokenizer(read.readLine());
            friends[f] = new Friend(
                    Integer.parseInt(friend.nextToken()),
                    Integer.parseInt(friend.nextToken()),
                    Integer.parseInt(friend.nextToken())
            );
        }
        Arrays.sort(friends, Comparator.comparingInt(f -> f.cones));

        int[] maxPop = new int[cash + cones + 1];
        Arrays.fill(maxPop, Integer.MIN_VALUE);
        maxPop[0] = 0;
        for (Friend f : friends) {
            int[] newPop = maxPop.clone();
            for (int c = 0; c <= cash + cones; c++) {
                if (maxPop[c] == Integer.MIN_VALUE) {
                    continue;
                }
                final int useCones = Math.max(0, cones - c);
                final int discount = Math.min(f.money, useCones / f.cones);
                final int price = f.money - discount;
                final int base = Math.max(c, cones);
                if (price == 0) {
                    final int used = discount * f.cones;
                    newPop[c + used] = Math.max(newPop[c + used], maxPop[c] + f.pop);
                } else if (base + price <= cash + cones) {
                    newPop[base + price] = Math.max(newPop[base + price], maxPop[c] + f.pop);
                }
            }
            maxPop = newPop;
        }

        System.out.println(Arrays.stream(maxPop).max().getAsInt());
    }
}
