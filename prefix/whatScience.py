"""
ID: kevinsh4
TASK: prefix
LANG: PYTHON3

this one is bad, it only passes 5 of the 6 test cases
but its more intuitive so i still included it here
or i just cant let go of my code
you decide
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

currBuild = prefixes.copy()
maxLen = 0

while currBuild:  # go until all the current builds are invalid
    inLine = []
    for b in currBuild:
        for s in prefixes:
            if b + s == molecule[:len(b + s)]:  # add only if valid
                inLine.append(b + s)
    if not inLine:  # if there were no valid prefixes, which means we've constructed the longest
        for prePre in currBuild:  # do an initial check for the valid prefixes and all that in case nothing is valid
            if molecule[:len(prePre)] == prePre and len(prePre) > maxLen:
                print(prePre)
                maxLen = len(prePre)
    currBuild = inLine

with open('outputs.txt', 'w') as written:
    print(maxLen)
    written.write(str(maxLen) + '\n')
