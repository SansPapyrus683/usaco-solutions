/*
ID: kevinsh4
TASK: calfflac
LANG: C++
*/
#include <fstream>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

using std::cout;
using std::endl;
using std::string;
using std::vector;

class HashedString {
   private:
    static const long long M = 1e9 + 9;
    static const long long B = 9973;
    static vector<long long> pow;
    vector<long long> p_hash;

   public:
    HashedString(const string &s) : p_hash(s.size() + 1) {
        while (pow.size() <= s.size()) {
            pow.push_back((pow.back() * B) % M);
        }

        p_hash[0] = 0;
        for (int i = 0; i < s.size(); i++) {
            p_hash[i + 1] = ((p_hash[i] * B) % M + s[i]) % M;
        }
    }

    long long get_hash(int start, int end) {
        long long raw_val = (p_hash[end + 1] - (p_hash[start] * pow[end - start + 1]));
        return (raw_val % M + M) % M;
    }
};
vector<long long> HashedString::pow = {1};

int main() {
    std::stringstream buffer;
    buffer << std::ifstream("calfflac.in").rdbuf();
    string raw_str = buffer.str();

    vector<char> chars;
    vector<int> inds;
    for (int i = 0; i < raw_str.size(); i++) {
        if (std::isalpha(raw_str[i])) {
            chars.push_back(std::tolower(raw_str[i]));
            inds.push_back(i);
        }
    }
    HashedString hash(string(chars.begin(), chars.end()));
    HashedString rhash(string(chars.rbegin(), chars.rend()));

    const int n = chars.size();  // shorthand
    int longest = 0;
    std::pair<int, int> range;
    for (int center = 0; center < n; center++) {
        int lo = 0;
        int hi = std::min(center, n - center - 1);
        int valid = 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;

            long long h1 = hash.get_hash(center - mid, center);
            long long h2 = rhash.get_hash(n - (center + mid) - 1, n - center - 1);
            if (h1 == h2) {
                valid = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        if (2 * valid + 1 > longest) {
            longest = 2 * valid + 1;
            range = {center - valid, center + valid};
        }

        // AND THEN SOMETHING JUST SNAPPED!
        // I JUST DIDN'T *CARE* ANYMORE!
        lo = 0;
        hi = std::min(center, n - center);
        valid = 0;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            long long h1 = hash.get_hash(center - mid, center - 1);
            long long h2 = rhash.get_hash(n - (center + mid - 1) - 1, n - center - 1);
            if (h1 == h2) {
                valid = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        if (2 * valid > longest) {
            longest = 2 * valid;
            range = {center - valid, center + valid - 1};
        }
    }

    std::ofstream written("calfflac.out");
    written << longest << '\n';
    for (int i = inds[range.first]; i <= inds[range.second]; i++) {
        written << raw_str[i];
    }
    written << endl;
}
