"""
ID: kevinsh4
TASK: money
LANG: PYTHON3
"""
coins = []
with open('cowsNotPigs.txt') as read:
    for v, line in enumerate(read):
        if v == 0:
            amtToMake = int(line.rstrip().split()[1])
        else:
            for m in line.rstrip().split():
                coins.append(int(m))

print(amtToMake, coins)
