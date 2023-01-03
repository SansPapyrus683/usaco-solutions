#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;

constexpr char BAD = 'X';

// 2016 jan plat
int main() {
    std::ifstream read("fortmoo.in");
    int row_num;
    int col_num;
    read >> row_num >> col_num;

    vector<vector<bool>> sturdy(row_num, vector<bool>(col_num));
    vector<vector<int>> row_bad_nums(row_num, vector<int>(col_num + 1));
    for (int r = 0; r < row_num; r++) {
        for (int c = 0; c < col_num; c++) {
            char cell;
            read >> cell;
            sturdy[r][c] = cell != BAD;
            row_bad_nums[r][c + 1] += row_bad_nums[r][c] + (cell == BAD);
        }
    }

    int max_area = 0;
    for (int c_start = 0; c_start < col_num; c_start++) {
        for (int c_end = c_start + 1; c_end < col_num; c_end++) {
            int r_start = 0;
            for (int r_end = 0; r_end < row_num; r_end++) {
                bool rowValid = row_bad_nums[r_end][c_end + 1] - row_bad_nums[r_end][c_start] == 0;
                if (r_end == r_start && !rowValid) {
                    r_start++;
                    continue;
                }
                if (!sturdy[r_end][c_start] || !sturdy[r_end][c_end]) {
                    r_start = r_end + 1;
                } else if (rowValid && r_start != r_end) {
                    max_area = std::max(max_area, (c_end - c_start + 1) * (r_end - r_start + 1));
                }
            }
        }
    }
    cout << max_area << endl;
    std::ofstream("fortmoo.out") << max_area << endl;
}
