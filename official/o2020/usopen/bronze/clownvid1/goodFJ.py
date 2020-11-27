import sys
import math

with open('socdist1.in') as read:
    read.readline()
    stalls = [s == '1' for s in read.readline().rstrip()]

written = open('socdist1.out', 'w')
if not any(stalls):
    written.write(str(len(stalls) - 1) + '\n')
    sys.exit()

firstOcc = 0  # occ means occupied (to save line length lol)
while not stalls[firstOcc]:
    firstOcc += 1
lastOcc = len(stalls) - 1
while not stalls[lastOcc]:
    lastOcc -= 1
endDistance = len(stalls) - lastOcc - 1

singleCowDist = [firstOcc, endDistance]  # the max social distance if we only put 1 cow in the interval
doubleCowDist = [firstOcc // 2, endDistance // 2]  # well then, what about 2 cows?

baselineHighest = float('inf')
recentOcc = firstOcc
for i in range(firstOcc + 1, lastOcc + 1):
    if stalls[i] and i - recentOcc > 1:
        dist = i - recentOcc - 1
        baselineHighest = min(baselineHighest, dist + 1)  # fj can only make the distance so high i mean
        singleCowDist.append(math.ceil(dist / 2))  # treat these closed intervals differently from the open ones
        doubleCowDist.append(math.ceil((dist - 1) / 3))
    if stalls[i]:
        recentOcc = i


maxDistance = min(baselineHighest, max(1, max(sorted(singleCowDist)[-2], sorted(doubleCowDist)[-1])))
print(maxDistance)
written.write(str(maxDistance) + '\n')
