"""
ID: kevinsh4
TASK: ditch
LANG: PYTHON3
bruh moment
"""
from typing import List, Optional
from collections import deque


def directed_good_path(
        neighbors: List[List[int]], start: int = 0, end: Optional[int] = None
) -> List[int]:
    end = len(neighbors) - 1 if end is None else end
    came_from = {start: None}
    frontier = deque([start])
    while frontier:
        curr = frontier.pop()
        if curr == end:
            break

        for n, f in enumerate(neighbors[curr]):
            # if there's still some "good" flow left
            if n not in came_from and f > 0:
                frontier.append(n)
                came_from[n] = curr
    else:
        return []  # return empty list if no good path found
    
    full_path = [end]
    at = end
    while at != start:
        at = came_from[at]
        full_path.append(at)
    return full_path[::-1]


with open('ditch.in') as read:
    path_num, int_num = [int(i) for i in read.readline().split()]
    graph = [[0 for _ in range(int_num)] for _ in range(int_num)]
    for _ in range(path_num):
        start, end, flow = [int(i) for i in read.readline().split()]
        # += for possibly multiple pipes from same locations
        graph[start - 1][end - 1] += flow

max_flow = 0
while True:
    path = directed_good_path(graph)
    if not path:  # frick it, no more good paths
        break
    flow = float('inf')
    for v, p in enumerate(path[:-1]):  # calc the flow for this path
        flow = min(flow, graph[p][path[v + 1]])
    max_flow += flow
    # update the graph with the new flow
    for v, p in enumerate(path[:-1]):
        graph[p][path[v + 1]] -= flow
        # no idea why i have to do the reverse, i'll look into it later lol
        graph[path[v + 1]][p] += flow

print(max_flow)
with open('ditch.out', 'w') as written:
    written.write(str(max_flow) + '\n')
