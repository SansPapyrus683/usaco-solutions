#include <iostream>
#include <fstream>
#include <cassert>
#include <vector>
#include <algorithm>

using std::cout;
using std::endl;
using std::pair;

int main() {
    std::ifstream read("paint.in");
    pair<pair<int, int>, pair<int, int>> segs;
    read >> segs.first.first >> segs.first.second
         >> segs.second.first >> segs.second.second;
    assert(segs.first.second >= segs.first.first
            && segs.second.second >= segs.second.first);    
    
    if (segs.first > segs.second) {
        std::swap(segs.first, segs.second);
    }
    
    int painted;
    if (segs.first.second < segs.second.first) {
        int first = segs.first.second - segs.first.first;
        int second = segs.second.second - segs.second.first;
        painted = first + second;
    } else {
        int end = std::max(segs.first.second, segs.second.second);
        painted = end - segs.first.first;
    }
    std::ofstream("paint.out") << painted << endl;
    cout << painted << endl;
}
