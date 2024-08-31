package official.o2023.feb.silver;

import java.io.*;
import java.util.*;

// 2023 feb silver
public class Bakery {
    private static class Friend {
        public int cookieNum;
        public int muffinNum;
        public long time;

        public Friend(int cookieNum, int muffinNum, long time) {
            this.cookieNum = cookieNum;
            this.muffinNum = muffinNum;
            this.time = time;
        }

        @Override
        public String toString() {
            return String.format("(%d,%d,%d)", cookieNum, muffinNum, time);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            read.readLine();  // WHY IS THERE AN EMPTY LINE????
            StringTokenizer initial = new StringTokenizer(read.readLine());
            int friendNum = Integer.parseInt(initial.nextToken());
            int cookieTime = Integer.parseInt(initial.nextToken());
            int muffinTime = Integer.parseInt(initial.nextToken());
            Friend[] friends = new Friend[friendNum];
            for (int f = 0; f < friendNum; f++) {
                StringTokenizer friend = new StringTokenizer(read.readLine());
                friends[f] = new Friend(
                        Integer.parseInt(friend.nextToken()),
                        Integer.parseInt(friend.nextToken()),
                        Long.parseLong(friend.nextToken())
                );
            }

            long lo = 0;
            long hi = cookieTime + muffinTime;
            long valid = -1;
            while (lo <= hi) {
                long mid = (lo + hi) / 2;

                long lb = Integer.MIN_VALUE;
                long ub = Integer.MAX_VALUE;
                for (Friend f : friends) {
                    long lhs = f.cookieNum - f.muffinNum;
                    // :skull:
                    long rhs = (long) f.muffinNum * muffinTime + (long) f.cookieNum * cookieTime - f.time - (long) f.muffinNum * mid;
                    if (lhs > 0) {
                        lb = Math.max(lb, (long) Math.ceil((double) rhs / lhs));
                    } else if (lhs < 0) {
                        ub = Math.min(ub, (long) Math.floor((double) rhs / lhs));
                    } else {
                        if (rhs > 0) {
                            lb = 0;
                            ub = -1;
                            break;
                        }
                    }
                }

                boolean possible = true;
                if (lb > ub) {
                    possible = false;
                } else {
                    long[] inter1 = rangeInter(new long[] {lb, ub}, new long[] {0, cookieTime - 1});
                    long[] inter2 = rangeInter(new long[] {mid - ub, mid - lb}, new long[] {0, muffinTime - 1});
                    if (inter1[0] > inter1[1] || inter2[0] > inter2[1]) {
                        possible = false;
                    }
                }

                if (possible) {
                    valid = mid;
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }

            System.out.println(valid);
        }
    }

    public static long[] rangeInter(long[] r1, long[] r2) {
        return new long[]{Math.max(r1[0], r2[0]), Math.min(r1[1], r2[1])};
    }
}
