#include <algorithm>
#include <iostream>
#include <set>
#include <vector>

using std::cout;
using std::endl;
using std::pair;
using std::vector;

/** 2024 us open silver */
int main() {
    int len;
    std::cin >> len;
    vector<char> genome(len * 2, '[');
    for (int i = 0; i < len; i++) {
        std::cin >> genome[i];
    }

    vector<int> inds(len);
    for (int i = 0; i < len; i++) {
        inds[i] = i;
    }
    vector<pair<int, int>> ranges{{0, len}};
    vector<int> pair_num(len + 1);
    for (int sub = 1; sub <= len; sub++) {
        for (const auto& [start, end] : ranges) {
            // rare cpp W
            std::stable_sort(inds.begin() + start, inds.begin() + end,
                             [&](int a, int b) {
                                 return genome[a + sub - 1] < genome[b + sub - 1];
                             });
        }

        std::set<int> old_ends;
        for (int r = 1; r < ranges.size(); r++) {
            old_ends.insert(ranges[r].first);
        }

        vector<int> rank(len - sub + 1);
        vector<pair<int, int>> new_ranges;
        int last = 0;
        int priority = 0;
        for (int i = 1; i < len; i++) {
            if (genome[inds[i - 1] + sub - 1] != genome[inds[i] + sub - 1] ||
                old_ends.count(i)) {
                priority++;
                new_ranges.push_back({last, i});
                last = i;
            }
            if (inds[i] < rank.size()) {
                rank[inds[i]] = priority;
            }
        }
        new_ranges.push_back({last, len});
        ranges = new_ranges;

        vector<pair<int, int>> stack{{-1, -1}};
        vector<int> longest_set(rank.size());
        for (int i = 0; i < rank.size(); i++) {
            auto it = --std::upper_bound(stack.begin(), stack.end(),
                                         pair<int, int>{rank[i], i});
            longest_set[i] = it->second + 1;
            while (!stack.empty() && rank[i] <= stack.back().first) {
                stack.pop_back();
            }
            stack.push_back({rank[i], i});
        }

        stack = {{-1, rank.size()}};
        vector<int> deltas(rank.size() + 1);
        for (int i = rank.size() - 1; i >= 0; i--) {
            auto it = --std::lower_bound(stack.begin(), stack.end(),
                                         pair<int, int>{rank[i], i});
            longest_set[i] = it->second - longest_set[i];
            deltas[longest_set[i]]--;
            while (!stack.empty() && rank[i] <= stack.back().first) {
                stack.pop_back();
            }
            stack.push_back({rank[i], i});
        }

        int curr = rank.size();
        for (int i = 0; i < rank.size(); i++) {
            curr += deltas[i];
            pair_num[curr]++;
        }
    }

    for (int i = 1; i <= len; i++) {
        cout << pair_num[i] << '\n';
    }
}
