#include <algorithm>
#include <cstdint>
#include <iostream>
#include <vector>

using std::cout;
using std::endl;
using std::vector;

struct Cow {
    int pos;
    int weight;
};

struct State {
    int last;  // the POSITION (not index) of the last unpaired cow
    int wasted = 0;
    int max_b4 = 0;
};

bool operator<(const State& a, const State& b) { return a.last < b.last; }

int max_unpaired(const vector<Cow>& cows, int max_dist) {
    auto lb = [&] (const vector<State>& v, int pos) {
        return lower_bound(v.begin(), v.end(), State{pos});
    };
    vector<State> prev{{cows[0].pos, cows[0].weight, cows[0].weight}};
    vector<State> pprev{{INT32_MIN}};
    // i really wish i could explain how this worked. i can't.
    // i just, yknow, feel it in my BONES
    for (int c = 1; c < cows.size(); c++) {
        const Cow &curr = cows[c], b4 = cows[c - 1];
        std::swap(prev, pprev);
        if (curr.pos - b4.pos <= max_dist) {
            if (c >= 2 && curr.pos - cows[c - 2].pos <= max_dist) {
                auto it = lb(pprev, b4.pos - max_dist);
                if (it != pprev.begin()) {
                    it--;
                    const int wasted = it->max_b4 + b4.weight;
                    const int last = prev.back().max_b4;
                    State s{b4.pos, wasted, std::max(wasted, last)};
                    prev.push_back(s);
                }
            }

            auto it = lb(pprev, curr.pos - max_dist);
            if (it != pprev.begin()) {
                it--;
                const int wasted = it->max_b4 + curr.weight;
                const int last = prev.back().max_b4;
                State s{curr.pos, wasted, std::max(wasted, last)};
                prev.push_back(s);
            }
        } else if (curr.pos - b4.pos > max_dist) {
            int best = pprev.back().max_b4 + curr.weight;
            prev = {{curr.pos, best, best}};
        }
    }

    return prev.back().max_b4;
}

/** 2021 dec gold */
int main() {
    int type;
    int max_dist;
    int cow_num;
    std::cin >> type >> cow_num >> max_dist;
    vector<Cow> cows(cow_num);  // assume will be sorted alr
    for (Cow& c : cows) {
        std::cin >> c.pos >> c.weight;
        if (type == 1) {
            c.weight *= -1;
        }
    }

    int ans = max_unpaired(cows, max_dist);
    cout << (type == 1 ? -ans : ans) << endl;
}
