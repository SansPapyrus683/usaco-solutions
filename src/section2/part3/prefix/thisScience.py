"""
ID: kevinsh4
TASK: prefix
LANG: PYTHON3
"""


def getValid(thePrefixes, hugeMolecule):
    possible = [False for _ in range(len(hugeMolecule) + 1)]  # this[i] = whether prefix of len i can be made
    possible[0] = True  # i mean we can always make a prefix of len 0

    for upTo in range(len(hugeMolecule) + 1):
        for poss in thePrefixes:
            if upTo - len(poss) < 0:
                continue
            # if the previous one is possible, see if we can just tack poss onto the prev to get another prefix
            if possible[upTo - len(poss)] and hugeMolecule[upTo - len(poss): upTo] == poss:
                possible[upTo] = True
                break
    return possible


prefixes = set()
molecule = ''
with open('bioTrash.txt') as read:
    detectPrefix = True
    for v, line in enumerate(read):
        if line.rstrip() == '.':
            detectPrefix = False
            continue
        if detectPrefix:
            for s in line.rstrip().split():
                prefixes.add(s)
        else:
            molecule += line.rstrip()

reachable = getValid(prefixes, molecule)
longest = 0
for i in range(len(reachable) - 1, -1, -1):
    if reachable[i]:
        longest = i
        break

print(longest)
with open('outputs.txt', 'w') as written:
    written.write(str(longest) + '\n')
