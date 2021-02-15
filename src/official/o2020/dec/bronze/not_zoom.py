"""
2020 dec bronze
2 2 11 4 9 7 9
should output 2 2 7
"""
from itertools import permutations


def number_combs(a, b, c):
    return [
        a + b,
        b + c,
        a + c,
        a + b + c
    ]


numbers = [int(i) for i in input().split()]
assert len(numbers) == 7, 'there should be only 7 numbers'

for triplet in permutations(numbers, 3):  # bro just brute force all 3 numbers lol
    the_rest = numbers.copy()
    for n in triplet:
        the_rest.remove(n)
    for s in number_combs(*triplet):
        if s not in the_rest:
            break
        the_rest.remove(s)
    else:
        print(*sorted(triplet))
        break
