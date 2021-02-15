"""
ID: kevinsh4
TASK: transform
LANG: PYTHON3
"""


def reflect(square):
    return [r[::-1] for r in square]


def rotate(square, amt=1):
    """
    rotation credits from from this link:
    https://stackoverflow.com/questions/8421337/rotating-a-two-dimensional-array-in-python
    """
    for _ in range(amt):
        square = [''.join(r) for r in zip(*square[::-1])]
    return square


with open('optimus_prime.txt') as read:
    sideLen = int(read.readline())
    before = []
    for _ in range(sideLen):
        before.append(read.readline().rstrip())
    after = []
    for _ in range(sideLen):
        after.append(read.readline().rstrip())

transformation = 7
if rotate(before) == after:
    transformation = 1
elif rotate(before, 2) == after:
    transformation = 2
elif rotate(before, 3) == after:
    transformation = 3
elif reflect(before) == after:
    transformation = 4
elif before == after:
    transformation = 6
else:
    for i in range(1, 3 + 1):
        if rotate(reflect(before), i) == after:
            transformation = 5
            break

print(transformation)
with open('outputs.txt', 'w') as written:
    written.write(str(transformation) + '\n')
