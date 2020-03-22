"""
ID: kevinsh4
TASK: cowtour
LANG: PYTHON3
"""
with open('notTouristsDumbo.txt') as read:
    read = [l.rstrip() for l in read.readlines()]
    connection = {p: [] for p in range(1, int(read[0]) + 1)}
    locations = {}
    for v, loc in enumerate(read[1:len(connection) + 1]):
        locations[v+1] = tuple([int(c) for c in loc.split()])
    for v, adj in enumerate(read[len(connection) + 1:]):
        for otherV, i in enumerate(adj):
            if int(i):
                connection[v+1].append(otherV + 1)
    print(connection)
