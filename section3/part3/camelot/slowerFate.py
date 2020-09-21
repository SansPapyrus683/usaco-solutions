"""
ID: kevinsh4
TASK: camelot
LANG: PYTHON3
NOTE: this one is slow but it's guaranteed to give the correct answer
"""
import time

start = time.perf_counter()
with open('camelot.in') as read:
    knightPos = []
    for v, l in enumerate(read):
        if v == 0:
            rows, cols = [int(i) for i in l.split()]
        else:
            l = l.split()
            for i in range(len(l) // 2):  # don't worry, i'll extract the king later
                knightPos.append(l[2 * i: 2 * i + 2])


def clamp(n, low, hi):
    if low <= n <= hi:
        return n
    if n < low:
        return low
    if n > hi:
        return hi


letterLegend = {l: n for n, l in enumerate('ABCDEFGHIJKLMNOPQRSTUVWXYZ')}
for v, p in enumerate(knightPos):
    knightPos[v] = (letterLegend[p[0]], int(p[1]) - 1)  # zero based indexing for the win bois
kingPos = knightPos[0]
del knightPos[0]
bounds = 2
distances = {(c, r): {(c_, r_): float('inf') for c_ in range(cols) for r_ in range(rows)}
             for c in range(cols) for r in range(rows)}
cachedNeighbors = {}


def kingCalc(kingAt, goToPos) -> int:
    travelAmt = [abs(kingAt[0] - goToPos[0]), abs(kingAt[1] - goToPos[1])]
    return sum(travelAmt) - min(travelAmt)


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
            actuallyPossible.append(p)
    cachedNeighbors[currPos] = actuallyPossible
    return actuallyPossible


def knightExpand(knightPos):
    visited = set()
    frontier = {knightPos}
    movesTaken = 0
    while frontier:
        inLine = set()
        for p_ in frontier:
            if distances[knightPos][p_] == float('inf'):
                distances[knightPos][p_] = movesTaken
                distances[p_][knightPos] = movesTaken

            for n in knightNeighbors(p_):
                if n not in visited:
                    inLine.add(n)
                    visited.add(n)
        frontier = inLine
        movesTaken += 1


def meetingBruteForce() -> int:
    best = float('inf')
    for c in range(cols):
        for r in range(rows):
            best = min(best, pickupBruteForce((c, r)))
    if best == float('inf'):
        return 0
    return best


def pickupBruteForce(meetingPos) -> int:
    kingBounds = 5
    leastCost = float('inf')
    for r in range(clamp(kingPos[1] - kingBounds, 0, rows), clamp(kingPos[1] + kingBounds, 0, rows)):
        for c in range(clamp(kingPos[0] - kingBounds, 0, cols), clamp(kingPos[0] + kingBounds, 0, cols)):
            leastCost = min(leastCost, whichKnightBruteForce(meetingPos, (c, r)))
    return leastCost


def whichKnightBruteForce(meetingPos, pickupPos) -> int:
    # yes ik global variables are a bad practice but it runs faster with them lol
    defTravelled = kingCalc(kingPos, pickupPos)
    minDetour = float('inf')
    for kn in knightPos:
        pickup, meeting, direct = distances[kn][pickupPos], distances[pickupPos][meetingPos], distances[kn][meetingPos]
        minDetour = min(minDetour, pickup + meeting - direct)
        defTravelled += direct
    return defTravelled + minDetour

# asdf = time.perf_counter()
for r in range(rows):  # precalculate all the distances
    for c in range(cols):  # 0.1
        knightExpand((c, r))

with open('camelot.out', 'w') as written:
    output = meetingBruteForce()
    print(output)
    written.write(f'{output}\n')
    print(time.perf_counter() - start)
