"""
ID: kevinsh4
TASK: ditch
LANG: PYTHON3
"""
from typing import List


def directedGoodPath(neighbors: List[List[int]], start=0, end=None):
    end = len(neighbors) - 1 if end is None else end
    cameFrom = {start: None}
    frontier = [start]
    while frontier:
        current = frontier.pop(0)
        if current == end:
            break

        for n, f in enumerate(neighbors[current]):
            if n not in cameFrom and f > 0:  # if there's still some "good" flow left
                frontier.append(n)
                cameFrom[n] = current
    else:
        return []  # return empty list if no good path found
    
    fullPath = [end]
    at = end
    while at != start:
        at = cameFrom[at]
        fullPath.append(at)
    return fullPath[::-1]


with open('ditch.in') as read:
    pathNum, intersectionNum = [int(i) for i in read.readline().split()]
    interNeighbors = [[0 for _ in range(intersectionNum)] for _ in range(intersectionNum)]
    for _ in range(pathNum):
        path = [int(i) for i in read.readline().split()]
        interNeighbors[path[0] - 1][path[1] - 1] += path[2]  # += for possibly multiple pipes from same locations

maxFlow = 0
while True:
    path = directedGoodPath(interNeighbors)
    if not path:  # frick it, no more good paths
        break
    flowAmt = float('inf')
    for v, p in enumerate(path[:-1]):  # calc the flow for this path
        flowAmt = min(flowAmt, interNeighbors[p][path[v + 1]])
    maxFlow += flowAmt
    # update the graph with the new flow
    for v, p in enumerate(path[:-1]):
        interNeighbors[p][path[v + 1]] -= flowAmt
        interNeighbors[path[v + 1]][p] += flowAmt  # no idea why i have to do the reverse, i'll look into it later lol

print(maxFlow)
with open('ditch.out', 'w') as written:
    written.write(str(maxFlow) + '\n')
