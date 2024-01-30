#include <algorithm>
#include <fstream>
#include <limits>
#include <iostream>
#include <vector>

using std::cout;
using std::endl;
using std::pair;
using std::string;
using std::vector;

template <class T> class MinSegmentTree {
  private:
    const T DEFAULT = std::numeric_limits<T>().max();
 
    vector<T> segtree;
    int len;
 
  public:
    MinSegmentTree(int len) : len(len), segtree(len * 2, DEFAULT) {}
 
    /** Sets the value at ind to val. */
    void set(int ind, T val) {
        ind += len;
        segtree[ind] = val;
        for (; ind > 1; ind /= 2) {
            segtree[ind / 2] = std::min(segtree[ind], segtree[ind ^ 1]);
        }
    }
 
    T range_min(int start, int end) const {
        T min = DEFAULT;
        for (start += len, end += len; start < end; start /= 2, end /= 2) {
            if (start % 2 == 1) { min = std::min(min, segtree[start++]); }
            if (end % 2 == 1) { min = std::min(min, segtree[--end]); }
        }
        return min;
    }
};

int main() {
    std::ifstream read("standingout.in");
    int cow_num;
    read >> cow_num;
    vector<string> cows(cow_num);
    int len = 0;
    for (string &c : cows) {
        read >> c;
        len += c.size();
    }

    string str;
    str.reserve(len + cow_num);
    vector<int> starts;
    vector<int> cow_ind(len + cow_num, -1);
    for (const string &c : cows) {
        for (int i = 0; i < c.size(); i++) {
            cow_ind[str.size() + i] = starts.size();
        }
        starts.push_back(str.size());
        str.append(c);
        str.append(" ");
    }
    const int n = str.size();

#pragma region suffix array crap
    vector<pair<pair<int, int>, int>> suffs(n);
    for (int i = 0; i < n; i++) {
        suffs[i] = {{str[i], str[i]}, i};
    }
    std::sort(suffs.begin(), suffs.end());
    vector<vector<int>> equiv{vector<int>(n)};
    for (int i = 1; i < n; i++) {
        auto [c_val, cs] = suffs[i];
        auto [p_val, ps] = suffs[i - 1];
        equiv[0][cs] = equiv[0][ps] + (c_val > p_val);
    }

    int cmp_amt = 1;
    while (cmp_amt < n) {
        const vector<int> &prev = equiv.back();
        for (auto &[val, s] : suffs) {
            val = {prev[s], prev[(s + cmp_amt) % n]};
        }
        std::sort(suffs.begin(), suffs.end());

        vector<int> nxt_eq = vector<int>(n);
        for (int i = 1; i < n; i++) {
            auto [c_val, cs] = suffs[i];
            auto [p_val, ps] = suffs[i - 1];
            nxt_eq[cs] = nxt_eq[ps] + (c_val > p_val);
        }
        equiv.push_back(nxt_eq);

        cmp_amt *= 2;
    }

    vector<int> suff_ind(n);
    for (int i = 0; i < n; i++) {
        suff_ind[suffs[i].second] = i;
    }
    MinSegmentTree<int> lcp(n - 1);
    int start_at = 0;  // the variable talked about in the explanation
    for (int i = 0; i < n - 1; i++) {
        int prev = suffs[suff_ind[i] - 1].second;
        int curr_cmp = start_at;
        while (str[i + curr_cmp] == str[prev + curr_cmp]) {
            curr_cmp++;
        }
        lcp.set(suff_ind[i] - 1, curr_cmp);
        start_at = std::max(curr_cmp - 1, 0);
    }
#pragma endregion

    vector<int> next_non(n, -1);
    int start = cow_num;
    for (int i = cow_num; i < n; i++) {
        if (cow_ind[suffs[i].second] != cow_ind[suffs[start].second]) {
            for (int j = start; j < i; j++) {
                next_non[j] = i;
            }
            start = i;
        }
    }

    vector<long long> unique(cow_num);
    for (int i = 0; i < n; i++) {
        if (cow_ind[i] == -1) {
            continue;
        }
        
        int ind = suff_ind[i];
        int pref_bad = lcp.range_min(ind - 1, ind);
        if (next_non[ind] != -1) {
            pref_bad = std::max(pref_bad, lcp.range_min(ind, next_non[ind]));
        }

        int c = cow_ind[i];
        unique[c] += std::max((int)cows[c].size() + starts[c] - i - pref_bad, 0);
    }

    std::ofstream written("standingout.out");
    for (long long i : unique) {
        written << i << '\n';
    }
}
