"""
ID: kevinsh4
TASK: money
LANG: PYTHON3
"""
# followed the tutorial over here: https://www.mathblog.dk/project-euler-31-combinations-english-currency-denominations/
coins = []
with open('cows_not_pigs.txt') as read:
    for v, line in enumerate(read):
        if v == 0:
            amt_to_make = int(line.rstrip().split()[1])
        else:
            for m in line.rstrip().split():
                coins.append(int(m))
            coins.sort()

way_to_array = {i: 0 for i in range(amt_to_make + 1)}  # the amount of ways to make each amount
for v, a in enumerate(way_to_array):
    if v // coins[0] == v / coins[0]:
        way_to_array[v] += 1  # so we have a base

for c in coins:
    if c == coins[0]:
        continue  # did first one already
    for v, a in enumerate(way_to_array):
        if 0 <= (v - c):
            way_to_array[v] += way_to_array[v - c]

with open('outputs.txt', 'w') as written:
    written.write(str(way_to_array[amt_to_make]) + '\n')
