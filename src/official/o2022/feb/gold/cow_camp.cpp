#include <cassert>
#include <cmath>
#include <cstdint>
#include <iostream>
#include <vector>
#include <iomanip>

using std::cout;
using std::endl;
using std::vector;

using ld = long double;  // sue me.

ld choose(int n, int k) {
    ld ret = 1;
    for (int i = n; i > n - k; i--) { ret *= i; }
    for (int i = 2; i <= k; i++) { ret /= i; }
    return ret;
}

ld exp(ld x, int n) {
    ld res = 1;
    while (n > 0) {
        res *= n % 2 == 1 ? x : 1;
        x *= x;
        n /= 2;
    }
    return res;
}

ld app_result(ld init, ld prob, ld higher, int amt) {
    return higher + exp(1 - prob, amt) * (init - higher);
}

int min_apps(ld init, ld thresh, ld prob, ld higher) {
    // if this assertion causes any problems just delete it idek
    assert(init <= thresh && thresh <= higher);
    int lo = 1;
    int hi = 1e9;  // I DON'T CARE!!
    int valid = INT32_MAX;
    while (lo <= hi) {
        int mid = (lo + hi) / 2;
        if (app_result(init, prob, higher, mid) > thresh) {
            valid = mid;
            hi = mid - 1;
        } else {
            lo = mid + 1;
        }
    }
    return valid;
}

int main() {
    int test_num;
    int submit_num;
    std::cin >> test_num >> submit_num;
    test_num--;  // idgaf abt sample

    ld total = 1;
    for (int i = 0; i < test_num; i++) { total *= 2; }
    vector<ld> prob(test_num + 1);
    for (int t = 0; t <= test_num; t++) { prob[t] = choose(test_num, t) / total; }
    vector<ld> ge_prob(prob);
    vector<ld> ge_ev(test_num + 1);
    for (int t = test_num; t >= 0; t--) {
        ge_prob[t] += t < test_num ? ge_prob[t + 1] : 0;
        for (int i = t; i <= test_num; i++) { ge_ev[t] += prob[i] / ge_prob[t] * i; }
    }

    ld ev = (double)test_num / 2;
    int amt_left = submit_num - 1;
    while (amt_left > 0) {
        int thresh = ceil(ev);
        int amt = min_apps(ev, thresh, ge_prob[thresh], ge_ev[thresh]);
        int app_num = std::min(amt_left, amt);
        ev = app_result(ev, ge_prob[thresh], ge_ev[thresh], app_num);
        amt_left -= app_num;
    }

    cout << std::fixed << std::setprecision(15) << ev + 1 << endl;
}
