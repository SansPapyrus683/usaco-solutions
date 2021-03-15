#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <climits>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;

struct Cow {
    int height;
    int weight;
    int strength;
};

// 2014 dec gold (jesus christ how tall is mark)
int main() {
    std::ifstream read("guard.in");
    int cow_num;
    int mark_height;
    read >> cow_num >> mark_height;
    vector<Cow> cows(cow_num);
    int max_strength = 0;
    for (int c = 0; c < cow_num; c++) {
        read >> cows[c].height >> cows[c].weight >> cows[c].strength;
        max_strength = std::max(max_strength, cows[c].strength);
    }

    long long max_safety = -1;
    // max safety of a subset given we use all the cows in that subset
    vector<long long> all_used_safety(1 << cows.size(), -1);
    all_used_safety[0] = LLONG_MAX;  // i mean the ground has basically inf safety factor
    for (int i = 1; i < (1 << cows.size()); i++) {
        long long total_height = 0;
        long long total_weight = 0;
        for (int c = 0; c < cows.size(); c++) {
            if ((i & (1 << c)) != 0) {
                total_height += cows[c].height;
                total_weight += cows[c].weight;
            }
        }
        // see if we can insert a cow at the bottom of a previous subset
        for (int c = 0; c < cows.size(); c++) {
            if ((i & (1 << c)) == 0) {
                continue;
            }
            int prev = i & ~(1 << c);
            long long carried = total_weight - cows[c].weight;
            /*
             * if this cow can carry all the weight of the cows above it
             * and the previous cows are actually valid
             */
            if (cows[c].strength >= carried && all_used_safety[prev] >= 0) {
                all_used_safety[i] = std::max(
                    all_used_safety[i],
                    std::min(all_used_safety[prev], cows[c].strength - carried)
                );
            }
        }
        if (total_height >= mark_height) {
            max_safety = std::max(max_safety, all_used_safety[i]);
        }
    }
    std::string ans = max_safety != -1 ? std::to_string(max_safety) : "Mark is too tall";
    std::ofstream("guard.out") << ans << endl;
    cout << ans << endl;
}
