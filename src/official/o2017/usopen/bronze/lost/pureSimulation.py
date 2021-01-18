# 2017 usopen bronze
with open('lostcow.in') as read:
    farmerPos, bessiePos = [int(i) for i in read.readline().split()]

travelled = 0
lastAt = farmerPos
magnitude = 1
while True:
    target = farmerPos + magnitude
    if min(lastAt, target) <= bessiePos <= max(lastAt, target):
        travelled += abs(bessiePos - lastAt)
        break
    else:
        travelled += abs(target - lastAt)
    lastAt = target
    magnitude *= -2

print(travelled)
print(travelled, file=open('lostcow.out', 'w'))
