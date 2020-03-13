"""
ID: kevinsh4
TASK: nocows
LANG: PYTHON3
"""
from collections import defaultdict

with open('banjos.txt') as read:
    cowNum, genNum = [int(c) for c in read.read().rstrip().split()]

treeTable = defaultdict(lambda: defaultdict(lambda: 0))  # the treeTable[i][cowN] is # of trees of depth i and cowN cowN
shallowerTrees = defaultdict(lambda: defaultdict(lambda: 0))  # the treeTable[i][cowN] still has cowN cowN, but all's
# depth <= i
treeTable[1][1] = 1

for i in range(2, genNum + 1):  # we'll search the depths of the trees one at a time
    for cowN in range(1, cowNum + 1, 2):
        other = 1
        while other <= (cowN - 1 - other):  # calcs all the number of trees smaller
            if other != (cowN - 1 - other):
                multiplier = 2
            else:
                multiplier = 1
            treeTable[i][cowN] += multiplier * (shallowerTrees[i - 2][other] * treeTable[i - 1][cowN - 1 - other]
                                                + treeTable[i - 1][other] * shallowerTrees[i - 2][cowN - 1 - other]
                                                + treeTable[i - 1][other] * treeTable[i - 1][cowN - 1 - other])
            treeTable[i][cowN] %= 9901
            other += 2

    for other in range(cowNum + 1):
        shallowerTrees[i - 1][other] += treeTable[i - 1][other] + shallowerTrees[i - 2][other]
        shallowerTrees[i - 1][other] %= 9901

with open('outputs.txt', 'w') as written:
    written.write(str(treeTable[genNum][cowNum]) + '\n')
