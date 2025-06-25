#include <algorithm>
#include <cassert>
#include <functional>
#include <iostream>
#include <set>
#include <vector>

using std::cout;
using std::endl;
using std::vector;

constexpr int MAX_COURAGE = 10;

struct Path {
    int down;
    int up;
    int difficulty;
    int enjoyment;
};

int main() {
    int wp_num; // short for waypoints
    std::cin >> wp_num;
    vector<vector<Path>> paths(wp_num);
    vector<Path> all_paths;
    for (int w = 1; w < wp_num; w++) {
        int to;
        int difficulty;
        int enjoyment;
        std::cin >> to >> difficulty >> enjoyment;
        paths[to - 1].push_back({to - 1, w, difficulty, enjoyment});
        all_paths.push_back({to - 1, w, difficulty, enjoyment});
    }
    sort(all_paths.begin(), all_paths.end(), [](const Path &a, const Path &b) {
        return a.difficulty < b.difficulty;
    });

    int friend_num;
    std::cin >> friend_num;
    vector<std::pair<int, std::pair<int, int>>> friends(friend_num);
    for (int f = 0; f < friend_num; f++) {
        friends[f].first = f;
        std::cin >> friends[f].second.first >> friends[f].second.second;
        assert(friends[f].second.second <= MAX_COURAGE);
    }
    sort(friends.begin(), friends.end(),
             [](auto &a, auto &b) { return a.second.first < b.second.first; });

    vector<int> badness(wp_num);
    vector<long long> best_paths(MAX_COURAGE + 1);
    vector<long long> enjoyment(wp_num);
    std::function<void(int)> initial_setup;
    initial_setup = [&](int at) {
        for (int i = badness[at]; i <= MAX_COURAGE; i++) {
            best_paths[i] = std::max(best_paths[i], enjoyment[at]);
        }
        for (const Path &p : paths[at]) {
            badness[p.up] = std::min(badness[at] + 1, MAX_COURAGE + 1);
            enjoyment[p.up] = enjoyment[at] + p.enjoyment;
            initial_setup(p.up);
        }
    };
    initial_setup(0);

    int path_at = 0;
    vector<long long> most_enjoyment(friend_num);
    for (const std::pair<int, std::pair<int, int>> &f : friends) {
        std::set<int> visited;
        std::function<void(int)> update_badness;
        update_badness = [&](int at) {
            if (badness[at] > MAX_COURAGE || visited.count(at)) {
                return;
            }
            visited.insert(at);
            for (int i = badness[at]; i <= MAX_COURAGE; i++) {
                best_paths[i] = std::max(best_paths[i], enjoyment[at]);
            }
            for (const Path &p : paths[at]) {
                badness[p.up] = badness[at] + (p.difficulty > f.second.first);
                update_badness(p.up);
            }
        };

        while (path_at < all_paths.size() &&
                     all_paths[path_at].difficulty <= f.second.first) {
            const Path &now_fine = all_paths[path_at++];
            if (badness[now_fine.down] <= MAX_COURAGE) {
                badness[now_fine.up] = badness[now_fine.down];
                update_badness(now_fine.up);
            }
        }
        most_enjoyment[f.first] = best_paths[f.second.second];
    }

    for (long long me : most_enjoyment) {
        cout << me << '\n';
    }
}
