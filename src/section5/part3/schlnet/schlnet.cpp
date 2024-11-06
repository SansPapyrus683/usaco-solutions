/*
ID: kevinsh4
TASK: schlnet
LANG: C++
*/
#include <algorithm>
#include <fstream>
#include <iostream>
#include <set>
#include <vector>

#if __has_include("debugging.hpp")
#include "debugging.hpp"
#endif

using std::cout;
using std::endl;
using std::vector;

/** https://usaco.guide/adv/SCC?lang=cpp#implementation-1 */
class TarjanSolver {
   private:
    vector<vector<int>> rev_adj;
    vector<int> post;
    vector<int> comp;

    vector<bool> visited;
    int timer = 0;
    int id = 0;

    void fill_post(int at) {
        visited[at] = true;
        for (int n : rev_adj[at]) {
            if (!visited[n]) {
                fill_post(n);
            }
        }
        post[at] = timer++;
    }

    void find_comp(int at) {
        visited[at] = true;
        comp[at] = id;
        for (int n : adj[at]) {
            if (!visited[n]) {
                find_comp(n);
            }
        }
    }

   public:
    const vector<vector<int>>& adj;
    TarjanSolver(const vector<vector<int>>& adj)
        : rev_adj(adj.size()),
          post(adj.size()),
          comp(adj.size()),
          visited(adj.size()),
          adj(adj) {
        vector<int> nodes(adj.size());
        for (int n = 0; n < adj.size(); n++) {
            nodes[n] = n;
            for (int next : adj[n]) {
                rev_adj[next].push_back(n);
            }
        }

        for (int n = 0; n < adj.size(); n++) {
            if (!visited[n]) {
                fill_post(n);
            }
        }
        std::sort(nodes.begin(), nodes.end(),
                  [&](int n1, int n2) { return post[n1] > post[n2]; });

        visited.assign(adj.size(), false);
        for (int n : nodes) {
            if (!visited[n]) {
                find_comp(n);
                id++;
            }
        }
    }

    int comp_num() const { return id; }

    int get_comp(int n) const { return comp[n]; }
};

int main() {
    std::ifstream read("schlnet.in");
    int school_num;
    read >> school_num;
    vector<vector<int>> send_to(school_num);
    for (int s = 0; s < school_num; s++) {
        int n;
        read >> n;
        while (n != 0) {
            send_to[s].push_back(--n);
            read >> n;
        }
    }

    // using tarjan for this is probably overkill but idc
    TarjanSolver solver(send_to);

    int min_copies = 1;
    int min_conns = 0;
    if (solver.comp_num() > 1) {
        vector<std::set<int>> comp_adj(solver.comp_num()), comp_radj(solver.comp_num());
        for (int s = 0; s < school_num; s++) {
            for (int n : send_to[s]) {
                comp_adj[solver.get_comp(s)].insert(solver.get_comp(n));
                comp_radj[solver.get_comp(n)].insert(solver.get_comp(s));
            }
        }
        for (int s = 0; s < school_num; s++) {
            comp_adj[solver.get_comp(s)].erase(solver.get_comp(s));
            comp_radj[solver.get_comp(s)].erase(solver.get_comp(s));
        }

        int outdeg0 = 0;
        for (const std::set<int>& adj : comp_adj) {
            outdeg0 += adj.size() == 0;
        }
        int indeg0 = 0;
        for (const std::set<int>& radj : comp_radj) {
            indeg0 += radj.size() == 0;
        }
        min_copies = indeg0;
        min_conns = std::max(outdeg0, indeg0);
    }

    cout << min_copies << '\n' << min_conns << endl;
    std::ofstream("schlnet.out") << min_copies << '\n' << min_conns << endl;
}
