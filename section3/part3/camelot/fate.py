"""
ID: kevinsh4
TASK: camelot
LANG: PYTHON3
"""
from collections import defaultdict

with open('camelot.in') as read:
    knightPos = []
    for v, l in enumerate(read):
        if v == 0:
            rows, cols = [int(i) for i in l.split()]
        else:
            l = l.split()
            for i in range(len(l) // 2):  # don't worry, i'll extract the king later
                knightPos.append(l[2 * i: 2 * i + 2])

letterLegend = {l: n for n, l in enumerate('ABCDEFGHIJKLMNOPQRSTUVWXYZ')}
for v, p in enumerate(knightPos):
    knightPos[v] = (letterLegend[p[0]], int(p[1]) - 1)  # zero based indexing for the win bois
kingPos = knightPos[0]
del knightPos[0]
cachedDistances = defaultdict(dict)


def kingCalc(kingAt, goToPos) -> int:
    travelAmt = [abs(kingAt[0] - goToPos[0]), abs(kingAt[1] - goToPos[1])]
    return sum(travelAmt) - min(travelAmt)


def knightNeighbors(currPos):
    maybePossible = [
        (currPos[0] + 2, currPos[1] + 1), (currPos[0] + 1, currPos[1] + 2),
        (currPos[0] - 1, currPos[1] - 2), (currPos[0] - 2, currPos[1] - 1),
        (currPos[0] + 2, currPos[1] - 1), (currPos[0] + 1, currPos[1] - 2),
        (currPos[0] - 1, currPos[1] + 2), (currPos[0] - 2, currPos[1] + 1)
    ]
    actuallyPossible = []
    for p in maybePossible:
        if 0 <= p[0] < cols and 0 <= p[1] < rows:
            actuallyPossible.append(p)
    return actuallyPossible


def knightCalc(knightPos, goToPos) -> int:
    visited = set()
    frontier = {knightPos}
    movesTaken = 0
    while frontier:
        inLine = set()
        for p_ in frontier:
            if p_ == goToPos:  # knights should be able to reach every square of the board
                cachedDistances[knightPos][goToPos] = movesTaken
                return movesTaken
            for n in knightNeighbors(p_):
                if n not in visited:
                    inLine.add(n)
                    visited.add(n)
        frontier = inLine
        movesTaken += 1


def meetingBruteForce(knightPos_, kingPos_) -> int:
    return min([pickupBruteForce(knightPos_, kingPos_, (c, r)) for c in range(cols) for r in range(rows)])


def pickupBruteForce(knightPos_, kingPos_, meetingPos) -> int:
    print(f'calculating for {meetingPos}')
    leastCost = float('inf')
    for r in range(rows):
        for c in range(cols):
            if (c, r) == meetingPos:
                continue
            leastCost = min(leastCost, whichKnightBruteForce(knightPos_, kingPos_, meetingPos, (c, r)))
    if leastCost == float('inf'):
        return 0
    return leastCost


def whichKnightBruteForce(knightPos_, kingPos_, meetingPos, pickupPos) -> int:
    kingDist = kingCalc(kingPos_, pickupPos)
    minKnightDist = sum([knightCalc(kn, meetingPos) for kn in knightPos_])
    minDetour = min([knightCalc(kn, kingPos_) + knightCalc(kingPos_, meetingPos) - knightCalc(kn, meetingPos)
                     for kn in knightPos_])
    return kingDist + minKnightDist + minDetour


with open('camelot.out', 'w') as written:
    output = meetingBruteForce(knightPos, kingPos)
    print(output)
    written.write(f'{output}\n')
