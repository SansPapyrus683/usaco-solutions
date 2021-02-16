#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <queue>

using std::cout;
using std::endl;
using std::vector;

class DisjointSets {
    private:
        vector<int> parents;
        vector<int> sizes;
    public:
        DisjointSets(int size) : parents(size), sizes(size, 1) {
            for (int i = 0; i < size; i++) {
                parents[i] = i;
            }
        }

        int get_ultimate(int n) {
            return parents[n] == n ? n : (parents[n] = get_ultimate(parents[n]));
        }

        bool link(int n1, int n2) {
            n1 = get_ultimate(n1);
            n2 = get_ultimate(n2);
            if (n1 == n2) {
                return false;
            }
            if (sizes[n1] < sizes[n2]) {
                std::swap(n1, n2);
            }
            sizes[n1] += sizes[n2];
            parents[n2] = n1;
            return true;
        }
};

/**
 * 2016 feb gold
 * for some stupid reason prim's times out
 * so i just copied the official kruskal's sol lol
 */
int main() {
    std::ifstream read("fencedin.in");
    int vert_bound;
    int hor_bound;
    int vert_num;
    int hor_num;
    read >> vert_bound >> hor_bound >> vert_num >> hor_num;

    vector<int> vertical(vert_num + 1);
    
    for (int f = 0; f < vert_num; f++) {
        read >> vertical[f + 1];
    }
    std::sort(vertical.begin(), vertical.end());
    for (int v = 0; v < vert_num; v++) {
        vertical[v] = vertical[v + 1] - vertical[v];
    }
    vertical[vert_num] = vert_bound - vertical[vert_num];
    std::sort(vertical.begin(), vertical.end());  // doesn't matter what order the distances are in

    vector<int> horizontal(hor_num + 1);
    for (int f = 0; f < hor_num; f++) {
        read >> horizontal[f + 1];
    }    
    std::sort(horizontal.begin(), horizontal.end());
    for (int h = 0; h < hor_num; h++) {
        horizontal[h] = horizontal[h + 1] - horizontal[h];
    }
    horizontal[hor_num] = hor_bound - horizontal[hor_num];
    std::sort(horizontal.begin(), horizontal.end());

    long long min_removed = 0;
    /*
     * assign ids like so:
     * 5 6 7 8 9
     * 0 1 2 3 4 (starting from the bottom as you can see)
     */
    DisjointSets regions((vert_num + 1) * (hor_num + 1));
    int v_at = 0;
    int h_at = 0;
    while (v_at <= vert_num || h_at <= hor_num) {
        // start merging a bunch of the smaller edges first
        if (h_at == hor_num + 1 || (v_at <= vert_num && vertical[v_at] < horizontal[h_at])) {
            for (int y = 0; y < hor_num; y++) {
                int first = y * (vert_num + 1) + v_at;
                int second = (y + 1) * (vert_num + 1) + v_at;
                min_removed += regions.link(first, second) * vertical[v_at];
            }
            v_at++;
        } else {
            for (int x = 0; x < vert_num; x++) {
                int first = h_at * (vert_num + 1) + x;
                int second = h_at * (vert_num + 1) + x + 1;
                min_removed += regions.link(first, second) * horizontal[h_at];
            }
            h_at++;
        }
    }
    std::ofstream("fencedin.out") << min_removed << endl;
    cout << min_removed << endl;
}
