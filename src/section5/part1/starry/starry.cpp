/*
ID: kevinsh4
TASK: starry
LANG: C++
*/
#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <map>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;
using std::pair;

vector<pair<int, int>> adj8(int r, int c) {
    return {
        {r - 1, c}, {r + 1, c}, {r, c - 1}, {r, c + 1},
        {r - 1, c - 1}, {r - 1, c + 1}, {r + 1, c - 1}, {r + 1, c + 1}
    };
}

void rotate(vector<pair<int, int>>& pts) {
    for (auto& [x, y] : pts) {
        int tmp = y;
        y = x;
        x = -tmp;
    }
}

void normalize(vector<pair<int, int>>& pts) {
    std::sort(pts.begin(), pts.end());
    auto [sub_x, sub_y] = pts[0];
    for (auto& [x, y] : pts) {
        x -= sub_x;
        y -= sub_y;
    }
}

std::set<vector<pair<int, int>>> similar(vector<pair<int, int>> pts) {
    std::set<vector<pair<int, int>>> ret;
    for (int i = 0; i < 4; i++) {
        rotate(pts);
        normalize(pts);
        ret.insert(pts);
    }
    for (auto& [_, y] : pts) {
        y = -y;  // reflect the points
    }
    for (int i = 0; i < 4; i++) {
        rotate(pts);
        normalize(pts);
        ret.insert(pts);
    }

    return ret;
}

int main() {
    std::ifstream read("starry.in");
    int col_num, row_num;
    read >> col_num >> row_num;
    // have a border of false's around for oob stuff
    vector<vector<bool>> stars(row_num + 2, vector<bool>(col_num + 2));
    for (int r = 0; r < row_num; r++) {
        for (int c = 0; c < col_num; c++) {
            char pt;
            read >> pt;
            stars[r + 1][c + 1] = pt == '1';
        }
    }

    vector<vector<char>> visited(row_num + 2, vector<char>(col_num + 2, '0'));
    std::map<char, std::set<vector<pair<int, int>>>> constellations;
    for (int r = 1; r <= row_num; r++) {
        for (int c = 1; c <= col_num; c++) {
            if (visited[r][c] != '0' || !stars[r][c]) {
                continue;
            }
            
            visited[r][c] = '@';
            vector<pair<int, int>> comp;
            vector<pair<int, int>> frontier{{r, c}};
            while (!frontier.empty()) {
                pair<int, int> curr = frontier.back();
                frontier.pop_back();
                comp.push_back(curr);
                for (auto [nr, nc] : adj8(curr.first, curr.second)) {
                    if (stars[nr][nc] && visited[nr][nc] == '0') {
                        visited[nr][nc] = '@';
                        frontier.push_back({nr, nc});
                    }
                }
            }

            std::set<vector<pair<int, int>>> sim = similar(comp);
            char use = 'a' + constellations.size();
            for (const auto& [id, c] : constellations) {
                if (c == sim) {
                    use = id;
                    break;
                }
            }
            constellations[use] = sim;
            for (const auto& [r, c] : comp) {
                visited[r][c] = use;
            }
        }
    }

    std::ofstream written("starry.out");
    for (int r = 1; r <= row_num; r++) {
        for (int c = 1; c <= col_num; c++) {
            written << visited[r][c];
        }
        written << '\n';
    }
}
