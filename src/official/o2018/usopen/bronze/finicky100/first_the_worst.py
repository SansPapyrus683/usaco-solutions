# 2018 usopen bronze
from sys import exit

SICK = 0


def valid_order(takenUp, order):
    if not order:
        return True

    setCows = set(takenUp)  # there's probably some -1's in there, but don't worry about that
    order_ind = {}
    cowAt = 0
    for v, c in enumerate(takenUp):
        if c == -1:
            if order[cowAt] in setCows:
                continue
            order_ind[order[cowAt]] = v
            cowAt += 1
        elif c == order[cowAt]:
            order_ind[order[cowAt]] = v
            cowAt += 1

        if cowAt == len(order):  # we've assigned all the cows
            break

    for i in range(len(order) - 1):
        if order_ind.get(order[i], -1) >= order_ind.get(order[i + 1], -1):
            return False
    return True


with open('milkorder.in') as read:
    cow_num, _, specific_num = [int(i) for i in read.readline().split()]
    rel_order = [int(i) - 1 for i in read.readline().split()]
    defined = [-1 for _ in range(cow_num)]
    for _ in range(specific_num):
        cow, pos = [int(i) - 1 for i in read.readline().split()]
        defined[pos] = cow

if SICK in defined:
    print(defined.index(SICK) + 1)
    print(defined.index(SICK) + 1, file=open('milkorder.out', 'w'))
    exit()

for v, c in enumerate(defined):
    if c == -1:
        defined[v] = SICK
        if valid_order(defined, rel_order):
            print(v + 1)
            print(v + 1, file=open('milkorder.out', 'w'))
            exit()
        defined[v] = -1
