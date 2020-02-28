"""
ID: kevinsh4
TASK: prefix
LANG: PYTHON3
"""
prefixes = []
molecule = ''
with open('prefix.in') as read:
    detectPrefix = True
    for v, line in enumerate(read):
        if line.rstrip() == '.':
            detectPrefix = False
            continue
        if detectPrefix:
            prefixes.extend([s for s in line.rstrip().split()])
        else:
            molecule += line.rstrip()

prefixBuilds = prefixes.copy()
visited = set()
while prefixBuilds:
    inLine = []
    for p in prefixBuilds:
        for otherP in prefixes:
            if p + otherP not in visited:
                visited.add(p + otherP)
                inLine.append(p + otherP)
    prefixBuilds = inLine

currBuild = prefixes.copy()
maxLen = 0
while currBuild:  # go until all the current builds are invalid
    inLine = []
    for b in currBuild:
        if molecule[:len(b)] == b and len(b) > maxLen:
            maxLen = len(b)
        for s in prefixes:
            if b + s == molecule[:len(b+s)]:
                inLine.append(b+s)
    currBuild = list(set(inLine))

with open('prefix.out', 'w') as written:
    print('asdfasdf', flush = True)
    written.write(str(maxLen) + '\n')
