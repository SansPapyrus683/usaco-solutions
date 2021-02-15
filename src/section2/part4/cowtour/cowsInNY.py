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
        for other_v, i in enumerate(adj):
            if int(i):
                connection[v + 1].append(other_v + 1)

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
    this_field = [loc]
    while not frontier.empty():  # simply bfs- first time using Queue() as well lol
        l = frontier.get()
        for neighbor in connection[l]:
            if neighbor not in joined:
                frontier.put(neighbor)
                joined.add(neighbor)
                this_field.append(neighbor)
    fields.append(tuple(this_field))


def diameter(field: tuple) -> tuple:  # calculates diameter of field lol
    initial_dist = defaultdict(lambda: defaultdict(lambda: float('inf')))
    for p in field:  # pastures that are directly connected
        initial_dist[p][p] = 0  # so apparently these two giant for-loops together is called the Floyd-Warshall algorithm
        for n in connection[p]:  # given a graph, it finds the shortest path between all nodes
            initial_dist[n][p] = initial_dist[p][n] = 0 if n == p else distances[n][p]

    for k in field:
        for i in field:
            for j in field:
                if initial_dist[i][j] > initial_dist[i][k] + initial_dist[k][j]:
                    initial_dist[i][j] = initial_dist[i][k] + initial_dist[k][j]

    distance_compilation = []
    for i in initial_dist.values():
        distance_compilation.extend(list(i.values()))
    return initial_dist, max(distance_compilation)  # we'll need these both for later


field_diameters = {}
field_distances = {}
for f in fields:
    field_distances[f], field_diameters[f] = diameter(f)

min_di = float('inf')
for field1, field2 in combinations(fields, 2):
    for link1 in field1:
        for link2 in field2:  # diameter of two combined fields = max(field1 or field2 diameter)
            joined_d = max(field_diameters[field1], field_diameters[field2],
                           max(field_distances[field1][link1].values()) +
                           max(field_distances[field2][link2].values()) +
                           distances[link1][link2])  # OR farthest point from link1 + farthest point from link 2
            if joined_d < min_di:                     # + distance from link1 to link 2
                min_di = joined_d

with open('outputs.txt', 'w') as written:
    min_di = str(round(min_di, 6))
    for v, c in enumerate(min_di):  # formatting crap
        if c == '.':
            break
    min_di += '0' * (6 - len(min_di[v + 1:]))
    written.write(min_di + '\n')
