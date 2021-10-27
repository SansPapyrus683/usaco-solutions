#include <fstream>
#include <iostream>
#include <vector>
#include <deque>

using std::cout;
using std::endl;
using std::vector;
using std::pair;

bool reachable(const vector<vector<int>>& heights,
               const vector<pair<int, int>>& checkpoints,
               const pair<int, int>& start,
               int difficulty) {
    vector<vector<bool>> visited(heights.size(), vector<bool>(heights[0].size()));
    visited[start.first][start.second] = true;
    std::deque<pair<int, int>> frontier{start};
    while (!frontier.empty()) {  // do a bfs to see all the nodes we can go through with this difficulty
        pair<int, int> curr = frontier.front();
        frontier.pop_front();
        int curr_height = heights[curr.first][curr.second];

        // decrease the row number (go up)
        if (curr.first > 0
                && std::abs(curr_height - heights[curr.first - 1][curr.second]) <= difficulty
                && !visited[curr.first - 1][curr.second]) {
            frontier.push_back({curr.first - 1, curr.second});
            visited[curr.first - 1][curr.second] = true;
        }
        // increase the row number (go down)
        if (curr.first < heights.size() - 1
                && std::abs(curr_height - heights[curr.first + 1][curr.second]) <= difficulty
                && !visited[curr.first + 1][curr.second]) {
            frontier.push_back({curr.first + 1, curr.second});
            visited[curr.first + 1][curr.second] = true;
        }
        // decrease the col number (go left)
        if (curr.second > 0
                && std::abs(curr_height - heights[curr.first][curr.second - 1]) <= difficulty
                && !visited[curr.first][curr.second - 1]) {
            frontier.push_back({curr.first, curr.second - 1});
            visited[curr.first][curr.second - 1] = true;
        }
        // increase the col number (go right)
        if (curr.second < heights[0].size() - 1
                && std::abs(curr_height - heights[curr.first][curr.second + 1]) <= difficulty
                && !visited[curr.first][curr.second + 1]) {
            frontier.push_back({curr.first, curr.second + 1});
            visited[curr.first][curr.second + 1] = true;
        }
    }
    for (pair<int, int> cp : checkpoints) {  // see if all the checkpoints have been visited
        if (!visited[cp.first][cp.second]) {
            return false;
        }
    }
    return true;
}

// 2014 jan silver (this one, unlike the java version, passes all the test cases)
int main() {
    std::ifstream read("ccski.in");
    int row_num;
    int col_num;
    read >> row_num >> col_num;
    vector<vector<int>> heights(row_num, vector<int>(col_num));
    vector<pair<int, int>> checkpoints;

    for (int r = 0; r < row_num; r++) {
        for (int c = 0; c < col_num; c++) {
            read >> heights[r][c];
        }
    }
    pair<int, int> start{-1, -1};
    for (int r = 0; r < row_num; r++) {
        for (int c = 0; c < col_num; c++) {
            int point;
            read >> point;
            if (point) {
                start = {r, c};  // we can start at any checkpoint, really
                checkpoints.push_back({r, c});
            }
        }
    }
    if (start == pair<int, int>{-1, -1}) {
        std::ofstream("ccski.out") << 0 << endl;
        cout << 0 << endl;
    }

    int lo = 0;
    int hi = INT32_MAX / 2;  // this should probably be big enough
    int valid = -1;
    while (lo <= hi) {  // binsearch for the lowest possible difficulty
        int mid = (lo + hi) / 2;
        if (reachable(heights, checkpoints, start, mid)) {
            valid = mid;
            hi = mid - 1;
        } else {
            lo = mid + 1;
        }
    }
    std::ofstream("ccski.out") << valid << endl;
    cout << valid << endl;
}
