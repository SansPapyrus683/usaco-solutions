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
max_milking_time = 0
max_passive_time = 0
start = intervals[0][0]
end = intervals[0][1]
for i in intervals[1:]:
    if i[0] > end:
        max_passive_time = max(max_passive_time, i[0] - end)
        max_milking_time = max(max_milking_time, end - start)
        start = i[0]
        end = i[1]
    else:
        end = max(end, i[1])
max_milking_time = max(max_milking_time, end - start)

print(max_milking_time, max_passive_time)
print(max_milking_time, max_passive_time, file=open('milk2.out', 'w'))
