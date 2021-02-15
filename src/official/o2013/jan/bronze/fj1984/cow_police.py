from sys import exit
from bisect import bisect_left, bisect_right


# 0, 1, 2, and 3 are north, east, south, and west respectively
def resulting_dir(rn_dir: int, met_mirror: str) -> int:
    if met_mirror == '/':
        if rn_dir in [0, 1]:
            return 1 if rn_dir == 0 else 0
        elif rn_dir in [2, 3]:
            return 3 if rn_dir == 2 else 2
    elif met_mirror == '\\':
        if rn_dir in [0, 3]:
            return 3 if rn_dir == 0 else 0
        elif rn_dir in [1, 2]:
            return 1 if rn_dir == 2 else 2
    else:
        raise ValueError("invalid mirror my guy: " + met_mirror)


same_x = {}
same_y = {}
mirror_types = {}
mirrors = []
with open('mirrors.in') as read:
    mirror_num, target_x, target_y = [int(i) for i in read.readline().split()]
    for _ in range(mirror_num):
        mirror = read.readline().split()
        mirror[0], mirror[1] = int(mirror[0]), int(mirror[1])
        if mirror[0] not in same_x:
            same_x[mirror[0]] = []
        if mirror[1] not in same_y:
            same_y[mirror[1]] = []
        same_x[mirror[0]].append(mirror[1])
        same_y[mirror[1]].append(mirror[0])
        m_coo = tuple(mirror[:-1])
        mirror_types[m_coo] = mirror[2]
        mirrors.append(m_coo)

if target_x not in same_x:  # i'll just mark the finishing destination as a mirror
    same_x[target_x] = []
if target_y not in same_y:
    same_y[target_y] = []
same_x[target_x].append(target_y)
same_y[target_y].append(target_x)
mirror_types[(target_x, target_y)] = '/'  # or '\\', both work

# sort the coordinates so binary search works
for y in same_x.values():
    y.sort()
for x in same_y.values():
    x.sort()

for m in range(mirror_num + 1):
    if m != 0:
        mirror_types[mirrors[m - 1]] = '/' if mirror_types[mirrors[m - 1]] == '\\' else '\\'

    at = (0, 0)
    dir = 1
    visited = set()
    while at + (dir,) not in visited:
        visited.add(at + (dir,))
        if at[0] == target_x and at[1] == target_y:
            print(m)
            print(m, file=open('mirrors.out', 'w'))
            exit()

        if dir in [0, 2]:
            y_list = same_x.get(at[0], [])
            ind = bisect_right(y_list, at[1]) if dir == 0 else bisect_left(y_list, at[1]) - 1
            if 0 <= ind < len(y_list):
                nextY = y_list[ind]
            else:
                break
            at = (at[0], nextY)
        else:
            xList = same_y.get(at[1], [])
            ind = bisect_right(xList, at[0]) if dir == 1 else bisect_left(xList, at[0]) - 1
            if 0 <= ind < len(xList):
                nextX = xList[ind]
            else:
                break
            at = (nextX, at[1])
        dir = resulting_dir(dir, mirror_types[at])

    if m != 0:  # revert the inversion
        mirror_types[mirrors[m - 1]] = '/' if mirror_types[mirrors[m - 1]] == '\\' else '\\'
else:
    print(-1)
    print(-1, file=open('mirrors.out', 'w'))
