"""
ID: kevinsh4
TASK: subset
LANG: PYTHON3
"""
"""
ok so this algo needs some 'splaining to do

"""

with open('wellTooBad.txt') as read:
    upToNum = int(read.read().rstrip())

actualSet = {i for i in range(1, upToNum + 1)}

sumNumbers = {1: [1, 1], 2: [1, 1, 1, 1]}  # 0, 1, 2, 3...
needToAdd = 3
while upToNum not in sumNumbers:
    oldList = sumNumbers[needToAdd - 1].copy()
    addToOld = [0 for x in range(needToAdd)] + oldList
    for v, i in enumerate(oldList):
        addToOld[v] += i
    sumNumbers[needToAdd] = addToOld
    needToAdd += 1

print(sumNumbers)
with open('outputs.txt', 'w') as written:
    if sum(actualSet) % 2:
        written.write('0\n')
    else:
        written.write(str(sumNumbers[upToNum][sum(actualSet)//2]//2) + '\n')
