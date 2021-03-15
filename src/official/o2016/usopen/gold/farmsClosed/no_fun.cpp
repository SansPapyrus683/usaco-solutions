#include <fstream>
#include <vector>
#include <set>
#include <algorithm>

using std::vector;

class DisjointSets {
    private:
        vector<int> parents;
        vector<int> sizes;
    public:
        DisjointSets(int size) : parents(size), sizes(size, 1) {
            for (int i = 0; i < size; i++) {
                parents[i] = i;
            }
        }

        int get_ultimate(int n) {
            return parents[n] == n ? n : (parents[n] = get_ultimate(parents[n]));
        }

        int size(int n) {
            return sizes[get_ultimate(n)];
        }

        void link(int n1, int n2) {
            n1 = get_ultimate(n1);
            n2 = get_ultimate(n2);
            if (n1 == n2) {
                return;
            }
            if (sizes[n1] < sizes[n2]) {
                std::swap(n1, n2);
            }
            sizes[n1] += sizes[n2];
            parents[n2] = n1;
        }
};

// 2020 usopen gold
int main() {
    std::ifstream read("closing.in");
    int barn_num;
    int path_num;
    read >> barn_num >> path_num;
    vector<vector<int>> neighbors(barn_num);
    for (int p = 0; p < path_num; p++) {
        int from;
        int to;
        read >> from >> to;
        neighbors[--from].push_back(--to);
        neighbors[to].push_back(from);
    }

    vector<int> closed_barns(barn_num);
    for (int b = 0; b < barn_num; b++) {
        read >> closed_barns[b];
        closed_barns[b]--;
    }
    
    vector<bool> still_linked(barn_num);
    std::set<int> opened;
    DisjointSets barns(barn_num);
    // let's simulate the process in reverse, opening the barns one by one
    for (int open_ind = barn_num - 1; open_ind >= 0; open_ind--) {
        int to_open = closed_barns[open_ind];
        for (int n : neighbors[to_open]) {
            if (opened.find(n) != opened.end()) {
                barns.link(to_open, n);
            }
        }
        opened.insert(to_open);
        still_linked[open_ind] = barns.size(to_open) == opened.size();
    }
    std::ofstream written("closing.out");
    for (bool status : still_linked) {
        // no printing to cout bc that would be way too slow
        written << (status ? "YES" : "NO") << '\n';
    }
}
