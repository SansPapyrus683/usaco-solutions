#include <iostream>
#include <cassert>
#include <vector>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;

/** 2022 dec silver (sample input omitted due to ambiguity) */
int main() {
    int sz;
    std::cin >> sz;
    vector<vector<int>> range_diff(sz, vector<int>(sz));
    for (int i = 0; i < sz; i++) {
        for (int j = i; j < sz; j++) {
            std::cin >> range_diff[i][j];
            assert(range_diff[i][j] >= 0);
        }
    }

    vector<std::pair<int, vector<int>>> consec_diffs;
    vector<int> temp;  // diffs are appended to this vector, it's a middleman
    int start = 0;
    for (int i = 0; i < sz; i++) {
        if (i == sz - 1 || range_diff[i][i + 1] == 0) {
            if (!temp.empty()) {
                for (int j = 0; j < temp.size() - 1; j++) {
                    int at = i - (temp.size() - j);
                    int range = range_diff[at][at + 2];
                    bool same_sign = abs(temp[j]) + abs(temp[j + 1]) == range;
                    temp[j + 1] *= temp[j] > 0 ^ same_sign ? -1 : 1;
                }
                consec_diffs.push_back({start, temp});
                temp = {};
            }
        } else {
            if (temp.empty()) {
                start = i;
            }
            temp.push_back(range_diff[i][i + 1]);
        }
    }
    
    vector<int> arr{0};
    int diff_at = 0;
    std::pair<int, vector<int>> curr;
    for (const auto& [start, diff] : consec_diffs) {
        while (arr.size() <= start) {
            arr.push_back(arr.back());
        }
        for (int mul_by : vector<int>{1, -1}) {
            vector<int> test_arr(arr);
            for (int d : diff) {
                test_arr.push_back(test_arr.back() + d * mul_by);
            }
            
            bool valid = true;
            for (int i = 0; i < test_arr.size(); i++) {
                int min = test_arr[i];
                int max = test_arr[i];
                for (int j = i + 1; j < test_arr.size(); j++) {
                    min = std::min(min, test_arr[j]);
                    max = std::max(max, test_arr[j]);
                    if (max - min != range_diff[i][j]) {
                        valid = false;
                        goto check_end;
                    }
                }
            }
            
            check_end:
            if (valid) {
                arr = test_arr;
                break;
            }
        }
    }
    while (arr.size() < sz) {
        arr.push_back(arr.back());
    }

    for (int i = 0; i < arr.size() - 1; i++) {
        cout << arr[i] << ' ';
    }
    cout << arr.back() << endl;
}
