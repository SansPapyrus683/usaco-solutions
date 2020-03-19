"""
ID: kevinsh4
TASK: ttwo
LANG: PYTHON3
"""
from sys import exit

points = set()  # set bc fast lookup
turning = {'up': 'right', 'right': 'down', 'down': 'left', 'left': 'up'}  # turn to what?
with open('mentalWard.txt') as read:
    for y, line in enumerate(read):
        for x, c in enumerate(line.rstrip()):
            if c != '*':  # don't add if obstacle
                points.add((x, y))
                if c == 'F':
                    farmer = originalF = (x, y, 'up')  # default orientation is up
                elif c == 'C':
                    cow = originalC = (x, y, 'up')

def move():
    new = []
    for e in [farmer, cow]:
        if e[-1] == 'up':  # add moved if valid, if not just add the turned version of the point
            if (e[0], e[1] - 1) not in points:
                new.append((e[0], e[1], turning[e[-1]]))
            else:
                new.append((e[0], e[1] - 1, e[-1]))
        elif e[-1] == 'down':
            if (e[0], e[1] + 1) not in points:
                new.append((e[0], e[1], turning[e[-1]]))
            else:
                new.append((e[0], e[1] + 1, e[-1]))
        elif e[-1] == 'left':
            if (e[0] - 1, e[1]) not in points:
                new.append((e[0], e[1], turning[e[-1]]))
            else:
                new.append((e[0] - 1, e[1], e[-1]))
        elif e[-1] == 'right':
            if (e[0] + 1, e[1]) not in points:
                new.append((e[0], e[1], turning[e[-1]]))
            else:
                new.append((e[0] + 1, e[1], e[-1]))
    return new

written = open('outputs.txt', 'w')
count = 1
farmer, cow = move()
for i in range(160000):  # only 160k poss configurations for the farmer and the cows, so only that many times
    if farmer[:-1] == cow[:-1]:
        break
    farmer, cow = move()
    count += 1
else:
    written.write('0\n')  # oof
    exit()

written.write(str(count) + '\n')
