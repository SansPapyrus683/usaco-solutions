"""
ID: kevinsh4
TASK: castle
LANG: PYTHON3
"""
from itertools import combinations

# 1: wall to the west
# 2: wall to the north
# 4: wall to the east
# 8: wall to the south
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

print(castle)
print(walls)


def findRoomNeighbors(cell):
    possibleNeighbors = {poss for poss in [(cell[0] - 1, cell[1]), (cell[0] + 1, cell[1]), (cell[0], cell[1] - 1), (cell[0], cell[1] + 1)] if poss not in walls[cell]}
    return possibleNeighbors


visited = {}
for mini in castle:
    frontier = [mini]
    for c in frontier:
        for n in findRoomNeighbors(c):
            pass
