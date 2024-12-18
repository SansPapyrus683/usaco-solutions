/*
ID: kevinsh4
TASK: charrec
LANG: C++
*/
#include <algorithm>
#include <bitset>
#include <cstdint>
#include <fstream>
#include <iostream>
#include <string>
#include <vector>

using std::bitset;
using std::cout;
using std::endl;
using std::vector;

constexpr int HEIGHT = 20;
constexpr int WIDTH = 20;
const std::string CHARS = " abcdefghijklmnopqrstuvwxyz";

class CharRec {
   private:
    vector<vector<bitset<WIDTH>>> chars;

    int diff(const vector<bitset<WIDTH>>& rows, const vector<bitset<WIDTH>>& c) {
        int best = INT32_MAX;
        if (rows.size() == HEIGHT - 1) {
            for (int exc = 0; exc < HEIGHT; exc++) {
                int cost = 0;
                for (int r = 0; r < exc; r++) {
                    cost += (rows[r] ^ c[r]).count();
                }
                for (int r = exc + 1; r < HEIGHT; r++) {
                    cost += (rows[r - 1] ^ c[r]).count();
                }
                best = std::min(best, cost);
            }
        } else if (rows.size() == HEIGHT) {
            best = 0;
            for (int r = 0; r < HEIGHT; r++) {
                best += (rows[r] ^ c[r]).count();
            }
        } else if (rows.size() == HEIGHT + 1) {
            for (int dup = 0; dup < HEIGHT; dup++) {
                int cost = std::min((rows[dup] ^ c[dup]).count(),
                                    (rows[dup + 1] ^ c[dup]).count());
                for (int r = 0; r < dup; r++) {
                    cost += (rows[r] ^ c[r]).count();
                }
                for (int r = dup + 2; r <= HEIGHT; r++) {
                    cost += (rows[r] ^ c[r - 1]).count();
                }
                best = std::min(best, cost);
            }
        }
        return best;
    }

   public:
    CharRec(const std::string& fin) : chars(CHARS.size()) {
        std::ifstream fonts(fin);
        fonts.ignore(INT32_MAX, '\n');
        for (int i = 0; i < CHARS.size(); i++) {
            for (int row = 0; row < HEIGHT; row++) {
                std::string line;
                fonts >> line;
                chars[i].push_back(bitset<WIDTH>(line));
            }
        }
    }

    std::pair<int, char> min_corruptions(const vector<bitset<WIDTH>>& rows) {
        std::pair<int, char> best{INT32_MAX, '\0'};
        for (int i = 0; i < CHARS.size(); i++) {
            best = std::min(best, {diff(rows, chars[i]), CHARS[i]});
        }
        return best;
    }
};

int main() {
    std::ifstream read("charrec.in");
    int row_num;
    read >> row_num;
    vector<bitset<WIDTH>> input(row_num);
    for (int r = 0; r < row_num; r++) {
        std::string line;
        read >> line;
        input[r] = bitset<WIDTH>(line);
    }

    CharRec cr("font.in");
    struct Back {
        char recog;
        int prev_ind;
        int cost;
    };
    vector<Back> best(row_num + 1, {'\0', -1, INT32_MAX});
    best[0].cost = 0;
    for (int r = 1; r <= row_num; r++) {
        for (int prev : vector<int>{r - HEIGHT - 1, r - HEIGHT, r - HEIGHT + 1}) {
            if (prev < 0 || best[prev].cost == INT32_MAX) {
                continue;
            }

            vector<bitset<WIDTH>> slice(input.begin() + prev, input.begin() + r);
            std::pair<int, char> rec = cr.min_corruptions(slice);
            const int new_cost = best[prev].cost + rec.first;
            if (new_cost < best[r].cost) {
                best[r] = {rec.second, prev, new_cost};
            }
        }
    }

    vector<char> out;
    int at = row_num;
    while (at != 0) {
        const int next = best[at].prev_ind;
        const int char_cost = best[at].cost - best[next].cost;
        out.push_back(char_cost > WIDTH * HEIGHT * 0.3 ? '?' : best[at].recog);
        at = next;
    }

    std::ofstream written("charrec.out");
    for (int i = out.size() - 1; i >= 0; i--) {
        written << out[i];
    }
    written << endl;
}
