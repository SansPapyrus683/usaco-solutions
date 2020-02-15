"""
ID: kevinsh4
TASK: castle
LANG: PYTHON3
"""
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
            maxWidth, maxHeight = [int(i) + 1 for i in row.rstrip().split()]


def findRoomNeighbors(cell):
    poss = {c for c in
            [(cell[0] - 1, cell[1]), (cell[0] + 1, cell[1]), (cell[0], cell[1] - 1), (cell[0], cell[1] + 1)]
            if c not in walls[cell]}
    return poss


biggestSize = 0
color = 1
paintedCastle = {i:None for i in castle}
sizePaints = {}
for mini in paintedCastle:
    if not paintedCastle[mini]:
        frontier = [mini]
        paintedCastle[mini] = color
        sizeCount = 1
        visited = {mini}
        while frontier:
            inLine = []
            for c in frontier:
                for n in findRoomNeighbors(c):
                    if n not in visited:
                        inLine.append(n)
                        visited.add(n)
                        paintedCastle[n] = color
                        sizeCount += 1
            frontier = inLine
        if sizeCount > biggestSize:
            biggestSize = sizeCount
        sizePaints[color] = sizeCount
        color += 1

written.write(str(color - 1) + '\n' + str(biggestSize) + '\n')  # color - 1 because after the last room, it's still +1
bigRooms = {}
for cellWalls in walls.items():
    for pair in [(cellWalls[0], w) for w in cellWalls[1]]:
        if not{maxWidth, 0}.intersection({i[1] for i in pair}) and not{maxHeight, 0}.intersection({i[0] for i in pair}):
            if paintedCastle[pair[0]] != paintedCastle[pair[1]]:
                bigRooms[pair] = sizePaints[paintedCastle[pair[0]]] + sizePaints[paintedCastle[pair[1]]]

optimalSize = max(i for i in bigRooms.values())
written.write(str(max(i for i in bigRooms.values())) + '\n')
optimalWalls = [p for p in bigRooms if bigRooms[p] == optimalSize]
print('yea')
for v, wall in enumerate(optimalWalls):  # THIS PROCESS TAKES THE LONGEST
    optimalWalls[v] = [wall[0], 'E'] if wall[0][1] != wall[1][1] else [wall[1], 'N']  # left-right or up-down wall

westest = [w for w in optimalWalls if w[0][1] == min([i[0][1] for i in optimalWalls])]
southest = [w for w in westest if w[0][0] == max([i[0][0] for i in westest])][0]
written.write(str(southest[0][0]) + ' ' + str(southest[0][1]) + ' ' + southest[1] + '\n')
