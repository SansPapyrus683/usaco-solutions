#include <algorithm>
#include <cassert>
#include <iostream>
#include <map>
#include <set>
#include <string>

using std::cout;
using std::endl;
using std::string;

bool is_moo(const string& str) {
    return str.size() == 3 && str[1] == str[2] && str[1] != str[0];
}

/** 2024 dec bronze */
int main() {
    int char_num;
    int at_least;
    string str;
    std::cin >> char_num >> at_least >> str;
    assert(str.size() == char_num);

    std::map<string, int> occs;
    for (int i = 0; i + 3 <= str.size(); i++) {
        occs[str.substr(i, 3)]++;
    }

    std::set<string> poss_moos;
    for (int p = 0; p < char_num; p++) {
        int start = std::max(p - 2, 0);
        int end = std::min(p, (int)str.length() - 3);
        for (int i = start; i <= end; i++) {
            occs[str.substr(i, 3)]--;
        }

        char og = str[p];
        for (char to = 'a'; to <= 'z'; to++) {
            str[p] = to;
            for (int i = start; i <= end; i++) {
                string sub = str.substr(i, 3);
                if (++occs[sub] >= at_least && is_moo(sub)) {
                    poss_moos.insert(sub);
                }
            }
            for (int i = start; i <= end; i++) {
                occs[str.substr(i, 3)]--;
            }
        }

        str[p] = og;
        for (int i = start; i <= end; i++) {
            occs[str.substr(i, 3)]++;
        }
    }

    cout << poss_moos.size() << '\n';
    for (const string& m : poss_moos) {
        cout << m << '\n';
    }
}
