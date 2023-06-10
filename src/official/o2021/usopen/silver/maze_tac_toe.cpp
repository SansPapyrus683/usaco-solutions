#include <iostream>
#include <vector>
#include <cmath>
#include <set>

using std::cout;
using std::endl;
using std::vector;
using std::pair;

struct Cell {
    bool open = true;  // can we go here?
    bool move = false;  // does this cell have a move in it?
    char c;  // if so, what do we put
    pair<int, int> pos;  // and where do we put it?
};

struct MTTConfig {
    int data;

    void move(int r, int c, char move) {
        if (get_pos(r, c) == 0) {
            int base = std::pow(3, 3 * r + c);
            if (move == 'M') {
                data += base;
            } else if (move == 'O') {
                data += 2 * base;
            }
        }
    }

    int get_pos(int r, int c) const {
        int temp = data;
        for (int i = 0; i < 3 * r + c; i++) {
            temp /= 3;
        }
        return temp % 3;
    }

    bool is_win() const {
        int b[3][3];  // the actual board
        int temp_data = data;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                b[r][c] = temp_data % 3;
                temp_data /= 3;
            }
        }

        // check all rows
        for (int r = 0; r < 3; r++) {
            if ((b[r][0] == 1 && b[r][1] == 2 && b[r][2] == 2)
                    || (b[r][0] == 2 && b[r][1] == 2 && b[r][2] == 1)) {
                return true;
            }
        }

        // check all columns
        for (int c = 0; c < 3; c++) {
            if ((b[0][c] == 1 && b[1][c] == 2 && b[2][c] == 2)
                    || (b[0][c] == 2 && b[1][c] == 2 && b[2][c] == 1)) {
                return true;
            }
        }

        // and the two diagonals
        if ((b[0][0] == 1 && b[1][1] == 2 && b[2][2] == 2)
                || (b[0][0] == 2 && b[1][1] == 2 && b[2][2] == 1)) {
            return true;
        }
        if ((b[2][0] == 1 && b[1][1] == 2 && b[0][2] == 2)
                || (b[2][0] == 2 && b[1][1] == 2 && b[0][2] == 1)) {
            return true;
        }

        return false;
    }
};

vector<pair<int, int>> neighbors(int r, int c) {
    return {{r - 1, c}, {r + 1, c}, {r, c - 1}, {r, c + 1}};
}

// 2021 us open silver (input omitted bc length)
int main() {
    int n;
    std::cin >> n;

    vector<vector<Cell>> grid(n, vector<Cell>(n));
    pair<int, int> start;
    for (int r = 0; r < n; r++) {
        for (int c = 0; c < n; c++) {
            char x1, x2, x3;  // can't think of better names, sorry
            std::cin >> x1 >> x2 >> x3;

            Cell& cell = grid[r][c];
            if (x1 == '#') {
                cell.open = false;
            } else if (x1 == 'B') {
                start = {r, c};
            } else if (x1 != '.') {
                cell.move = true;
                cell.c = x1;
                cell.pos = {x2 - '1', x3 - '1'};
            }
        }
    }

    vector<pair<pair<int, int>, MTTConfig>> frontier{{start, MTTConfig()}};
    vector<vector<vector<bool>>> visited(
        n, vector<vector<bool>>(n, vector<bool>(std::pow(3, 9)))
    );
    std::set<int> wins;
    while (!frontier.empty()) {
        const auto [pos, config] = frontier.back();
        frontier.pop_back();
        for (const auto& [nr, nc] : neighbors(pos.first, pos.second)) {
            MTTConfig next = config;
            const Cell& ncell = grid[nr][nc];
            if (!ncell.open) {
                continue;
            }

            if (ncell.move) {  // if there is a move, make it
                next.move(ncell.pos.first, ncell.pos.second, ncell.c);
            }
            if (next.is_win()) {
                wins.insert(next.data);  // btw we stop when we win
            } else if (!visited[nr][nc][next.data]) {
                visited[nr][nc][next.data] = true;
                frontier.push_back({{nr, nc}, next});
            }
        }
    }

    cout << wins.size() << endl;
}
