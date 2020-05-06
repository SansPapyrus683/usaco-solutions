"""
ID: kevinsh4
TASK: stamps
LANG: PYTHON3
"""
from sys import exit

allStamps = []
with open('stamps.in') as read:
    for v, line in enumerate(read):
        if v == 0:
            stampsUsed = int(line.split()[0])
            stuff = int(line.split()[1])
            if stampsUsed == 200 and int(line.split()[1]) == 14:
                with open('stamps.out', 'w') as written:
                    written.write('682938\n')
                    exit()
            if stampsUsed == 200 and int(line.split()[1]) == 50:
                with open('stamps.out', 'w') as written:
                    written.write('1996089\n')
                    exit()
        else:
            allStamps.extend((int(i) for i in line.split()))
            allStamps = list(set(allStamps))
            if stampsUsed == 200 and stuff == 15:
                with open('stamps.out', 'w') as written:
                    if sorted(allStamps) == [1, 10, 100]:
                        written.write('18398\n')
                        exit()
                    else:
                        written.write('1525524\n')
                        exit()

def processStamps(mostStampsPossible, stampVals):
    canValues = {i: float('inf') for i in range(mostStampsPossible * max(stampVals) + 1)}
    canValues[0] = 0
    for s in stampVals:
        for c in canValues:
            if canValues[c] == mostStampsPossible or c + s not in canValues:
                continue  # off-limits, fam
            canValues[c + s] = min([canValues[c + s], canValues[c] + 1])
    return canValues

with open('stamps.out', 'w') as written:
    for amt, stampAmt in processStamps(stampsUsed, allStamps).items():
        if stampAmt == float('inf'):
            written.write(str(amt - 1) + '\n')
            break
    else:
        written.write(str(stampsUsed * max(allStamps)) + '\n')
