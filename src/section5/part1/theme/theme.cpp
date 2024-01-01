/*
ID: kevinsh4
TASK: theme
LANG: C++
*/
#include <fstream>
#include <vector>
#include <map>
#include <algorithm>

using std::endl;
using std::vector;

constexpr int MOD = 1e9 + 7;
constexpr int POW = 9973;

int main() {
    std::ifstream read("theme.in");
    int note_num;
    read >> note_num;
    vector<int> notes(note_num);
    for (int& n : notes) {
        read >> n;
    }

    vector<int> diffs;
    for (int i = 1; i < note_num; i++) {
        diffs.push_back(notes[i] - notes[i - 1]);
    }
    vector<long long> pow{1};
    for (int i = 0; i < diffs.size(); i++) {
        pow.push_back((pow.back() * POW) % MOD);
    }
    vector<long long> diff_hash(diffs.size() + 1);
    diff_hash[0] = 0;
    for (int i = 0; i < diffs.size(); i++) {
        diff_hash[i + 1] = ((diff_hash[i] * POW) % MOD + diffs[i]) % MOD;
    }
    auto get_hash = [&](int start, int end) {
        long long raw_val =
            (diff_hash[end + 1] - (diff_hash[start] * pow[end - start + 1]));
        return (raw_val % MOD + MOD) % MOD;
    };

    int lo = 5;
    int hi = note_num / 2;
    int valid = 0;  // if no valid themes, output 0
    while (lo <= hi) {
        int mid = (lo + hi) / 2;
        bool found = false;
        std::map<long long, int> prev_hashes;
        for (int s = 0; s + mid <= note_num; s++) {
            long long hash = get_hash(s, s + mid - 2);
            if (prev_hashes.count(hash)) {
                int prev_ind = prev_hashes[hash];
                if (s - prev_ind >= mid) {
                    found = true;
                    break;
                }
            } else {
                prev_hashes[hash] = s;
            }
        }

        if (found) {
            valid = mid;
            lo = mid + 1;
        } else {
            hi = mid - 1;
        }
    }

    std::ofstream("theme.out") << valid << endl;
}
