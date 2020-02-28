"""
ID: kevinsh4
TASK: nocows
LANG: PYTHON3
"""
with open('banjos.txt') as read:
    cowNum, genNum = [int(c) for c in read.read().rstrip().split()]

def cowNeighbors(gen, cowNum):  # like array indexes- stats at 0 and all that
    return [gen + 1, cowNum]

pedigree = [[False] * 2**i for i in range(genNum)]  # no cow in the gen space = False, cow = True
