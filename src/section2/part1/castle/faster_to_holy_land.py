"""
ID: kevinsh4
TASK: castle
LANG: PYTHON3
"""
from sys import exit

written = open('outputs.txt', 'w')
castle = []
walls = {}
with open('holyMusicStops.txt') as read:
    for v, row in enumerate(read):
        if v != 0:
            for x, col in enumerate(row.rstrip().split()):
                x += 1
                compo = [a for a in [1, 2, 4, 8] if a & int(col)]  # which walls? idek how this works but sure
                castle.append((v, x))
                walls[(v, x)] = set()
                for w in compo:
                    if w == 1:
                        walls[(v, x)].add((v, x - 1))
                    elif w == 2:
                        walls[(v, x)].add((v - 1, x))
                    elif w == 4:
                        walls[(v, x)].add((v, x + 1))  # wall between these two cells
                    elif w == 8:
                        walls[(v, x)].add((v + 1, x))  # zeroes mean they're on the border or smth like that
        else:
            max_width, max_height = [int(i) + 1 for i in row.rstrip().split()]


def room_neighbors(cell):
    poss = {c for c in
            [(cell[0] - 1, cell[1]), (cell[0] + 1, cell[1]), (cell[0], cell[1] - 1), (cell[0], cell[1] + 1)]
            if c not in walls[cell]}
    return poss


biggest = 0
color = 1
painted = {i: None for i in castle}
size_paints = {}
for mini in painted:
    if not painted[mini]:
        frontier = [mini]
        painted[mini] = color
        size_count = 1
        visited = {mini}
        while frontier:
            in_line = []
            for c in frontier:
                for n in room_neighbors(c):
                    if n not in visited:
                        in_line.append(n)
                        visited.add(n)
                        painted[n] = color
                        size_count += 1
            frontier = in_line
        if size_count > biggest:
            biggest = size_count
        size_paints[color] = size_count
        color += 1

written.write(str(color - 1) + '\n' + str(biggest) + '\n')  # color - 1 because after the last room, it's still +1
big_rooms = {}
for cell_walls in walls.items():
    for pair in [(cell_walls[0], w) for w in cell_walls[1]]:
        if not {max_width, 0}.intersection({i[1] for i in pair}) and \
                not {max_height, 0}.intersection({i[0] for i in pair}):
            if painted[pair[0]] != painted[pair[1]]:
                big_rooms[pair] = size_paints[painted[pair[0]]] + size_paints[painted[pair[1]]]

optimal_size = max(i for i in big_rooms.values())
written.write(str(max(i for i in big_rooms.values())) + '\n')
optimal_walls = [p for p in big_rooms if big_rooms[p] == optimal_size]
wall_cells = set()  # cells that have an optimal wall next to them
prime_for_output = {}
for w in optimal_walls:
    if w[0][1] != w[1][1]:  # the cells are in a other-right orientation
        wall_cells.add(w[0])
        prime_for_output[w[0]] = 'E'
    else:  # the cells are in a top-down orientation
        wall_cells.add(w[1])
        prime_for_output[w[1]] = 'N'

for x in range(1, max_width):
    for y in range(max_height - 1, 0, -1):
        if (y, x) in wall_cells:
            if ((y, x), (y - 1, x)) in optimal_walls:  # might, just might it be a top-down? we prioritize those
                written.write(str(y) + ' ' + str(x) + ' N\n')
                exit()
            written.write(str(y) + ' ' + str(x) + ' ' + prime_for_output[(y, x)] + '\n')
            exit()
