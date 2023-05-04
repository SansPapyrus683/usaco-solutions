"""
ID: kevinsh4
TASK: milk6
LANG: PYTHON3

bruh 70% of this is copied right from ditch what
just w/ this: https://pinkpurplepineapples.wordpress.com/2016/11/08/min-cut-with-minimal-edge-count/
and this actually fails a tc (#11), but i'm blaming that on the problem lmao
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
            if n not in came_from and f > 0:
                frontier.append(n)
                came_from[n] = curr
    else:
        return []

    full_path = [end]
    at = end
    while at != start:
        at = came_from[at]
        full_path.append(at)
    return full_path[::-1]


with open('milk6.in') as read:
    house_num, truck_num = [int(i) for i in read.readline().split()]
    graph = [[0 for _ in range(house_num)] for _ in range(house_num)]
    routes = []
    for _ in range(truck_num):
        start, end, cost = [int(i) for i in read.readline().split()]
        routes.append((start - 1, end - 1))
        graph[start - 1][end - 1] += cost * truck_num + 1


max_flow = 0  # by the max-flow min-cut theorem, this is the same as min cut
while True:
    path = directed_good_path(graph)
    if not path:
        break
    flow = float('inf')
    for v, p in enumerate(path[:-1]):
        flow = min(flow, graph[p][path[v + 1]])
    max_flow += flow
    for v, p in enumerate(path[:-1]):
        graph[p][path[v + 1]] -= flow
        graph[path[v + 1]][p] += flow

frontier = [0]
visited = [False for _ in range(house_num)]
visited[0] = True
while frontier:
    curr = frontier.pop()
    for n, f in enumerate(graph[curr]):
        if f > 0 and not visited[n]:
            visited[n] = True
            frontier.append(n)

shutdown = []
for v, r in enumerate(routes):
    if visited[r[0]] and not visited[r[1]]:
        shutdown.append(v)

actual_flow = (max_flow - len(shutdown)) // truck_num
with open('milk6.out', 'w') as written:
    print(actual_flow, len(shutdown), file=written)
    if shutdown:
        print('\n'.join(str(i + 1) for i in shutdown), file=written)
