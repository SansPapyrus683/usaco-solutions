with open('speeding.in') as read:
    seg_num, speed_num = [int(i) for i in read.readline().split()]
    segs = []
    for _ in range(seg_num):
        segs.append([int(i) for i in read.readline().split()])
    speeds = []
    for _ in range(speed_num):
        speeds.append([int(i) for i in read.readline().split()])
assert sum(s[0] for s in segs) == sum(s[0] for s in speeds)

# definitely a more efficient way to do this but this way is easier so...
allowed = []
for s in segs:
    for _ in range(s[0]):
        allowed.append(s[1])

actual_speed = []
for s in speeds:
    for _ in range(s[0]):
        actual_speed.append(s[1])

worst_infrac = 0
for limit, actual in zip(allowed, actual_speed):
    worst_infrac = max(worst_infrac, actual - limit)

print(worst_infrac, file=open('speeding.out', 'w'))
print(worst_infrac)
