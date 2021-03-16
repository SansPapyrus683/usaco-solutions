# 2014 dec bronze (could be compressed but i don't want super long lines)
from typing import List


def dist(p1: List[int], p2: List[int]) -> int:
    return abs(p1[0] - p2[0]) + abs(p1[1] - p2[1])


checkpoints = []
with open('marathon.in') as read:
    for _ in range(int(read.readline())):
        checkpoints.append([int(i) for i in read.readline().split()])

total_dist = 0
for i in range(len(checkpoints) - 1):
    total_dist += dist(checkpoints[i], checkpoints[i + 1])

max_saved = 0
for i in range(1, len(checkpoints) - 1):
    max_saved = max(
        max_saved,
        dist(checkpoints[i - 1], checkpoints[i])
        + dist(checkpoints[i], checkpoints[i + 1])
        - dist(checkpoints[i - 1], checkpoints[i + 1])
    )

min_dist = total_dist - max_saved
print(min_dist)
print(min_dist, file=open('marathon.out', 'w'))
