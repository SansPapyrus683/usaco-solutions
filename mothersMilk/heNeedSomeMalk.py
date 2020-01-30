"""
ID: kevinsh4
TASK: milk3
LANG: PYTHON3
"""
from queue import Queue
from copy import deepcopy

milkBuckets = {}

with open('malk.txt') as buckets:
    for line in buckets.readlines():
        line = [int(i) for i in line.rstrip().split()]
        milkBuckets['A'] = [line[0], 0]
        milkBuckets['B'] = [line[1], 0]
        milkBuckets['C'] = [line[2], line[2]]


def pour(pouring: 'bucket id', pouree: 'another bucket id', currStates: dict):
    nextStates = deepcopy(currStates)
    firstBucket = currStates[pouring].copy()
    secondBucket = currStates[pouree].copy()
    while firstBucket[-1] != 0:  # pours until first empty or second full
        firstBucket[-1] -= 1
        secondBucket[-1] += 1
        if secondBucket[0] == secondBucket[-1]:
            break
    nextStates[pouring] = firstBucket
    nextStates[pouree] = secondBucket
    return nextStates


possValues = []
statesInLine = [pour('C', 'A', milkBuckets), pour('C', 'B', milkBuckets)]  # sets up the first two nodes for bfs

visitedStates = deepcopy(statesInLine)
while True:  # does a bfs through all possible states
    inLine = []
    for state in statesInLine:  # one node will be linked to others if it's accessible through a single pour
        pass
