#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;
using std::pair;

// 2013 march gold
int main() {
    std::ifstream read("cowrun.in");
    int cow_num;
    read >> cow_num;
    vector<int> pos(cow_num);
    for (int& p : pos) {
        read >> p;
    }
    std::sort(pos.begin(), pos.end());

    /*
     * this[s][e] = min cost if we only consider halting the cows from s to e
     * pair is to distinguish whether we end @ the left or right
     */
    vector<vector<pair<int, int>>> min_cost(
        cow_num, vector<pair<int, int>>(cow_num)
    );
    for (int c = 0; c < cow_num; c++) {
        min_cost[c][c] = {abs(pos[c]) * cow_num, abs(pos[c]) * cow_num};
    }
    
    for (int len = 2; len <= cow_num; len++) {
        int rem = cow_num - len + 1;
        for (int s = 0; s + len - 1 < cow_num; s++) {
            int e = s + len - 1;
            
            // first halt all the ones to the right then end at the left side
            pair<int, int> right = min_cost[s + 1][e];
            min_cost[s][e].first = std::min(
                  right.first + rem * (pos[s + 1] - pos[s]),
                  right.second + rem * (pos[e] - pos[s])
            );

            // or we could halt all the ones to the left & end at the right
            pair<int, int> left = min_cost[s][e - 1];
            min_cost[s][e].second = std::min(
                left.first + rem * (pos[e] - pos[s]),
                left.second + rem * (pos[e] - pos[e - 1])
            );
        }
    }

    pair<int, int> final = min_cost[0][cow_num - 1];
    std::ofstream("cowrun.out") << std::min(final.first, final.second) << endl;
}
