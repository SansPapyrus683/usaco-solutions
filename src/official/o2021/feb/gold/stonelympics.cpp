#include <iostream>
#include <vector>
#include <algorithm>

// #include "debugging.h"

using std::cout;
using std::endl;
using std::vector;

/**
 * 2021 feb gold (c++ for the lower_bound and stuff like that)
 * 1
 * 7 should output 4
 * 6
 * 3 2 3 2 3 1 should output 8
 */
int main() {
    int pile_num;
    std::cin >> pile_num;

    vector<int> piles(pile_num);  // one more for the grouping down below
    int max_pile = 1;
    for (int p = 0; p < pile_num; p++) {
        std::cin >> piles[p];
        max_pile = std::max(max_pile, piles[p]);
    }
    std::sort(piles.begin(), piles.end());

    long long valid_moves = 0;
    // go through all the possible AMOUNTS we can start to remove
    for (int remove_amt = 1; remove_amt <= max_pile; remove_amt++) {
        /*
         * if you don't wanna read the official analysis i can try to do it here
         * each move can be thought of as floor diving all the piles by a pos. int. and then taking 1 away
         * 
         * so then we want to make all the remaining divved piles occur and even amount of times by the first move
         * this way whatever elsie does we can just do as well and then eventually leave her with no stones
         * so we first count all the divved piles that occur an odd amt of times
         */
        vector<std::pair<int, int>> odd_amts;
        int prev_ind = std::lower_bound(piles.begin(), piles.end(), remove_amt) - piles.begin();
        // go through all possible divved pile sizes
        for (int use_amt = 1; use_amt * remove_amt <= max_pile; use_amt++) {
            int end_ind = std::lower_bound(piles.begin(), piles.end(), (use_amt + 1) * remove_amt) - piles.begin();
            if ((end_ind - prev_ind) % 2 == 1) {
                odd_amts.push_back({use_amt, end_ind - prev_ind});
                if (odd_amts.size() == 3) {  // at this point it doesn't matter anymore so let's just stop
                    break;
                }
            }
            prev_ind = end_ind;
        }

        if (odd_amts.size() == 1 && odd_amts[0].first == 1) {
            // if there's only one odd, it has to be 1 because then we can make it even without affecting any others
            valid_moves += odd_amts[0].second;
        } else if (odd_amts.size() == 2 && odd_amts[0].first + 1 == odd_amts[1].first) {
            // if there's 2, they ahve to be adjacent so we can dec the first & inc the second
            valid_moves += odd_amts[1].second;
        }
    }
    cout << valid_moves << endl;
}
