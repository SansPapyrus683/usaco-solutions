package official.o2022.usopen.bronze;

import java.io.*;
import java.util.*;

// 2022 us open bronze (input omitted bc length)
public class Alchemy {
    // global variables used because i can't be bothered to make a separate class
    private static int[] metals;
    private static int[][] recipes;

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int metalNum = Integer.parseInt(read.readLine());

        metals = new int[metalNum];
        StringTokenizer metalST = new StringTokenizer(read.readLine());
        for (int m = 0; m < metalNum; m++) {
            metals[m] = Integer.parseInt(metalST.nextToken());
        }

        int recipeNum = Integer.parseInt(read.readLine());
        recipes = new int[metalNum][];
        for (int r = 0; r < recipeNum; r++) {
            StringTokenizer recipe = new StringTokenizer(read.readLine());
            int outType = Integer.parseInt(recipe.nextToken()) - 1;
            int ingredientNum = Integer.parseInt(recipe.nextToken());

            // some rudimentary input validation
            assert recipes[outType] == null;

            recipes[outType] = new int[ingredientNum];
            for (int i = 0; i < ingredientNum; i++) {
                recipes[outType][i] = Integer.parseInt(recipe.nextToken()) - 1;
                assert recipes[outType][i] < outType;
            }
        }

        int maxAmt = 0;
        while (canCraft(metalNum - 1)) {
            maxAmt++;
        }
        System.out.println(maxAmt);
    }

    /*
     * if we can craft a metal, this function returns true &
     * removes the used ingredients
     * if not it just returns false
     */
    private static boolean canCraft(int m) {
        if (recipes[m] == null) {
            if (metals[m] == 0) {
                return false;
            }
            metals[m]--;
            return true;
        }
        if (metals[m] > 0) {
            metals[m]--;
            return true;
        }
        for (int p : recipes[m]) {
            if (metals[p] > 0) {
                metals[p]--;
                continue;
            }
            if (!canCraft(p)) {
                return false;
            }
        }
        return true;
    }
}
