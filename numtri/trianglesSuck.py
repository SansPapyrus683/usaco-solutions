"""
ID: kevinsh4
TASK: numtri
LANG: PYTHON3
"""
triangle = []

with open('pascalsOrNo.txt') as read:
    for v, line in enumerate(read):
        if v != 0:
            triangle.append([int(x) for x in line.rstrip().split()])
        else:
            rowNum = int(line.rstrip())

for r in range(rowNum - 1, -1, -1):  # iterate through the rows backwards
    if r == rowNum - 1:
        continue
    for i in range(r+1):
        if triangle[r+1][i] < triangle[r+1][i+1]:
            triangle[r][i] += triangle[r+1][i+1]
        else:
            triangle[r][i] += triangle[r+1][i]  # if they were the same, it doesn't really matter which one we choose

with open('outputs.txt', 'w') as written:
    written.write(str(triangle[0][0]) + '\n')
