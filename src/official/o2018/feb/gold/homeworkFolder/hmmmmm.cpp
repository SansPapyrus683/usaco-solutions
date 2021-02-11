#include <iostream>
#include <fstream>
#include <stdexcept>
#include <string>
#include <vector>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;

// i wonder what the stuff here contains🤔
class FileSys {
    private:
        const int PARENT_LEN = 3;  // "../"
        
        vector<vector<int>> children;
        vector<int> lengths;
        vector<int> files_contained;
        int root = -1;
        int file_dir_num;
        int file_num = 0;

        int calc_file_num(int at) {
            if (children[at].size() == 0) {
                return files_contained[at] = 1;
            }
            files_contained[at] = 0;
            for (int c : children[at]) {
                files_contained[at] += calc_file_num(c);
            }
            return files_contained[at];
        }

        // minimum cost if we only use nodes STRICTLY below that "at" node
        long long min_total_path(int at, long long at_cost) {
            long long best = 9223372036854775807L;
            for (int f : children[at]) {
                long long farther_amt = (file_num - files_contained[f]) * PARENT_LEN;
                long long closer_amt = files_contained[f] * (lengths[f] + 1);
                long long child_cost = at_cost + farther_amt - closer_amt;
                best = std::min(best, std::min(child_cost, min_total_path(f, child_cost)));
            }
            return best;
        }
    public:
        FileSys(vector<vector<int>> children, vector<int> lengths)
            : children(children), lengths(lengths), 
              file_dir_num(children.size()), files_contained(children.size()) {
            vector<bool> has_parent(file_dir_num);
            for (vector<int> kids : children) {
                file_num += kids.empty();
                for (int k : kids) {
                    has_parent[k] = true;
                }
            }

            for (int i = 0; i < file_dir_num; i++) {
                if (!has_parent[i]) {
                    if (root != -1) {  // we can't have multiple roots now can we
                        throw std::invalid_argument("invalid file system detected- initiating self destruction");
                    }
                    root = i;
                }
            }
            if (root == -1) {  // bruh there's no root?
                throw std::invalid_argument("invalid file system detected- initiating self destruction");
            }
            calc_file_num(root);
        }

        long long min_total_path() {
            long long init_cost = 0;
            for (int i = 0; i < file_dir_num; i++) {
                if (i != root) { 
                    init_cost += files_contained[i] * (lengths[i] + 1);
                }
            }
            long long others_best = min_total_path(root, init_cost);
            // -file_num because of the trailing /'s
            return std::min(init_cost, others_best) - file_num;
        }
};

// 2018 feb gold
int main() {
    std::ifstream read("dirtraverse.in");
    int file_dir_num;
    read >> file_dir_num;
    
    vector<vector<int>> contains(file_dir_num, vector<int>());
    vector<int> file_dir_lens(file_dir_num);
    for (int i = 0; i < file_dir_num; i++) {
        std::string name;
        read >> name;
        file_dir_lens[i] = name.length();
        
        int child_num;
        read >> child_num;
        contains[i] = vector<int>(child_num);
        for (int c = 0; c < child_num; c++) {
            read >> contains[i][c];
            contains[i][c]--;  // make the ids 0-indexed
        }
    }
    long long min_path_sum = FileSys(contains, file_dir_lens).min_total_path();

    std::ofstream written("dirtraverse.out");
    written << min_path_sum << endl;
    cout << min_path_sum << endl;
}
