"""
ID: kevinsh4
TASK: numtri
LANG: PYTHON3
"""
triangle = []

with open('numtri.in') as read:
    for v, line in enumerate(read):
        if v != 0:
            triangle.append([int(x) for x in line.split()])
        else:
            row_num = int(line)

for r in range(row_num - 2, -1, -1):  # iterate through the rows (excluding the bottom one) backwards
    for i in range(r + 1):
        triangle[r][i] += max(triangle[r + 1][i], triangle[r + 1][i + 1])

print(triangle[0][0])
print(triangle[0][0], file=open('numtri.out', 'w'))
