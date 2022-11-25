#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;

/**
 * 2016 us open gold
 * (just notice that the answer will always be achieved by
 * merging some continuous interval of the board)
 */
int main() {
    std::ifstream read("248.in");
    int size;
    read >> size;
    vector<int> game_board(size);
    for (int i = 0; i < size; i++) {
        read >> game_board[i];
    }
    
    int max_score = 0;
    // this[i][j] = result for if we COMPLETELY merged the subarr from i to j inclusive
    // if we can't completely merge it, the value's 0
    vector<vector<int>> sub_hi_score(size, vector<int>(size));
    for (int i = 0; i < size; i++) {
        sub_hi_score[i][i] = game_board[i];
        max_score = std::max(max_score, game_board[i]);
    }
    for (int sub_len = 2; sub_len <= size; sub_len++) {
        for (int start = 0; start <= size - sub_len; start++) {
            int end = start + sub_len - 1;
            for (int split = start; split < end; split++) {
                // if these two complete subintervals can be merged
                if (sub_hi_score[start][split] == sub_hi_score[split + 1][end]) {
                    sub_hi_score[start][end] = std::max(
                        sub_hi_score[start][end],
                        sub_hi_score[start][split] + 1
                    );
                }
            }
            max_score = std::max(max_score, sub_hi_score[start][end]);
        }
    }
    std::ofstream("248.out") << max_score << endl;
    cout << max_score << endl;
}
