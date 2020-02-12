"""
ID: kevinsh4
TASK: castle
LANG: PYTHON3
"""
from itertools import combinations
from sys import exit

written = open('outputs.txt', 'w')
castle = []
walls = {}
with open('holyMusicStops.txt') as read:
    for v, row in enumerate(read):
        if v != 0:
            for x, col in enumerate(row.rstrip().split()):
                x += 1
                compo = [a for a in [1, 2, 4, 8] if a & int(col)]  # which walls? idek how this works but sure
                castle.append((v, x))
                walls[(v, x)] = set()
                for w in compo:
                    if w == 1:
                        walls[(v, x)].add((v, x - 1))
                    elif w == 2:
                        walls[(v, x)].add((v - 1, x))
                    elif w == 4:
                        walls[(v, x)].add((v, x + 1))  # wall between these two cells
                    elif w == 8:
                        walls[(v, x)].add((v + 1, x))  # zeroes mean they're on the border or smth like that
        else:
            maxWidth, maxHeight = [int(i) for i in row.rstrip().split()]


def findRoomNeighbors(cell):
    poss = {(cell[0] - 1, cell[1]), (cell[0] + 1, cell[1]), (cell[0], cell[1] - 1), (cell[0], cell[1] + 1)}
    cellWalls = []
    killList = []
    for p in poss:
        if p in walls[cell]:
            killList.append(p)
            if p[0] not in (0, maxHeight + 1) and p[1] not in (0, maxWidth + 1):  # make sure it's not the border walls
                cellWalls.append(tuple(sorted((cell, p))))

    for target in killList:
        poss.remove(target)

    return poss, cellWalls


visited = set()
roomList = []
for mini in castle:
    if mini in visited:
        continue
    frontier = [mini]
    sizeCount = 1
    roomWalls = set()
    while frontier:
        inLine = []
        for c in frontier:
            neighbors = findRoomNeighbors(c)
            visited.add(c)
            for wall in neighbors[1]:
                roomWalls.add(wall)
            for n in neighbors[0]:
                if n not in visited:
                    inLine.append(n)
                    sizeCount += 1
        frontier = inLine
    roomList.append([sizeCount, roomWalls])

written.write(str(len(roomList)) + '\n' + str(max(i[0] for i in roomList)) + '\n')

combinedSizes = []
for comb in combinations(roomList, 2):
    currentPair = []
    for w in comb[0][1]:
        if w in comb[1][1]:
            currentPair.append(w)
    if currentPair:
        currentPair.append(comb[0][0] + comb[1][0])
        combinedSizes.append(currentPair)

biggest = max(i[-1] for i in combinedSizes)
written.write(str(biggest) + '\n')
optimalWalls = []

for bigRoom in combinedSizes:
    if bigRoom[-1] == biggest:
        optimalWalls.extend(bigRoom[:-1])

for v, wall in enumerate(optimalWalls):  # now i just change it to be associated with cells instead of walls
    optimalWalls[v] = [wall[0], 'E'] if wall[0][1] != wall[1][1] else [wall[1], 'N']  # left-right or up-down wall

westest = [w for w in optimalWalls if w[0][0] == max([i[0][0] for i in optimalWalls])]
southest = [w for w in westest if w[0][1] == min([i[0][1] for i in westest])][0]
written.write(str(southest[0][0]) + ' ' + str(southest[0][1]) + ' ' + southest[1] + '\n')
