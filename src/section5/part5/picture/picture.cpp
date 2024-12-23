/*
ID: kevinsh4
TASK: picture
LANG: C++
*/
#include <algorithm>
#include <fstream>
#include <iostream>
#include <vector>

using std::cout;
using std::endl;
using std::pair;
using std::vector;

constexpr int MAX_POS = 1e4;

struct RectSide {
    int pos;
    bool is_end;
    int from, to;

    bool operator<(const RectSide& o) {
        // it's crucial that starts come b4 ends
        return std::make_pair(pos, is_end) < std::make_pair(o.pos, o.is_end);
    }
};

int free_borders(const vector<RectSide>& borders) {
    int total = 0;
    vector<int> active(2 * MAX_POS + 1);
    for (const RectSide& rs : borders) {
        for (int i = rs.from + MAX_POS; i < rs.to + MAX_POS; i++) {
            const int old = active[i];
            active[i] += rs.is_end ? -1 : 1;
            total += (old > 0 && active[i] == 0) || (old == 0 && active[i] > 0);
        }
    }
    return total;
}

int main() {
    std::ifstream read("picture.in");
    int rect_num;
    read >> rect_num;

    vector<RectSide> vert, horz;
    for (int r = 0; r < rect_num; r++) {
        int x1, y1, x2, y2;
        read >> x1 >> y1 >> x2 >> y2;
        vert.push_back({x1, false, y1, y2});
        vert.push_back({x2, true, y1, y2});
        horz.push_back({y1, false, x1, x2});
        horz.push_back({y2, true, x1, x2});
    }
    std::sort(vert.begin(), vert.end());
    std::sort(horz.begin(), horz.end());

    const int vert_perim = free_borders(vert);
    const int horz_perim = free_borders(horz);
    std::ofstream("picture.out") << vert_perim + horz_perim << endl;
}
