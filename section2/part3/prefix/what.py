"""
ID: kevinsh4
TASK: prefix
LANG: PYTHON3
"""
# TOO SLOW
# JUST A PYTHON IMPLEMENTATION OF THEIR GOSH DARN APPROACH
prefixes = set()
molecule = ''
with open('prefix.in') as read:
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

def getValid(thePrefixes, hugeMolecule):
    validDict = {i: False for i in range(len(hugeMolecule))}
    for p in thePrefixes:
        if hugeMolecule[:len(p)] == p:
            validDict[len(p) - 1] = True

    for upTo in validDict:
        for poss in thePrefixes:
            if upTo - len(poss) < 0:
                continue
            if validDict[upTo - len(poss)] and hugeMolecule[upTo - len(poss) + 1: upTo + 1] == poss:
                validDict[upTo] = True
    return validDict

with open('prefix.out', 'w') as written:
    try:
        written.write(str(max(p[0] for p in getValid(prefixes, molecule).items() if p[1]) + 1) + '\n')
    except ValueError:
        written.write('0\n')
