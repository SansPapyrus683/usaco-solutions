"""
ID: kevinsh4
TASK: maze1
LANG: PYTHON3
"""
points = set()
with open('hoesMad.txt') as read:
    for y, line in enumerate(read):
        if y != 0:
            if y == 1:
                conformLen = len(line.rstrip())
            elif len(line.rstrip()) != conformLen:
                line = line.rstrip() + ' ' * (conformLen - len(line.rstrip()))
            y -= 1  # to align the y with the x
            for x, c in enumerate(line):
                if c == ' ':
                    points.add((x, y))
        else:
            width, height = [int(i)*2 for i in line.rstrip().split()]


def findNeighbors(pt: tuple) -> set:
    possibleNeighbors = {
        (pt[0] - 1, pt[1]),
        (pt[0] + 1, pt[1]),
        (pt[0], pt[1] - 1),
        (pt[0], pt[1] + 1),
    }
    return possibleNeighbors.intersection(points)


frontier = []  # called frontier because that's what we'll expand from
for p in points:
    for n in findNeighbors(p):
        if n[0] in [0, width] or n[1] in [0, height]:
            frontier.append(p)

killList = []
for p in points:
    if p[0] in [0, width] or p[1] in [0, height]:
        killList.append(p)

for target in killList:
    points.remove(target)

visited = set(frontier)
expansionCount = 0
while frontier:
    inLine = []
    for p in frontier:
        for n in findNeighbors(p):
            if n not in visited:
                inLine.append(n)
                visited.add(n)
    frontier = inLine
    expansionCount += 1

with open('outputs.txt', 'w') as written:
    written.write(str((expansionCount + 1) // 2) + '\n')
