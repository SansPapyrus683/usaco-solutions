# 2019 feb bronze
TYPE_NUM = 4
with open('revegetate.in') as read:
    fieldNum, cowNum = [int(i) for i in read.readline().split()]
    diffReq = [[] for _ in range(fieldNum)]
    for _ in range(cowNum):
        field1, field2 = [int(i) - 1 for i in read.readline().split()]
        diffReq[field1].append(field2)
        diffReq[field2].append(field1)

lowest = [-1 for _ in range(TYPE_NUM)]
assigned = [-1 for _ in range(fieldNum)]
lowest[0] = 0  # arbitrarily assign field 1 to be of type 0
for f in range(fieldNum):
    taken = set()  # these are all the ones that have been assigned alr
    for n in diffReq[f]:
        taken.add(assigned[n])
    for t in range(TYPE_NUM):
        if t not in taken:  # if this one is unique, assign it
            assigned[f] = t
            break

plan = ''.join(str(t + 1) for t in assigned)
print(plan)
print(plan, file=open('revegetate.out', 'w'))
