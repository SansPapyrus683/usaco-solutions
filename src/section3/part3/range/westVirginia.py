"""
ID: kevinsh4
LANG: PYTHON3
TASK: range
yeah this is too slow lol, but i'm going to leave it here
"""

with open('range.in') as read:
    for v, l in enumerate(read):
        if v == 0:
            fieldWidth = int(l)
            grass = [[] for _ in range(int(l))]
        else:
            grass[v - 1] = [int(i) for i in l.rstrip()]

grassSoFar = [[0 for _ in range(fieldWidth + 1)] for _ in range(fieldWidth + 1)]
squareSizes = {s: 0 for s in range(2, fieldWidth + 1)}

# so this uses 2d prefix sums (i'm too lazy to explain it, just look it up)
# it basically sums submatrices in O(1) time, which optimizes it enough so it's probably fast enough
grassSoFar[1][1] = grass[0][0]
for i in range(1, fieldWidth + 1):
    grassSoFar[1][i] = grassSoFar[1][i - 1] + grass[0][i - 1]
    grassSoFar[i][1] = grassSoFar[i - 1][1] + grass[i - 1][0]
for r in range(2, fieldWidth + 1):
    for c in range(2, fieldWidth + 1):
        grassSoFar[r][c] = (grassSoFar[r - 1][c] +
                            grassSoFar[r][c - 1] -
                            grassSoFar[r - 1][c - 1] +
                            grass[r - 1][c - 1])

written = open('range.out', 'w')
for w in range(2, fieldWidth + 1):
    targetSum = w ** 2
    numberValid = 0
    for r in range(fieldWidth - w + 1):
        for c in range(fieldWidth - w + 1):
            if (grassSoFar[r + w][c + w] - grassSoFar[r][c + w] - grassSoFar[r + w][c] + grassSoFar[r][c]) == targetSum:
                numberValid += 1
    if numberValid:
        print(f'{w} {numberValid}')
        written.write(f'{w} {numberValid}\n')
written.close()
