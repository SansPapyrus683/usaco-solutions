"""
ID: kevinsh4
TASK: milk2
LANG: PYTHON3
"""

with open('milk2.in') as read:
    intervals = []
    for i in range(int(read.readline())):
        intervals.append([int(i) for i in read.readline().rstrip().split()])

intervals.sort()
print(intervals)
maxMilkingTime = 0
maxPassiveTime = 0
start = intervals[0][0]
end = intervals[0][1]
for i in intervals[1:]:
    if i[0] > end:
        maxPassiveTime = max(maxPassiveTime, i[0] - end)
        maxMilkingTime = max(maxMilkingTime, end - start)
        start = i[0]
        end = i[1]
    else:
        end = max(end, i[1])
maxMilkingTime = max(maxMilkingTime, end - start)

with open('milk2.out', 'w') as written:
    print(maxMilkingTime, maxPassiveTime)
    written.write(str(maxMilkingTime) + " " + str(maxPassiveTime) + "\n")
