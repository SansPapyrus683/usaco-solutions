"""
ID: kevinsh4
TASK: cowtour
LANG: PYTHON3
"""
from itertools import combinations
from math import sqrt
from queue import Queue
from collections import defaultdict

with open('notTouristsDumbo.txt') as read:
    read = [l.rstrip() for l in read.readlines()]
    connection = {p: [] for p in range(1, int(read[0]) + 1)}
    locations = {}
    for v, loc in enumerate(read[1:len(connection) + 1]):
        locations[v + 1] = tuple([int(c) for c in loc.split()])
    for v, adj in enumerate(read[len(connection) + 1:]):
        for otherV, i in enumerate(adj):
            if int(i):
                connection[v + 1].append(otherV + 1)

distances = defaultdict(lambda: defaultdict(lambda: 0.0))
for pair in combinations(connection.keys(), 2):  # get distances of all pastures within one another
    raw = (locations[pair[0]][0] - locations[pair[1]][0]) ** 2 + (locations[pair[0]][1] - locations[pair[1]][1]) ** 2
    distances[pair[0]][pair[1]] = distances[pair[1]][pair[0]] = sqrt(raw)
distances = {i: dict(distances[i]) for i in distances}

joined = set()
fields = []  # these two for loops precompute the distances and the fields
for loc in connection.keys():  # figure out what group the fields in in so i can figure out the heck to even join
    if loc in joined:  # already joined, so skip
        continue
    frontier = Queue()
    frontier.put(loc)
    joined.add(loc)
    thisField = [loc]
    while not frontier.empty():  # simply bfs- first time using Queue() as well lol
        l = frontier.get()
        for neighbor in connection[l]:
            if neighbor not in joined:
                frontier.put(neighbor)
                joined.add(neighbor)
                thisField.append(neighbor)
    fields.append(tuple(thisField))


def diameter(field: tuple) -> tuple:  # calculates diameter of field lol
    initialDist = defaultdict(lambda: defaultdict(lambda: float('inf')))
    for p in field:  # pastures that are directly connected
        initialDist[p][p] = 0  # so apparently these two giant for-loops together is called the Floyd-Warshall algorithm
        for n in connection[p]:  # given a graph, it finds the shortest path between all nodes
            initialDist[n][p] = initialDist[p][n] = 0 if n == p else distances[n][p]

    for k in field:
        for i in field:
            for j in field:
                if initialDist[i][j] > initialDist[i][k] + initialDist[k][j]:
                    initialDist[i][j] = initialDist[i][k] + initialDist[k][j]

    distanceCompilation = []
    for i in initialDist.values():
        distanceCompilation.extend(list(i.values()))
    return initialDist, max(distanceCompilation)  # we'll need these both for later


fieldDiameters = {}
fieldDistances = {}
for f in fields:
    fieldDistances[f], fieldDiameters[f] = diameter(f)

minDi = float('inf')
for field1, field2 in combinations(fields, 2):
    for link1 in field1:
        for link2 in field2:  # diamter of two combined fields = max(field1 or field2 diameter)
            joinedD = max(fieldDiameters[field1], fieldDiameters[field2],
                          max(fieldDistances[field1][link1].values()) +
                          max(fieldDistances[field2][link2].values()) +
                          distances[link1][link2])  # OR farthest point from link1 + farthest point from link 2
            if joinedD < minDi:                     # + distance from link1 to link 2
                minDi = joinedD

with open('outputs.txt', 'w') as written:
    minDi = str(round(minDi, 6))
    for v, c in enumerate(minDi):  # formatting crap
        if c == '.':
            break
    minDi += '0' * (6 - len(minDi[v + 1:]))
    written.write(minDi + '\n')
