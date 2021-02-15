"""
ID: kevinsh4
TASK: frac1
LANG: PYTHON3
"""
from fractions import Fraction

with open('metGala.txt') as read:
    limit = int(read.read().rstrip())

good_fractions = {Fraction(1, 1)}
for i in range(1, limit + 1):
    for x in range(i):
        good_fractions.add(Fraction(x, i))

print(good_fractions)
with open('outputs.txt', 'w') as written:
    good_fractions = list(good_fractions)
    for v, frac in enumerate(sorted(good_fractions)):
        if v not in [0, len(good_fractions) - 1]:
            written.write(str(frac) + '\n')
        else:
            if v == 0:
                written.write('0/1\n')
            elif v == len(good_fractions) - 1:
                written.write('1/1\n')
