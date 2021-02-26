#include <iostream>
#include <fstream>
#include <vector>
#include <stack>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;
using std::pair;

// 2020 feb gold (this solution was more convoluted than i thought it would be)
int main() {
    std::ifstream read("timeline.in");
    int session_num;
    int total_days;
    int memory_num;
    read >> session_num >> total_days >> memory_num;

    vector<int> min_days(session_num);
    for (int s = 0; s < session_num; s++) {
        read >> min_days[s];
    }
    vector<vector<pair<int, int>>> sessions_after(session_num);
    for (int m = 0; m < memory_num; m++) {
        int before;
        int after;
        int difference;
        read >> before >> after >> difference;
        sessions_after[before - 1].push_back({after - 1, difference});
    }
    
    // sauce for toposort: https://stackoverflow.com/questions/20153488/topological-sort-using-dfs-without-recursion
    vector<bool> visited(session_num);
    vector<int> relative_order;
    for (int s = 0; s < session_num; s++) {
        if (visited[s]) {
            continue;
        }
        std::stack<pair<bool, int>> frontier;
        frontier.push({false, s});
        while (!frontier.empty()) {
            pair<bool, int> curr = frontier.top();
            frontier.pop();
            if (curr.first) {
                relative_order.push_back(curr.second);
                continue;
            }
            visited[curr.second] = true;
            frontier.push({true, curr.second});
            for (pair<int, int> after : sessions_after[curr.second]) {
                if (!visited[after.first]) {
                    frontier.push({false, after.first});
                }
            }
        }
    }
    std::reverse(relative_order.begin(), relative_order.end());

    for (int s : relative_order) {
        // given the date of the current one, let's see how this affects the ones it's before
        for (pair<int, int> after : sessions_after[s]) {
            min_days[after.first] = std::max(min_days[after.first], min_days[s] + after.second);
        }
    }

    std::ofstream written("timeline.out");
    for (int t : min_days) {
        written << t << endl;
        cout << t << endl;
    }
}
