/*
ID: kevinsh4
TASK: hidden
LANG: C++
*/
#include <fstream>
#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using std::cout;
using std::endl;
using std::string;
using std::vector;

int main() {
    std::ifstream read("hidden.in");
    int n;
    read >> n;
    vector<char> vec(n);
    for (char& c : vec) {
        read >> c;
    }
    string str(vec.begin(), vec.end());

    // HAHAHAHA I JUST COPY PASTED SUFFIX ARRAY LMAAAAOOOOO
    vector<int> suffs(n);
    for (int i = 0; i < suffs.size(); i++) {
        suffs[i] = i;
    }
    std::stable_sort(suffs.begin(), suffs.end(),
              [&](int a, int b) { return str[a] < str[b]; });
    vector<vector<int>> equiv{vector<int>(n)};
    for (int i = 1; i < n; i++) {
        bool gt = str[suffs[i]] > str[suffs[i - 1]];
        equiv[0][suffs[i]] = equiv[0][suffs[i - 1]] + gt;
    }

    int cmp_amt = 1;
    while (cmp_amt < n) {
        const vector<int>& prev = equiv.back();
        auto cmp = [&](int a, int b) {
            if (prev[a] != prev[b]) {
                return prev[a] < prev[b];
            }
            int right_a = prev[(a + cmp_amt) % n];
            int right_b = prev[(b + cmp_amt) % n];
            return right_a < right_b;
        };
        std::stable_sort(suffs.begin(), suffs.end(), cmp);

        vector<int> nxt_eq = vector<int>(n);
        for (int i = 1; i < n; i++) {
            nxt_eq[suffs[i]] = nxt_eq[suffs[i - 1]] + cmp(suffs[i - 1], suffs[i]);
        }
        equiv.push_back(nxt_eq);

        cmp_amt *= 2;
    }

    std::ofstream("hidden.out") << suffs[0] << endl;
}
