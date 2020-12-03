"""
ID: kevinsh4
TASK: agrinet
LANG: PYTHON3
"""
from collections import defaultdict

distances = defaultdict(lambda: defaultdict(lambda: float('inf')))
with open('tikTokBad.txt') as read:
    for v, line in enumerate(read):
        if v == 0:
            farmNum = int(line.rstrip())
            compareCount = 1
            adjFarm = 1
        else:
            for i in line.rstrip().split():
                if adjFarm != compareCount:  # don't put a node against itself
                    distances[adjFarm][compareCount] = int(i)
                compareCount += 1
                if compareCount > farmNum:  # ok, we compared enough, let's shift farms
                    compareCount = 1
                    adjFarm += 1
    distances = {f: dict(distances[f]) for f in distances}  # if you wanna print it, makes it more readable

cableLen = 0
farmsIn = [1]  # it doesn't matter which node we start at so just node 1
for _ in range(farmNum):
    connections = []
    for f in farmsIn:
        for n in distances[f]:
            if n not in farmsIn:  # get all possible connections (that are necessary)
                connections.append((f, n, distances[f][n]))

    minimumCable = min([pc[-1] for pc in connections]) if connections else None
    for c in connections:  # now we actually add the minimum connections
        if c[-1] == minimumCable:  # pc means possible connection
            cableLen += c[-1]
            farmsIn.append(c[1])
            break

with open('outputs.txt', 'w') as written:
    written.write('%i\n' % cableLen)
