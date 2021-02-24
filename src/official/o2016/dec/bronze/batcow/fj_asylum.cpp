#include <iostream>
#include <fstream>
#include <string>

using std::cout;
using std::endl;
using std::string;

// 2016 dec bronze
int main() {
    std::ifstream read("cowsignal.in");
    int row_num;
    int col_num;
    int factor;
    read >> row_num >> col_num >> factor;

    std::ofstream written("cowsignal.out");
    for (int r = 0; r < row_num; r++) {
        string curr_row = "";
        for (int c = 0; c < col_num; c++) {
            char cell;
            read >> cell;
            for (int i = 0; i < factor; i++) {
                curr_row += cell;
            }
        }
        for (int i = 0; i < factor; i++) {
            written << curr_row << endl;
            cout << curr_row << endl;
        }
    }
}
