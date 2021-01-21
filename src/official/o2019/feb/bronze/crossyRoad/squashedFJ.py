# 2019 feb bronze
from itertools import groupby

ON = 'on'
OFF = 'off'
NONE = 'none'


def intersection(a, b):
    if a[0] > a[1] or b[0] > b[1]:
        raise ValueError("one of the intervals you gave me is invalid")
    if a[0] > b[0]:  # make sure a always occurs before b
        return intersection(b, a)

    return None if a[1] < b[0] else [b[0], min(a[1], b[1])]


sensors = []
with open('traffic.in') as read:
    for _ in range(int(read.readline())):
        reading = read.readline().split()
        sensors.append([reading[0].lower(), [int(reading[1]), int(reading[2])]])
        if sensors[-1][0] not in [ON, OFF, NONE]:
            raise ValueError('uh what kind of reading is %s' % sensors[-1][0])
if NONE not in [s[0] for s in sensors]:
    raise ValueError("sorry but if i don't have any none's i can't deduce anything lol")

startPoss = []
endPoss = []
# very hacky and idek if going through all the none's are necessary but hey
for i in range(len(sensors)):
    if sensors[i][0] != NONE:
        continue

    start = sensors[i][1].copy()
    for j in range(i - 1, -1, -1):
        if sensors[j][0] == NONE:  # just take the intersection, nothing too difficult
            start = intersection(start, sensors[j][1])
        elif sensors[j][0] == ON:  # we subtract some cars from the upper and lower bound because we're going backwards
            start[0] -= sensors[j][1][1]
            start[1] -= sensors[j][1][0]
        elif sensors[j][0] == OFF:  # the converse- we add some cards
            start[0] += sensors[j][1][0]
            start[1] += sensors[j][1][1]
        start = [max(i, 0) for i in start]
    startPoss.append(start)

    end = sensors[i][1].copy()
    for j in range(i + 1, len(sensors)):
        if sensors[j][0] == NONE:
            end = intersection(end, sensors[j][1])
        elif sensors[j][0] == ON:
            end[0] += sensors[j][1][0]
            end[1] += sensors[j][1][1]
        elif sensors[j][0] == OFF:
            end[0] -= sensors[j][1][1]
            end[1] -= sensors[j][1][0]
        end = [max(i, 0) for i in end]
    endPoss.append(end)

finalStart = startPoss[0]
for p in startPoss[1:]:
    finalStart = intersection(finalStart, p)
finalEnd = endPoss[0]
for p in endPoss[1:]:
    finalEnd = intersection(finalEnd, p)

print(finalStart, finalEnd)
print("%i %i\n%i %i" % (finalStart[0], finalStart[1], finalEnd[0], finalEnd[1]), file=open('traffic.out', 'w'))
