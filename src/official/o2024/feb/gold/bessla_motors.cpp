#include <algorithm>
#include <iostream>
#include <map>
#include <queue>
#include <vector>

using std::cout;
using std::endl;
using std::pair;
using std::vector;

struct Pos {
    int at;
    int charger;
};

struct PosCmp {
    bool operator()(const pair<int, Pos>& p1, const pair<int, Pos>& p2) {
        return p1.first > p2.first;
    }
};

/** 2024 feb gold */
int main() {
    int point_num, road_num;
    int charging_num;
    int range;
    int good_thresh;
    std::cin >> point_num >> road_num >> charging_num >> range >> good_thresh;
    vector<vector<pair<int, int>>> neighbors(point_num);
    for (int r = 0; r < road_num; r++) {
        int from, to, dist;
        std::cin >> from >> to >> dist;
        neighbors[--from].push_back({--to, dist});
        neighbors[to].push_back({from, dist});
    }

    std::priority_queue<pair<int, Pos>, vector<pair<int, Pos>>, PosCmp> frontier;
    vector<std::map<int, int>> best(point_num);
    for (int i = 0; i < charging_num; i++) {
        frontier.push({0, {i, i}});
        best[i] = {{i, 0}};
    }
    while (!frontier.empty()) {
        auto [curr_dist, curr] = frontier.top();
        frontier.pop();
        // membership check; might've been erased actually
        if (!best[curr.at].count(curr.charger) ||
            best[curr.at][curr.charger] != curr_dist) {
            continue;
        }
        for (const auto& [n, dist] : neighbors[curr.at]) {
            int new_dist = dist + curr_dist;
            if (new_dist > range) {
                continue;
            }

            std::map<int, int>& n_log = best[n];
            bool should_add = false;
            if (n_log.count(curr.charger)) {
                should_add = new_dist < n_log[curr.charger];
            } else if (n_log.size() < good_thresh) {
                should_add = true;
            } else {
                auto longest = std::max_element(
                    n_log.begin(), n_log.end(),
                    [&](const pair<int, int>& a, const pair<int, int>& b) {
                        return a.second < b.second;
                    });
                if (new_dist < longest->second) {
                    n_log.erase(longest);
                    should_add = true;
                }
            }

            if (should_add) {
                n_log[curr.charger] = new_dist;
                frontier.push({new_dist, {n, curr.charger}});
            }
        }
    }

    vector<int> well_connected;
    for (int p = charging_num; p < point_num; p++) {
        if (best[p].size() == good_thresh) {
            well_connected.push_back(p);
        }
    }

    cout << well_connected.size() << '\n';
    for (int p : well_connected) {
        cout << p + 1 << '\n';
    }
}
