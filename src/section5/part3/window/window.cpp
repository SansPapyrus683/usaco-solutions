/*
ID: kevinsh4
TASK: window
LANG: C++
*/
#include <algorithm>
#include <fstream>
#include <iomanip>
#include <iostream>
#include <map>
#include <set>
#include <sstream>
#include <string>
#include <vector>

using namespace std;  // i don't care.

struct Rect {
    int x1, y1;
    int x2, y2;

    Rect(int x1, int y1, int x2, int y2) : x1(x1), y1(y1), x2(x2), y2(y2) {
        if (this->x1 > this->x2) {
            swap(this->x1, this->x2);
        }
        if (this->y1 > this->y2) {
            swap(this->y1, this->y2);
        }
    }

    int area() const { return (x2 - x1) * (y2 - y1); }

    bool contains(const Rect& other) const {
        bool x_in = x1 <= other.x1 && other.x2 <= x2;
        bool y_in = y1 <= other.y1 && other.y2 <= y2;
        return x_in && y_in;
    }
};

int main() {
    ifstream read("window.in");
    ofstream written("window.out");
    written << fixed << setprecision(3);

    map<char, Rect> windows;
    vector<char> order;
    for (string s; getline(read, s);) {
        istringstream iss(s.substr(2, s.size() - 3));
        string token;
        getline(iss, token, ',');

        const char op = s[0];
        const char win_id = token[0];
        switch (op) {
            case 'w': {
                vector<int> dims;
                for (int i = 0; i < 4; i++) {
                    getline(iss, token, ',');
                    dims.push_back(stoi(token));
                }
                order.push_back(win_id);
                windows.insert({win_id, Rect(dims[0], dims[1], dims[2], dims[3])});
            } break;

            case 't':
                order.erase(remove(order.begin(), order.end(), win_id), order.end());
                order.push_back(win_id);
                break;

            case 'b':
                order.erase(remove(order.begin(), order.end(), win_id), order.end());
                order.insert(order.begin(), win_id);
                break;

            case 'd':
                order.erase(remove(order.begin(), order.end(), win_id), order.end());
                windows.erase(win_id);
                break;

            case 's':
                // idc i'm recalculating this each time screw you
                set<int> rel_x, rel_y;
                for (const auto& [wid, rect] : windows) {
                    rel_x.insert(rect.x1);
                    rel_x.insert(rect.x2);
                    rel_y.insert(rect.y1);
                    rel_y.insert(rect.y2);
                }
                vector<int> x(rel_x.begin(), rel_x.end());
                vector<int> y(rel_y.begin(), rel_y.end());

                int visible = 0;
                for (int i = 0; i < x.size() - 1; i++) {
                    for (int j = 0; j < y.size() - 1; j++) {
                        const Rect rect(x[i], y[j], x[i + 1], y[j + 1]);
                        for (auto it = order.rbegin(); it != order.rend(); it++) {
                            if (windows.at(*it).contains(rect)) {
                                visible += (*it == win_id) * rect.area();
                                break;
                            }
                        }
                    }
                }

                double ratio = (double)visible / windows.at(win_id).area() * 100;
                written << ratio << '\n';

                break;
        }
    }
}
