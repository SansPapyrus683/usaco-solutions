"""
ID: kevinsh4
TASK: nocows
LANG: PYTHON3
"""
from collections import defaultdict

with open('banjos.txt') as read:
    cowNum, genNum = [int(c) for c in read.read().rstrip().split()]

treeTable = defaultdict(lambda: defaultdict(lambda: 0))  # the table[i][j] is # of trees of depth i and j cows
shallowerTrees = defaultdict(lambda: defaultdict(lambda: 0))  # the table[i][j] still has j cows, but all's depth <= i
treeTable[1][1] = 1

for i in range(2, genNum + 1):  # we'll find the depths of the trees one at a time
    print('doing gen %i' % i)
    for cows in range(1, sum([2**x for x in range(i)]) + 1, 2):
        print(cows)
    print('-----')
