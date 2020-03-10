"""
ID: kevinsh4
TASK: nocows
LANG: PYTHON3
"""
from collections import defaultdict

with open('nocows.in') as read:
    cowNum, genNum = [int(c) for c in read.read().rstrip().split()]

treeTable = defaultdict(lambda: defaultdict(lambda: 0))  # the table[i][j] is # of trees of depth i and j cows
shallowerTrees = defaultdict(lambda: defaultdict(lambda: 0))  # the table[i][j] still has j cows, but all's depth <= i
treeTable[1][1] = 1

for i in range(2, genNum + 1):  # we'll find the depths of the trees one at a time
    for cows in range(1, cowNum + 1, 2):
        left = 1
        while left <= (cows - left):  # calcs all the number of trees smaller
            left += 2
            if left != (cows - 1 - left):
                multiplier = 2
            else:
                multiplier = 1
            treeTable[i][cows] += multiplier * (shallowerTrees[i - 2][left]
                                                + treeTable[i - 2][cows - 1 - left]
                                                + treeTable[i-1][cows - 1 - left])
            treeTable[i][cows] %= 9901
        for smallLeft in range(cowNum + 1):
            shallowerTrees[i-1][smallLeft] += treeTable[i-1][smallLeft] + shallowerTrees[i-2][smallLeft]
            shallowerTrees[i-1][smallLeft] %= 9901

with open('nocows.out', 'w') as written:
    written.write(str(treeTable[genNum][cowNum]) + '\n')