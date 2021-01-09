"""
ID: kevinsh4
TASK: combo
LANG: PYTHON3
"""

from itertools import product

LOCK_NUM = 2

with open('ohBaby.txt') as read:
    lockAmt = int(read.readline())
    combs = [tuple([int(i) for i in read.readline().split()]) for _ in range(LOCK_NUM)]

validCombs = []
for comb in combs:
    singlePossVals = []
    for c in comb:
        onePossVal = []
        for i in range(-2, 3):
            val = c + i
            while val < 1 or val > lockAmt:
                if val > lockAmt:
                    val -= lockAmt
                elif c + i < 1:
                    val += lockAmt
            onePossVal.append(val)
        singlePossVals.append(onePossVal)
    validCombs.extend(list(product(*singlePossVals)))

valid = len(set(validCombs))
print(valid)
print(valid, file=open('outputs.txt', 'w'))
