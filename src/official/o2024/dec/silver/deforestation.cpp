#include <algorithm>
#include <deque>
#include <iostream>
#include <set>
#include <vector>

using std::cout;
using std::endl;
using std::pair;
using std::vector;

int main() {
    int test_num;
    std::cin >> test_num;
    for (int t = 0; t < test_num; t++) {
        int tree_num;
        int law_num;
        std::cin >> tree_num >> law_num;

        vector<int> trees(tree_num);
        vector<pair<int, int>> events;
        for (int& t : trees) {
            std::cin >> t;
            events.push_back({t, 0});
        }
        std::sort(trees.begin(), trees.end());

        // incredibly rare instance where 1-indexing actually helps lol
        vector<int> law_caps(law_num + 1);
        for (int l = 1; l <= law_num; l++) {
            int start, end, min_trees;
            std::cin >> start >> end >> min_trees;
            const int in_range = std::upper_bound(trees.begin(), trees.end(), end) -
                                 std::lower_bound(trees.begin(), trees.end(), start);
            law_caps[l] = in_range - min_trees;
            events.push_back({start, -l});
            events.push_back({end, l});
        }

        std::sort(events.begin(), events.end());
        vector<bool> active(law_num + 1);
        std::deque<std::set<int>> tree_dq(tree_num + 1);
        int cutdown = 0;
        for (const auto& [pos, type] : events) {
            const int law_id = abs(type);
            if (type < 0) {
                active[law_id] = true;
                tree_dq[law_caps[law_id]].insert(law_id);
            } else if (type > 0) {
                active[law_id] = false;
                tree_dq[0].erase(law_id);
            } else {
                if (!tree_dq[0].empty()) {
                    continue;
                }
                tree_dq.pop_front();
                cutdown++;
                tree_dq.push_back({});

                std::set<int>& new_front = tree_dq[0];
                auto it = new_front.begin();
                while (it != new_front.end()) {
                    if (!active[*it]) {
                        it = new_front.erase(it);
                    } else {
                        it++;
                    }
                }
            }
        }

        cout << cutdown << '\n';
    }
}
