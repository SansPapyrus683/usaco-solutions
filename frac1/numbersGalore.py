"""
ID: kevinsh4
TASK: frac1
LANG: PYTHON3
"""
from fractions import Fraction

with open('metGala.txt') as read:
    limit = int(read.read().rstrip())

goodFractions = {Fraction(1, 1)}
for i in range(1, limit + 1):
    for x in range(i):
        goodFractions.add(Fraction(x, i))

print(goodFractions)
with open('outputs.txt', 'w') as written:
    goodFractions = list(goodFractions)
    for v, frac in enumerate(sorted(goodFractions)):
        if v not in [0, len(goodFractions)-1]:
            written.write(str(frac) + '\n')
        else:
            if v == 0:
                written.write('0/1\n')
            elif v == len(goodFractions) - 1:
                written.write('1/1\n')
