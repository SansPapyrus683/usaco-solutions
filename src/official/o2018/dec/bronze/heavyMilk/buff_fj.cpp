#include <iostream>
#include <fstream>
#include <vector>
#include <unordered_set>
#include <set>

using std::cout;
using std::endl;
using std::vector;
using std::pair;
using std::unordered_set;

// forgive me father, for i have sinned
typedef std::multiset<int> i_set;

constexpr int FARM_AMT = 1000;
constexpr int BUCKET_NUM = 10;
constexpr int DAY_NUM = 4;

// 2018 dec bronze
int main() {
    std::ifstream read("backforth.in");
    i_set first_buckets;
    i_set second_buckets;
    for (int b = 0; b < BUCKET_NUM; b++) {
        int bucket;
        read >> bucket;
        first_buckets.insert(bucket);
    }
    for (int b = 0; b < BUCKET_NUM; b++) {
        int bucket;
        read >> bucket;
        second_buckets.insert(bucket);
    }

    // 1st is a pair of the buckets at each farm, and the milk amt at each farm
    vector<pair<pair<i_set, i_set>, pair<int, int>>> frontier;
    frontier.push_back({{first_buckets, second_buckets}, {FARM_AMT, FARM_AMT}});
    for (int d = 0; d < DAY_NUM; d++) {
        vector<pair<pair<i_set, i_set>, pair<int, int>>> in_line;
        // using auto because i am not typing those types all over again
        for (auto state : frontier) {
            if (d % 2 == 0) {  // first to second
                // this has got to be some of the most hacky code i've ever written
                for (int b : state.first.first) {
                    auto copy = state;
                    copy.first.first.erase(copy.first.first.find(b));
                    copy.first.second.insert(b);
                    copy.second.first -= b;
                    copy.second.second += b;
                    in_line.push_back(copy);
                }
            } else {  // second to first
                for (int b : state.first.second) {
                    auto copy = state;
                    copy.first.second.erase(copy.first.second.find(b));
                    copy.first.first.insert(b);
                    copy.second.second -= b;
                    copy.second.first += b;
                    in_line.push_back(copy);
                }
            }
        }
        frontier = in_line;
    }

    unordered_set<int> poss_readings;
    for (auto ending : frontier) {
        poss_readings.insert(ending.second.first);
    }
    std::ofstream written("backforth.out");
    written << poss_readings.size() << endl;
    cout << poss_readings.size() << endl;
}
