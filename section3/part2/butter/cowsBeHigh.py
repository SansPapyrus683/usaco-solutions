"""
ID: kevinsh4
TASK: butter
LANG: PYTHON3
"""
# TODO: THIS IS TOO SLOW FOR GODDAMN HALF THE TEST CASES. IF ANYONE CAN TELL ME HOW TO OPTIMIZE THIS, PLZZZZ DO SO
from collections import defaultdict

with open('onSugarOFC.txt') as read:
    read = [l.rstrip() for l in read.readlines()]
    cowNum, pastureNum = int(read[0].split()[0]), int(read[0].split()[1])
    distances = defaultdict(lambda: defaultdict(lambda: 0))
    pasturesBeIn = []
    for l in read[1: 1 + cowNum]:
        pasturesBeIn.append(int(l))

    for l in read[cowNum + 2:]:
        l = [int(i) for i in l.split()]
        distances[l[0]][l[1]] = distances[l[1]][l[0]] = l[2]

def allFieldPaths(fieldDistances, allTheFields):
    initialDist = {f: {i: float('inf') for i in fieldDistances} for f in fieldDistances}
    for pa in allTheFields:  # pastures that are directly connected
        initialDist[pa][pa] = 0
        for n in distances[pa]:  # given a graph, it finds the shortest path between all nodes
            initialDist[n][pa] = initialDist[pa][n] = 0 if n == pa else fieldDistances[n][pa]

    for k in allTheFields:
        for i in allTheFields:
            for j in allTheFields:
                if initialDist[i][j] > initialDist[i][k] + initialDist[k][j]:
                    initialDist[i][j] = initialDist[i][k] + initialDist[k][j]

    return initialDist

allDistances = allFieldPaths(distances, distances.keys())
with open('outputs.txt', 'w') as written:
    costs = []
    for p in allDistances:
        cost = 0
        for c in pasturesBeIn:
            cost += allDistances[p][c]
        costs.append(cost)

    written.write(f'{min(costs)}\n')
