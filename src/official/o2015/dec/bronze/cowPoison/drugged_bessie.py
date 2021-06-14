from functools import cmp_to_key


with open('badmilk.in') as read:
    initial = [int(i) for i in read.readline().split()]
    friend_num, milk_num, drink_num, dead_num = initial

    events = []
    drank_num = [set() for _ in range(milk_num)]
    for _ in range(drink_num):
        friend, milk, time = [int(i) for i in read.readline().split()]
        assert 1 <= friend <= friend_num
        assert 1 <= milk <= milk_num
        drank_num[milk - 1].add(friend - 1)
        events.append([True, time, friend - 1, milk - 1])
    
    for _ in range(dead_num):
        friend, time = [int(i) for i in read.readline().split()]
        assert 1 <= friend <= friend_num
        events.append([False, time, friend - 1])

# first sort time, then have all the sick people come first
cmp = lambda e1, e2: e1[1] - e2[1] if e1[1] != e2[1] else e1[0] - e2[0]
events.sort(key=cmp_to_key(cmp))

max_medicine = 0
for poss_bad in range(milk_num):
    drank_alr = set()
    for e in events:
        if e[0]:  # someone drank milk
            if e[3] == poss_bad:
                drank_alr.add(e[2])
        else:
            if e[2] not in drank_alr:
                break
    else:
        max_medicine = max(max_medicine, len(drank_num[poss_bad]))

print(max_medicine)
print(max_medicine, file=open('badmilk.out', 'w'))
