"""
ID: kevinsh4
TASK: numtri
LANG: PYTHON3
"""
triangle = {}

with open('pascalsOrNo.txt') as read:
    for v, line in enumerate(read):
        if v != 0:
            triangle[v] = tuple([int(x) for x in line.rstrip().split()])
        else:
            rowNum = int(line.rstrip())


def triNeighbors(row, index):
    if row == rowNum:
        return []
    return [(row+1, index), (row+1, index+1)]  # just returns the positions of the neighbors


start = (1, 0)
frontier = [start]
costs = {start: triangle[1][0]}

while frontier:
    inLine = []
    for current in frontier:
        for neighbor in triNeighbors(*current):
            new_cost = costs[current] + triangle[neighbor[0]][neighbor[1]]
            if neighbor not in costs or new_cost > costs[neighbor]:
                costs[neighbor] = new_cost
                inLine.append(neighbor)
    frontier = inLine

with open('outputs.txt', 'w') as written:
    print(max([node[-1] for node in costs.items() if node[0][0] == rowNum]))
    written.write(str(max([node[-1] for node in costs.items() if node[0][0] == rowNum])) + '\n')
