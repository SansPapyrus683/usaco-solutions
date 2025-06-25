#include <iostream>
#include <vector>
#include <algorithm>
#include <map>

using std::cout;
using std::endl;
using std::vector;

/** 2025 us open silver */
int main() {
    int id_num;
    int a, b;
    std::cin >> id_num >> a >> b;
    std::map<int, int> cows;
    for (int i = 0; i < id_num; i++) {
        int amt, id;
        std::cin >> amt >> id;
        cows[id] = amt;
    }

    std::map<int, vector<int>> adj;
    for (const auto& [id, amt] : cows) {
        if (a - id != id && cows.count(a - id)) {
            adj[id].push_back(a - id);
        }
        if (a != b && b - id != id && cows.count(b - id)) {
            adj[id].push_back(b - id);
        }
    }

    long long pairs = 0;
    // amt isn't used here but idk if underscores are the convention in c++
    for (const auto& [id, amt] : cows) {
        if (adj[id].size() != 1) {
            continue;
        }
        int prev = id;
        int at = adj[id][0];
        while (adj[at].size() == 2) {
            int use = std::min(cows[prev], cows[at]);
            pairs += use;
            cows[prev] -= use;
            cows[at] -= use;
            int old_at = at;
            at = adj[at][0] == prev ? adj[at][1] : adj[at][0];
            prev = old_at;
        }

        int use = std::min(cows[prev], cows[at]);
        pairs += use;
        cows[prev] -= use;
        cows[at] -= use;
    }
    for (const auto& [id, amt] : cows) {
        if (id + id == a || id + id == b) {
            pairs += amt / 2;
        }
    }

    cout << pairs << endl;
}
