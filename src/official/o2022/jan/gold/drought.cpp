#include <iostream>
#include <vector>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;
using std::pair;

constexpr int MOD = 1e9 + 7;

int num_ways(const vector<int>& levels) {
    // just handle the first two pesky cases
    vector<vector<int>> num_ways{vector<int>(levels[0] + 1, 1)};
    if (levels.size() >= 2) {
        int min = std::min(levels[0], levels[1]);
        num_ways.push_back({1});
        for (int i = 1; i <= levels[1]; i++) {
            num_ways[1].push_back(i <= min);
        }
    }

    vector<int> prefs{0};
    for (int i : num_ways.back()) {
        prefs.push_back(prefs.back() + i);  // no need for modding here
    }
    for (int c = 2; c < levels.size(); c++) {
        num_ways.push_back(vector<int>(levels[c] + 1));
        for (int l = 0; l <= levels[c - 1]; l++) {
            num_ways[c][0] = (num_ways[c][0] + num_ways[c - 1][l]) % MOD;
        }
        for (int l = 1; l <= levels[c]; l++) {
            if (l <= levels[c - 1]) {
                num_ways[c][l] = prefs[levels[c - 1] - l + 1];
            }
        }

        prefs = {0};  // calculate prefix sums for the next iteration
        for (int l = 0; l <= levels[c]; l++) {
            prefs.push_back((prefs.back() + num_ways[c][l]) % MOD);
        }
    }
    
    // sum everything up @ the end & return it
    int total = 0;
    for (int i : num_ways.back()) {
        total = (total + i) % MOD;
    }
    return total;
}

/**
 * 2022 jan gold
 * 3
 * 9 11 7 should output 241
 * 4
 * 6 8 5 9 should output 137
 */
int main() {
    int cow_num;
    std::cin >> cow_num;
    vector<int> levels(cow_num);
    int min_hunger = INT32_MAX;
    for (int& c : levels) {
        std::cin >> c;
        min_hunger = std::min(min_hunger, c);
    }
    
    int total = num_ways(levels);
    if (cow_num % 2 == 1) {
        for (int i = 1; i <= min_hunger; i++)  {
            for (int& c : levels) {
                c--;
            }
            total = (total + num_ways(levels)) % MOD;
        }
    }
    cout << total << endl;
}
