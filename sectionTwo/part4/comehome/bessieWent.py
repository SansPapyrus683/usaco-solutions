"""
ID: kevinsh4
TASK: comehome
LANG: PYTHON3
"""
from collections import defaultdict
from sys import exit

with open('sheGotMilk.txt') as read:
    distances = defaultdict(lambda: defaultdict(lambda: float('inf')))
    pastures = set()
    for v, line in enumerate(read):
        if v != 0:
            line = line.rstrip().split()
            if distances[line[0]][line[1]] > int(line[-1]):
                distances[line[0]][line[1]] = distances[line[1]][line[0]] = int(line[-1])
            pastures.add(line[0])
            pastures.add(line[1])
    distances = {d: dict(distances[d]) for d in distances}


def findBarn(cowPos):
    costs = {cowPos: 0}
    frontier = [cowPos]
    came_from = {cowPos: None}

    while frontier:  # dijkstra's or smth idk
        inLine = []
        for current in frontier:  # also is adapted from this:
            if cowPos == 'R':
                pass
            # https://www.redblobgames.com/pathfinding/a-star/introduction.html
            if current == 'Z':
                break
            for neighbor in distances[current]:
                newCost = costs[current] + distances[current][neighbor]

                if neighbor not in costs or newCost < costs[neighbor]:
                    costs[neighbor] = newCost
                    inLine.append(neighbor)
                    came_from[neighbor] = current
        frontier = inLine

    return costs['Z']

wayHome = {}
for p in pastures:
    if p.upper() == p and p != 'Z':
        wayHome[p] = findBarn(p)

with open('outputs.txt', 'w') as written:
    shortest = min(wayHome.values())
    for p in wayHome:
        if wayHome[p] == shortest:
            written.write('%s %i\n' % (p, shortest))
