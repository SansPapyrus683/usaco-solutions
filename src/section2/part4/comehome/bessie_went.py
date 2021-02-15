"""
ID: kevinsh4
TASK: comehome
LANG: PYTHON3
"""
from collections import defaultdict
from sys import exit

with open('she_got_milk.txt') as read:
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


def find_barn(cow_pos):
    costs = {cow_pos: 0}
    frontier = [cow_pos]
    came_from = {cow_pos: None}

    while frontier:  # dijkstra's or smth idk
        in_line = []
        for current in frontier:  # also is adapted from this:
            # https://www.redblobgames.com/pathfinding/a-star/introduction.html
            if current == 'Z':
                break

            for neighbor in distances[current]:
                new_cost = costs[current] + distances[current][neighbor]

                if neighbor not in costs or new_cost < costs[neighbor]:
                    costs[neighbor] = new_cost
                    in_line.append(neighbor)
                    came_from[neighbor] = current
        frontier = in_line
    return costs['Z']


way_home = {}
for p in pastures:
    if p.upper() == p and p != 'Z':
        way_home[p] = find_barn(p)

with open('outputs.txt', 'w') as written:
    shortest = min(way_home.values())
    for p in way_home:
        if way_home[p] == shortest:
            written.write('%s %i\n' % (p, shortest))
