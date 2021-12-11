#include <iostream>
#include <vector>
#include <deque>

using std::cout;
using std::endl;
using std::vector;
using Pos = std::pair<int, int>;

constexpr char ROCK = '#';
constexpr char START = 'S';

vector<Pos> neighbors(const Pos& at, int side) {
    vector<Pos> possible{
        {at.first + 1, at.second},
        {at.first - 1, at.second},
        {at.first, at.second + 1},
        {at.first, at.second - 1},
    };
    vector<Pos> actual;
    for (const Pos& p : possible) {
        if (0 <= p.first && p.first < side && 0 <= p.second && p.second < side) {
            actual.push_back(p);
        }
    }
    return actual;
}

/**
 * http://usaco.org/index.php?page=viewproblem2&cpid=1065
 * (sample input ommitted due to length)
 */
int main() {
    int side;
    int rep_time;
    std::cin >> side >> rep_time;
    vector<vector<char>> farm(side, vector<char>(side));
    vector<Pos> rocks;
    vector<Pos> starts;
    for (int r = 0; r < side; r++) {
        for (int c = 0; c < side; c++) {
            std::cin >> farm[r][c];
            if (farm[r][c] == ROCK) {
                rocks.push_back({r, c});
            } else if (farm[r][c] == START) {
                starts.push_back({r, c});
            }
        }
    }

    #pragma region get all the dists for the rocks
    vector<vector<int>> rock_dist(side, vector<int>(side, INT32_MAX));
    for (const Pos& r : rocks) {
        rock_dist[r.first][r.second] = 0;
    }
    std::deque<Pos> rock_frontier(rocks.begin(), rocks.end());
    while (!rock_frontier.empty()) {
        Pos curr = rock_frontier.front();
        rock_frontier.pop_front();
        int curr_dist = rock_dist[curr.first][curr.second];
        for (Pos n : neighbors(curr, side)) {
            if (rock_dist[n.first][n.second] == INT32_MAX) {
                rock_dist[n.first][n.second] = curr_dist + 1;
                rock_frontier.push_back(n);
            }
        }
    }
    #pragma endregion

    vector<vector<bool>> visitable(side, vector<bool>(side));
    for (const Pos& s : starts) {
        visitable[s.first][s.second] = true;
    }
    vector<Pos> robot_frontier(starts);
    int time = 0;
    while (!robot_frontier.empty()) {
        vector<Pos> next;
        int radius = time / rep_time + 1;
        for (Pos p : robot_frontier) {
            for (Pos n : neighbors(p, side)) {
                int rock = rock_dist[n.first][n.second];
                if (!visitable[n.first][n.second] && radius <= rock) {
                    next.push_back(n);
                    visitable[n.first][n.second] = true;
                }
            }
        }
        time++;
        // get the new radius & see which robots die by expanding
        radius = time / rep_time + 1;
        robot_frontier = vector<Pos>();
        for (Pos p : next) {
            if (radius <= rock_dist[p.first][p.second]) {
                robot_frontier.push_back(p);
            }
        }
    }

    // this should be somewhere around the max radius
    vector<vector<Pos>> max_rad(side / 2 + 2);
    for (int r = 0; r < side; r++) {
        for (int c = 0; c < side; c++) {
            if (visitable[r][c]) {
                max_rad[rock_dist[r][c] - 1].push_back({r, c});
            }
        }
    }

    vector<vector<bool>> has_robot(side, vector<bool>(side));
    robot_frontier = vector<Pos>();
    for (int r = max_rad.size() - 1; r >= 0; r--) {
        vector<Pos> next;
        for (Pos p : robot_frontier) {
            for (Pos n : neighbors(p, side)) {
                if (!has_robot[n.first][n.second]) {
                    has_robot[n.first][n.second] = true;
                    next.push_back(n);
                }
            }
        }
        robot_frontier = next;

        for (Pos p : max_rad[r]) {
            if (!has_robot[p.first][p.second]) {
                robot_frontier.push_back(p);
                has_robot[p.first][p.second] = true;
            }
        }
    }

    int total_robots = 0;
    for (int r = 0; r < side; r++) {
        for (int c = 0; c < side; c++) {
            total_robots += has_robot[r][c];
        }
    }
    cout << total_robots << endl;
}
