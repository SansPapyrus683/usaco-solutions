#include <iostream>
#include <cassert>
#include <vector>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;

constexpr int MAX_COWS = 5e6;

/** 2022 dec silver (sample input omitted due to length) */
int main() {
    vector<bool> is_prime(MAX_COWS + 1, true);
    is_prime[0] = false;  // for purposes of this problem, 1 can be a prime
    vector<int> last_mod{-1, 1, -1, -1};
    vector<int> fj_pick{-1, 1};
    for (int i = 2; i <= MAX_COWS; i++) {
        if (is_prime[i]) {
            last_mod[i % 4] = i;
            fj_pick.push_back(i);
            for (int j = 2 * i; j <= MAX_COWS; j += i) {
                is_prime[j] = false;
            }
        } else {
            fj_pick.push_back(last_mod[i % 4]);
        }
    }

    int test_num;
    std::cin >> test_num;
    for (int t = 0; t < test_num; t++) {
        int room_num;
        std::cin >> room_num;
        vector<int> rooms(room_num);
        for (int& r : rooms) {
            std::cin >> r;
            assert(1 <= r && r <= MAX_COWS);
        }

        vector<int> win_time(room_num);
        int earlist_win = INT32_MAX;
        for (int i = 0; i < room_num; i++) {
            int r = rooms[i];
            if (r % 4 == 0) {
                win_time[i] = -(r / 4);  // negative means fn wins
            } else {
                win_time[i] = (r - fj_pick[r]) / 4;
            }
            earlist_win = std::min(earlist_win, abs(win_time[i]));
        }

        for (int i = 0; i < room_num; i++) {
            int time = win_time[i];
            if (abs(time) == earlist_win) {
                cout << (time >= 0 ? "Farmer John" : "Farmer Nhoj") << '\n';
                break;
            }
        }
    }
}
