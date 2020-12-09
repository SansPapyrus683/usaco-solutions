"""
ID: kevinsh4
TASK: palsquare
LANG: PYTHON3
"""
import string

UP_TO = 300


def int2base(x, base):
    """
    copied from link below:
    https://stackoverflow.com/questions/2267362/how-to-convert-an-integer-to-a-string-in-any-base
    (exactly the same as the one in dualpal)
    """
    assert x > 0, 'sorry man i only do nonnegative integers'

    digs = string.digits + string.ascii_uppercase
    if x == 0:
        return digs[0]

    digits = []
    while x:
        digits.append(digs[int(x % base)])
        x = x // base
    digits.reverse()

    return ''.join(digits)


with open('palSatan.txt') as read:
    convert_to = int(read.readline())

palSquares = []
for n in range(1, UP_TO + 1):
    squareBase = int2base(n ** 2, convert_to)
    if squareBase == squareBase[::-1]:
        palSquares.append(n)

with open('outputs.txt', 'w') as written:
    for n in palSquares:
        convertedN, convertedSquare = int2base(n, convert_to), int2base(n ** 2, convert_to)
        print(convertedN, convertedSquare)
        written.write(convertedN + ' ' + convertedSquare + '\n')
