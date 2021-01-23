#include <iostream>
#include <fstream>
#include <vector>
#include <unordered_set>
#include <queue>  // queuwu what's this (just kill me alr)

using std::endl;
using std::cout;
using std::vector;
using std::unordered_set;

int barn_num;

bool connected(vector<unordered_set<int>> paths, unordered_set<int> barns) {
    int start = *barns.begin();
    vector<bool> visited(barn_num);
    visited[start] = true;
    std::queue<int> frontier;
    frontier.push(start);
    while (!frontier.empty()) {
        int curr = frontier.front();
        frontier.pop();
        for (int n : paths[curr]) {
            if (!visited[n]) {
                frontier.push(n);
                visited[n] = true;
            }
        }
    }

    for (int b : barns) {
        if (!visited[b]) {
            return false;
        }
    }
    return true;
}

/**
 * 2016 usopen silver
 * for some reason this fricking runs slower than the java code
 * but hey, what can i do about it
 * and it fails for 3 of the test cases
 * aaaaaaaaaaaaaaa
 */
int main() {
    std::ifstream read("closing.in");
    int path_num;
    read >> barn_num >> path_num;
    vector<unordered_set<int>> paths(barn_num);
    for (int p = 0; p < path_num; p++) {
        int from;
        int to;
        read >> from >> to;
        from--;
        to--;
        paths[from].insert(to);
        paths[to].insert(from);
    }

    std::ofstream written("closing.out");
    unordered_set<int> barns_left;
    for (int b = 0; b < barn_num; b++) {
        barns_left.insert(b);
    }
    for (int b = 0; b < barn_num; b++) {
        bool all_reachable = connected(paths, barns_left);
        written << (all_reachable ? "YES": "NO") << '\n';
        int to_remove;
        read >> to_remove;
        to_remove--;
        barns_left.erase(to_remove);
        for (unordered_set<int>& p : paths) {
            p.erase(to_remove);
        }
    }
}
