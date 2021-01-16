#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <stdexcept>
#include <algorithm>

using std::cout;
using std::endl;
using std::vector;
using std::set;

struct IDSquare {
    int x;
    int y;
    int width;
    int id;
    
    IDSquare(int x, int y, int width, int id) : x(x), y(y), width(width), id(id) {
        if (width % 2 != 0) {
            throw std::invalid_argument("sorry man i can only support even widths");
        }
    }

    bool overlaps(const IDSquare other) const {  // i swear i will never get how the const keyword works
        int this_half = width / 2;
        int other_half = other.width / 2;
        return x - this_half < other.x + other_half && x + this_half > other.x - other_half && 
               y - this_half < other.y + other_half && y + this_half > other.y - other_half;
    }

    bool operator==(const IDSquare& other) const {
        return id == other.id; 
    }

    bool operator<(const IDSquare& other) const {  // order the squares themsevles by y-coordinate
        return y != other.y ? y < other.y : x < other.x;
    }
};



// just for my own debugging purposes, i'm still going to leave it here
std::ostream& operator<<(std::ostream& stream, IDSquare const& s) { 
  stream << "(" << s.x << ", " << s.y << ")";
  return stream;
}

struct Point {
    int x;
    int y;
    Point(int x, int y) : x(x), y(y) { }
};

// copied from https://www.geeksforgeeks.org/total-area-two-overlapping-rectangles/
long long overlapping_area(Point l1, Point r1, Point l2, Point r2) {
    int x_dist = std::min(r1.x, r2.x) - std::max(l1.x, l2.x);
    int y_dist = (std::min(r1.y, r2.y) - std::max(l1.y, l2.y));
    int area = 0;
    if (x_dist > 0 && y_dist > 0) {
        area = x_dist * y_dist;
    }
    return area;
}
 

// 2013 jan silver
// i would've used java but it can't do iterators like c++ so (also sorry for the long-butt file)
int main() {
    std::ifstream read("squares.in");
    int square_num;
    int width;
    read >> square_num >> width;
    vector<IDSquare> squares;
    vector<std::pair<int, int>> points;  // the points that we care about for the line sweep
    for (int s = 0; s < square_num; s++) {
        int x;
        int y;
        read >> x >> y;
        squares.push_back(IDSquare(x, y, width, s));
        points.push_back({x - width / 2, s});
        points.push_back({x + width / 2, s});
    }
    std::sort(points.begin(), points.end());  // order the starting/ending points by x coordinate

    vector<std::pair<IDSquare, IDSquare>> overlapping;    
    set<IDSquare> relevant;
    for (std::pair<int, int> p : points) {
        IDSquare square = squares[p.second];
        set<IDSquare>::iterator presence = relevant.find(square);

        if (presence != relevant.end()) {  // oh, this is the end of a square, let's do erase it
            relevant.erase(presence);
        } else {  // a new one- let's compare it with the ones directly above and below
            set<IDSquare>::iterator higher = relevant.lower_bound(square);
            while (overlapping.size() < 3 && higher != relevant.end() && square.overlaps(*higher)) {
                overlapping.push_back({square, *higher});
                higher++;
            }

            set<IDSquare>::iterator lower = relevant.lower_bound(square);
            if (overlapping.size() < 3 && lower != relevant.begin()) {
                while (lower != relevant.begin() && square.overlaps(*(--lower))) {
                    overlapping.push_back({square, *lower});
                }
            }
            relevant.insert(square);
        }
    }

    // there might be duplicates? idk just make sure
    for (std::pair<IDSquare, IDSquare>& p : overlapping) {
        if (p.second < p.first) {
            std::swap(p.first, p.second);
        }
    }
    std::sort(overlapping.begin(), overlapping.end());  // apparently unique assumes it's sorted alr
    overlapping.erase(std::unique(overlapping.begin(), overlapping.end()), overlapping.end());

    long long inter_area;
    if (overlapping.size() >= 2) {
        inter_area = -1;
    } else {
        inter_area = 0;
        int half_width = width / 2;
        for (std::pair<IDSquare, IDSquare> p : overlapping) {
            inter_area += overlapping_area(Point(p.first.x - half_width, p.first.y - half_width), 
                                           Point(p.first.x + half_width, p.first.y + half_width), 
                                           Point(p.second.x - half_width, p.second.y - half_width), 
                                           Point(p.second.x + half_width, p.second.y + half_width));
        }
    }
    std::ofstream written("squares.out");
    written << inter_area << endl;
    cout << inter_area << endl;
}
