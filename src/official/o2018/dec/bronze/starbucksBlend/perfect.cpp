#include <iostream>
#include <fstream>
#include <stdexcept>
#include <vector>

using std::cout;
using std::endl;
using std::pair;

constexpr int BUCKET_NUM = 3;
constexpr int POUR_AMT = 100;

void pour(pair<int, int>& from, pair<int, int>& to) {
    if (from.second > from.first || to.second > to.first) {
        throw std::invalid_argument("uh one of your buckets is overflowing");
    }
    // see if there's too much milk to be poured
    if (from.second >= to.first - to.second) {
        from.second -= to.first - to.second;
        to.second = to.first;
    } else {  // ok there's more than enough capacity
        to.second += from.second;
        from.second = 0;
    }
}

// 2018 dec bronze
int main() {
    std::ifstream read("mixmilk.in");
    std::vector<pair<int, int>> buckets(BUCKET_NUM);
    for (int b = 0; b < BUCKET_NUM; b++) {
        // first is capacity, second is amount
        read >> buckets[b].first >> buckets[b].second;
    }
    
    // just simulate the pouring process until there's enough
    int at = 0;
    for (int i = 0; i < POUR_AMT; i++) {
        int next = (at + 1) % BUCKET_NUM;
        pour(buckets[at], buckets[next]);
        at = next;
    }

    std::ofstream written("mixmilk.out");
    for (pair<int, int> b : buckets) {
        cout << b.second << endl;
        written << b.second << endl;
    }
}
