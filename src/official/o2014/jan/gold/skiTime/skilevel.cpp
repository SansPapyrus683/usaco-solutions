#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;

class SizeWiseSets {
    private:
        vector<int> parents;
        vector<int> sizes;
        vector<std::set<int>> members;
        int size_req;
    public:
        SizeWiseSets(int size, int size_req)
                : parents(size), sizes(size, 1),
                  members(size), size_req(size_req) {
            for (int i = 0; i < size; i++) {
                parents[i] = i;
                members[i] = {i};
            }
        }

        int get_ultimate(int n) {
            return parents[n] == n ? n : (parents[n] = get_ultimate(parents[n]));
        }

        std::set<int> link(int n1, int n2) {
            n1 = get_ultimate(n1);
            n2 = get_ultimate(n2);
            if (n1 == n2) {
                return {};
            }
            if (sizes[n1] < sizes[n2]) {
                return link(n2, n1);
            }
            parents[n2] = n1;
            sizes[n1] += sizes[n2];
            members[n1].insert(members[n2].begin(), members[n2].end());
            members[n2] = {};
            if (sizes[n1] >= size_req) {
                std::set<int> to_return(members[n1]);
                members[n1] = {};
                return to_return;
            }
            return {};
        }
};

// 2014 jan gold
int main() {
    std::ifstream read("skilevel.in");
    int row_num;
    int col_num;
    int size_req;
    read >> row_num >> col_num >> size_req;
    vector<vector<int>> heights(row_num, vector<int>(col_num));
    for (int r = 0; r < row_num; r++) {
        for (int c = 0; c < col_num; c++) {
            read >> heights[r][c];
        }
    }
    vector<vector<int>> start(row_num, vector<int>(col_num));
    for (int r = 0; r < row_num; r++) {
        for (int c = 0; c < col_num; c++) {
            read >> start[r][c];
        }
    }

    vector<vector<int>> edges;
    for (int r = 0; r < row_num; r++) {
        for (int c = 0; c < col_num; c++) {
            if (r > 0) {
                edges.push_back({
                    std::abs(heights[r][c] - heights[r - 1][c]), r, c, r - 1, c
                });
            }
            if (c > 0) {
                edges.push_back({
                    std::abs(heights[r][c] - heights[r][c - 1]), r, c, r, c - 1
                });
            }
        }
    }
    std::sort(edges.begin(), edges.end());

    SizeWiseSets ski_course(row_num * col_num, size_req);
    long long total = 0;
    for (const vector<int>& e : edges) {
        for (int n : ski_course.link(e[1] * col_num + e[2], e[3] * col_num + e[4])) {
            total += e[0] * start[n / col_num][n % col_num];
        }
    }
    cout << total << endl;
    std::ofstream("skilevel.out") << total << endl;
}
