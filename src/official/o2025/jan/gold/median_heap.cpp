#include <algorithm>
#include <cassert>
#include <cstdint>
#include <iostream>
#include <set>
#include <vector>

using namespace std;

class MedianHeap {
   private:
    struct Info {
        long long eq, gte, lte;
    };

    const vector<pair<int, int>>& tree;
    int curr_median = INT32_MIN;
    vector<pair<int, int>> bkpts;  // short for "breakpoints"
    int bkpt_at = 0;
    vector<Info> costs;

    int heapify(int at = 0) {
        int curr = tree[at].first;
        if (2 * at + 1 >= tree.size()) {
            bkpts.push_back({curr, at});
            bkpts.push_back({curr + 1, at});
            return curr;
        }
        int left = heapify(2 * at + 1);
        int right = heapify(2 * at + 2);

        // might be some duplicates but idc
        for (int val : vector<int>{curr, curr + 1, left, left + 1, right, right + 1}) {
            bkpts.push_back({val, at});
        }

        if (curr > left && curr > right) { swap(curr, left > right ? left : right); }
        if (curr < left && curr < right) { swap(curr, left < right ? left : right); }
        return curr;
    }

    void recalculate(int node) {
        const auto& [v, c] = tree[node];  // just a shorthand
        Info my = {(curr_median != v) * c, (curr_median > v) * c,
                   (curr_median < v) * c};
        if (2 * node + 1 >= tree.size()) {
            costs[node] = my;
            return;
        }

        costs[node] = {INT64_MAX, INT64_MAX, INT64_MAX};
        vector<Info> local{my, costs[2 * node + 1], costs[2 * node + 2]};
        vector<int> perm{0, 1, 2};
        do {
            const Info &a = local[perm[0]], &b = local[perm[1]], &c = local[perm[2]];
            costs[node].eq = min(costs[node].eq, a.eq + b.lte + c.gte);
            costs[node].lte = min(costs[node].lte, a.lte + b.lte);
            costs[node].gte = min(costs[node].gte, a.gte + b.gte);
        } while (next_permutation(perm.begin(), perm.end()));
    }

   public:
    MedianHeap(const vector<pair<int, int>>& tree) : tree(tree), costs(tree.size()) {
        assert(tree.size() % 2 == 1);
        heapify();
        sort(bkpts.begin(), bkpts.end());
        for (int n = tree.size() - 1; n >= 0; n--) { recalculate(n); }
    }

    void update_median(int m) {
        assert(m >= curr_median);
        curr_median = m;

        set<int, greater<int>> to_update;
        while (bkpt_at < bkpts.size() && bkpts[bkpt_at].first <= m) {
            to_update.insert(bkpts[bkpt_at++].second);
        }
        for (int n : to_update) {
            for (; n > 0; n = (n - 1) / 2) { recalculate(n); }
            recalculate(0);
        }
    }

    long long exact(int node) { return costs[node].eq; }
};

/** 2025 jan gold */
int main() {
    int n;  // yeah, sue me.
    cin >> n;
    vector<pair<int, int>> tree(n);
    for (auto& [val, cost] : tree) { cin >> val >> cost; }

    int query_num;
    cin >> query_num;
    vector<pair<int, int>> queries(query_num);
    for (int q = 0; q < query_num; q++) {
        cin >> queries[q].first;
        queries[q].second = q;
    }
    sort(queries.begin(), queries.end());

    vector<long long> q_ans(query_num);
    MedianHeap mh(tree);

    for (const auto& [median, ind] : queries) {
        mh.update_median(median);
        q_ans[ind] = mh.exact(0);
    }

    for (long long a : q_ans) { cout << a << '\n'; }
}
