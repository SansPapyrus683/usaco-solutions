# 2018 usopen bronze
from string import ascii_uppercase

WIDTH = 3


def victory(board, allies):
    winningArrangments = [
        [(i, i) for i in range(WIDTH)],
        [(WIDTH - i - 1, i) for i in range(WIDTH)]
    ]
    for i in range(WIDTH):
        winningArrangments.append([[i, c] for c in range(WIDTH)])
        winningArrangments.append([[r, i] for r in range(WIDTH)])

    for a in winningArrangments:
        claimed = {board[p[0]][p[1]] for p in a}
        if all(c in allies for c in claimed) and all(c in claimed for c in allies):
            return True
    return False


with open('tttt.in') as read:
    grid = [read.readline().strip().upper() for _ in range(WIDTH)]
    assert all(len(r) == WIDTH for r in grid), 'it should only by a %i by %i grid bro' % (WIDTH, WIDTH)

singleWon = 0
for c in ascii_uppercase:
    singleWon += victory(grid, {c})

doubleWon = 0
for i in range(len(ascii_uppercase)):
    first = ascii_uppercase[i]
    for j in range(i + 1, len(ascii_uppercase)):
        second = ascii_uppercase[j]
        doubleWon += victory(grid, {first, second})

print(singleWon, doubleWon)
print("%i\n%i" % (singleWon, doubleWon), file=open('tttt.out', 'w'))
