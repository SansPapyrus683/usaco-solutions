/*
ID: kevinsh4
TASK: snail
LANG: C++
*/
#include <fstream>
#include <iostream>
#include <string>
#include <vector>
#include <functional>
#include <algorithm>

using namespace std;

const vector<pair<int, int>> DIRS{{1, 0}, {0, -1}, {-1, 0}, {0, 1}};

int main() {
    ifstream read("snail.in");
    int side;
    read >> side;
    vector<string> grid(side + 2, string(side + 2, '.'));
    grid[0] = grid.back() = string(side + 2, '#');
    for (string& row : grid) {
        row[0] = row.back() = '#';
    }
    
    int barrier_num;
    read >> barrier_num;
    for (int b = 0; b < barrier_num; b++) {
        char col;
        int row;
        read >> col >> row;
        grid[row][col - 'A' + 1] = '#';
    }


    int max_vis = 1;
    int curr_vis = 1;
    function<void(int, int, int)> dfs;
    dfs = [&] (int r, int c, int dir) {
        if (grid[r][c] == 'X') {
            return;
        }
        if (grid[r][c] == '#') {
            int prev_r = r - DIRS[dir].first;
            int prev_c = c - DIRS[dir].second;

            int turn_left = (dir == 0 ? DIRS.size() : dir) - 1;
            const auto& [ldr, ldc] = DIRS[turn_left];
            if (grid[prev_r + ldr][prev_c + ldc] != '#') {
                dfs(prev_r + ldr, prev_c + ldc, turn_left);
            }

            int turn_right = (dir + 1) % DIRS.size();
            const auto& [rdr, rdc] = DIRS[turn_right];
            if (grid[prev_r + rdr][prev_c + rdc] != '#') {
                dfs(prev_r + rdr, prev_c + rdc, turn_right);
            }
            return;
        }

        max_vis = max(max_vis, ++curr_vis);
        grid[r][c] = 'X';
        dfs(r + DIRS[dir].first, c + DIRS[dir].second, dir);
        grid[r][c] = '.';
        curr_vis--;
    };

    grid[1][1] = 'X';
    dfs(1, 2, 3);
    dfs(2, 1, 0);

    cout << max_vis << endl;
    ofstream("snail.out") << max_vis << endl;
}
