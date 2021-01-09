"""
ID: kevinsh4
TASK: skidesign
LANG: PYTHON3
"""
MAX_DIFF = 17

with open('skidesign.in') as read:
    hills = [int(read.readline()) for _ in range(int(read.readline()))]

lowestCost = float('inf')
for possLowest in range(min(hills), max(hills) - MAX_DIFF + 1):  # +1 because range
    cost = 0
    for h in hills:
        if h < possLowest:
            cost += (possLowest - h) ** 2
        elif h > possLowest + 17:
            cost += (h - (possLowest + 17)) ** 2
    if cost < lowestCost:
        lowestCost = cost

print(lowestCost)
print(lowestCost, file=open('outputs.txt', 'w'))
