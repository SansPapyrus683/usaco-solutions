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
            row_num = int(line.rstrip())

for r in range(row_num - 1, -1, -1):  # iterate through the rows backwards
    if r == row_num - 1:  # we don't process the bottom one
        continue
    for i in range(r+1):
        if triangle[r+1][i] < triangle[r+1][i+1]:
            triangle[r][i] += triangle[r+1][i+1]  # adds the greater number from the bottom to the current one
        else:
            triangle[r][i] += triangle[r+1][i]  # if they were the same, it doesn't really matter which one we choose

with open('outputs.txt', 'w') as written:
    written.write(str(triangle[0][0]) + '\n')
