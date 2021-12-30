#include <iostream>
#include <fstream>
#include <functional>
#include <vector>

using std::cout;
using std::endl;
using std::vector;

// 2020 us open gold
int main() {
    std::ifstream read("fcolor.in");
    int cow_num;
    int relation_num;
    read >> cow_num >> relation_num;
    vector<vector<int>> simps(cow_num);
    for (int r = 0; r < relation_num; r++) {
        int girl;
        int simp;
        read >> girl >> simp;
        simps[--girl].push_back(--simp);
    }
    
    vector<int> parent(cow_num);
    for (int c = 0; c < cow_num; c++) {
        parent[c] = c;
    }
    vector<int> size(cow_num);
    vector<vector<int>> merged_simps(simps);

    std::function<int(int)> get_top_cow;
    get_top_cow = [&] (int c) {
        return parent[c] == c ? c : parent[c] = get_top_cow(parent[c]);
    };
    std::function<bool(int, int)> merge;
    merge = [&] (int c1, int c2) -> bool {
        c1 = get_top_cow(c1);
        c2 = get_top_cow(c2);
        if (c1 == c2) {
            return false;
        }
        if (size[c1] < size[c2]) {
            return merge(c2, c1);
        }
        size[c1] += size[c2];
        parent[c2] = c1;
        merged_simps[c1].insert(
            merged_simps[c1].end(),
            merged_simps[c2].begin(), merged_simps[c2].end()
        );
        merged_simps[c2] = {};
        return true;
    };

    vector<vector<int>> to_merge(simps);
    while (!to_merge.empty()) {
        vector<int> curr = to_merge.back();
        to_merge.pop_back();
        bool more_needed = false;
        for (int i = 0; i < (int) curr.size() - 1; i++) {
            if (merge(curr[i], curr[i + 1])) {
                more_needed = true;
            }
        }
        if (more_needed) {
            to_merge.push_back(merged_simps[get_top_cow(curr[0])]);
        }
    }

    vector<int> colors(cow_num);
    vector<int> assigned(cow_num);
    int curr_color = 0;
    for (int c = 0; c < cow_num; c++) {
        int top_cow = get_top_cow(c);
        if (colors[top_cow] == 0) {
            colors[top_cow] = ++curr_color;
        }
        assigned[c] = colors[top_cow];
    }
    
    std::ofstream written("fcolor.out");
    for (int c : assigned) {
        written << c << '\n';
    }
}
