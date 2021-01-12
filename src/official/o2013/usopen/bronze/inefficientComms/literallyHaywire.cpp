#include <iostream>
#include <fstream>
#include <vector>
#include <limits>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;

constexpr int FRIEND_NUM = 3;

class WireSystem {
    private:
        vector<vector<int>> friendly;
        vector<int> cow_pos;  // this[i] = the stall number of cow i (everything's 0-indexed)
        int cow_num;
        int best = std::numeric_limits<int>::max();

        void calc_min(int arranged_alr, int cost_so_far, int dangling_num, int dangling_cost) {
            if (arranged_alr == cow_num) {
                best = std::min(best, cost_so_far);
                return;
            }
            if (cost_so_far + dangling_cost >= best) {
                return;  // no hope left, we've already exceeded the best amount
            }

            arranged_alr++;
            for (int c = 0; c < cow_num; c++) {
                if (cow_pos[c] != -1) {  // this cow is already assigned, let's leave it alone
                    continue;
                }
                cow_pos[c] = arranged_alr;  // assign it to the empty spot

                int more_dangling = FRIEND_NUM;
                int to_add = 0;
                for (int f : friendly[c]) {
                    if (cow_pos[f] != -1) {  // oh wait, we already assigned this one, let's complete it
                        to_add += cow_pos[c] - cow_pos[f];
                        more_dangling -= 2;  // -2 because the friend also had a missing link along with this one
                    }
                }
                calc_min(arranged_alr, 
                         cost_so_far + to_add, 
                         dangling_num + more_dangling, 
                         dangling_cost + (dangling_num + more_dangling) - to_add);

                cow_pos[c] = -1;
            }
        }
    public:
        // 1/11/2021- i still don't know how to throw exceptions so no input validation
        WireSystem(vector<vector<int>> friends) {
            friendly = friends;
            cow_num = friends.size();
            cow_pos = vector<int>(cow_num, -1);
        }

        int min_cost() {
            calc_min(0, 0, 0, 0);
            return best;
        }
};

/**
 * ok so because this is a brute force problem
 * i decided to use c++ because of its speed
 * if you were looking for a sol in another language i'm sorry
 */
int main() {
    std::ifstream read("haywire.in");
    int cow_num;
    read >> cow_num;
    vector<vector<int>> friends(cow_num, vector<int>(FRIEND_NUM));
    for (int c = 0; c < cow_num; c++) {
        for (int f = 0; f < FRIEND_NUM; f++) {
            read >> friends[c][f];
            friends[c][f]--;  // make the cows 0-indexed
        }
    }

    WireSystem system(friends);
    int min_cost = system.min_cost();

    std::ofstream written("haywire.out");
    written << min_cost << endl;
    cout << min_cost << endl;
}
