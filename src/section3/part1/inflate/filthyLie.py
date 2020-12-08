"""
ID: kevinsh4
TASK: inflate
LANG: PYTHON3
too slow for some of the test cases lol
"""
from typing import List


def calcBest(limit: int, probList: List[List[int]]) -> int:
    bestPoints = [0 for _ in range(limit + 1)]
    for p in probList:
        for t in range(p[1], limit + 1):
            # we can use the old amt of points from before this problem and add this problem's point amt
            bestPoints[t] = max(bestPoints[t], bestPoints[t - p[1]] + p[0])
    return max(bestPoints)


problems = []
with open('loScores.txt') as read:
    for v, line in enumerate(read):
        if v == 0:
            timeLimit = int(line.split()[0])
        else:
            problems.append([int(i) for i in line.split()])  # points, minutes

totalBest = calcBest(timeLimit, problems)
print(totalBest)
with open('outputs.txt', 'w') as written:
    written.write(str(totalBest) + '\n')
