# 2015 usopen bronze
from string import ascii_uppercase as letters

REDACTED = '█'


def moo_num(board, m, o):
    front = f'{m}{o}{o}'
    back = front[::-1]

    moos = 0
    for c in zip(*board):
        c = ''.join(c)
        moos += c.count(front) + c.count(back)
    for r in board:
        moos += r.count(front) + r.count(back)

    for k in range(len(board) + len(board[0]) - 1):
        left_side = ''
        right_side = ''
        for j in range(k + 1):
            i = k - j
            if i < len(board) and j < len(board[0]):
                left_side += board[i][j]
                right_side += board[i][-(j + 1)]
        moos += left_side.count(front) + left_side.count(back) + right_side.count(front) + right_side.count(back)
    return moos


with open('moocrypt.in') as read:
    row_num, col_num = [int(i) for i in read.readline().split()]
    puzzle = [read.readline().strip().upper() for _ in range(row_num)]
    assert all(len(r) == col_num for r in puzzle), 'it should be a nice rectangle right?'

max_moos = 0
for i in range(len(letters)):
    for j in range(len(letters)):
        if letters[i] == 'M' or letters[j] == 'O' or letters[i] == letters[j]:
            continue
        max_moos = max(max_moos, moo_num(puzzle, letters[i], letters[j]))

print(max_moos)
print(max_moos, file=open('moocrypt.out', 'w'))
