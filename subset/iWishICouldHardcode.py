"""
ID: kevinsh4
TASK: subset
LANG: PYTHON3
"""
from itertools import combinations
from sys import exit

with open('wellTooBad.txt') as read:
    upToNum = int(read.read().rstrip())

actualSet = {i for i in range(1, upToNum + 1)}

if sum(actualSet) % 2:
    with open('outputs.txt', 'w') as written:
        written.write('\n')
        exit()

checkSum = sum(actualSet)//2
goodPartitionCount = 0
for i in range(1, upToNum//2 + 1):  # only generate so many subsets
    for firstPart in combinations(actualSet, i):
        if sum(firstPart) == checkSum:
            goodPartitionCount += 1

with open('outputs.txt', 'w') as written:
    written.write(str(goodPartitionCount) + '\n')
