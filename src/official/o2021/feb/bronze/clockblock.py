"""
2021 feb bronze
2
NESW
WSSSEENWNEESSENNNNWWWS should output CW and CCW, each on a new line
"""
from typing import List

ORIENTATIONS = {
    'N': lambda x, y: (x, y + 1),
    'S': lambda x, y: (x, y - 1),
    'E': lambda x, y: (x + 1, y),
    'W': lambda x, y: (x - 1, y),
}


# sauce: https://stackoverflow.com/questions/1165647/how-to-determine-if-a-list-of-polygon-points-are-in-clockwise-order
def clockwise(point_order: List[List[int]]) -> bool:
    twice_signed_area = 0
    for p in range(len(point_order)):
        nxt = point_order[(p + 1) % len(point_order)]
        twice_signed_area += (nxt[0] - point_order[p][0]) * (nxt[1] + point_order[p][1])
    return twice_signed_area > 0


for _ in range(int(input())):
    curr = (0, 0)
    points = [curr]
    for m in input():
        curr = ORIENTATIONS[m.upper()](*curr)
        points.append(curr)
    assert points[0] == points[-1], 'you should end back up at the start'
    assert len(set(points)) + 1 == len(points), 'besides the start, no other point should be visited twice'
    print('CW' if clockwise(points) else 'CCW')
