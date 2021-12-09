#include <iostream>
#include <fstream>
#include <vector>
#include <map>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;
using std::pair;

// 2019 dec plat
int main() {
    std::ifstream read("pieaters.in");
    int pie_num;
    int cow_num;
    read >> pie_num >> cow_num;
    std::map<pair<int, int>, int> weights;
    for (int c = 0; c < cow_num; c++) {
        int weight;
        int start;
        int end;
        read >> weight >> start >> end;
        start--;
        end--;
        weights[{start, end}] = std::max(weights[{start, end}], weight);
    }
    
    /*
     * this[s][e][i] = 
     * given that we only consider cows in [s, e],
     * what's the largest weight of a cow that consumes the ith pie in [s, e]?
     */
    vector<vector<vector<int>>> range_maxes(
        pie_num, vector<vector<int>>(pie_num)
    );
    for (int start = 0; start < pie_num; start++) {
        for (int end = start; end < pie_num; end++) {
            range_maxes[start][end] = vector<int>(end - start + 1);
        }
    }
    for (const auto& [r, w] : weights) {
        for (int i = r.first; i <= r.second; i++) {
            range_maxes[r.first][r.second][i - r.first] = std::max(
                range_maxes[r.first][r.second][i - r.first], w
            );
        }
    }
    for (int len = 2; len <= pie_num; len++) {
        for (int start = 0; start + len - 1 < pie_num; start++) {
            int end = start + len - 1;
            for (int mid = start; mid <= end; mid++) {
                int ind = mid - start;
                int l_seg = 0;
                if (mid != start) {
                    l_seg = range_maxes[start + 1][end][ind - 1];
                }
                int r_seg = 0;
                if (mid != end) {
                    r_seg = range_maxes[start][end - 1][ind];
                }
                range_maxes[start][end][ind] = std::max({
                    range_maxes[start][end][ind],
                    std::max(l_seg, r_seg)
                });
            }
        }
    }

    // max weight given that we can only use pies in a certain range
    vector<vector<int>> max_weight(pie_num, vector<int>(pie_num));
    vector<vector<int>> asdf(pie_num, vector<int>(pie_num));
    for (int len = 1; len <= pie_num; len++) {
        for (int start = 0; start + len - 1 < pie_num; start++) {
            int end = start + len - 1;
            // go through all pies which we can leave out
            for (int leave = start; leave <= end; leave++) {
                int l_seg = leave != start ? max_weight[start][leave - 1] : 0;
                int r_seg = leave != end ? max_weight[leave + 1][end] : 0;
                max_weight[start][end] = std::max(
                    max_weight[start][end],
                    l_seg + range_maxes[start][end][leave - start] + r_seg
                );
            }
        }
    }

    int total_max = max_weight[0][pie_num - 1];
    cout << total_max << endl;
    std::ofstream("pieaters.out") << total_max << endl;
}
