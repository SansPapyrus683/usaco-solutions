#include <iostream>
#include <vector>
#include <map>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;
using std::pair;
using Point = pair<long long, long long>;

Point add_pts(const Point& p1, const Point& p2) {
    return {p1.first + p2.first, p1.second + p2.second};
}

vector<pair<Point, int>> subset_sums(const vector<Point>& points) {
    vector<pair<Point, int>> all_points{{{0, 0}, 0}};
    for (const Point& p : points) {
        int size = all_points.size();
        for (int i = 0; i < size; i++) {
            const pair<Point, int>& prev = all_points[i];
            all_points.push_back({add_pts(p, prev.first), prev.second + 1});
        }
    }
    return all_points;
}

// 2022 feb gold (input ommitted due to length)
int main() {
    int instruction_num;
    std::cin >> instruction_num;

    Point target;
    std::cin >> target.first >> target.second;

    vector<Point> half1(instruction_num / 2);
    vector<Point> half2(instruction_num - half1.size());
    for (int p = 0; p < instruction_num; p++) {
        if (p < half1.size()) {
            std::cin >> half1[p].first >> half1[p].second;
        } else {
            int ind = p - half1.size();
            std::cin >> half2[ind].first >> half2[ind].second;
        }
    }

    vector<pair<Point, int>> subs1 = subset_sums(half1);
    vector<pair<Point, int>> subs2 = subset_sums(half2);
    std::sort(subs1.begin(), subs1.end());
    std::sort(subs2.begin(), subs2.end(), std::greater<pair<Point, int>>());

    auto add = [&] (int ind1, int ind2) -> Point {
        return add_pts(subs1[ind1].first, subs2[ind2].first);
    };

    vector<long long> valid_subsets(instruction_num + 1);
    int at2 = 0;
    for (int at1 = 0; at1 < subs1.size(); at1++) {
        while (at2 < subs2.size() && add(at1, at2) > target) {
            at2++;
        }
        if (add(at1, at2) != target) {
            continue;
        }

        Point initial1 = subs1[at1].first;
        Point initial2 = subs2[at2].first;
        std::map<int, long long> sizes1{{subs1[at1].second, 1}};
        std::map<int, long long> sizes2{{subs2[at2].second, 1}};
        while (at1 + 1 < subs1.size() && subs1[at1 + 1].first == initial1) {
            at1++;
            sizes1[subs1[at1].second]++;
        }
        while (at2 + 1 < subs2.size() && subs2[at2 + 1].first == initial2) {
            at2++;
            sizes2[subs2[at2].second]++;
        }
        for (const auto& [sz1, amt1] : sizes1) {
            for (const auto& [sz2, amt2] : sizes2) {
                valid_subsets[sz1 + sz2] += amt1 * amt2;
            }
        }
    }

    for (int i = 1; i <= instruction_num; i++) {
        cout << valid_subsets[i] << endl;
    }
}
