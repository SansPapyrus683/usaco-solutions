from functools import reduce
from operator import mul

missing = []
with open('nocow.in') as read:
    adjNum, cowToFind = [int(i) for i in read.readline().split()]
    for _ in range(adjNum):
        missing.append(read.readline().lower().replace('farmer john has no ', '').replace(' cow.', '').split())
assert len(set(len(c) for c in missing)) == 1, 'all the cows should have the same # of adjectives'

adjectives = []
for col in zip(*missing):
    adjectives.append(sorted(set(col)))

missingIDs = []  # like, if they were all there, what would the IDs be? (the IDs start from 1)
# missing.append(['small', 'spotted', 'noisy'])
adjAmts = [len(a) for a in adjectives]
totalAmt = reduce(mul, adjAmts)
for c in missing:
    hypotheticalID = 1
    product = totalAmt
    for i in range(len(adjectives)):
        product //= adjAmts[i]
        hypotheticalID += adjectives[i].index(c[i]) * product
    missingIDs.append(hypotheticalID)
missingIDs.sort()

cowMissing = 0
intervals = [0] + missingIDs
kthCowNo = -1
for v, m in enumerate(intervals):
    hypothetical = cowToFind + cowMissing
    if v == len(intervals) - 1 or hypothetical < intervals[v + 1]:
        kthCowNo = hypothetical
        break
    cowMissing += 1

product = totalAmt // adjAmts[0]
adjIndices = []
cowNoLeft = kthCowNo - 1  # turning it to 0-based so the modulus, division, and other stuff work correctly
for i in range(len(adjectives)):
    adjIndices.append(cowNoLeft // product)
    cowNoLeft %= product
    if i < len(adjectives) - 1:
        product //= adjAmts[i + 1]

foundCow = ' '.join(adjectives[v][i] for v, i in enumerate(adjIndices))
print(foundCow)
print(foundCow, file=open('nocow.out', 'w'))
