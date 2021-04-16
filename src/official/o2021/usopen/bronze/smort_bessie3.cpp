#include <iostream>
#include <vector>
#include <set>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;
using std::pair;

constexpr char COW = 'C';

vector<pair<int, int>> neighbor_cows(const vector<vector<char>>& field, const pair<int, int>& pos) {
    vector<pair<int, int>> neighbor_cows;
    if (pos.first > 0 && field[pos.first - 1][pos.second] == COW) {
        neighbor_cows.push_back({pos.first - 1, pos.second});
    }
    if (pos.second > 0 && field[pos.first][pos.second - 1] == COW) {
        neighbor_cows.push_back({pos.first, pos.second - 1});
    }
    if (pos.first < field.size() - 1 && field[pos.first + 1][pos.second] == COW) {
        neighbor_cows.push_back({pos.first + 1, pos.second});
    }
    if (pos.second < field[0].size() - 1 && field[pos.first][pos.second + 1] == COW) {
        neighbor_cows.push_back({pos.first, pos.second + 1});
    }
    return neighbor_cows;
}

/**
 * 2021 usopen bronze
 * 4 5
 * .CGGC
 * .CGCG
 * CGCG.
 * .CC.C should output 4
 */
int main() {
    int row_num;
    int col_num;
    std::cin >> row_num >> col_num;
    vector<vector<char>> field(row_num, vector<char>(col_num));
    for (int r = 0; r < row_num; r++) {
        for (int c = 0; c < col_num; c++) {
            std::cin >> field[r][c];
        }
    }

    std::set<pair<pair<int, int>, pair<int, int>>> friends;
    for (int r = 0; r < row_num; r++) {
        for (int c = 0; c < col_num; c++) {
            if (field[r][c] == 'G') {
                vector<pair<int, int>> reachable = neighbor_cows(field, {r, c});
                std::sort(reachable.begin(), reachable.end());
                pair<pair<int, int>, pair<int, int>> friendship{{-1, -1}, {-1, -1}};
                if (reachable.size() == 2) {
                    // if there's only 2 cows, just add them, what's the worst that could happen
                    friendship = {reachable[0], reachable[1]};
                } else if (reachable.size() > 2) {
                    /*
                     * we have more than 2, so add the ones that only this grass square
                     * could achieve, aka cows on opposite sides
                     */
                    for (int i = 0; i < reachable.size(); i++) {
                        for (int j = i + 1; j < reachable.size(); j++) {
                            if (((reachable[i].first != reachable[j].first)
                                    + (reachable[i].second != reachable[j].second)) == 1) {
                                friendship = {reachable[i], reachable[j]};
                            }
                        }
                    }
                }
                if (friendship.first.first != -1) {
                    friends.insert(friendship);
                }
            }
        }
    }
    cout << friends.size() << endl;
}
