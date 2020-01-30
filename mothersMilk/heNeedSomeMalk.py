"""
ID: kevinsh4
TASK: milk3
LANG: PYTHON3
"""
from copy import deepcopy
from itertools import permutations

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
        if secondBucket[0] == secondBucket[-1]:
            break
        firstBucket[-1] -= 1
        secondBucket[-1] += 1
    nextStates[pouring] = firstBucket
    nextStates[pouree] = secondBucket
    return nextStates


possValues = [pour('C', 'B', milkBuckets)['C'][-1]]  # an easy possible value
statesInLine = [pour('C', 'A', milkBuckets), pour('C', 'B', milkBuckets)]  # sets up the first two nodes for bfs

visitedStates = deepcopy(statesInLine)
foundAllStates = False
while not foundAllStates:  # does a bfs through all possible states
    foundAllStates = True
    inLine = []
    for state in statesInLine:  # one node will be linked to others if it's accessible through a single pour
        for pourComb in permutations(['A', 'B', 'C'], 2):
            poss = pour(pourComb[0], pourComb[1], state)
            if poss not in visitedStates:
                inLine.append(poss)
                visitedStates.append(poss)
                foundAllStates = False
            if poss['A'][-1] == 0 and poss['C'][-1] not in possValues:
                print(poss['C'][-1], state, poss)
                possValues.append(poss['C'][-1])
    statesInLine = inLine  # pushes all the inLine states into the processing line

<<<<<<< HEAD
print(possValues)
=======
with open('outputs.txt', 'w') as written:
    possValues.sort()
    written.write(' '.join([str(x) for x in possValues]))
    written.write('\n')
>>>>>>> 7994decb976021215f49e6161b27db8b708452bb
