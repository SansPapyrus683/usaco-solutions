#include <fstream>
#include <vector>
#include <algorithm>

using std::vector;
using std::pair;

int dist(pair<int, int> p1, pair<int, int> p2) {
    return abs(p1.first - p2.first) + abs(p1.second - p2.second);
}

class CityRoute {  // custom segtree or smth idk
    private:
        struct CheckPSeg {
            // start index and end index and min dist (w/o skipping) and max dist saved
            int from;
            int to;
            int dist;
            int saved;
        };
        const CheckPSeg INVALID{-1, -1, -1, -1};
        
        const int arr_size;
        vector<pair<int, int>> pts;
        vector<CheckPSeg> segtree;
        int size;
        
        void set(int ind, int curr, int left, int right) {
            if (right - left > 1) {
                int mid = (left + right) / 2;
                if (ind < mid) {
                    set(ind, 2 * curr + 1, left, mid);
                } else {
                    set(ind, 2 * curr + 2, mid, right);
                }
                segtree[curr] = merge(segtree[2 * curr + 1], segtree[2 * curr + 2]);
            }
        }

        CheckPSeg min_dist(int from, int to, int curr, int left, int right) {
            if (right <= from || to <= left) {
                return INVALID;
            }
            if (from <= left && right <= to) {
                return segtree[curr];
            }
            int mid = (left + right) / 2;
            CheckPSeg leftPart = min_dist(from, to, 2 * curr + 1, left, mid);
            CheckPSeg rightPart = min_dist(from, to, 2 * curr + 2, mid, right);
            return merge(leftPart, rightPart);
        }

        CheckPSeg merge(const CheckPSeg& left, const CheckPSeg& right) {
            if (left.from == -1 || right.from == -1) {  // check for any returned INVALIDs
                return left.from == -1 ? right : left;
            }
            int max_saved = std::max(left.saved, right.saved);
            // now we can skip some new checkpoints! let's see what their values are
            if (left.from != left.to) {
                max_saved = std::max(max_saved, dist(pts[left.to - 1], pts[left.to])
                                                + dist(pts[left.to], pts[right.from])
                                                - dist(pts[left.to - 1], pts[right.from]));
            }
            if (right.from != right.to) {
                max_saved = std::max(max_saved, dist(pts[left.to], pts[right.from])
                                                + dist(pts[right.from], pts[right.from + 1])
                                                - dist(pts[left.to], pts[right.from + 1]));
            }
            return CheckPSeg{left.from, right.to, left.dist + right.dist + dist(pts[left.to], pts[right.from]), max_saved};
        }
    public:
        CityRoute(int len) : arr_size(len) {
            size = 1;
            while (size < arr_size) {
                size *= 2;
            }
            pts = vector<pair<int, int>>(size);
            segtree = vector<CheckPSeg>(size * 2 - 1);
            for (int i = 0; i < size; i++) {  // set the froms and tos for the base leaves
                segtree[size * 2 - i - 2] = {size - i - 1, size - i - 1, 0, 0};
            }
            for (int i = 0; i < size; i++) {  // propagate the changes up the tree
                set(i, {0, 0});
            }
        }

        void set(int ind, pair<int, int> cp) {
            pts[ind] = cp;
            set(ind, 0, 0, size);
        }

        // min dist from checkpoint from to checkpoint to - 1 (inclusive)
        int min_dist(int from, int to) {
            CheckPSeg merged = min_dist(from, to, 0, 0, size);
            return merged.dist - merged.saved;
        }
};

// 2014 dec gold
int main() {
    std::ifstream read("marathon.in");
    int checkpt_num;
    int query_num;
    read >> checkpt_num >> query_num;
    vector<pair<int, int>> checkpts(checkpt_num);
    for (int c = 0; c < checkpt_num; c++) {
        read >> checkpts[c].first >> checkpts[c].second;
    }

    CityRoute route(checkpt_num);
    for (int c = 0; c < checkpt_num; c++) {
        route.set(c, checkpts[c]);
    }
    std::ofstream written("marathon.out");
    for (int q = 0; q < query_num; q++) {
        char type;
        read >> type;
        if (type == 'Q') {
            int from;
            int to;
            read >> from >> to;
            // we don't -1 to because my segtree doesn't include the end
            written << route.min_dist(--from, to) << '\n';  // stdout is too slow so no printing there
        } else if (type == 'U') {
            int changed;
            pair<int, int> new_checkpt;
            read >> changed >> new_checkpt.first >> new_checkpt.second;
            route.set(--changed, new_checkpt);
        }
    }
}
