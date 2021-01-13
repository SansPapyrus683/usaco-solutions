from sys import exit
from bisect import bisect_left, bisect_right


# 0, 1, 2, and 3 are north, east, south, and west respectively
def resultingDir(rnDir: int, metMirror: str) -> int:
    if metMirror == '/':
        if rnDir in [0, 1]:
            return 1 if rnDir == 0 else 0
        elif rnDir in [2, 3]:
            return 3 if rnDir == 2 else 2
    elif metMirror == '\\':
        if rnDir in [0, 3]:
            return 3 if rnDir == 0 else 0
        elif rnDir in [1, 2]:
            return 1 if rnDir == 2 else 2
    else:
        raise ValueError("invalid mirror my guy: " + metMirror)


sameX = {}
sameY = {}
mirrorTypes = {}
mirrors = []
with open('mirrors.in') as read:
    mirrorNum, targetX, targetY = [int(i) for i in read.readline().split()]
    for _ in range(mirrorNum):
        mirror = read.readline().split()
        mirror[0], mirror[1] = int(mirror[0]), int(mirror[1])
        if mirror[0] not in sameX:
            sameX[mirror[0]] = []
        if mirror[1] not in sameY:
            sameY[mirror[1]] = []
        sameX[mirror[0]].append(mirror[1])
        sameY[mirror[1]].append(mirror[0])
        mCoo = tuple(mirror[:-1])
        mirrorTypes[mCoo] = mirror[2]
        mirrors.append(mCoo)

if targetX not in sameX:  # i'll just mark the finishing destination as a mirror
    sameX[targetX] = []
if targetY not in sameY:
    sameY[targetY] = []
sameX[targetX].append(targetY)
sameY[targetY].append(targetX)
mirrorTypes[(targetX, targetY)] = '/'  # or '\\', both work

# sort the coordinates so binary search works
for y in sameX.values():
    y.sort()
for x in sameY.values():
    x.sort()

for m in range(mirrorNum + 1):
    if m != 0:
        mirrorTypes[mirrors[m - 1]] = '/' if mirrorTypes[mirrors[m - 1]] == '\\' else '\\'

    at = (0, 0)
    dir = 1
    visited = set()
    while at + (dir,) not in visited:
        visited.add(at + (dir,))
        if at[0] == targetX and at[1] == targetY:
            print(m)
            print(m, file=open('mirrors.out', 'w'))
            exit()

        if dir in [0, 2]:
            yList = sameX.get(at[0], [])
            ind = bisect_right(yList, at[1]) if dir == 0 else bisect_left(yList, at[1]) - 1
            if 0 <= ind < len(yList):
                nextY = yList[ind]
            else:
                break
            at = (at[0], nextY)
        else:
            xList = sameY.get(at[1], [])
            ind = bisect_right(xList, at[0]) if dir == 1 else bisect_left(xList, at[0]) - 1
            if 0 <= ind < len(xList):
                nextX = xList[ind]
            else:
                break
            at = (nextX, at[1])
        dir = resultingDir(dir, mirrorTypes[at])

    if m != 0:  # revert the inversion
        mirrorTypes[mirrors[m - 1]] = '/' if mirrorTypes[mirrors[m - 1]] == '\\' else '\\'
else:
    print(-1)
    print(-1, file=open('mirrors.out', 'w'))
