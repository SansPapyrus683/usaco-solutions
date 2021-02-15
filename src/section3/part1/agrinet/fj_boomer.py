"""
ID: kevinsh4
TASK: agrinet
LANG: PYTHON3
"""
from collections import defaultdict

distances = defaultdict(lambda: defaultdict(lambda: float('inf')))
with open('tik_tok_bad.txt') as read:
    for v, line in enumerate(read):
        if v == 0:
            farm_num = int(line.rstrip())
            compare_count = 1
            adj_farm = 1
        else:
            for i in line.rstrip().split():
                if adj_farm != compare_count:  # don't put a node against itself
                    distances[adj_farm][compare_count] = int(i)
                compare_count += 1
                if compare_count > farm_num:  # ok, we compared enough, let's shift farms
                    compare_count = 1
                    adj_farm += 1
    distances = {f: dict(distances[f]) for f in distances}  # if you wanna print it, makes it more readable

cable_len = 0
farms_in = [1]  # it doesn't matter which node we start at so just node 1
for _ in range(farm_num):
    connections = []
    for f in farms_in:
        for n in distances[f]:
            if n not in farms_in:  # get all possible connections (that are necessary)
                connections.append((f, n, distances[f][n]))

    minimum_cable = min([pc[-1] for pc in connections]) if connections else None
    for c in connections:  # now we actually add the minimum connections
        if c[-1] == minimum_cable:  # pc means possible connection
            cable_len += c[-1]
            farms_in.append(c[1])
            break

with open('outputs.txt', 'w') as written:
    written.write('%i\n' % cable_len)
