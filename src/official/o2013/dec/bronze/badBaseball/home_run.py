from bisect import bisect_left, bisect_right

with open('baseball.in') as read:
    cows = sorted(int(read.readline()) for _ in range(int(read.readline())))

poss_pairs = 0
for i in range(len(cows)):
    for j in range(i + 1, len(cows)):
        dist = cows[j] - cows[i]
        closest = bisect_left(cows, cows[j] + dist)  # closest cow that the second one could've thrown it to
        farthest = bisect_right(cows, cows[j] + 2 * dist)  # and then the farthest one
        poss_pairs += farthest - closest  # that cow could've thrown it to ALL the cows between those 2 (inclusive, ofc)

print(poss_pairs)
print(poss_pairs, file=open('baseball.out', 'w'))
