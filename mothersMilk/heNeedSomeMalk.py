"""
ID: kevinsh4
TASK: milk3
LANG: PYTHON3
"""
from queue import Queue
from sys import exit

milkBuckets = {}

with open('malk.txt') as buckets:

    for line in buckets.readlines():
        line = [int(i) for i in line.rstrip().split()]
        milkBuckets['A'] = [line[0], 0]
        milkBuckets['B'] = [line[1], 0]
        milkBuckets['C'] = [line[2], line[2]]


def pour(pouring: 'bucket id', pouree: 'another bucket id', currStates: dict):
    firstBucket = currStates[pouring]
    secondBucket = currStates[pouree]
    while firstBucket[-1] != 0:  # pours until first empty or second full
        firstBucket[-1] -= 1
        secondBucket[-1] += 1
        if secondBucket[0] == secondBucket[-1]:
            break
    currStates[pouring] = firstBucket
    currStates[pouree] = secondBucket
    return currStates


possValues = []
statesInLine = Queue()
statesInLine.put(pour('C', 'A', dict(milkBuckets)))
statesInLine.put(pour('C', 'B', milkBuckets.copy()))
print(statesInLine.get()); print(statesInLine.get())
