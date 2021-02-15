"""
ID: kevinsh4
TASK: ditch
LANG: PYTHON3
"""
from typing import List


def directed_good_path(neighbors: List[List[int]], start=0, end=None) -> List[int]:
    end = len(neighbors) - 1 if end is None else end
    came_from = {start: None}
    frontier = [start]
    while frontier:
        current = frontier.pop(0)
        if current == end:
            break

        for n, f in enumerate(neighbors[current]):
            if n not in came_from and f > 0:  # if there's still some "good" flow left
                frontier.append(n)
                came_from[n] = current
    else:
        return []  # return empty list if no good path found
    
    full_path = [end]
    at = end
    while at != start:
        at = came_from[at]
        full_path.append(at)
    return full_path[::-1]


with open('ditch.in') as read:
    path_num, intersection_num = [int(i) for i in read.readline().split()]
    inter_neighbors = [[0 for _ in range(intersection_num)] for _ in range(intersection_num)]
    for _ in range(path_num):
        path = [int(i) for i in read.readline().split()]
        inter_neighbors[path[0] - 1][path[1] - 1] += path[2]  # += for possibly multiple pipes from same locations

max_flow = 0
while True:
    path = directed_good_path(inter_neighbors)
    if not path:  # frick it, no more good paths
        break
    flow_amt = float('inf')
    for v, p in enumerate(path[:-1]):  # calc the flow for this path
        flow_amt = min(flow_amt, inter_neighbors[p][path[v + 1]])
    max_flow += flow_amt
    # update the graph with the new flow
    for v, p in enumerate(path[:-1]):
        inter_neighbors[p][path[v + 1]] -= flow_amt
        inter_neighbors[path[v + 1]][p] += flow_amt  # no idea why i have to do the reverse, i'll look into it later lol

print(max_flow)
with open('ditch.out', 'w') as written:
    written.write(str(max_flow) + '\n')
