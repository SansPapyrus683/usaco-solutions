"""
look, i have no idea how this works
i guess it's something about the degenerate triangles but otherwise idk
2020 feb bronze
"""

from itertools import combinations

coordinates = []
with open('triangles.in') as read:
    for v, line in enumerate(read):
        if v > 0:
            coordinates.append(tuple([int(i) for i in line.rstrip().split()]))


def slope(p1, p2):
    try:
        s = -((p1[1] - p2[1]) / (p1[0] - p2[0]))
    except ZeroDivisionError:
        s = float("inf")
    return s


maxArea = 0
for (a, b, c) in combinations(coordinates, 3):
    if slope(a, b) == slope(b, c):  # stupid collinear things
        continue

    possArea = 0  # no idea why this code exists, maybe just to handle all the stupid edge cases
    if (a[0] == b[0] or b[0] == c[0] or a[0] == c[0]) and (a[1] == b[1] or b[1] == c[1] or a[1] == c[1]):
        if a[0] == b[0]:
            firstLen = abs(a[1] - b[1])
        elif b[0] == c[0]:
            firstLen = abs(b[1] - c[1])
        elif a[0] == c[0]:
            firstLen = abs(a[1] - c[1])

        if a[1] == b[1]:
            secondLen = abs(a[0] - b[0])
        elif b[1] == c[1]:
            secondLen = abs(b[0] - c[0])
        elif a[1] == c[1]:
            secondLen = abs(a[0] - c[0])
        possArea = firstLen * secondLen

    if possArea > maxArea:
        maxArea = possArea

with open('triangles.out', 'w') as written:
    written.write(str(maxArea) + '\n')
