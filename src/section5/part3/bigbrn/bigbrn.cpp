/*
ID: kevinsh4
TASK: bigbrn
LANG: C++
*/
#include <algorithm>
#include <fstream>
#include <iostream>
#include <vector>

using std::cout;
using std::endl;
using std::vector;

int main() {
    std::ifstream read("bigbrn.in");
    int side;
    int tree_num;
    read >> side >> tree_num;

    vector<vector<int>> pref(side + 1, vector<int>(side + 1));
    for (int t = 0; t < tree_num; t++) {
        int r, c;
        read >> r >> c;
        pref[r][c]++;
    }

    for (int r = 1; r <= side; r++) {
        for (int c = 1; c <= side; c++) {
            pref[r][c] += pref[r - 1][c] + pref[r][c - 1] - pref[r - 1][c - 1];
        }
    }
    // returns # of trees from [sr, sc] to [er, ec] (inclusive)
    auto rect = [&](int sr, int sc, int er, int ec) {
        return pref[er + 1][ec + 1] - pref[er + 1][sc] - pref[sr][ec + 1] +
               pref[sr][sc];
    };

    int lo = 1;
    int hi = side;
    int valid = 0;
    while (lo <= hi) {
        int mid = (lo + hi) / 2;

        bool can_place = false;
        for (int sr = 0; sr + mid - 1 < side; sr++) {
            for (int sc = 0; sc + mid - 1 < side; sc++) {
                if (rect(sr, sc, sr + mid - 1, sc + mid - 1) == 0) {
                    can_place = true;
                    goto done;
                }
            }
        }
        done:;

        if (can_place) {
            valid = mid;
            lo = mid + 1;
        } else {
            hi = mid - 1;
        }
    }

    cout << valid << endl;
    std::ofstream("bigbrn.out") << valid << endl;
}
