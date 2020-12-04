"""
here have a python implementation
no reason whatsoever i just fel like doing it in python for once
this folllows basically the exact same logic as the java implementation
so all the code comments are over there
(2019 dec gold)
"""
from heapq import heappush


def minCostWithFlow(flowReq, neighbors, start=0, end=-1):
    end = len(neighbors) - 1 if end == -1 else end

    forwardCosts = [float('inf') for _ in range(len(neighbors))]
    forwardCosts[0] = 0
    frontier = [[0, start]]
    while frontier:
        curr = frontier.pop(0)[1]
        rnCost = forwardCosts[curr]
        for n in neighbors[curr]:
            if rnCost + n[1] < forwardCosts[n[0]] and n[2] >= flowReq:
                forwardCosts[n[0]] = rnCost + n[1]
                heappush(frontier, [forwardCosts[n[0]], n[0]])
    
    backwardCosts = [float('inf') for _ in range(len(neighbors))]
    backwardCosts[-1] = 0
    frontier = [[0, end]]
    while frontier:
        curr = frontier.pop(0)[1]
        rnCost = backwardCosts[curr]
        for n in neighbors[curr]:
            if rnCost + n[1] < backwardCosts[n[0]] and n[2] >= flowReq:
                backwardCosts[n[0]] = rnCost + n[1]
                heappush(frontier, [backwardCosts[n[0]], n[0]])
    
    minCost = float('inf')
    for j, nList in enumerate(neighbors):
        for n in nList:
            if n[2] == flowReq:
                minCost = min(minCost, forwardCosts[j] + n[1] + backwardCosts[n[0]])
    return minCost


with open('pump.in') as read:
    # junctions are points, pipes are edges
    junctionNum, pipeNum = [int(i) for i in read.readline().split()]
    adjJunctions = [[] for _ in range(junctionNum)]
    flowRates = set()
    for _ in range(pipeNum):
        pipe = [int(i) for i in read.readline().split()]
        pipe[0] -= 1
        pipe[1] -= 1
        adjJunctions[pipe[0]].append(pipe[1:])  # note: neighbor, followed by the cost & flow rate
        adjJunctions[pipe[1]].append(pipe[:1] + pipe[2:])
        flowRates.add(pipe[3])

bestScore = 0
for r in flowRates:
    bestScore = max(bestScore, r / minCostWithFlow(r, adjJunctions))
print(bestScore)
with open('pump.out', 'w') as written:
    written.write(str(int(bestScore * 10 ** 6)) + '\n')
