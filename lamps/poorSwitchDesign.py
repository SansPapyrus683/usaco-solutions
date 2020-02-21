"""
ID: kevinsh4
TASK: lamps
LANG: PYTHON3
"""
from collections import defaultdict

with open('nintendoSwitch.txt') as read:
    for lineNum, line in enumerate(read):
        if lineNum == 0:
            lamps = tuple(True for _ in range(int(line.rstrip())))  # i just learned about for _ in range() lol
        elif lineNum == 1:
            offOnTimes = int(line.rstrip())
        elif lineNum == 2:
            onLamps = [int(i) - 1 for i in line.rstrip().split()[:-1]]
        elif lineNum == 3:  # minus one because aRrAYS stARt At 0
            offLamps = [int(i) - 1 for i in line.rstrip().split()[:-1]]


goodStates = set()
frontier = [lamps]
cachedN = {}

def stateNeighbors(currStates):
    global cachedN
    if currStates in cachedN:  # do some caching to speed up runtime
        return cachedN[currStates]
    second = list(currStates)
    third = list(currStates)
    fourth = list(currStates)
    for v, l in enumerate(currStates):
        if (v + 1) % 2:
            second[v] = not l
            third[v] = l
        else:
            third[v] = not l
            second[v] = l
        if not v % 3:
            fourth[v] = not l
        else:
            fourth[v] = l

    cachedN[currStates] = (tuple(not l for l in currStates), tuple(second), tuple(third), tuple(fourth))
    return tuple(not l for l in currStates), tuple(second), tuple(third), tuple(fourth)

for c in range(offOnTimes):  # do the actual switching and stuff
    inLine = []
    for state in frontier:
        for n in stateNeighbors(state):
            inLine.append(n)
    frontier = list(set(inLine))


print(frontier)
for arrangement in frontier:
    for req in onLamps:  # test for validity of lamp states
        if not arrangement[req]:
            break
    else:
        for req in offLamps:
            if arrangement[req]:
                break
        else:
            goodStates.add(arrangement)

goodStates = list(goodStates)
goodStates.sort()
with open('outputs.txt', 'w') as written:
    if not goodStates:
        written.write('IMPOSSIBLE\n')
    for state in goodStates:
        state = list(state)
        for v, i in enumerate(state):
            state[v] = 1 if i else 0
        written.write(''.join([str(s) for s in state]) + '\n')
