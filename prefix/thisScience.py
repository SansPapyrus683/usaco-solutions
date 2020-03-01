"""
ID: kevinsh4
TASK: prefix
LANG: PYTHON3
ok um uh this approach doesnt work either
frick me
"""
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

validDict = {i: False for i in range(len(molecule))}
expressibleList = []
for p in prefixes:
    if molecule[:len(p)] == p:
        validDict[len(p) - 1] = True

for upTo in validDict:
    for poss in prefixes:
        if upTo - len(poss) < 0:
            continue
        if validDict[upTo - len(poss)] and molecule[upTo - len(poss) + 1: upTo + 1] == poss:
            validDict[upTo] = True

with open('outputs.txt', 'w') as written:
    try:
        written.write(str(max(p[0] for p in validDict.items() if p[1]) + 1) + '\n')
    except ValueError:
        written.write('0\n')
