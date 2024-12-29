/*
ID: kevinsh4
TASK: rectbarn
LANG: C++
*/
#include <algorithm>
#include <fstream>
#include <iostream>
#include <vector>
#include <set>

using std::cout;
using std::endl;
using std::vector;

int main() {
    std::ifstream read("rectbarn.in");
    int row_num;
    int col_num;
    int bad_num;
    read >> row_num >> col_num >> bad_num;
    vector<std::set<int>> bad(row_num);
    for (int b = 0; b < bad_num; b++) {
        int r, c;
        read >> r >> c;
        bad[--r].insert(--c);
    }

    int max_area = 0;
    vector<int> right_clear(row_num);
    // copied from gfg lmao i cba
    for (int c = col_num - 1; c >= 0; c--) {
        vector<int> s;
        for (int r = 0; r < row_num; r++) {
            right_clear[r] = bad[r].count(c) ? 0 : right_clear[r] + 1;

            while (!s.empty() && right_clear[s.back()] >= right_clear[r]) {
                int tp = s.back();
                s.pop_back();
                int width = s.empty() ? r : r - s.back() - 1;
                max_area = std::max(max_area, right_clear[tp] * width);
            }
            s.push_back(r);
        }

        while (!s.empty()) {
            int tp = s.back();
            s.pop_back();
            int curr = right_clear[tp] * (s.empty() ? row_num : row_num - s.back() - 1);
            max_area = std::max(max_area, curr);
        }
    }

    std::ofstream("rectbarn.out") << max_area << endl;
}
