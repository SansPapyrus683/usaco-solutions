#include <iostream>
#include <fstream>
#include <vector>
#include <deque>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;

// assumes like all the nodes are just from 0 to neighbors.size() - 1
bool all_reachable(int start, vector<vector<int>> neighbors) {
    std::deque<int> frontier;
    frontier.push_back(start);
    vector<bool> visited(neighbors.size());
    visited[start] = true;
    while (!frontier.empty()) {
        int curr = frontier[0];
        frontier.pop_front();
        for (int n : neighbors[curr]) {
            if (!visited[n]) {
                visited[n] = true;
                frontier.push_back(n);
            }
        }
    }
    return std::find(visited.begin(), visited.end(), false) == visited.end();
}

// 2019 us open bronze (screw it, i'm using c++, i need more practice with it anyways)
int main() {
    std::ifstream read("factory.in");
    int station_num;
    read >> station_num;
    vector<vector<int>> neighbors(station_num);
    for (int i = 0; i < station_num - 1; i++) {
        int from;
        int to;
        read >> from >> to;
        /*
         * the problem wants a station so that you can get to it from any other one
         * because that's dumb, we make the edges in reverse and instead 
         * find a station that can reach any other one
         */
        neighbors[to - 1].push_back(from - 1);  // make the stations 0-indexed
    }

    int valid = -1;
    for (int s = 0; s < station_num; s++) {
        if (all_reachable(s, neighbors)) {
            valid = s + 1;  // convert back to 1-indexing
            break;
        }
    }
    std::ofstream("factory.out") << valid << endl;
    cout << valid << endl;
}
