#include <iostream>
#include <string>
#include <vector>
#include <map>

using std::cout;
using std::endl;
using std::vector;

constexpr int MOD = 1e9 + 7;

/**
 * 2020 dec gold
 * ? should output 4
 * GAT?GTT should output 3
 */
int main() {
    const std::map<char, int> VALID{{'A', 0}, {'T', 1}, {'C', 2}, {'G', 3}};
    const int AMT = VALID.size();
    const char ANY = '?';

    std::string seq;
    std::cin >> seq;
    
    /*
     * seq_num[i][at][start][prev_start]
     * number of sequences GIVEN THAT:
     * 1. we've processed i characters
     * 2. the last character is be that of at
     * 3. the start of the current cut is at
     * 4. the start of the previous cut is prev_start
     */
    vector<vector<vector<vector<int>>>> seq_num(
        seq.size() + 1, vector<vector<vector<int>>>(
            AMT, vector<vector<int>>(
                AMT, vector<int>(AMT)
            )
        )
    );
    
    // base case shenanigans
    if (seq[0] == ANY) {
        for (int at = 0; at < AMT; at++) {
            for (int prev_start = 0; prev_start < AMT; prev_start++) {
                seq_num[1][at][at][prev_start] = 1;
            }
        }
    } else {
        int val = VALID.at(seq[0]);  // VALID is const so i have to do this
        for (int prev_start = 0; prev_start < AMT; prev_start++) {
            seq_num[1][val][val][prev_start] = 1;
        }
    }

    for (int i = 0; i < seq.size() - 1; i++) {
        char c = seq[i];
        for (int at = 0; at < AMT; at++) {
            if (c != ANY && at != VALID.at(c)) {
                continue;
            }
            for (int start = 0; start < AMT; start++) {
                for (int prev_start = 0; prev_start < AMT; prev_start++) {
                    /*
                     * look, i know the actual next char might not actually
                     * be n_at, but notice that it doesn't really matter lmao
                     */
                    for (int n_at = 0; n_at < AMT; n_at++) {
                        if (n_at != at) {
                            int& next = seq_num[i + 2][n_at][start][prev_start];
                            next += seq_num[i + 1][at][start][prev_start];
                            next %= MOD;
                        }
                        if (at == prev_start) {
                            int& next = seq_num[i + 2][n_at][n_at][start];
                            next += seq_num[i + 1][at][start][prev_start];
                            next %= MOD;
                        }
                    }
                }
            }
        }
    }

    long long total = 0;
    for (int at = 0; at < AMT; at++) {
        if (seq.back() != ANY && at != VALID.at(seq.back())) {
            continue;
        }
        for (int start = 0; start < AMT; start++) {
            total = (total + seq_num[seq.size()][at][start][at]) % MOD;
        }
    }
    cout << total << endl;
}
