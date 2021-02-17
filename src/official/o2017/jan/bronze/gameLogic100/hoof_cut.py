# 2017 jan bronze
from itertools import permutations

OP_NUM = 3

matches = [[0 for _ in range(OP_NUM)] for _ in range(OP_NUM)]
with open('hps.in') as read:
    for _ in range(int(read.readline())):
        first, second = [int(i) - 1 for i in read.readline().split()]
        matches[first][second] += 1

max_wins = 0
# what if hypothetically, we have a beat b, b beat c, and c beat a?
for a, b, c in permutations(range(OP_NUM)):
    max_wins = max(max_wins, sum([matches[a][b], matches[b][c], matches[c][a]]))

print(max_wins)
print(max_wins, file=open('hps.out', 'w'))
