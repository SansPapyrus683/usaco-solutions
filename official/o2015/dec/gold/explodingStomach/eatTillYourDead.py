# 2015 dec gold (pretty much 100% identical to the java sol)
with open('feast.in') as read:
    capacity, orange, lemon = [int(i) for i in read.read().split()]

afterWaterPossible = [False for _ in range(capacity + 1)]
withoutWaterPossible = [False for _ in range(capacity + 1)]
withoutWaterPossible[0] = True
maxCapacity = 0
for c in range(capacity + 1):
    if withoutWaterPossible[c]:
        maxCapacity = max(maxCapacity, c)
        afterWaterPossible[c // 2] = True
        if c + orange <= capacity:
            withoutWaterPossible[c + orange] = True
        if c + lemon <= capacity:
            withoutWaterPossible[c + lemon] = True

for c in range(capacity + 1):
    if afterWaterPossible[c]:
        maxCapacity = max(maxCapacity, c)
        if c + orange <= capacity:
            afterWaterPossible[c + orange] = True
        if c + lemon <= capacity:
            afterWaterPossible[c + lemon] = True

print(maxCapacity)
with open('feast.out', 'w') as written:
    written.write(str(maxCapacity) + '\n')
