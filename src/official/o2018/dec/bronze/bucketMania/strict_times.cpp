#include <iostream>
#include <fstream>
#include <stdexcept>
#include <vector>
#include <map>

using std::cout;
using std::endl;
using std::pair;

// 2018 dec bronze
int main() {
    std::ifstream read("blist.in");
    int cow_num;
    read >> cow_num;
    // a pair contains whether it's a start or end and the amount of buckets
    std::map<int, pair<bool, int>> times;
    for (int c = 0; c < cow_num; c++) {
        int start;
        int end;
        int buckets;
        read >> start >> end >> buckets;
        if (times.find(start) != times.end() || times.find(end) != times.end()) {
            throw std::invalid_argument("your start and end times aren't distinct bud");
        }
        times[start] = {true, buckets};
        times[end] = {false, buckets};
    }

    int total_buckets = 0;
    int idle_buckets = 0;
    for (pair<int, pair<bool, int>> time : times) {
        pair<bool, int> info = time.second;
        if (info.first) {
            // see if we have enough buckets- if not, add the extra to the total count
            if (info.second > idle_buckets) {
                total_buckets += info.second - idle_buckets;
                idle_buckets = 0;
            } else {
                idle_buckets -= info.second;
            }
        } else {
            idle_buckets += info.second;
        }
    }
    std::ofstream("blist.out") << total_buckets << endl;
    cout << total_buckets << endl;
}
