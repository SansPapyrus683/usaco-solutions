# 2017 us open bronze
with open('lostcow.in') as read:
    farmer_pos, bessie_pos = [int(i) for i in read.readline().split()]

travelled = 0
last_at = farmer_pos
magnitude = 1
while True:
    target = farmer_pos + magnitude
    if min(last_at, target) <= bessie_pos <= max(last_at, target):
        travelled += abs(bessie_pos - last_at)
        break
    else:
        travelled += abs(target - last_at)
    last_at = target
    magnitude *= -2

print(travelled)
print(travelled, file=open('lostcow.out', 'w'))
