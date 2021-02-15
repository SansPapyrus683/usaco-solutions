"""
ID: kevinsh4
LANG: PYTHON3
TASK: range
yeah this is too slow lol, but i'm going to leave it here
"""

with open('range.in') as read:
    for v, l in enumerate(read):
        if v == 0:
            field_width = int(l)
            grass = [[] for _ in range(int(l))]
        else:
            grass[v - 1] = [int(i) for i in l.rstrip()]

pref_grass = [[0 for _ in range(field_width + 1)] for _ in range(field_width + 1)]
square_sizes = {s: 0 for s in range(2, field_width + 1)}

# so this uses 2d prefix sums (i'm too lazy to explain it, just look it up)
# it basically sums submatrices in O(1) time, which optimizes it enough so it's probably fast enough
pref_grass[1][1] = grass[0][0]
for i in range(1, field_width + 1):
    pref_grass[1][i] = pref_grass[1][i - 1] + grass[0][i - 1]
    pref_grass[i][1] = pref_grass[i - 1][1] + grass[i - 1][0]
for r in range(2, field_width + 1):
    for c in range(2, field_width + 1):
        pref_grass[r][c] = (pref_grass[r - 1][c] +
                            pref_grass[r][c - 1] -
                            pref_grass[r - 1][c - 1] +
                            grass[r - 1][c - 1])

written = open('range.out', 'w')
for w in range(2, field_width + 1):
    target = w ** 2
    valid = 0
    for r in range(field_width - w + 1):
        for c in range(field_width - w + 1):
            if (pref_grass[r + w][c + w] - pref_grass[r][c + w] - pref_grass[r + w][c] + pref_grass[r][c]) == target:
                valid += 1
    if valid:
        print(f'{w} {valid}')
        written.write(f'{w} {valid}\n')
written.close()
