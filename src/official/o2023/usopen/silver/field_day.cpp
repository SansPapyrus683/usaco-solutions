#include <iostream>
#include <cassert>
#include <vector>
#include <map>
#include <set>

using std::cout;
using std::endl;
using std::vector;

/** 2023 us open silver */
int main() {
    int team_sz;
    int team_num;
    std::cin >> team_sz >> team_num;
    assert(team_sz <= 18);

    int half1_sz = team_sz / 2;
    int half2_sz = team_sz - half1_sz;
    vector<std::pair<int, int>> teams;
    std::map<int, std::set<int>> valid_half2s;  // all half2s w/ the given half1
    for (int t = 0; t < team_num; t++) {
        int team = 0;
        for (int c = 0; c < team_sz; c++) {
            char cow;
            std::cin >> cow;
            team += cow == 'G';  // G is 1, H is 0
            team <<= 1;  // make room for the next cow (same as multiply by 2)
        }
        team >>= 1;  // we over-multiplied by 2 once

        int half1 = team >> half2_sz;
        int half2 = team & ((1 << half2_sz) - 1);
        teams.push_back({half1, half2});
        valid_half2s[half1].insert(half2);
    }

    // half2_sz >= half1_sz always, so just use the bigger one
    vector<vector<int>> team_diff(1 << half2_sz, vector<int>(1 << half2_sz));
    // precalculate the differences between team halves (not rlly needed tbh)
    for (int t1 = 0; t1 < (1 << half2_sz); t1++) {
        for (int t2 = 0; t2 < (1 << half2_sz); t2++) {
            team_diff[t1][t2] = __builtin_popcount(t1 ^ t2);
        }
    }

    // given the current half1 & the other half2, what's the best we can do?
    vector<vector<int>> best_poss(1 << half1_sz, vector<int>(1 << half2_sz, -1));
    for (int h1 = 0; h1 < (1 << half1_sz); h1++) {
        for (int h2 = 0; h2 < (1 << half2_sz); h2++) {
            for (int t_h2 : valid_half2s[h1]) {
                best_poss[h1][h2] = std::max(
                    best_poss[h1][h2], team_diff[h2][t_h2]
                );
            }
        }
    }

    vector<int> max_diffs;
    for (const auto& [h1, h2] : teams) {
        int max_diff = 0;
        // go through all possible other half1s & use our precalculation
        for (int other_h1 = 0; other_h1 < (1 << half1_sz); other_h1++) {
            // is this other_h1 actually in our team list tho?
            if (best_poss[other_h1][h2] == -1) {
                continue;
            }
            max_diff = std::max(
                max_diff,
                team_diff[h1][other_h1] + best_poss[other_h1][h2]
            );
        }
        max_diffs.push_back(max_diff);
    }

    for (int d : max_diffs) {
        cout << d << '\n';
    }
}
