#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <cassert>

using std::cout;
using std::endl;
using std::vector;

/** 2020 feb bronze */
int main() {
    std::ifstream read("breedflip.in");
    int cow_num;
    read >> cow_num;
    std::string needed, have_rn;
    read >> needed >> have_rn;
    assert(needed.size() == cow_num && have_rn.size() == cow_num);

    vector<bool> is_diff(cow_num);
    for (int c = 0; c < cow_num; c++) {
        is_diff[c] = needed[c] != have_rn[c];
    }

    int consec_num = 0;
    for (int i = 1; i < cow_num; i++) {
        consec_num += !is_diff[i] && is_diff[i - 1];
    }
    consec_num += is_diff.back();

    std::ofstream("breedflip.out") << consec_num << endl;
}
