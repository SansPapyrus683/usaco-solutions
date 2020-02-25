"""
ID: kevinsh4
TASK: money
LANG: PYTHON3
"""
# followed the tutorial over here: https://www.mathblog.dk/project-euler-31-combinations-english-currency-denominations/
coins = []
with open('money.in') as read:
    for v, line in enumerate(read):
        if v == 0:
            amtToMake = int(line.rstrip().split()[1])
        else:
            for m in line.rstrip().split():
                coins.append(int(m))
            coins.sort()

wayToArray = {i: 0 for i in range(amtToMake + 1)}  # the amount of ways to make each amount
for v, a in enumerate(wayToArray):
    if v//coins[0] == v/coins[0]:
        wayToArray[v] += 1  # so we have a base

for c in coins:
    if c == coins[0]:
        continue  # did first one already
    for v, a in enumerate(wayToArray):
        if 0 <= (v - c):
            wayToArray[v] += wayToArray[v-c]

with open('money.out', 'w') as written:
    written.write(str(wayToArray[amtToMake]) + '\n')
