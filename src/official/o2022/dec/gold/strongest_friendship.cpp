#include <iostream>
#include <vector>
#include <set>
#include <numeric>
#include <algorithm>

using namespace std;  // just way too many at this point

class CowGroupDSU {
    private:
        vector<int> parents;
        vector<int> sizes;
        vector<int> deg;
        vector<multiset<int>> all_deg;
    public:
        CowGroupDSU(int size) : parents(size), sizes(size, 1), deg(size, 0), all_deg(size, {0}) {
            iota(parents.begin(), parents.end(), 0);
        }

        int get_strength(int c) {
            return sizes[get_top(c)] * *all_deg[get_top(c)].begin();
        }

        int get_top(int n) {
            return parents[n] == n ? n : (parents[n] = get_top(parents[n]));
        }

        bool link(int c1, int c2) {
            int c1_top = get_top(c1);
            int c2_top = get_top(c2);
            all_deg[c1_top].erase(deg[c1]++);
            all_deg[c2_top].erase(deg[c2]++);
            all_deg[c1_top].insert(deg[c1]);
            all_deg[c2_top].insert(deg[c2]);

            if ((c1 = c1_top) == (c2 = c2_top)) {
                return false;
            }
            if (sizes[c2] > sizes[c1]) {
                swap(c1, c2);
            }

            parents[c2] = c1;
            sizes[c1] += sizes[c2];
            all_deg[c1].insert(all_deg[c2].begin(), all_deg[c2].end());
            return true;
        }
};

int main() {
    int cow_num;
    int friend_num;
    cin >> cow_num >> friend_num;

    vector<set<int>> neighbors(cow_num);
    for (int f = 0; f < friend_num; f++) {
        int cow1, cow2;
        cin >> cow1 >> cow2;
        neighbors[--cow1].insert(--cow2);
        neighbors[cow2].insert(cow1);
    }

    multiset<pair<int, int>> remaining;
    for (int c = 0; c < cow_num; c++) {
        remaining.insert({neighbors[c].size(), c});
    }

    vector<pair<int, int>> remove_order;
    while (!remaining.empty()) {
        const auto& [curr_deg, curr] = *remaining.begin();
        for (int n : neighbors[curr]) {
            remove_order.push_back({curr, n});
            remaining.erase({neighbors[n].size(), n});
            neighbors[n].erase(curr);
            remaining.insert({neighbors[n].size(), n});
        }
        remaining.erase({curr_deg, curr});
    }

    int max_strength = 0;
    CowGroupDSU groups(cow_num);
    for (int f = friend_num - 1; f >= 0; f--) {
        const auto& [c1, c2] = remove_order[f];
        groups.link(c1, c2);
        max_strength = max(max_strength, groups.get_strength(c1));
    }

    cout << max_strength << endl;
}
