"""
ID: kevinsh4
TASK: camelot
LANG: PYTHON3
"""
import time
from queue import Queue

# TODO: this one doesn't work but it's just here if you want the python version
timeStart = time.perf_counter()
with open('camelot.in') as read:
    knightPos = []
    for v, l in enumerate(read):
        if v == 0:
            rows, cols = [int(i) for i in l.split()]
        else:
            l = l.split()
            for i in range(len(l) // 2):  # don't worry, i'll extract the king later
                knightPos.append(l[2 * i: 2 * i + 2])

for v, p in enumerate(knightPos):
    knightPos[v] = (ord(p[0]) - 65, int(p[1]) - 1)  # zero based indexing for the win bois
kingPos = knightPos.pop(0)
distances = {(c, r): {(c_, r_, i): float('inf') for c_ in range(cols) for r_ in range(rows) for i in [True, False]}
             for c in range(cols) for r in range(rows)}
cachedNeighbors = {}
cachedKing = {}


def kingCalc(kingAt, goToPos) -> int:
    if goToPos in cachedKing:
        return cachedKing[goToPos]
    travelAmt = [abs(kingAt[0] - goToPos[0]), abs(kingAt[1] - goToPos[1])]
    cachedKing[goToPos] = sum(travelAmt) - min(travelAmt)
    return cachedKing[goToPos]


def knightNeighbors(currPos):
    if currPos in cachedNeighbors:
        return cachedNeighbors[currPos]
    maybePossible = [
        (currPos[0] + 2, currPos[1] + 1), (currPos[0] + 1, currPos[1] + 2),
        (currPos[0] - 1, currPos[1] - 2), (currPos[0] - 2, currPos[1] - 1),
        (currPos[0] + 2, currPos[1] - 1), (currPos[0] + 1, currPos[1] - 2),
        (currPos[0] - 1, currPos[1] + 2), (currPos[0] - 2, currPos[1] + 1)
    ]
    actuallyPossible = []
    for p in maybePossible:
        if 0 <= p[0] < cols and 0 <= p[1] < rows:
            actuallyPossible.append(p + currPos[2:])
    cachedNeighbors[currPos] = actuallyPossible
    return actuallyPossible


def knightExpand(knightPos):
    """
    Given a position, updates the global distances variable so test the shortest paths
    The True/False is for whether they've picked up the king so far yet
    """
    frontier = Queue()
    frontier.put((*knightPos, False))
    costs = {(*knightPos, False): 0}
    while not frontier.empty():
        curr = frontier.get()
        rnCost = costs[curr]
        for n in knightNeighbors(curr):
            newCost = rnCost + 1
            if n not in costs or costs[n] > newCost:
                costs[n] = newCost
                frontier.put(n)

            if n[-1] is False:
                newN = (*n[:-1], True)
                newCost = rnCost + 1 + kingCalc(kingPos, n[:-1])
                if newN not in costs or costs[newN] > newCost:
                    costs[newN] = newCost
                    frontier.put(newN)

    costs[(*knightPos, True)] = kingCalc(kingPos, knightPos[:2])
    distances[knightPos].update(costs)


meetingPosCosts = [[(0, float('nan')) for _ in range(cols)] for _ in range(rows)]  # float('inf') breaks it
for (c, r) in knightPos:
    knightExpand((c, r))

for kn in knightPos:
    for c in range(cols):
        for r in range(rows):
            direct, detour = distances[kn][(c, r, False)], distances[kn][(c, r, True)]
            posCost = meetingPosCosts[r][c]
            if meetingPosCosts[r][c][1] > detour - direct:
                posCost = (posCost[0] - posCost[1] + detour, detour - direct)
            else:
                posCost = (posCost[0] + direct, posCost[1])
            meetingPosCosts[r][c] = posCost

with open('camelot.out', 'w') as written:
    output = min([min([i[0] for i in m]) for m in meetingPosCosts])
    print(output)
    written.write(f"{output}\n")
print(time.perf_counter() - timeStart)
