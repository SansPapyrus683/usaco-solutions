#include <iostream>
#include <cassert>
#include <set>
#include <vector>

using std::cout;
using std::endl;
using std::vector;
using std::set;

class DisjointSets {
    public:
        vector<int> parents;
        vector<set<int>> members;
        vector<int> sizes;
        vector<bool> rel;
    public:
        DisjointSets(int size) : sizes(size, 1), rel(size, false) {
            for (int i = 0; i < size; i++) {
                parents.push_back(i);
                members.push_back({i});
            }
        }
 
        int get_ultimate(int n) {
            return parents[n] == n ? n : get_ultimate(parents[n]);
        }
 
        set<int> link(int n1, int n2) {
            n1 = get_ultimate(n1);
            n2 = get_ultimate(n2);
            if (n1 == n2) {
                return {};
            }
            if (sizes[n1] < sizes[n2]) {
                return link(n2, n1);
            }
            members[n1].insert(members[n2].begin(), members[n2].end());
            members[n2] = {};
            sizes[n1] += sizes[n2];
            parents[n2] = n1;

            rel[n1] = rel[n1] || rel[n2];
            if (rel[n1]) {
                set<int> ret(members[n1]);
                members[n1] = {};
                return ret;
            }
            return {};
        }

        set<int> activate(int i) {
            int root = get_ultimate(i);
            set<int> ret(members[root]);
            members[root] = {};
            rel[root] = true;
            return ret;
        } 
};

/**
 * 2022 jan gold
 * (input omitted due to length)
 */
int main() {
    int farm_num;
    int query_num;
    std::cin >> farm_num >> query_num;
    
    struct Query {
        char type;
        int arg1, arg2;
    };
    vector<Query> queries(query_num);
    vector<std::pair<int, int>> edges;
    vector<bool> exists;
    vector<bool> activated(farm_num, true);
    for (Query& q : queries) {
        std::cin >> q.type;
        if (q.type == 'A') {
            std::cin >> q.arg1 >> q.arg2;
            edges.push_back({--q.arg1, --q.arg2});
            assert(activated[q.arg1] && activated[q.arg2]);
            exists.push_back(true);
        } else if (q.type == 'D') {
            std::cin >> q.arg1;
            activated[--q.arg1] = false;
        } else if (q.type == 'R') {
            std::cin >> q.arg1;
            exists[--q.arg1] = false;
        }
    }

    DisjointSets farms(farm_num);
    for (int e = 0; e < edges.size(); e++) {
        if (exists[e]) {
            farms.link(edges[e].first, edges[e].second);
        }
    }
    vector<int> latest_relevance(farm_num);
    for (int f = 0; f < farm_num; f++) {
        if (activated[f]) {
            for (int rf : farms.activate(f)) {
                latest_relevance[rf] = query_num;
            }
        }
    }

    for (int qi = query_num - 1; qi >= 0; qi--) {
        const Query& q = queries[qi];
        set<int> rel = {};
        if (q.type == 'D') {
            rel = farms.activate(q.arg1);
        } else if (q.type == 'R') {
            rel = farms.link(edges[q.arg1].first, edges[q.arg1].second);
        }

        for (int f : rel) {
            latest_relevance[f] = qi;
        }
    }
    
    for (int lr : latest_relevance) {
        cout << lr << endl;
    }
}
