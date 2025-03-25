#include <algorithm>
#include <cstdint>
#include <iostream>
#include <map>
#include <set>
#include <vector>

using namespace std;

constexpr int INF = INT32_MAX;  // to save line space LMAO

pair<int, int> path_at(const vector<pair<int, int>>& seq, int time) {
    long long passed = 0;
    for (int i = 0; i < seq.size() - 1; i++) {
        pair<int, int> curr = seq[i], next = seq[i + 1];
        passed += (long long)next.first - curr.first + next.second - curr.second;
        if (passed >= time) {
            if (next.first != curr.first) {
                return {next.first - (int)(passed - time), next.second};
            }
            return {next.first, next.second - (int)(passed - time)};
        }
    }
    return {-1, -1};
}

/** 2024 jan gold (awful, no good sol, but hey! it passes) */
int main() {
    int road_num, cow_num;
    cin >> road_num >> cow_num;

    vector<set<int>> vert{{INF}, {INF}}, horz{{INF}, {INF}};
    map<int, int> all_vert{{INF, -1}}, all_horz{{INF, -1}};
    for (int r = 0; r < road_num; r++) {
        char dir;
        int pos;
        cin >> dir >> pos;
        if (dir == 'V') {
            vert[pos % 2].insert(pos);
            all_vert[pos] = -1;
        } else if (dir == 'H') {
            horz[pos % 2].insert(pos);
            all_horz[pos] = -1;
        }
    }

    vector<int> v_pos{all_vert.begin()->first};
    all_vert.begin()->second = 0;
    for (auto& [x, ind] : all_vert) {
        if (x % 2 != v_pos.back() % 2) {
            ind = v_pos.size();
            v_pos.push_back(x);
        }
    }
    vector<int> h_pos{all_horz.begin()->first};
    all_horz.begin()->second = 0;
    for (auto& [x, ind] : all_horz) {
        if (x % 2 != h_pos.back() % 2) {
            ind = h_pos.size();
            h_pos.push_back(x);
        }
    }

    for (int c = 0; c < cow_num; c++) {
        int x, y, d;
        cin >> x >> y >> d;

        vector<pair<int, int>> history{{x, y}};
        int passed = 0;
        if (!all_vert.count(x)) {
            int next = all_vert.upper_bound(x)->first;
            passed = next - x;
            history.push_back({x = next, y});
        } else if (!all_horz.count(y)) {
            int next = all_horz.upper_bound(y)->first;
            passed = next - y;
            history.push_back({x, y = next});
        }

        pair<int, int> res = path_at(history, d);
        if (res.first == -1) {
            for (int i = 0; i < 2; i++) {
                if (passed % 2 == 0) {
                    int next = *horz[1 - y % 2].upper_bound(y);
                    passed += next - y;
                    y = next;
                } else {
                    int next = *vert[1 - x % 2].upper_bound(x);
                    passed += next - x;
                    x = next;
                }
                history.push_back({x, y});
                if ((res = path_at(history, d)).first != -1) { break; }
            }
        }

        if (res.first != -1) {
            cout << res.first << ' ' << res.second << '\n';
            continue;
        }

        int x_ind = all_vert[x], y_ind = all_horz[y];
        int lo = 0;
        int hi = min(v_pos.size() - x_ind - 1, h_pos.size() - y_ind - 1);
        int valid = -1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            long long dx = v_pos[x_ind + mid] - x;
            long long dy = h_pos[y_ind + mid] - y;
            if (dx + dy + passed <= d) {
                lo = mid + 1;
                valid = mid;
            } else {
                hi = mid - 1;
            }
        }

        passed += v_pos[x_ind + valid] - x + h_pos[y_ind + valid] - y;
        x = v_pos[x_ind + valid];
        y = h_pos[y_ind + valid];

        history = {{x, y}};
        int time_left = d - passed;
        for (int i = 0; i < 2; i++) {
            if (passed % 2 == 0) {
                int next = *horz[1 - y % 2].upper_bound(y);
                passed += next - y;
                y = next;
            } else {
                int next = *vert[1 - x % 2].upper_bound(x);
                passed += next - x;
                x = next;
            }
            history.push_back({x, y});

            if ((res = path_at(history, time_left)).first != -1) {
                cout << res.first << ' ' << res.second << '\n';
                break;
            }
        }
    }
}
