#include <iostream>
#include <fstream>
#include <vector>
#include <map>

using std::cout;
using std::endl;
using std::vector;

constexpr int MOD = 1e9 + 7;

class Farm {
    const int COLOR_NUM = 3;
    private:
        std::map<std::pair<int, int>, int> cached_poss;
        const vector<int> painted_alr;
        const vector<vector<int>> neighbors;
        // # of ways we can paint the tree with at given that it has to be painted root_color
        long long paint_poss(int at, int parent, int root_color) {
            // don't go calculating the same thing multiple times
            if (cached_poss.find({at, root_color}) != cached_poss.end()) {
                return cached_poss[{at, root_color}];
            }
            if (painted_alr[at] != -1 && painted_alr[at] != root_color) {  // if there's a contradiction
                return 0;
            }
            // the possibilities for which we can paint each of the children
            vector<int> children_poss;
            for (int n : neighbors[at]) {
                if (n != parent) {
                    long long total = 0;
                    for (int c = 0; c < COLOR_NUM; c++) {
                        if (c != root_color) {  // can't paint a child the same color as we are now, can we?
                            total += paint_poss(n, at, c);
                        }
                    }
                    children_poss.push_back(total);
                }
            }
            long long total = 1;
            // do some combinatorics voodoo to find out the actual # of ways for the entire tree
            for (int c : children_poss) {
                total = (total * c) % MOD;
            }
            cached_poss[{at, root_color}] = total;
            return total;
        }
    public:
        Farm(vector<vector<int>> neighbors, vector<int> painted_alr)
                : neighbors(neighbors), painted_alr(painted_alr) { }

        long long paint_poss() {
            long long total = 0;
            for (int c = 0; c < COLOR_NUM; c++) {
                total += paint_poss(0, 0, c);  // just arbitrarily root the tree at 0
            }
            return total % MOD;
        }
};

// 2017 dec gold
int main() {
    std::ifstream read("barnpainting.in");
    int barn_num;
    int painted_alr_num;
    read >> barn_num >> painted_alr_num;

    vector<vector<int>> neighbors(barn_num);
    for (int p = 0; p < barn_num - 1; p++) {
        int from;
        int to;
        read >> from >> to;
        neighbors[--from].push_back(--to);
        neighbors[to].push_back(from);
    }

    vector<int> painted_alr(barn_num, -1);
    for (int p = 0; p < painted_alr_num; p++) {
        int barn;
        int painted;
        read >> barn >> painted;
        painted_alr[--barn] = --painted;
    }

    long long paint_poss = Farm(neighbors, painted_alr).paint_poss();
    std::ofstream("barnpainting.out") << paint_poss << endl;
    cout << paint_poss << endl;
}
