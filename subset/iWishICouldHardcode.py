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
checkSum = sum(actualSet)//2

<<<<<<< HEAD
sumNumbers = {1: [1, 2], 2: [1, 1, 1, 1]}  # 0, 1, 2, 3...
needToAdd = 3
while upToNum not in sumNumbers:
    oldList = sumNumbers[needToAdd-1].copy()
    for i in range(sum([x for x in range(needToAdd)])):
        if i < needToAdd:
            continue
        if i < len(oldList):
            oldList[i] += 1
        else:
            oldList.append(1)
    needToAdd += 1
=======
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
>>>>>>> 6e9456067b0fa2c011fcf60cbd4c4f8260229bc8

with open('outputs.txt', 'w') as written:
    if sum(actualSet) % 2:
        written.write('0\n')
    else:
        written.write(str(sumNumbers[upToNum][checkSum]))
