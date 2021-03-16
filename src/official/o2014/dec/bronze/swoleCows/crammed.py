# 2014 dec bronze
cows = []
with open('cowjog.in') as read:
    for _ in range(int(read.readline())):
        cows.append([int(i) for i in read.readline().split()])
cows.sort()

prev_speed = float('inf')
group_num = 0
for c in reversed(cows):
    if c[1] <= prev_speed:
        group_num += 1
    prev_speed = min(prev_speed, c[1])

print(group_num)
print(group_num, file=open('cowjog.out', 'w'))
