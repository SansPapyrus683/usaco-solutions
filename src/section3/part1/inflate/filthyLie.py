"""
ID: kevinsh4
TASK: inflate
LANG: PYTHON3
"""
# TODO: THIS IS WAY TOO SLOW.
#  I JUST IMPLEMENTED USACO'S APPROACH AND PYTHON IS JUST TOO SLOW.
#  SO PLZ TELL ME HOW TO OPTIMIZE THIS DARN THING
from collections import defaultdict

problems = []
with open('loScores.txt') as read:
    for v, line in enumerate(read):
        if v == 0:
            timeLimit = int(line.split()[0])
        else:
            problems.append([int(i) for i in reversed(line.split())])  # [minutes, points]

def calcBest(limit, probList):
    bestPoints = defaultdict(lambda: 0)
    for p in probList:
        for t in range(p[0], limit + 1):
            bestPoints[t] = max([bestPoints[t], bestPoints[t - p[0]] + p[1]])
    return bestPoints

with open('outputs.txt', 'w') as written:
    written.write(str(max(calcBest(timeLimit, problems).values())) + '\n')
