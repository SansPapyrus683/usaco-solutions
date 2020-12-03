"""
ID: kevinsh4
TASK: fence9
LANG: PYTHON3
"""
from math import ceil, floor
from fractions import Fraction

with open('fence9.in') as read:
    x, y, end = [int(i) for i in read.read().rstrip().split()]
    assert x >= 0 and y > 0 and end > 0

totalCows = 0
height = Fraction(0) if x != 0 else Fraction(y)  # use decimal bc the normal floating points is dumb
if x != 0:
    firstSlope = Fraction(y, x)
    for i in range(x):
        height += firstSlope
        totalCows += ceil(height) - 1

if end > x:  # ok, we've gone up enough, now it's time to come down
    secondSlope = Fraction(y, end - x)
    for i in range(end - x):
        height -= secondSlope
        totalCows += max(ceil(height) - 1, 0)
else:
    if x == end:  # ok now worries, just subtract the dge
        totalCows -= ceil(height) - 1
    else:  # frick, we have to go again and subtract all the ones we overcounted
        secondSlope = Fraction(y, x - end)
        height = 0
        for i in range(x - end):
            height += secondSlope
            totalCows -= floor(height)  # floor because this time we factor in the upper boundary as well
        totalCows += 1

with open('fence9.out', 'w') as written:
    print(totalCows)
    written.write(str(totalCows) + "\n")
