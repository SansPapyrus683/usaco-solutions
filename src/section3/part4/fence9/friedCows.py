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

total_cows = 0
height = Fraction(0) if x != 0 else Fraction(y)  # use decimal bc the normal floating point is dumb
if x != 0:
    first_slope = Fraction(y, x)
    for i in range(x):
        height += first_slope
        total_cows += ceil(height) - 1

if end > x:  # ok, we've gone up enough, now it's time to come down
    second_slope = Fraction(y, end - x)
    for i in range(end - x):
        height -= second_slope
        total_cows += max(ceil(height) - 1, 0)
else:
    if x == end:  # ok no worries, just subtract the edge
        total_cows -= ceil(height) - 1
    else:  # frick, we have to go again and subtract all the ones we overcounted
        second_slope = Fraction(y, x - end)
        height = 0
        for i in range(x - end):
            height += second_slope
            total_cows -= floor(height)  # floor because this time we factor in the upper boundary as well
        total_cows += 1

with open('fence9.out', 'w') as written:
    print(total_cows)
    written.write(str(total_cows) + "\n")
