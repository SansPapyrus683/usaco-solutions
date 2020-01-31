triangle = {}

with open('pascalsOrNo.txt') as read:
    for v, line in enumerate(read):
        if v != 0:
            triangle[v] = tuple([int(x) for x in line.rstrip().split()])
print(triangle)


def triNeighbors(row, index):
    print(row+1)
    return triangle[row+1][index], triangle[row+1][index+1]

print(triNeighbors(2, 0))