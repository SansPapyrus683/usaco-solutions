#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <queue>
#include <unordered_map>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;
using std::pair;

constexpr int MOD = 1e9 + 7;

int main() {
    std::ifstream read("mooriokart.in");
    int meadow_num;
    int road_num;
    int added_len;
    int threshold;
    read >> meadow_num >> road_num >> added_len >> threshold;
    vector<vector<pair<int, int>>> neighbors(meadow_num);
    for (int r = 0; r < road_num; r++) {
        int from;
        int to;
        int dist;
        read >> from >> to >> dist;
        neighbors[--from].push_back({--to, dist});
        neighbors[to].push_back({from, dist});
    }

    std::set<vector<int>> farms;
    vector<vector<int>> dists(meadow_num, vector<int>(meadow_num, ~0U >> 1));  // too lazy to #include climits
    // run a bfs to get all meadow distance from each other
    for (int m = 0; m < meadow_num; m++) {
        vector<int> farm{m};
        std::queue<int> frontier;
        frontier.push(m);
        dists[m][m] = 0;
        while (!frontier.empty()) {
            int curr = frontier.front();
            int curr_dist = dists[m][curr];
            frontier.pop();
            for (pair<int, int> n : neighbors[curr]) {
                if (curr_dist + n.second < dists[m][n.first]) {
                    frontier.push(n.first);
                    dists[m][n.first] = curr_dist + n.second;
                    farm.push_back(n.first);
                }
            }
        }
        std::sort(farm.begin(), farm.end());
        farms.insert(farm);
    }

    /*
     * this[i] = # of paths and sum of path lengths that are length i
     * all paths that meet the threshold are grouped together
     */
    vector<pair<long long, long long>> tracks(threshold + 1);
    tracks[0].first = 1;
    bool first = true;
    for (vector<int> f : farms) {
        std::unordered_map<int, std::pair<long long, long long>> farm_paths;
        for (int i = 0; i < f.size(); i++) {
            for (int j = i + 1; j < f.size(); j++) {
                int len = dists[f[i]][f[j]] + added_len;
                // += 2 because it can go either way (no idea why i have to have the "first" check but it makes it work)
                farm_paths[std::min(len, threshold)].first += first ? 1 : 2;
                farm_paths[std::min(len, threshold)].second += len * (first ? 1 : 2);
            }
        }
        first = false;

        vector<pair<long long, long long>> before = tracks;
        tracks = vector<pair<long long, long long>>(threshold + 1);
        for (int prev_len = 0; prev_len <= threshold; prev_len++) {
            for (auto [added, info] : farm_paths) {
                int new_len = std::min(prev_len + added, threshold);
                tracks[new_len].first = (tracks[new_len].first + (before[prev_len].first * info.first) % MOD) % MOD;
                tracks[new_len].second = (tracks[new_len].second + (before[prev_len].first * info.second) % MOD + (info.first * before[prev_len].second) % MOD) % MOD;
            }
        }
    }

    long long total = tracks[threshold].second;
    /* 
     * we can visit the farms in n ways, but because of how tracks are distinguished,
     * we have to divide it by n (rotational symmetry stuff), so it's the same as
     * not multiplying it by n in the first place- hence the < instead of <=
     */
    for (int i = 1; i < farms.size(); i++) {
        total = (total * i) % MOD;
    }
    std::ofstream("mooriokart.out") << total << endl;
    cout << total << endl;
}
