#include <functional>
#include <iostream>
#include <cassert>
#include <vector>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;

/** 2022 dec silver (input omitted bc length) */
int main() {
    int barn_num;
    std::cin >> barn_num;
    vector<long long> bales(barn_num);
    long long total = 0;
    for (long long& b : bales) {
        std::cin >> b;
        total += b;
    }
    assert(total % barn_num == 0);
    long long target = total / barn_num;

    vector<vector<int>> neighbors(barn_num);
    for (int r = 0; r < barn_num - 1; r++) {
        int b1, b2;
        std::cin >> b1 >> b2;
        neighbors[--b1].push_back(--b2);
        neighbors[b2].push_back(b1);
    }

    vector<long long> sub_debt(barn_num);
    vector<int> parent(barn_num);
    vector<vector<int>> children(barn_num);
    std::function<void(int, int)> dfs;
    dfs = [&] (int at, int prev) {
        parent[at] = prev;
        sub_debt[at] = bales[at] - target;
        for (int n : neighbors[at]) {
            if (n != prev) {
                children[at].push_back(n);
                dfs(n, at);
                sub_debt[at] += sub_debt[n];
            }
        }
    };
    dfs(0, 0);

    struct Move {
        int from, to;
        long long amt;
    };
    vector<Move> moves;
    dfs = [&] (int at, int prev) {
        std::sort(
            children[at].begin(), children[at].end(),
            [&] (int b1, int b2) { return sub_debt[b1] > sub_debt[b2]; }
        );
        if (sub_debt[at] < 0) {
            long long move_amt = -sub_debt[at];
            bales[at] += move_amt;
            bales[prev] -= move_amt;
            moves.push_back({prev, at, move_amt});
        }
        for (int n : children[at]) {
            dfs(n, at);
        }
        if (sub_debt[at] > 0) {
            long long move_amt = sub_debt[at];
            bales[at] -= move_amt;
            bales[prev] += move_amt;
            moves.push_back({at, prev, move_amt});
        }
    };
    dfs(0, 0);

    cout << moves.size() << '\n';
    for (const Move& m : moves) {
        printf("%i %i %lld\n", m.from + 1, m.to + 1, m.amt);
    }
}
