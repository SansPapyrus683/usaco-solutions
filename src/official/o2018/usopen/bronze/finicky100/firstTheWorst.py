# 2018 usopen bronze
from sys import exit

SICK = 0


def validOrder(takenUp, order):
    if not order:
        return True

    setCows = set(takenUp)  # there's probably some -1's in there, but don't worry about that
    orderIndices = {}
    cowAt = 0
    for v, c in enumerate(takenUp):
        if c == -1:
            if order[cowAt] in setCows:
                continue
            orderIndices[order[cowAt]] = v
            cowAt += 1
        elif c == order[cowAt]:
            orderIndices[order[cowAt]] = v
            cowAt += 1

        if cowAt == len(order):  # we've assigned all the cows
            break

    for i in range(len(order) - 1):
        if orderIndices.get(order[i], -1) >= orderIndices.get(order[i + 1], -1):
            return False
    return True


with open('milkorder.in') as read:
    cowNum, _, specificNum = [int(i) for i in read.readline().split()]
    relativeOrder = [int(i) - 1 for i in read.readline().split()]
    defined = [-1 for _ in range(cowNum)]
    for _ in range(specificNum):
        cow, pos = [int(i) - 1 for i in read.readline().split()]
        defined[pos] = cow

if SICK in defined:
    print(defined.index(SICK) + 1)
    print(defined.index(SICK) + 1, file=open('milkorder.out', 'w'))
    exit()

for v, c in enumerate(defined):
    if c == -1:
        defined[v] = SICK
        if validOrder(defined, relativeOrder):
            print(v + 1)
            print(v + 1, file=open('milkorder.out', 'w'))
            exit()
        defined[v] = -1
