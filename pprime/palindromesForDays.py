"""
ID: kevinsh4
TASK: pprime
LANG: PYTHON3
"""
from math import sqrt, ceil
from itertools import count, islice, permutations

with open('inputtupin.txt') as inp:
    minVal, maxVal = [int(i) for i in inp.read().rstrip().split()]
    palLengths = [i for i in range(len(str(minVal)), len(str(maxVal)) + 1)]


def primeCheck(n: int) -> bool:  # same one i used for sprime
    return n > 1 and all(n % i for i in islice(count(2), int(sqrt(n) - 1)))


digits = [i for i in range(10)]
toCheck = []
for l in palLengths:
    print('processing length %i' % l)
    if l != 1:
        digPerms = permutations(digits * l, ceil(l/2))
    else:
        digPerms = [[2], [3], [5], [7]]
    if l % 2:  # odd number of digits
        for pal in digPerms:  # this is just the structure of a palindrome
            if l == 1:
                actualPal = int(pal[0])  # TODO: make the for pal in digPerms loop the main loop and do the digit test
                                        # then
            else:
                actualPal = int(''.join([str(i) for i in pal + pal[1::-1]]))
            if l == 7:
                print(actualPal)
    else:  # even number of digits
        for pal in digPerms:
            actualPal = int(''.join([str(i) for i in pal + pal[::-1]]))

