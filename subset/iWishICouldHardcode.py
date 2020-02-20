"""
ID: kevinsh4
TASK: subset
LANG: PYTHON3
"""
from itertools import combinations

with open('wellTooBad.txt') as read:
    upToNum = int(read.read().rstrip())

actualSet = {i for i in range(1, upToNum + 1)}

goodPartitionCount = 0
for i in range(1, upToNum//2 + 1):
    for firstPart in combinations(actualSet, i):
        firstPart = set(firstPart)
        secondPart = actualSet - firstPart
        if sum(secondPart) == sum(firstPart):
            goodPartitionCount += 1

with open('outputs.txt', 'w') as written:
    written.write(str(goodPartitionCount) + '\n')
