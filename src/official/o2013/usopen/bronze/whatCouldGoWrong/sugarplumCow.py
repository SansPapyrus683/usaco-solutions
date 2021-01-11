from sys import exit


# this is an absolutely horrid function, but it works, and that's all the matters in usaco
def move(point, dir: str, orientation: int):
    dir = dir.upper()
    if orientation == 0:  # north
        if dir == 'F':
            point[1] += 1
        elif dir == 'B':
            point[1] -= 1
        elif dir == 'L':
            point[0] -= 1
        elif dir == 'R':
            point[0] += 1

    elif orientation == 1:  # east
        if dir == 'F':
            point[0] += 1
        elif dir == 'B':
            point[0] -= 1
        elif dir == 'L':
            point[1] += 1
        elif dir == 'R':
            point[1] -= 1

    elif orientation == 2:  # south
        if dir == 'F':
            point[1] -= 1
        elif dir == 'B':
            point[1] += 1
        elif dir == 'L':
            point[0] += 1
        elif dir == 'R':
            point[0] -= 1

    elif orientation == 3:  # west
        if dir == 'F':
            point[0] -= 1
        elif dir == 'B':
            point[0] += 1
        elif dir == 'L':
            point[1] -= 1
        elif dir == 'R':
            point[1] += 1


with open('ballet.in') as read:
    instructions = [read.readline().strip().upper() for _ in range(int(read.readline()))]

# front right, front left, back right, back left
feetNames = {'FR': 0, 'FL': 1, 'RR': 2, 'RL': 3}
feet = [[1, 1], [0, 1], [1, 0], [0, 0]]

maxX = 1
minX = 0
maxY = 1
minY = 0
facing = 0  # north, east, south, west
for i in instructions:
    toMove = feet[feetNames[i[:-1]]]
    if i[-1] == 'P':
        # in this case toMove should really be named toPlant but i'm too lazy lol
        rotated = []
        for f in feet:
            if f == toMove:
                rotated.append(f)
                continue
            f[0] -= toMove[0]
            f[1] -= toMove[1]
            f = [f[1], -f[0]]
            f[0] += toMove[0]
            f[1] += toMove[1]
            rotated.append(f)
        feet = rotated
        facing = (facing + 1) % 4
    else:
        move(toMove, i[-1], facing)

    if len(set(tuple(f) for f in feet)) < len(feet):
        print(-1)
        print(-1, file=open('ballet.out', 'w'))
        exit()

    maxX = max(maxX, max(f[0] for f in feet))
    minX = min(minX, min(f[0] for f in feet))
    maxY = max(maxY, max(f[1] for f in feet))
    minY = min(minY, min(f[1] for f in feet))

# +1 because of something? idk it's just off by one, take my word for it
minArea = (maxX - minX + 1) * (maxY - minY + 1)
print(minArea)
print(minArea, file=open('ballet.out', 'w'))
