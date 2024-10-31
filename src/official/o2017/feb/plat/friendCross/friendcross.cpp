#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;

template <class T>
class BIT {
   private:
    int size;
    vector<T> bit;

   public:
    BIT(int size) : size(size), bit(size + 1) {}

    void add(int ind, T val) {
        ind++;
        for (; ind <= size; ind += ind & -ind) {
            bit[ind] += val;
        }
    }

    T pref_sum(int ind) {
        ind++;
        T total = 0;
        for (; ind > 0; ind -= ind & -ind) {
            total += bit[ind];
        }
        return total;
    }
};

class WTFSegtree {
   private:
    int len;
    const vector<int>& arr;
    vector<BIT<int>> segtree;
    vector<vector<int>> subarrs;

    void build(int at, int at_left, int at_right) {
        subarrs[at] = vector<int>(arr.begin() + at_left, arr.begin() + at_right + 1);
        std::sort(subarrs[at].begin(), subarrs[at].end());
        segtree[at] = BIT<int>(at_right - at_left + 1);
        if (at_left == at_right) {
            return;
        }
        int mid = (at_left + at_right) / 2;
        build(2 * at, at_left, mid);
        build(2 * at + 1, mid + 1, at_right);
    }

    void add(int ind, int inc, int at, int at_left, int at_right) {
        if (at_left == at_right) {
            segtree[at].add(0, inc);
            return;
        }
        int mid = (at_left + at_right) / 2;
        if (ind <= mid) {
            add(ind, inc, 2 * at, at_left, mid);
        } else {
            add(ind, inc, 2 * at + 1, mid + 1, at_right);
        }

        const vector<int>& sub = subarrs[at];
        int add_ind = std::lower_bound(sub.begin(), sub.end(), arr[ind]) - sub.begin();
        segtree[at].add(add_ind, inc);
    }

    int lt_num(int start, int end, int thresh, int at, int at_left, int at_right) {
        if (at_right < start || end < at_left) {
            return 0;
        }
        if (start <= at_left && at_right <= end) {
            const vector<int>& sub = subarrs[at];
            int q_ind = std::lower_bound(sub.begin(), sub.end(), thresh) - sub.begin();
            return q_ind > 0 ? segtree[at].pref_sum(q_ind - 1) : 0;
        }
        int mid = (at_left + at_right) / 2;
        int left_res = lt_num(start, end, thresh, 2 * at, at_left, mid);
        int right_res = lt_num(start, end, thresh, 2 * at + 1, mid + 1, at_right);
        return left_res + right_res;
    }

   public:
    WTFSegtree(int len, const vector<int>& arr) : len(len), arr(arr), subarrs(len * 4) {
        segtree = vector<BIT<int>>(len * 4, BIT<int>(0));
        build(1, 0, len - 1);
    }

    void add(int ind, int inc) { add(ind, inc, 1, 0, len - 1); }

    int lt_num(int start, int end, int thresh) {
        return lt_num(start, end, thresh, 1, 0, len - 1);
    }
};

/** 2017 feb plat (LMAO) */
int main() {
    std::ifstream read("friendcross.in");

    int breed_num;
    int friend_dist;
    read >> breed_num >> friend_dist;
    vector<int> x(breed_num), y(breed_num);
    for (int b = 0; b < breed_num; b++) {
        int cow;
        read >> cow;
        x[--cow] = b;
    }
    for (int b = 0; b < breed_num; b++) {
        int cow;
        read >> cow;
        y[--cow] = b;
    }

    vector<int> segtree_y(breed_num);
    for (int i = 0; i < breed_num; i++) {
        segtree_y[x[i]] = y[i];
    }

    WTFSegtree unfriendly(breed_num, segtree_y);
    BIT<int> active_x(breed_num), active_y(breed_num);
    for (int i = friend_dist + 1; i < breed_num; i++) {
        active_x.add(x[i], 1);
        active_y.add(y[i], 1);
        unfriendly.add(x[i], 1);
    }

    long long unfriendly_pairs = 0;
    for (int b = 0; b < breed_num; b++) {
        int x_lt = active_x.pref_sum(x[b]);
        int y_lt = active_y.pref_sum(y[b]);
        unfriendly_pairs += x_lt + y_lt - 2 * unfriendly.lt_num(0, x[b], y[b]);

        if (b >= friend_dist) {
            int add_x = x[b - friend_dist], add_y = y[b - friend_dist];
            active_x.add(add_x, 1);
            active_y.add(add_y, 1);
            unfriendly.add(add_x, 1);
        }
        if (b + friend_dist + 1 < breed_num) {
            int rem_x = x[b + friend_dist + 1], rem_y = y[b + friend_dist + 1];
            active_x.add(rem_x, -1);
            active_y.add(rem_y, -1);
            unfriendly.add(rem_x, -1);
        }
    }

    std::ofstream("friendcross.out") << unfriendly_pairs / 2 << endl;
}
