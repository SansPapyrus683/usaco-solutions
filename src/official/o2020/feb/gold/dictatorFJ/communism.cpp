#include <iostream>
#include <fstream>
#include <vector>
#include <unordered_set>

using std::cout;
using std::endl;
using std::vector;

class Farm {
    private:
        vector<vector<int>> neighbors;
        int root;
        int path_divisible(int at, int parent, int split_len) {
            std::unordered_multiset<int> leftover_paths;
            for (int c : neighbors[at]) {
                if (c != parent) {
                    leftover_paths.insert(path_divisible(c, at, split_len) + 1);
                }
            }
            if (leftover_paths.empty()) {
                return 0;
            }
            if (leftover_paths.find(0) != leftover_paths.end()) {
                return -1;
            }
            leftover_paths.erase(split_len);
            int remaining = -1;
            while (!leftover_paths.empty()) {
                int curr = *leftover_paths.begin();
                leftover_paths.erase(leftover_paths.begin());
                std::unordered_multiset<int>::iterator other = leftover_paths.find(split_len - curr);
                if (other == leftover_paths.end()) {
                    if (remaining != -1) {
                        return -1;
                    }
                    remaining = curr;
                } else {
                    leftover_paths.erase(other);
                }
            }
            return remaining == -1 ? 0 : remaining;
        }
    public:
        Farm(vector<vector<int>> neighbors, int root=0) : neighbors(neighbors), root(root) { }

        bool path_divisible(int split_len) {
            if ((neighbors.size() - 1) % split_len != 0) {
                return false;
            }
            return path_divisible(root, root, split_len) == 0;
        }
};

// 2020 feb gold
// TODO: somehow optimize this crap idk
int main() {
    std::ifstream read("deleg.in");
    int pasture_num;
    read >> pasture_num;
    vector<vector<int>> neighbors(pasture_num);
    for (int p = 0; p < pasture_num - 1; p++) {
        int from;
        int to;
        read >> from >> to;
        neighbors[--from].push_back(--to);  // make the 1-indexed 0-indexed
        neighbors[to].push_back(from);
    }

    Farm farm(neighbors);
    std::ofstream written("deleg.out");
    for (int split_len = 1; split_len < pasture_num; split_len++) {
        int divisible = farm.path_divisible(split_len);
        written << divisible;
    }
    written << endl;
}
