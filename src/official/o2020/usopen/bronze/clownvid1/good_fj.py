import sys
import math

# 2020 usopen bronze
with open('socdist1.in') as read:
    read.readline()
    stalls = [s == '1' for s in read.readline().rstrip()]

written = open('socdist1.out', 'w')
if not any(stalls):
    written.write(str(len(stalls) - 1) + '\n')
    sys.exit()

first_occ = 0  # occ means occupied (to save line length lol)
while not stalls[first_occ]:
    first_occ += 1
last_occ = len(stalls) - 1
while not stalls[last_occ]:
    last_occ -= 1
end_dist = len(stalls) - last_occ - 1

single_dist = [first_occ, end_dist]  # the max social distance if we only put 1 cow in the interval
double_dist = [first_occ // 2, end_dist // 2]  # well then, what about 2 cows?

baseline_highest = float('inf')
recentOcc = first_occ
for i in range(first_occ + 1, last_occ + 1):
    if stalls[i] and i - recentOcc > 1:
        dist = i - recentOcc - 1
        baseline_highest = min(baseline_highest, dist + 1)  # fj can only make the distance so high i mean
        single_dist.append(math.ceil(dist / 2))  # treat these closed intervals differently from the open ones
        double_dist.append(math.ceil((dist - 1) / 3))
    if stalls[i]:
        recentOcc = i


maxDistance = min(baseline_highest, max(1, max(sorted(single_dist)[-2], sorted(double_dist)[-1])))
print(maxDistance)
written.write(str(maxDistance) + '\n')
