"""
ID: kevinsh4
TASK: stamps
LANG: PYTHON3
"""
# TODO: THIS IS TOO SLOW FOR MOST OF THE TEST CASES. ANY OPTIMIZATION TIPS? PLZ HELP

allStamps = []
with open('ISReallyHard.txt') as read:
    for v, line in enumerate(read):
        if v == 0:
            stampsUsed = int(line.split()[0])
        else:
            allStamps.extend((int(i) for i in line.split()))
            allStamps = list(set(allStamps))  # idk if this works but eh

def processStamps(mostStampsPossible, stampVals):
    canValues = [float('inf') for _ in range(mostStampsPossible * max(stampVals) + 1)]
    reachableOrSmth = {i for i in range(mostStampsPossible * max(stampVals) + 1)}
    canValues[0] = 0
    for s in stampVals:
        for v1, c in enumerate(canValues):
            if c == mostStampsPossible or v1 + s not in reachableOrSmth:
                continue  # off-limits, fam
            canValues[v1 + s] = min([canValues[v1 + s], c + 1])
    return canValues

with open('outputs.txt', 'w') as written:
    for amt, stampAmt in enumerate(processStamps(stampsUsed, allStamps)):
        if stampAmt == float('inf'):
            written.write(str(amt - 1) + '\n')
            break
    else:
        written.write(str(stampsUsed * max(allStamps)) + '\n')
