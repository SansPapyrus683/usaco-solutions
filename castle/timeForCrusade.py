"""
ID: kevinsh4
TASK: castle
LANG: PYTHON3
"""
from copy import deepcopy
from sys import exit

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
            if p[0] not in (0, maxHeight + 1) and p[1] not in (0, maxWidth + 1):
                cellWalls.append((cell, p))

    for target in killList:
        poss.remove(target)

    return poss, cellWalls


visited = set()
sizeList = []
rooms = {}
for mini in castle:
    if mini in visited:
        continue
    frontier = [mini]
    sizeCount = 1
    roomWalls = set()
    roomCells = set()
    while frontier:
        inLine = []
        for c in frontier:
            neighbors = findRoomNeighbors(c)
            visited.add(c)
            roomCells.add(c)
            for wall in neighbors[1]:
                roomWalls.add(wall)
            for n in neighbors[0]:
                if n not in visited:
                    inLine.append(n)
                    sizeCount += 1
        frontier = inLine
    print(sizeCount)
    print(roomCells)
    print(roomWalls)
    sizeList.append(sizeCount)

print(sizeList)
