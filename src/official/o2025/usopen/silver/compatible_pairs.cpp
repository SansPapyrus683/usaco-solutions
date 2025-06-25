#include <iostream>
#include <map>
#include <set>
#include <vector>

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

    std::set<int> visited;
    long long pairs = 0;
    for (const auto& [id, amt] : cows) {  // idt second field is used actually
        if (visited.count(id)) { continue; }

        // this is so, so awful, but i legit don't know how else to get it
        visited.insert(id);
        vector<int> frontier{id};
        int start = -1;
        while (!frontier.empty()) {
            int curr = frontier.back();
            frontier.pop_back();
            bool has_next = false;
            for (int t : vector<int>{a, b}) {
                int next = t - curr;
                if (next != curr && cows.count(next) && !visited.count(next)) {
                    visited.insert(next);
                    frontier.push_back(next);
                    has_next = true;
                }
            }
            if (!has_next) { start = curr; }
        }

        vector<int> in_order{start};
        bool no_more;
        do {
            no_more = true;
            for (int t : vector<int>{a, b}) {
                int next = t - in_order.back();
                if (next != in_order.back() && cows.count(next) &&
                    (in_order.size() == 1 || next != in_order[in_order.size() - 2])) {
                    in_order.push_back(next);
                    no_more = false;
                }
            }
        } while (!no_more);

        for (int i = 0; i < in_order.size() - 1; i++) {
            int use = std::min(cows[in_order[i]], cows[in_order[i + 1]]);
            cows[in_order[i]] -= use;
            cows[in_order[i + 1]] -= use;
            pairs += use;
        }
        for (int i : vector<int>{in_order[0], in_order.back()}) {
            if (i + i == a || i + i == b) {
                int use = cows[i] / 2;
                cows[i] -= use * 2;
                pairs += use;
            }
        }
    }

    cout << pairs << endl;
}
