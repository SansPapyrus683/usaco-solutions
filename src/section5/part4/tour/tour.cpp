/*
ID: kevinsh4
TASK: tour
LANG: C++
*/
#include <algorithm>
#include <cstdint>
#include <fstream>
#include <iostream>
#include <map>
#include <set>
#include <string>
#include <vector>

using std::cout;
using std::endl;
using std::vector;

int main() {
    std::ifstream read("tour.in");
    int city_num;
    int flight_num;
    read >> city_num >> flight_num;
    std::map<std::string, int> city_id;
    for (int c = 0; c < city_num; c++) {
        std::string city;
        read >> city;
        city_id[city] = c;
    }

    vector<std::set<int>> fly_to(city_num);
    for (int f = 0; f < flight_num; f++) {
        std::string c1, c2;
        read >> c1 >> c2;
        if (city_id[c1] > city_id[c2]) {
            std::swap(c1, c2);
        }
        fly_to[city_id[c1]].insert(city_id[c2]);
    }

    vector<vector<int>> max_dist(city_num, vector<int>(city_num));
    max_dist[0][0] = 1;
    auto upd = [&](int c1, int c2, int val) {
        max_dist[c1][c2] = std::max(max_dist[c1][c2], val);
    };
    for (int c1 = 0; c1 < city_num; c1++) {
        for (int c2 = 0; c2 < city_num; c2++) {
            if (max_dist[c1][c2] == 0) {
                continue;
            }
            // DIABOLICAL transition but it gets the job done
            if (c1 > c2) {
                for (int n : fly_to[c1]) {
                    upd(n, c2, max_dist[c1][c2] + 1);
                }
                for (int n : fly_to[c2]) {
                    if (n > c1) {
                        upd(c1, n, max_dist[c1][c2] + 1);
                    }
                }
            } else {
                for (int n : fly_to[c2]) {
                    upd(c1, n, max_dist[c1][c2] + 1);
                }
                for (int n : fly_to[c1]) {
                    if (n > c2) {
                        upd(n, c2, max_dist[c1][c2] + 1);
                    }
                }
            }
            
            if (fly_to[c1].count(city_num - 1) && fly_to[c2].count(city_num - 1)) {
                upd(city_num - 1, city_num - 1, max_dist[c1][c2] + 1);
            }
        }
    }

    const int last = max_dist[city_num - 1][city_num - 1];
    std::ofstream("tour.out") << (last == 0 ? 1 : last) << endl;
}
