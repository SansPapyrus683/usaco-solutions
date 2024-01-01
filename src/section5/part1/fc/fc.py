"""
ID: kevinsh4
TASK: fc
LANG: PYTHON3
"""
from math import sqrt
from dataclasses import dataclass


@dataclass
class Point:
    x: float
    y: float

    def dist(self, p: "Point") -> float:
        return sqrt((p.x - self.x) ** 2 + (p.y - self.y) ** 2)


def orientation(p: int, q: int, r: int) -> int:
    val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y)
    if val == 0:
        return 0
    elif val > 0:
        return 1
    return 2


def convex_hull(points: list[Point]) -> list[Point] | None:
    """
    https://www.geeksforgeeks.org/convex-hull-algorithm/
    i used the wrapping one- the dnc one is supposed to be faster but whatever
    """
    if len(points) < 3:
        return

    start = 0
    for i in range(1, len(points)):
        if points[i].x < points[start].x:
            start = i
        elif points[i].x == points[start].x:
            if points[i].y > points[start].y:
                start = i

    hull = []
    p = start
    q = 0
    while True:
        hull.append(p)
        q = (p + 1) % len(points)
        for i in range(len(points)):
            if orientation(points[p], points[i], points[q]) == 2:
                q = i

        p = q
        if p == start:
            break

    return [points[i] for i in hull]


with open("fc.in") as read:
    cows = []
    for _ in range(int(read.readline())):
        cows.append(Point(*[float(i) for i in read.readline().split()]))

border = convex_hull(cows)
fence_len = 0
for i in range(len(border)):
    fence_len += border[i].dist(border[(i + 1) % len(border)])

print(round(fence_len, 2), file=open("fc.out", "w"))
