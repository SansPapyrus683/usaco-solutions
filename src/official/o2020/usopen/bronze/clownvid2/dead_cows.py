# 2020 usopen bronze
cows = []
with open('socdist2.in') as read:
    read.readline()
    for c in read.readlines():
        cows.append([int(i) for i in c.rstrip().split()])
cows.sort()

radius_ubound = float('inf')  # upper bound for spread radius (not inclusive)
for i in range(1, len(cows)):
    dist = cows[i][0] - cows[i - 1][0]
    if cows[i][1] ^ cows[i - 1][1]:  # if they're different, we have a possible uppder bound!
        radius_ubound = min(radius_ubound, dist)

start_infected = 0
cow_at = 0
while cow_at < len(cows):  # see how many "infection islands" we have to have for the upper bound to do its magic
    if not cows[cow_at][1]:
        cow_at += 1
        continue
    while cow_at + 1 < len(cows) and cows[cow_at + 1][1] and cows[cow_at + 1][0] - cows[cow_at][0] < radius_ubound:
        cow_at += 1
    cow_at += 1
    start_infected += 1

print(start_infected)
with open('socdist2.out', 'w') as written:
    written.write(str(start_infected) + '\n')
