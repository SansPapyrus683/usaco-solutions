"""
ID: kevinsh4
TASK: nuggets
LANG: PYTHON3
"""
MAX_SIZE = 256

with open('nuggets.in') as read:
    nuggets = [int(read.readline()) for _ in range(int(read.readline()))]
    assert all([n <= MAX_SIZE for n in nuggets])

# so given 2 relatively prime #'s the largest # that can't be formed is 1st * 2nd - (1st + 2nd)
# the MAX_SIZE + 50 is just some leeway
largestImpossible = MAX_SIZE * MAX_SIZE - (MAX_SIZE + MAX_SIZE)
possible = [False for _ in range(largestImpossible + MAX_SIZE)]
possible[0] = True
for i in range(len(possible)):
    if not possible[i]:
        continue
    for n in nuggets:
        if i + n < len(possible):
            possible[i + n] = True

maxImpossible = 0
for v, p in enumerate(possible):
    if not p:
        maxImpossible = v
if maxImpossible > largestImpossible:
    maxImpossible = 0

print(maxImpossible)
with open('nuggets.out', 'w') as written:
    written.write(str(maxImpossible) + '\n')
