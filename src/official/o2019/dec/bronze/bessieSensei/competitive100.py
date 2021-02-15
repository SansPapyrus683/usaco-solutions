# 2019 dec bronze
from itertools import combinations

with open('gymnastics.in') as read:
    sessionNum, cowNum = [int(i) for i in read.readline().split()]
    sessions = [[int(i) - 1 for i in read.readline().split()] for _ in range(sessionNum)]

consistent = 0
for c1, c2 in combinations(range(cowNum), 2):
    # this is really inefficient but given the bounds, it doesn't really matter
    consistent += all(s.index(c1) < s.index(c2) for s in sessions) or \
                  all(s.index(c1) > s.index(c2) for s in sessions)

print(consistent)
print(consistent, file=open('gymnastics.out', 'w'))
