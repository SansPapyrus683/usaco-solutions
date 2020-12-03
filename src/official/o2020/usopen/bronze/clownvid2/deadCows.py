# 2020 usopen bronze
cows = []
with open('socdist2.in') as read:
    read.readline()
    for c in read.readlines():
        cows.append([int(i) for i in c.rstrip().split()])
cows.sort()

radiusUBound = float('inf')  # upper bound for spread radius (not inclusive)
for i in range(1, len(cows)):
    dist = cows[i][0] - cows[i - 1][0]
    if cows[i][1] ^ cows[i - 1][1]:  # if they're different, we have a possible uppder bound!
        radiusUBound = min(radiusUBound, dist)

startInfected = 0
cowAt = 0
while cowAt < len(cows):  # see how many "infection islands" we have to have for the upper bound to do its magic
    if not cows[cowAt][1]:
        cowAt += 1
        continue
    while cowAt + 1 < len(cows) and cows[cowAt + 1][1] and cows[cowAt + 1][0] - cows[cowAt][0] < radiusUBound:
        cowAt += 1
    cowAt += 1
    startInfected += 1

print(startInfected)
with open('socdist2.out', 'w') as written:
    written.write(str(startInfected) + '\n')
