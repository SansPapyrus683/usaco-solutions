#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <algorithm>
#include <limits>

using std::cout;
using std::endl;

// 2017 dec gold
int main() {
    std::ifstream read("hayfeast.in");
    int plate_num;
    long long flavor_req;
    read >> plate_num >> flavor_req;
    std::vector<int> flavors(plate_num);
    std::vector<int> spiciness(plate_num);
    for (int p = 0; p < plate_num; p++) {
        read >> flavors[p] >> spiciness[p];
    }

    // frick java for not having a multiset class
    std::multiset<int> plates;
    int closest_left = 0;
    long long flavor_sum = 0;
    int min_spiciness = std::numeric_limits<int>::max();
    for (int right = 0; right < plate_num; right++) {
        // add the new plate that just got incremented
        flavor_sum += flavors[right];
        plates.insert(spiciness[right]);

        // move the left pointer to the right so we have as small a set as possible
        while (closest_left <= right
                && flavor_sum - flavors[closest_left] >= flavor_req) {
            flavor_sum -= flavors[closest_left];
            plates.erase(plates.find(spiciness[closest_left]));
            closest_left++;
        }

        if (flavor_sum >= flavor_req) {
            min_spiciness = std::min(min_spiciness, *plates.rbegin());
        }
    }

    std::ofstream("hayfeast.out") << min_spiciness << endl;
    cout << min_spiciness << endl;
}
