package official.o2015.usopen.gold;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

// 2015 us open gold (no problem folder because no input files lol)
public final class Googol {
    private static class Company {
        private static final String NONE = "0";
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        HashMap<String, String[]> children = new HashMap<String, String[]>() {{
            put(NONE, new String[] {NONE, NONE});
        }};
        String[] children(String node) throws IOException {
            if (children.containsKey(node)) {
                return children.get(node);
            }
            System.out.println(node);
            children.put(node, read.readLine().split(" "));
            return children.get(node);
        }

        public BigInteger totalChildren(String node) throws IOException {
            if (node.equals(NONE)) {
                return BigInteger.ZERO;
            }
            ArrayList<String> leftSide = new ArrayList<>(Collections.singletonList(node));
            // go all the way down to the left until there's no more
            while (true) {
                String[] children = children(leftSide.get(leftSide.size() - 1));
                if (children[0].equals(NONE)) {
                    break;
                }
                leftSide.add(children[0]);
            }

            BigInteger total = BigInteger.ZERO;
            Collections.reverse(leftSide);  // process the left side from the bottom up
            for (int i = 1; i < leftSide.size(); i++) {
                total = total.add(BigInteger.ONE);
                String rightChild = children(leftSide.get(i))[1];
                String[] rightGrandchildren = children(rightChild);
                if (total.testBit(0)) {  // odd
                    // do some simple math and you'll notice that the amount of nodes in the left tree is constant
                    BigInteger rightLeft = total.divide(BigInteger.TWO);
                    total = total.add(rightLeft).add(totalChildren(rightGrandchildren[1]));
                } else {  // even
                    // same goes for the right one if the left subtree total is even
                    BigInteger rightRight = total.divide(BigInteger.TWO).subtract(BigInteger.ONE);
                    total = total.add(rightRight).add(totalChildren(rightGrandchildren[0]));
                }
                if (!rightChild.equals(NONE)) {  // don't forget to add the right child itself
                    total = total.add(BigInteger.ONE);
                }
            }
            return total.add(BigInteger.ONE);  // add the root node
        }
    }
    
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        System.out.printf("Answer %s%n", new Company().totalChildren("1"));
        System.err.printf("how do you accidentally hire 10^100 employees wth: %d ms%n", System.currentTimeMillis() - start);
    }
}
