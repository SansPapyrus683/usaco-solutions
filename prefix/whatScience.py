"""
ID: kevinsh4
TASK: prefix
LANG: PYTHON3
"""
from sys import exit
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

prefixBuilds = prefixes.copy()  # eliminating all the prefixes that can be built up by others
visited = set()
while prefixBuilds:
    inLine = []
    for p in prefixBuilds:
        for otherP in prefixes:
            if p + otherP not in visited and len(p + otherP) <= max(len(s) for s in prefixes):
                visited.add(p + otherP)
                inLine.append(p + otherP)
    prefixBuilds = inLine

killList = []
for p in prefixes:
    if p in visited:
        killList.append(p)
for target in killList:
    prefixes.remove(target)

currBuild = prefixes.copy()
print(currBuild)
maxLen = 0
while currBuild:  # go until all the current builds are invalid
    inLine = []
    for b in currBuild:
        if molecule[:len(b)] == b and len(b) > maxLen:  # check for validity (initial) and all that stuff
            maxLen = len(b)
        for s in prefixes:
            if b + s == molecule[:len(b + s)]:  # add only if valid
                inLine.append(b + s)
    currBuild = inLine

with open('outputs.txt', 'w') as written:
    written.write(str(maxLen) + '\n')
