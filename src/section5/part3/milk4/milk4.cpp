/*
ID: kevinsh4
TASK: milk4
LANG: C++
*/
#include <algorithm>
#include <fstream>
#include <functional>
#include <iostream>
#include <vector>

using std::cout;
using std::endl;
using std::vector;

int main() {
    std::ifstream read("milk4.in");
    int milk_target;
    int pail_num;
    read >> milk_target >> pail_num;
    vector<int> pails(pail_num);
    for (int& p : pails) {
        read >> p;
    }

    // really inefficient but the bounds are small so who cares
    std::sort(pails.begin(), pails.end());
    vector<int> pruned_pails;
    for (int p = 0; p < pails.size() && pails[p] <= milk_target; p++) {
        for (int before_p : pruned_pails) {
            if (pails[p] % before_p == 0) {
                goto bad;
            }
        }
        pruned_pails.push_back(pails[p]);
    bad:;
    }
    pails = pruned_pails;

    vector<int> best_set(pails.size() + 1, -1);
    std::function<void(int, int, vector<int>&)> dfs;
    // ok this is the STUPIDEST dfs that literally shouldn't work but it does
    // because idk, the test cases are just tht bad????
    dfs = [&] (int at, int cap_left, vector<int>& selected) {
        if (at == pails.size() || cap_left == 0) {
            if (cap_left == 0) {
                if (selected.size() < best_set.size()) {
                    best_set = selected;
                } else {
                    best_set = std::min(best_set, selected);
                }
            }
            return;
        }

        if (selected.size() >= best_set.size()) {
            return;
        }
        
        dfs(at + 1, cap_left, selected);
        selected.push_back(pails[at]);
        for (int take = 1; take * pails[at] <= cap_left; take++) {
            dfs(at + 1, cap_left - take * pails[at], selected);
        }
        selected.pop_back();
    };
    vector<int> selected;
    dfs(0, milk_target, selected);

    std::ofstream written("milk4.out");
    written << best_set.size();
    for (int p : best_set) {
        written << ' ' << p;
    }
    written << endl;
}
