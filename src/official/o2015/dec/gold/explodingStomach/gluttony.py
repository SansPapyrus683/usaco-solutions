# 2015 dec gold (pretty much 100% identical to the java sol)
with open('feast.in') as read:
    capacity, orange, lemon = [int(i) for i in read.read().split()]

after_water_poss = [False for _ in range(capacity + 1)]
without_water_poss = [False for _ in range(capacity + 1)]
without_water_poss[0] = True
max_cap = 0
for c in range(capacity + 1):
    if without_water_poss[c]:
        max_cap = max(max_cap, c)
        after_water_poss[c // 2] = True
        if c + orange <= capacity:
            without_water_poss[c + orange] = True
        if c + lemon <= capacity:
            without_water_poss[c + lemon] = True

for c in range(capacity + 1):
    if after_water_poss[c]:
        max_cap = max(max_cap, c)
        if c + orange <= capacity:
            after_water_poss[c + orange] = True
        if c + lemon <= capacity:
            after_water_poss[c + lemon] = True

print(max_cap)
print(max_cap, file=open('feast.out', 'w'))
