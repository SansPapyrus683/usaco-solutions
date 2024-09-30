#include <algorithm>
#include <ext/pb_ds/assoc_container.hpp>
#include <iostream>
#include <map>
#include <vector>
using namespace __gnu_pbds;
template <class T>
using Tree =
    tree<T, null_type, std::less<T>, rb_tree_tag, tree_order_statistics_node_update>;

using std::cout;
using std::endl;
using std::map;
using std::pair;
using std::vector;

struct Cultivar {
    int id;
    int from;
    int to;
    int req_len;
};

/** 2024 us open silver */
int main() {
    int cultivar_num;
    std::cin >> cultivar_num;
    vector<Cultivar> cultivars(cultivar_num);
    map<int, vector<Cultivar>, std::greater<int>> by_len;
    map<int, vector<Cultivar>> events;
    for (int i = 0; i < cultivar_num; i++) {
        Cultivar& c = cultivars[i];
        c.id = i;
        std::cin >> c.from >> c.to >> c.req_len;
        by_len[c.to - c.from].push_back(c);
        events[c.from].push_back(c);
        events[c.to].push_back(c);
    }

    Tree<pair<int, int>> active;
    vector<int> out_num(cultivar_num);
    for (const auto& [pos, e_list] : events) {
        for (const Cultivar& e : e_list) {
            if (pos == e.to) {
                active.erase({e.from, e.id});
            }
        }
        for (const Cultivar& e : e_list) {
            if (pos == e.from) {
                active.insert({e.from, e.id});
            } else if (pos == e.to) {
                out_num[e.id] = active.order_of_key({e.from, 0});
            }
        }
    }

    /*
     * ok i just took a look at the official editorial and
     * this sol could have been 10x shorter if i did complementary counting instead
     * eh, it is what it is
     */
    Tree<pair<int, int>> start, end;
    std::sort(
        cultivars.begin(), cultivars.end(),
        [&](const Cultivar& a, const Cultivar& b) { return a.req_len > b.req_len; });
    int at = 0;
    vector<int> overlap_num(cultivar_num);
    for (const auto& [len, now_valid] : by_len) {
        for (const Cultivar& c : now_valid) {
            start.insert({c.from, c.id});
            end.insert({c.to, c.id});
        }

        const int tot = start.size();
        auto next = by_len.upper_bound(len);
        int next_len = next == by_len.end() ? INT32_MIN : next->first;
        while (at < cultivar_num && cultivars[at].req_len > next_len) {
            const Cultivar& c = cultivars[at];
            int mid_start = tot - start.order_of_key({c.from, 0}) - 1;
            int mid_end = end.order_of_key({c.to, INT32_MAX}) - 1;
            int contain_num = mid_start + mid_end + out_num[c.id] - (tot - 1);

            pair<int, int> thresh = {c.to - c.req_len, INT32_MAX};
            int start_good =
                start.order_of_key(thresh) - start.order_of_key({c.from, 0}) - 1;
            thresh = {c.from + c.req_len, 0};
            int end_good = end.order_of_key({c.to, INT32_MAX}) - end.order_of_key(thresh) - 1;

            overlap_num[c.id] = out_num[c.id] + start_good + end_good - contain_num;
            at++;
        }
    }

    for (int n : overlap_num) {
        cout << n << '\n';
    }
}
