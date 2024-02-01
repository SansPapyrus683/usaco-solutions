package official.o2023.dec.bronze;

import java.io.*;
import java.util.*;

/** 2023 dec bronze (sample input omitted due to length) */
public class FJFarms {
    static class Plant {
        public int height;
        public int growth;
        public int order;

        public Plant(int height, int growth, int order) {
            this.height = height;
            this.growth = growth;
            this.order = order;
        }

        /** @return the time until this plant can dominate the plant p (-1 means never) */
        public long minDomTime(Plant p) {
            if (height > p.height) {
                return 0;
            }
            if (growth <= p.growth) {
                return -1;
            }
            return (long)Math.ceil((double)(p.height - height + 1) / (growth - p.growth));
        }

        /** @return the maximum time this plant can dominate plant p (-1 means forever) */
        public long maxDomTime(Plant p) {
            if (growth >= p.growth) {
                return height == p.height && growth == p.growth ? 0 : -1;
            }
            return Math.max(0, (long)Math.ceil((double)(height - p.height) / (p.growth - growth)));
        }

        @Override
        public String toString() {
            return String.format("(h:%s,g:%s,%s)", height, growth, order);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            int plantNum = Integer.parseInt(read.readLine());
            StringTokenizer height = new StringTokenizer(read.readLine());
            StringTokenizer growth = new StringTokenizer(read.readLine());
            StringTokenizer order = new StringTokenizer(read.readLine());
            Plant[] plants = new Plant[plantNum];
            for (int p = 0; p < plantNum; p++) {
                plants[p] = new Plant(
                        Integer.parseInt(height.nextToken()),
                        Integer.parseInt(growth.nextToken()),
                        Integer.parseInt(order.nextToken())
                );
            }
            Arrays.sort(plants, Comparator.comparingInt(p -> -p.order));

            long minTime = 0;
            long maxTime = Long.MAX_VALUE;
            boolean possible = true;
            for (int p = 1; p < plantNum; p++) {
                long domStart = plants[p].minDomTime(plants[p - 1]);
                if (domStart == -1 || domStart >= maxTime) {
                    possible = false;
                    break;
                }
                minTime = Math.max(minTime, domStart);
                long domEnd = plants[p].maxDomTime(plants[p - 1]);
                if (domEnd != -1) {
                    maxTime = Math.min(maxTime, domEnd);
                }
            }

            System.out.println(possible ? minTime : -1);
        }
    }
}
