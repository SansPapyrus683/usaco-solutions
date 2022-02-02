#include <iostream>
#include <vector>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;
using std::pair;

constexpr int MOD = 1e9 + 7;

int solve(const vector<int>& cows) {
    vector<vector<int>> dp{vector<int>(cows[0] + 1, 1)};
    if (cows.size() >= 2) {
        int min = std::min(cows[0], cows[1]);
        dp.push_back({1});
        for (int i = 1; i <= cows[1]; i++) {
            dp[1].push_back(i <= min);
        }
    }

    vector<int> prefs{0};
    for (int i : dp.back()) {
        prefs.push_back(prefs.back() + i);  // no need for modding here
    }
    for (int c = 2; c < cows.size(); c++) {
        dp.push_back(vector<int>(cows[c] + 1));
        for (int i = 0; i <= cows[c - 1]; i++) {
            dp[c][0] = (dp[c][0] + dp[c - 1][i]) % MOD;
        }
        for (int i = 1; i <= cows[c]; i++) {
            if (i <= cows[c - 1]) {
                dp[c][i] = prefs[cows[c - 1] - i + 1];
            }
        }

        prefs = {0};
        for (int i = 0; i <= cows[c]; i++) {
            prefs.push_back((prefs.back() + dp[c][i]) % MOD);
        }
    }
    
    int total = 0;
    for (int i : dp.back()) {
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
    vector<int> cows(cow_num);
    int min_hunger = INT32_MAX;
    for (int& c : cows) {
        std::cin >> c;
        min_hunger = std::min(min_hunger, c);
    }
    
    int total = solve(cows);
    if (cow_num % 2 == 1) {
        for (int i = 1; i <= min_hunger; i++)  {
            for (int& c : cows) {
                c--;
            }
            total = (total + solve(cows)) % MOD;
        }
    }
    cout << total << endl;
}
