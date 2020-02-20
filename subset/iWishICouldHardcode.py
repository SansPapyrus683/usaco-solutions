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

with open('outputs.txt', 'w') as written:
    if sum(actualSet) % 2:
        written.write('0\n')
    else:
        written.write(str(sumNumbers[upToNum][checkSum]))
