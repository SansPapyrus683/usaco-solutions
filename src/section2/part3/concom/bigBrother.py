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

prevFound = {}  # do some caching bc why not


def find_owns(company):  # does simply bfs to figure out what others this company owns
    global owned  # JESUS CHRIST A GLOBAL VARIABLE WHAT WAS I DOING BACK THEN
    visited = {company}
    frontier = [company]
    shares_owned = defaultdict(lambda: 0)
    shares_owned[company] = 100  # explicit lol
    while frontier:
        for c in frontier:
            for share in owned[c]:
                shares_owned[share] += owned[c][share]  # add all shares that the company even remotely controls

        in_line = []
        for possPuppet in shares_owned:
            if shares_owned[possPuppet] > 50 and possPuppet not in visited:
                visited.add(possPuppet)  # only search through puppet if puppet is controlled
                in_line.append(possPuppet)
        frontier = in_line
    return [i for i in shares_owned if shares_owned[i] > 50 and i != company]


owned_pairs = []
for co in companies:
    owned_pairs.extend([(co, o) for o in find_owns(co)])  # just go through all companies, see how many they own

owned_pairs.sort()
with open('outputs.txt', 'w') as written:
    for p in owned_pairs:
        written.write(' '.join([str(s) for s in p]) + '\n')
