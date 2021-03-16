# 2014 dec bronze
from typing import List

EMPTY = '.'
MIN_LEN = 3


def clue(grid: List[List[bool]], cell: List[int]) -> bool:
    if c == 0 or not grid[r][c - 1]:
        for i in range(MIN_LEN):
            if c + i >= len(grid[r]) or not grid[r][c + i]:
                break
        else:
            return True
    
    if r == 0 or not grid[r - 1][c]:
        for i in range(MIN_LEN):
            if r + i >= len(grid) or not grid[r + i][c]:
                break
        else:
            return True
    return False


crossword = []
with open('crosswords.in') as read:
    row_num, col_num = [int(i) for i in read.readline().split()]
    for _ in range(row_num):
        row = read.readline().strip()
        crossword.append([c == EMPTY for c in row])

clues = []
for r in range(row_num):
    for c in range(col_num):
        if clue(crossword, [r, c]):
            clues.append([r, c])

print(clues)
with open('crosswords.out', 'w') as written:
    written.write(str(len(clues)) + '\n')
    for c in clues:
        written.write(str(c[0] + 1) + ' ' + str(c[1] + 1) + '\n')
