#include <iostream>
#include <fstream>
#include <vector>
#include <tuple>
#include <set>
#include <deque>
#include <algorithm>

// #include "debugging.h"

typedef std::tuple<int, int, int> state;
using std::cout;
using std::endl;
using std::vector;
using std::pair;

constexpr char EMPTY = 'E';

// the day i use globals will be the day i die
bool at_goal(const state& pos, int width) {
    return std::get<0>(pos) == width - 1 && std::get<1>(pos) == width - 1;
}

bool in_haybale(const state& pos, const vector<vector<bool>>& barn) {
    return !barn[std::get<0>(pos)][std::get<1>(pos)];
}

state forward(state curr, int width) {
    if (at_goal(curr, width)) {
        return curr;
    }
    switch (std::get<2>(curr)) {
        case 0:  // up (-1 because of how array indices work)
            std::get<0>(curr)--;
            break;
        case 1:  // right
            std::get<1>(curr)++;
            break;
        case 2:  // down
            std::get<0>(curr)++;
            break;
        case 3:  // left
            std::get<1>(curr)--;
            break;
    }
    std::get<0>(curr) = std::min(width - 1, std::max(0, std::get<0>(curr)));
    std::get<1>(curr) = std::min(width - 1, std::max(0, std::get<1>(curr)));
    return curr;
}

state turn(state curr, bool left) {
    if (left) {
        std::get<2>(curr)++;
    } else {
        std::get<2>(curr)--;
    }
    std::get<2>(curr) = (std::get<2>(curr) + 4) % 4;
    return curr;
}

// 2017 jan gold
int main() {
    std::ifstream read("cownav.in");
    int width;
    read >> width;
    vector<vector<bool>> barn(width, vector<bool>(width));
    for (int r = 0; r < width; r++) {
        for (int c = 0; c < width; c++) {  // omg c++ in c++
            char cell;
            read >> cell;
            // read the rows ins reverse so we start @ the (0, 0) and move to the bottom right
            barn[width - r - 1][c] = cell == EMPTY;
        }
    }

    // 0, 1, 2, and 3 are up, right, down, and left respectively
    vector<pair<state, state>> frontier{{{0, 0, 1}, {0, 0, 2}}};
    std::set<pair<state, state>> visited;
    visited.insert(frontier[0]);
    int moves = 0;
    // do a simple bfs to find the first time we reach the goal
    while (!frontier.empty()) {
        vector<pair<state, state>> in_line;
        for (pair<state, state> b : frontier) {
            if (at_goal(b.first, width) && at_goal(b.second, width)) {
                goto end;
            }
            pair<state, state> moved = {forward(b.first, width), forward(b.second, width)};
            if (in_haybale(moved.first, barn)) {  // revert back to previous if we move into a haybale
                moved.first = b.first;
            }
            if (in_haybale(moved.second, barn)) {
                moved.second = b.second;
            }
            
            pair<state, state> turn_left = {turn(b.first, true), turn(b.second, true)};
            pair<state, state> turn_right = {turn(b.first, false), turn(b.second, false)};
            for (pair<state, state> n : vector<pair<state, state>>{moved, turn_left, turn_right}) {
                if (visited.find(n) == visited.end()) {
                    visited.insert(n);
                    in_line.push_back(n);
                }
            }
        }
        moves++;
        frontier = in_line;
    }
    end:;
    std::ofstream("cownav.out") << moves << endl;
    cout << moves << endl;
}
