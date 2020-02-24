"""
ID: kevinsh4
TASK: subset
LANG: PYTHON3
"""
"""
ok so this algo needs some 'splaining to do
so here's the amount of ways for a subset of N to sum up to each of the natural numbers:
if N was 1, it can sum up to 0 in 1 way, and 1 in another way
if N was 2, it can sum up to 0 in 1 way, 1 in 1 way, 2 in 1 way, and 3 in 1 way
here's a table:
N   0   1   2   3   4   5   6   7   8   9   10
1|   1   1
2|   1   1   1   1
3|   1   1   1   2   1   1   1  
4|   1   1   1   2   2   2   2   1   1   1
where's the transition from N to N+1?
if we shift N's line o' numbers N+1 units to the right, and add the two like so (ill take 2 transitioning to 3):
1   1   1   1
            1   1   1   1, we get N+1's line of numbers, and we keep doing that so yea
"""

with open('subset.in') as read:
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

with open('subset.out', 'w') as written:
    if sum(actualSet) % 2:
        written.write('0\n')
    else:
        written.write(str(sumNumbers[upToNum][sum(actualSet)//2]//2) + '\n')
