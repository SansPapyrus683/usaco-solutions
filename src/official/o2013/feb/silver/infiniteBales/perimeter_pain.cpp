#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <deque>

using std::cout;
using std::endl;
using std::vector;
using std::pair;

vector<pair<int, int>> neighbors4(const pair<int, int>& curr) {
    return {
        {curr.first + 1, curr.second},
        {curr.first - 1, curr.second},
        {curr.first, curr.second + 1},
        {curr.first, curr.second - 1}
    };
}

vector<pair<int, int>> neighbors8(const pair<int, int>& curr) {
    vector<pair<int, int>> neighbors = neighbors4(curr);
    neighbors.push_back({curr.first + 1, curr.second + 1});
    neighbors.push_back({curr.first + 1, curr.second - 1});
    neighbors.push_back({curr.first - 1, curr.second + 1});
    neighbors.push_back({curr.first - 1, curr.second - 1});
    return neighbors;
}

// 2013 feb silver
int main() {
    std::ifstream read("perimeter.in");
    int bale_num;
    read >> bale_num;
    int min_x = INT32_MAX;
    std::set<pair<int, int>> bales;
    for (int b = 0; b < bale_num; b++) {
        pair<int, int> bale;
        read >> bale.first >> bale.second;
        bales.insert(bale);
        min_x = std::min(min_x, bale.first);
    }
    
    // get one of the leftmost points and subtract one from it
    // that point will definitely be outside the bales
    pair<int, int> start{-1, -1};
    for (const pair<int, int>& b : bales) {
        if (b.first == min_x) {
            start = {b.first - 1, b.second};
            break;
        }
    }
    
    int perimeter = 0;
    std::deque<pair<int, int>> frontier{start};
    std::set<pair<int, int>> visited{start};
    // exectue a bfs from the point, making sure to stay close to the bales
    while (!frontier.empty()) {
        pair<int, int> curr = frontier.front();
        frontier.pop_front();
        for (pair<int, int> n : neighbors4(curr)) {
            if (visited.count(n)) {
                continue;
            }
            bool alone = true;
            for (pair<int, int> nn : neighbors8(n)) {
                if (bales.count(nn)) {
                    alone = false;
                    break;
                }
            }
            if (alone) {
                continue;
            }
            if (bales.count(n)) {
                perimeter++;
                continue;
            }
            visited.insert(n);
            frontier.push_back(n);
        }
    }
    std::ofstream("perimeter.out") << perimeter << endl;
    cout << perimeter << endl;
}
