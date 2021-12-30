#include <iostream>
#include <vector>
#include <set>
#include <map>

using std::cout;
using std::endl;
using std::vector;

/**
 * 2021 dec gold
 * 5
 * 5 1 2 4 3
 * should output 0, 1, 1, 2, 1, and 0, each on a new line
 * no input validation because c++ isn't python
 */
int main() {
    int size;
    std::cin >> size;
    vector<int> perm(size);
    vector<int> ind(size + 1);
    for (int i = 0; i < size; i++) {
        std::cin >> perm[i];
        ind[perm[i]] = i;
    }

    vector<vector<int>> started_his(size + 1);
    std::set<int> added;
    for (int i : perm) {
        auto it = added.lower_bound(i);
        int start = it == added.begin() ? 0 : *(--it);
        started_his[start].push_back(ind[i]);
        added.insert(i);
    }

    vector<int> hilo_num;
    vector<int> lo;
    std::set<int> hi;
    // hi = true, lo = false
    std::map<int, bool> hilos{{-1, false}, {size + 1, true}};
    int curr_hilo = 0;
    for (int x = 0; x <= size; x++) {
        for (int h : started_his[x]) {
            hi.insert(h);
            auto it = hilos.lower_bound(h);
            if (!it->second && !(--it)->second) {
                curr_hilo++;
            }
            hilos[h] = true;
        }

        hilo_num.push_back(curr_hilo);

        if (x < size) {
            int changed = ind[x + 1];
            while (!lo.empty() && changed < lo.back()) {
                auto it = hilos.lower_bound(lo.back());
                if ((--it)->second) {
                    curr_hilo--;
                }
                hilos.erase(lo.back());
                lo.pop_back();
            }
            
            bool prev = (--hilos.lower_bound(changed))->second;
            bool next = (++hilos.lower_bound(changed))->second;
            curr_hilo += prev + !next;
            hi.erase(changed);
            lo.push_back(changed);
            hilos[changed] = false;
        }
    }

    for (int i : hilo_num) {
        cout << i << '\n';
    }
}
