"""
ID: kevinsh4
TASK: concom
LANG: PYTHON3
"""
from collections import defaultdict

with open('capitalismBeLike.txt') as read:
    owned = defaultdict(lambda: defaultdict(lambda: int()))  # owned[a][b] will give how much a owns of b
    companies = set()
    for v, line in enumerate(read):
        if v != 0:
            line = line.rstrip().split()
            owned[int(line[0])][int(line[1])] = int(line[2])
            companies.add(int(line[0]))
            companies.add(int(line[1]))
    owned = {i: dict(owned[i]) for i in owned}  # change it back to normal version

prevFound = {}  # do some caching bc why not


def findOwns(company):
    global owned
    visited = {company}
    frontier = [company]
    sharesOwned = defaultdict(lambda: 0)
    sharesOwned[company] = 100  # explicit lol
    while frontier:
        for c in frontier:
            for share in owned[c]:
                sharesOwned[share] += owned[c][share]

        inLine = []
        for possPuppet in sharesOwned:
            if sharesOwned[possPuppet] > 50 and possPuppet not in visited:
                visited.add(possPuppet)
                inLine.append(possPuppet)
        frontier = inLine
    return [i for i in sharesOwned if sharesOwned[i] > 50 and i != company]

ownedPairs = []
for co in companies:
    ownedPairs.extend([(co, o) for o in findOwns(co)])

ownedPairs.sort()
with open('outputs.txt', 'w') as written:
    for p in ownedPairs:
        written.write(' '.join([str(s) for s in p]) + '\n')
