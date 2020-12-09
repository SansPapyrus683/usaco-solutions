"""
ID: kevinsh4
TASK: wormhole
LANG: PYTHON3
"""
from itertools import combinations

cachedPairings = {}


def possiblePairings(positions):
    assert len(positions) % 2 == 0, "i mean it has to be even for complete pairings to be possible"
    if not positions:
        return []
    elif len(positions) == 2:
        return (tuple(positions),),

    if positions in cachedPairings:
        return cachedPairings[positions]

    possible = []
    for c in combinations(positions, 2):
        theRest = tuple(p for p in positions if p not in c)
        for p in possiblePairings(theRest):
            possible.append((c, *p))
    cachedPairings[positions] = tuple(possible)
    return tuple(possible)


def infLoopPossible(pairings):
    actualPoints = []
    for p in pairings:
        actualPoints.extend(p)
    for p in actualPoints:
        pos = p
        while True:
            possNextPos = [p_ for p_ in actualPoints if p_[1] == pos[1] and pos[0] < p_[0]]
            if not possNextPos:  # bessie starts to wander off to infinity and beyond
                break
            nextPos = min(possNextPos, key=lambda a: a[0])
            for pair in pairings:
                if nextPos in pair:
                    pos = pair[0] if nextPos == pair[1] else pair[1]
                    break
            if pos == p:
                return True
    return False


with open('quantumSuffering.txt') as read:
    wormholeNum = int(read.readline())
    wormholes = []
    for _ in range(wormholeNum):
        wormholes.append(tuple([int(i) for i in read.readline().split()]))
    wormholes = tuple(wormholes)

processed = set()
validPairings = set()
for pairing in possiblePairings(wormholes):
    pairing = tuple(sorted(tuple(sorted(p)) for p in pairing))
    if pairing in processed:
        continue
    if infLoopPossible(pairing):
        validPairings.add(pairing)
    processed.add(pairing)

print(len(validPairings))
with open('outputs.txt', 'w') as written:
    written.write(str(len(validPairings)) + '\n')
