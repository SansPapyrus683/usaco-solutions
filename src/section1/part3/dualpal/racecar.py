"""
ID: kevinsh4
TASK: dualpal
LANG: PYTHON3
"""
import string


def int2base(x, base):
    """
    copied from link below:
    https://stackoverflow.com/questions/2267362/how-to-convert-an-integer-to-a-string-in-any-base
    """
    assert x > 0, 'sorry man i only do nonnegative integers'
    digs = string.digits
    if x == 0:
        return digs[0]
    digits = []

    while x:
        digits.append(digs[int(x % base)])
        x = x // base

    return ''.join(reversed(digits))


with open('sameThing.txt') as read:
    first_n, number_at = [int(i) for i in read.readline().split()]

dual_pals = []
while len(dual_pals) < first_n:
    number_at += 1
    reprs = [int2base(number_at, b) for b in range(2, 10 + 1)]
    palCount = 0
    for r in reprs:
        if not r.endswith('0') and r[::-1] == r:
            palCount += 1
    if palCount >= 2:
        dual_pals.append(number_at)

print(dual_pals)
print('\n'.join([str(i) for i in dual_pals]), file=open('outputs.txt', 'w'))
