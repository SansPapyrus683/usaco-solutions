"""
ID: kevinsh4
TASK: fence
LANG: PYTHON3
"""
import sys
from collections import defaultdict
from copy import deepcopy

sys.setrecursionlimit(10000)
connectedIntersections = defaultdict(lambda: list())
with open('lazyAsFrick.txt') as read:
    for v, line in enumerate(read):
        line = [int(i) for i in line.split()]
        if v != 0:
            connectedIntersections[line[0]].append(line[1])
            connectedIntersections[line[1]].append(line[0])
    connectedIntersections = {i: sorted(connectedIntersections[i]) for i in connectedIntersections}

# ik global variables are a bad practice but hey, it gets the job done
circuit = {}
circuitPos = 0
sacrificialCircuit = deepcopy(connectedIntersections)  # we'll remove all the edges from this
path = []


def theTour(position):
    sacrificialCircuit[position].sort()
    if not sacrificialCircuit[position]:
        path.append(position)
    else:
        # while loop bc with a for loop it always iterates through EVERY thing, but this while loop cares if it's
        # still there in the loop
        while sacrificialCircuit[position]:
            n = sacrificialCircuit[position][0]
            sacrificialCircuit[position].remove(n)  # remove that edge
            sacrificialCircuit[n].remove(position)
            theTour(n)  # recurse on that node
        path.append(position)
    return path


def actualSol():
    global sacrificialCircuit  # yes ik global vars are bad practice
    global path
    connected = defaultdict(lambda: int())
    for i in connectedIntersections:
        for n in connectedIntersections[i]:
            connected[i] += 1
            connected[n] += 1

    twoOdds = False  # are there just two odd nodes
    theOdds = []
    for i in connected:
        if (connected[i] // 2) % 2 == 1:  # // 2 because i counted each edge twice
            twoOdds = True
            theOdds.append(i)
            if len(theOdds) == 2:
                break

    if twoOdds:
        for i in connected:
            if i == min(theOdds):
                tour = theTour(i)
                return tour
    else:
        for i in connected:
            tour = theTour(i)
            return tour


with open('outputs.txt', 'w') as written:
    for i in reversed(actualSol()):
        written.write(str(i) + '\n')
