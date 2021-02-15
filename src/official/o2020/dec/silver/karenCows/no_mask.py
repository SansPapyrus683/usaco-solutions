"""
2020 dec silver
4
1 2
1 3
1 4 should output 5
"""
from collections import deque


# straight from stackoverflow
def ceil_log2(x):  # returns the smallest n so that 2^n >= x
    return 1 if x == 0 else (x - 1).bit_length()


farm_num = int(input())
neighbors = [[] for _ in range(farm_num)]
for _ in range(farm_num - 1):
    start, end = [int(i) - 1 for i in input().split()]
    neighbors[start].append(end)
    neighbors[end].append(start)

min_days = 0
visited = [False for _ in range(farm_num)]
frontier = deque([0])
visited[0] = True
while frontier:
    curr = frontier.popleft()
    # this stores the number of farms that this farm should spread to for optimal spreading
    spread_to = 0
    for n in neighbors[curr]:
        if not visited[n]:
            spread_to += 1
            visited[n] = True
            frontier.append(n)
    # the log base 2 is so enough superspreader events go around so that there are enough cows,
    # and then we need to add the length of spreadTo so the cows can actually go to the other farms
    min_days += ceil_log2(spread_to + 1) + spread_to

print(min_days)
