#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;

constexpr int MOD = 1e9 + 7;

// i mean a prefix sum would also work but i'm too lazy
class BITree {
    private:
        vector<int> bit;
        int size;
    public:
        BITree(int size) : bit(size + 1), size(size) { }

        void increment(int updateAt, int val) {
            updateAt++;  // have the driver code not worry about 1-indexing
            for (; updateAt <= size; updateAt += updateAt & -updateAt) {
                bit[updateAt] += val;
            }
        }

        int query(int ind) {  // sum of elements in [0, ind]
            ind++;
            int sum = 0;
            for (; ind > 0; ind -= ind & -ind) {
                sum += bit[ind];
            }
            return sum;
        }
};

// 2020 feb gold
int main() {
    std::ifstream read("help.in");
    int range_num;
    read >> range_num;

    vector<int> two_pows(range_num + 1);
    two_pows[0] = 1;
    for (int i = 1; i < two_pows.size(); i++) {
        two_pows[i] = (two_pows[i - 1] * 2) % MOD;
    }

    vector<std::pair<int, int>> ranges(range_num);
    // going to assume valid input, screw you validate your own input
    BITree prev_ranges(2 * range_num);
    for (std::pair<int, int>& r : ranges) {
        read >> r.first >> r.second;
        r.first--;
        r.second--;
        prev_ranges.increment(r.second, 1);
    }
    std::sort(ranges.begin(), ranges.end());

    long long total = 0;
    for (int r = 0; r < range_num; r++) {
        int start = ranges[r].first;
        int prev = start != 0 ? prev_ranges.query(start - 1) : 0;
        total = (total * 2 + two_pows[prev]) % MOD;
    }
    cout << total << endl;
    std::ofstream("help.out") << total << endl;
}
