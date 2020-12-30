"""
2020 dec bronze
2 2 11 4 9 7 9
should output 2 2 7
"""
from itertools import permutations


def numberCombs(a, b, c):
    return [
        a + b,
        b + c,
        a + c,
        a + b + c
    ]


numbers = [int(i) for i in input().split()]
assert len(numbers) == 7, 'there should be only 7 numbers'

for triplet in permutations(numbers, 3):  # bro just brute force all 3 numbers lol
    theRest = numbers.copy()
    for n in triplet:
        theRest.remove(n)
    for s in numberCombs(*triplet):
        if s not in theRest:
            break
        theRest.remove(s)
    else:
        print(*sorted(triplet))
        break
