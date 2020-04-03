"""
ID: kevinsh4
TASK: inflate
LANG: PYTHON3
"""
# NOTE: THIS DOES NOT WORK.
# I JUST IMPLEMENTED USACO'S APPROACH AND PYTHON IS JUST TOO SLOW.
from collections import defaultdict

problems = []
with open('loScores.txt') as read:
    for v, line in enumerate(read):
        if v == 0:
            timeLimit = int(line.split()[0])
        else:
            problems.append([int(i) for i in reversed(line.split())])  # [minutes, points]

bestPoints = defaultdict(lambda: 0)
for p in problems:
    for t in range(p[0], timeLimit + 1):
        bestPoints[t] = max(bestPoints[t], bestPoints[t - p[0]] + p[1])

with open('outputs.txt', 'w') as written:
    written.write(str(max(bestPoints.values())) + '\n')
