#include <iostream>
#include <cassert>  // much better than #include stdexcept
#include <vector>
#include <set>
#include <queue>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;
using std::pair;

class DisjointSets {  // DSU for kruskal's
    private:
        vector<int> parents;
        vector<int> sizes;
    public:
        DisjointSets(int size) : parents(size), sizes(size, 1) {
            for (int i = 0; i < size; i++) {
                parents[i] = i;
            }
        }

        int get_ultimate(int n) {
            return parents[n] == n ? n : (parents[n] = get_ultimate(parents[n]));
        }

        bool link(int n1, int n2) {
            n1 = get_ultimate(n1);
            n2 = get_ultimate(n2);
            if (n1 == n2) {
                return false;
            }
            if (sizes[n1] < sizes[n2]) {
                std::swap(n1, n2);
            }
            sizes[n1] += sizes[n2];
            parents[n2] = n1;
            return true;
        }
};

// finds first occurrence of x in arr (returning -1 if it doesn't exit)
int index(const vector<int>& arr, int x) {
    auto it = std::find(arr.begin(), arr.end(), x);
    return it == arr.end() ? -1 : it - arr.begin();
}

// 2021 usopen gold (sample input ommitted bc too long lmao)
int main() {
    int node_num;
    std::cin >> node_num;
    vector<int> change_costs(node_num);
    // in this case i'm just going to use magic number bc it's super hard to generalize
    vector<vector<int>> node_portals(node_num, vector<int>(4));
    vector<vector<int>> portals(node_num * 2);
    for (int n = 0; n < node_num; n++) {
        std::cin >> change_costs[n];
        for (int p = 0; p < 4; p++) {
            std::cin >> node_portals[n][p];
            portals[--node_portals[n][p]].push_back(n);
        }
    }
    for (const vector<int>& p : portals) {
        assert(p.size() == 2);
    }

    // turn the horrid input format into something we can actually use
    vector<vector<vector<pair<int, int>>>> neighbors(node_num, vector<vector<pair<int, int>>>(2));
    for (int n = 0; n < node_num; n++) {
        for (int p_ind = 0; p_ind < 4; p_ind++) {
            int p = node_portals[n][p_ind];
            vector<int>& options = portals[p];
            int nxt = options[1] == n ? options[0] : options[1];
            int nxt_seg = index(node_portals[nxt], p) > 1;
            neighbors[n][p_ind > 1].push_back({nxt, nxt_seg});
        }
    }

    vector<std::set<pair<int, int>>> components;
    vector<vector<int>> bridges(node_num);  // each node when swapped can connect at most 2 components
    vector<vector<bool>> visited(node_num, vector<bool>(2));
    for (int n = 0; n < node_num; n++) {
        for (int seg = 0; seg < 2; seg++) {
            if (visited[n][seg]) {
                continue;
            }
            visited[n][seg] = true;
            bridges[n].push_back(components.size());

            std::set<pair<int, int>> network{{n, seg}};
            std::queue<pair<int, int>> frontier;
            frontier.push({n, seg});
            while (!frontier.empty()) {
                auto [at, part] = frontier.front();
                frontier.pop();
                for (const pair<int, int>& n : neighbors[at][part]) {
                    if (!visited[n.first][n.second]) {
                        frontier.push(n);
                        visited[n.first][n.second] = true;
                        network.insert(n);
                        bridges[n.first].push_back(components.size());
                    }
                }
            }
            components.push_back(network);
        }
    }
    
    vector<pair<int, int>> network_links;
    for (int n = 0; n < node_num; n++) {
        network_links.push_back({change_costs[n], n});
    }
    std::sort(network_links.begin(), network_links.end());
    DisjointSets linked_networks(components.size());
    int total_cost = 0;
    for (auto [cost, e] : network_links) {  // run a simple kruskal's
        total_cost += linked_networks.link(bridges[e][0], bridges[e][1]) * cost;
    }
    cout << total_cost << endl;
}
