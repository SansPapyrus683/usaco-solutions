#include <algorithm>
#include <cassert>
#include <cstdint>
#include <iostream>
#include <vector>

using std::cout;
using std::endl;
using std::vector;

int main() {
    int cow_num;
    int edge_num;
    std::cin >> cow_num >> edge_num;
    assert(cow_num <= 16);

    vector<int> neighbors(cow_num);
    for (int e = 0; e < edge_num; e++) {
        int c1, c2;
        std::cin >> c1 >> c2;
        c1--, c2--;
        neighbors[c1] |= 1 << c2;
        neighbors[c2] |= 1 << c1;
    }

    vector<int> within(1 << cow_num);
    for (int ss = 1; ss < (1 << cow_num); ss++) {
        for (int c = 0; c < cow_num; c++) {
            if ((ss & (1 << c)) != 0) {
                within[ss] += __builtin_popcount(neighbors[c] & ss);
            }
        }
        within[ss] /= 2;
    }

    vector<int> min_ops(1 << cow_num, INT32_MAX);
    min_ops[0] = 0;
    for (int ss = 1; ss < (1 << cow_num); ss++) {
        for (int bp1 = ss; bp1 != 0; bp1 = (bp1 - 1) & ss) {
            int bp2 = ss ^ bp1;
            int alr_have = within[ss] - within[bp1] - within[bp2];
            int needed = __builtin_popcount(bp1) * __builtin_popcount(bp2);
            int conn_cost = needed - alr_have;
            min_ops[ss] = std::min(min_ops[ss], within[bp1] + min_ops[bp2] + conn_cost);
        }
    }

    cout << min_ops[(1 << cow_num) - 1] << endl;
}
