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
    firstN, numberAt = [int(i) for i in read.readline().split()]

dualPals = []
while len(dualPals) < firstN:
    numberAt += 1
    reprs = [int2base(numberAt, b) for b in range(2, 10 + 1)]
    palCount = 0
    for r in reprs:
        if not r.endswith('0') and r[::-1] == r:
            palCount += 1
    if palCount >= 2:
        dualPals.append(numberAt)

print(dualPals)
with open('outputs.txt', 'w') as written:
    written.write('\n'.join([str(i) for i in dualPals]) + '\n')
