#include <iostream>
#include <fstream>
#include <vector>
#include <map>
#include <deque>
#include <algorithm>

using std::cout;
using std::endl;
using std::pair;
using std::vector;

// 2013 feb gold
int main() {
    std::ifstream read("route.in");
    int left_num;
    int right_num;
    int route_num;
    read >> left_num >> right_num >> route_num;

    vector<int> left(left_num);
    vector<int> right(right_num);
    for (int& l : left) {
        read >> l;
    }
    for (int& r : right) {
        read >> r;
    }
    vector<pair<int, int>> routes(route_num);
    for (pair<int, int>& r : routes) {
        read >> r.first >> r.second;
        r.first--;
        r.second--;
    }
    std::sort(routes.begin(), routes.end());

    // the maximum val given that we land on the ith spot on the left side
    vector<int> left_best(left);
    // and right side respectively
    vector<int> right_best(right);
    // we sorted the routes so that we don't have to worry about intersecting
    for (const pair<int, int>& r : routes) {
        // store the initial left value to simulate a simultaenous update
        int init_left_best = left_best[r.first];
        // go from the right to the left (testing the new value)
        left_best[r.first] = std::max(
            left_best[r.first], right_best[r.second] + left[r.first]
        );
        // and left to right
        right_best[r.second] = std::max(
            right_best[r.second], init_left_best + right[r.second]
        );
    }

    int most_interesting = std::max(
        *std::max_element(left_best.begin(), left_best.end()),
        *std::max_element(right_best.begin(), right_best.end())
    );
    std::ofstream("route.out") << most_interesting << endl;
    cout << most_interesting << endl;
}
