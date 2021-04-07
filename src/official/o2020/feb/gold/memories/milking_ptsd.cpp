#include <iostream>
#include <fstream>
#include <cassert>
#include <vector>
#include <queue>
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
        // milking sessions are 1-indexed which is stupid imo
        sessions_after[before - 1].push_back({after - 1, difference});
    }

    // sauce for toposort: https://www.geeksforgeeks.org/topological-sorting-indegree-based-solution/
    vector<int> come_before(session_num);
    for (int s = 0; s < session_num; s++) {
        for (const pair<int, int>& after : sessions_after[s]) {
            come_before[after.first]++;
        }
    }
    std::queue<int> frontier;
    for (int s = 0; s < session_num; s++) {
        if (come_before[s] == 0) {
            frontier.push(s);
        }
    }
    
    int visited_num = 0;
    vector<int> relative_order;
    while (!frontier.empty()) {
        int curr = frontier.front();
        frontier.pop();
        relative_order.push_back(curr);
        visited_num++;
        for (const pair<int, int>& after : sessions_after[curr]) {
            if (--come_before[after.first] == 0) {
                frontier.push(after.first);
            }
        }
    }
    assert(visited_num == session_num);

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
