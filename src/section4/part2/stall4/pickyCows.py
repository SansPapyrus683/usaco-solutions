"""
ID: kevinsh4
TASK: stall4
LANG: PYTHON3
"""


def foundJob(person, matchMat, taken=None, visited=None):
    """
    this function is probably a bad practice lol
    but anyways, stolen from: https://www.geeksforgeeks.org/maximum-bipartite-matching/
    """
    if visited is None:
        visited = set()
    if taken is None:
        taken = [-1 for _ in range(len(matchMat[0]))]

    for j in range(len(matchMat[0])):
        if matchMat[person][j] and j not in visited:
            visited.add(j)
            # if this isn't visited OR the cow we stole the stall from can also find a stall, assign it
            if taken[j] == -1 or foundJob(taken[j], matchMat, taken, visited):
                taken[j] = person
                return True
    return False


with open('stall4.in') as read:
    cowNum, stallNum = [int(i) for i in read.readline().split()]
    canMatch = [[0 for _ in range(stallNum)] for _ in range(cowNum)]
    for c in range(cowNum):
        for s in [int(i) - 1 for i in read.readline().split()[1:]]:
            canMatch[c][s] = 1

maxMatched = 0
assigned = [-1 for _ in range(stallNum)]
for c in range(cowNum):
    if foundJob(c, canMatch, assigned):
        maxMatched += 1

print(maxMatched)
print(maxMatched, file=open('stall4.out', 'w'))
