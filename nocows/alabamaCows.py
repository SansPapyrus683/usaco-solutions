"""
ID: kevinsh4
TASK: nocows
LANG: PYTHON3
"""
from collections import defaultdict

with open('banjos.txt') as read:
    cowNum, genNum = [int(c) for c in read.read().rstrip().split()]

allCowTrees = defaultdict(lambda: defaultdict())  # depth -> number of nodes -> number of trees constructible
allCowTrees[1][1] = 1
