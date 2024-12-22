/*
ID: kevinsh4
TASK: telecow
LANG: C++
*/
#include <algorithm>
#include <cstdint>
#include <fstream>
#include <iostream>
#include <map>
#include <vector>

using std::cout;
using std::endl;
using std::vector;

/**
 * https://github.com/kth-competitive-programming/kactl/blob/main/content/graph/Dinic.h
 * yeah even idk how the hell this stupid struct works
 */
struct Dinic {
    struct Edge {
        int to, rev;
        int c, oc;
    };
    vector<int> lvl, ptr, q;
    vector<vector<Edge>> adj;
    
    Dinic(int n) : lvl(n), ptr(n), q(n), adj(n) {}
    
    void add_edge(int a, int b, int c, int rcap = 0) {
        adj[a].push_back({b, (int)adj[b].size(), c, c});
        adj[b].push_back({a, (int)adj[a].size() - 1, rcap, rcap});
    }

    int dfs(int v, int t, int f) {
        if (v == t || !f) return f;
        for (int& i = ptr[v]; i < adj[v].size(); i++) {
            Edge& e = adj[v][i];
            if (lvl[e.to] == lvl[v] + 1)
                if (int p = dfs(e.to, t, std::min(f, e.c))) {
                    e.c -= p, adj[e.to][e.rev].c += p;
                    return p;
                }
        }
        return 0;
    }

    int calc(int s, int t) {
        int flow = 0;
        q[0] = s;
        for (int L = 0; L < 31; L++) {
            do {
                lvl = ptr = vector<int>(q.size());
                int qi = 0, qe = lvl[s] = 1;
                while (qi < qe && !lvl[t]) {
                    int v = q[qi++];
                    for (Edge e : adj[v])
                        if (!lvl[e.to] && e.c >> (30 - L))
                            q[qe++] = e.to, lvl[e.to] = lvl[v] + 1;
                }
                while (int p = dfs(s, t, INT32_MAX)) flow += p;
            } while (lvl[t]);
        }
        return flow;
    }
};

int min_nodes(const vector<vector<int>>& neighbors, int src, int dest) {
    Dinic flow_graph(2 * neighbors.size());
    const int big = 2 * neighbors.size();
    for (int i = 0; i < neighbors.size(); i++) {
        flow_graph.add_edge(2 * i, 2 * i + 1, i == src || i == dest ? big : 1);
        for (int j : neighbors[i]) {
            flow_graph.add_edge(2 * i + 1, 2 * j, big);
            flow_graph.add_edge(2 * j + 1, 2 * i, big);
        }
    }
    return flow_graph.calc(2 * src, 2 * dest + 1);
}

int main() {
    std::ifstream read("telecow.in");
    int comp_num;
    int conn_num;
    int src, dest;
    read >> comp_num >> conn_num >> src >> dest;
    src--;
    dest--;
    vector<vector<int>> neighbors(comp_num);
    for (int c = 0; c < conn_num; c++) {
        int c1, c2;
        read >> c1 >> c2;
        neighbors[--c1].push_back(--c2);
        neighbors[c2].push_back(c1);
    }

    int flow = min_nodes(neighbors, src, dest);
    vector<int> to_remove;
    for (int bad = 0; bad < comp_num; bad++) {
        if (bad == src || bad == dest) {
            continue;
        }

        vector<vector<int>> without_c(neighbors);
        without_c[bad] = {};
        for (vector<int>& n_list : without_c) {
            auto it = std::find(n_list.begin(), n_list.end(), bad);
            if (it != n_list.end()) {
                n_list.erase(it);
            }
        }
        int new_flow = min_nodes(without_c, src, dest);
        if (new_flow < flow) {
            flow = new_flow;
            neighbors = without_c;
            to_remove.push_back(bad);
        }
    }

    std::ofstream written("telecow.out");
    written << to_remove.size() << '\n';
    for (int i = 0; i < to_remove.size(); i++) {
        written << to_remove[i] + 1 << " \n"[i == to_remove.size() - 1];
    }
}
