"""
ID: kevinsh4
TASK: fence
LANG: PYTHON3
"""
from collections import defaultdict
from copy import deepcopy

connectedIntersections = defaultdict(lambda: list())
with open('lazyAsFrick.txt') as read:
    for v, line in enumerate(read):
        line = [int(i) for i in line.split()]
        if v != 0:
            connectedIntersections[line[0]].append(line[1])
            connectedIntersections[line[1]].append(line[0])
    connectedIntersections = {i: connectedIntersections[i] for i in connectedIntersections}

# ik global variables are a bad practice but hey, it gets the job done
sacrificialCircuit = deepcopy(connectedIntersections)  # we'll remove all the edges from this
path = []
def theTour(position):
    for n in sacrificialCircuit[position]:
        sacrificialCircuit[position].remove(n)
        sacrificialCircuit[n].remove(position)
        theTour(n)
    path.append(position)
    return path

def actualSol():
    global sacrificialCircuit
    global path
    connected = defaultdict(lambda: int())
    for i in connectedIntersections:
        for n in connectedIntersections[i]:
            connected[i] += 1
            connected[n] += 1

    validPaths = []
    twoOdds = False
    for i in connected:
        if (connected[i] // 2) % 2 == 1:  # // 2 because i counted each edge twice
            twoOdds = True
            break

    if twoOdds:
        for i in connected:
            if (connected[i] // 2) % 2 == 1:
                tour = theTour(i)
                validPaths.extend([tour, list(reversed(tour))])
                path = []
                sacrificialCircuit = deepcopy(connectedIntersections)
    else:
        for i in connected:
            tour = theTour(i)
            validPaths.extend([tour, list(reversed(tour))])
            path = []
            sacrificialCircuit = deepcopy(connectedIntersections)

    bestPathlen = float('inf')  # take the path who has the least amt when it's #'s are joined together
    for p in validPaths:
        if int(''.join([str(i) for i in p])) < bestPathlen:
            bestPath = p
            bestPathlen = int(''.join([str(i) for i in p]))

    return bestPath


with open('outputs.txt', 'w') as written:
    for i in actualSol():
        written.write(str(i) + '\n')
