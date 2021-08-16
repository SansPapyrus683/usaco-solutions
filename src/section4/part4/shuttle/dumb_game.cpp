/*
ID: kevinsh4
TASK: shuttle
LANG: C++
*/
#include <iostream>
#include <fstream>
#include <vector>

using std::cout;
using std::endl;
using std::vector;
using std::pair;

constexpr int SPLIT_AT = 20;

int main() {
    int board_len;
    std::ifstream("shuttle.in") >> board_len;

    int pos = true;
    vector<int> diffs;
    for (int i = 1; i <= board_len; i++) {
        for (int j = 0; j < i; j++) {
            diffs.push_back(pos ? 2 : -2);
        }
        if (i < board_len) {
            diffs.push_back(pos ? 1 : -1);
        }
        pos = !pos;
    }
    for (int i = board_len - 1; i >= 1; i--) {
        diffs.push_back(pos ? 1 : -1);
        for (int j = 0; j < i; j++) {
            diffs.push_back(pos ? 2 : -2);
        }
        pos = !pos;
    }
    diffs.push_back(pos ? 1 : -1);

    // remember, the board's one-indexed    
    vector<int> moves{board_len};
    for (int d : diffs) {
        moves.push_back(moves.back() + d);
    }

    std::ofstream written("shuttle.out");
    for (int i = 0; i < moves.size() - 1; i++) {
        written << moves[i] << (i % SPLIT_AT == SPLIT_AT - 1 ? '\n' : ' ');
    }
    written << moves.back() << endl;
}
