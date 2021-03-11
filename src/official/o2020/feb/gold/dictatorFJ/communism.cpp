#include <iostream>
#include <fstream>
#include <vector>

using std::cout;
using std::endl;
using std::vector;

class Farm {
    private:
        vector<vector<int>> neighbors;
        // the sizes of all the individual subtrees and the top part
        vector<vector<int>> partitioned_sizes;
        int deepest;
        int process_pastures(int at, int parent) {
            int subtree_size = 1;
            for (int n : neighbors[at]) {
                if (n != parent) {
                    int child_size = process_pastures(n, at);
                    subtree_size += child_size;
                    partitioned_sizes[at].push_back(child_size);
                }
            }
            // add the top part with the parent and stuff
            if (subtree_size != neighbors.size()) {
                partitioned_sizes[at].push_back(neighbors.size() - subtree_size);
            }
            return subtree_size;
        }
    public:
        Farm(vector<vector<int>> neighbors)
                : neighbors(neighbors),
                  partitioned_sizes(neighbors.size()) {
            process_pastures(0, 0);
        }

        bool splittable(int split_len) {
            if ((neighbors.size() - 1) % split_len != 0) {
                return false;
            }
            // i would reset this across all loops but it's supposed to stay all 0's
            vector<int> leftovers(split_len);
            for (int i = 0; i < neighbors.size(); i++) {
                int unmatched = 0;
                for (int size : partitioned_sizes[i]) {
                    int remainder = size % split_len;
                    if (remainder == 0) {
                        continue;
                    }
                    // see if we can match this remainder with anything else
                    if (leftovers[split_len - remainder] != 0) {
                        leftovers[split_len - remainder]--;
                        unmatched--;
                    } else {  // if we can't, oh well, let's put it in the backlog
                        leftovers[remainder]++;
                        unmatched++;
                    }
                }
                // we still have stuff in the backlog? oh crap
                if (unmatched != 0) {
                    return false;
                }
            }
            return true;
        }
};

// 2020 feb gold
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
    for (int s = 1; s < neighbors.size(); s++) {
        bool splittable = farm.splittable(s);
        written << splittable;
        cout << splittable;
    }
    written << endl;
    cout << endl;
}
