"""
2020 dec silver
4
1 2
1 3
1 4 should output 5
"""
from collections import deque


# straight from stackoverflow
def ceiLog2(x):  # returns the smallest n so that 2^n >= x
    return 1 if x == 0 else (x - 1).bit_length()


farmNum = int(input())
neighbors = [[] for _ in range(farmNum)]
for _ in range(farmNum - 1):
    start, end = [int(i) - 1 for i in input().split()]
    neighbors[start].append(end)
    neighbors[end].append(start)

minDays = 0
visited = [False for _ in range(farmNum)]
frontier = deque([0])
visited[0] = True
while frontier:
    curr = frontier.popleft()
    # this stores all the farms that this farm should spread to for optimal spreading
    spreadTo = 0
    for n in neighbors[curr]:
        if not visited[n]:
            spreadTo += 1
            visited[n] = True
            frontier.append(n)
    # the log base 2 is so enough superspreader events go around so that there are enough cows, and then we need to add the length of spreadTo so the cows can actually go to the other farms
    minDays += ceiLog2(spreadTo + 1) + spreadTo

print(minDays)
