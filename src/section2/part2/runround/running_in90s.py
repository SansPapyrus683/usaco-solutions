"""
ID: kevinsh4
TASK: runround
LANG: PYTHON3
"""
from sys import exit

with open('newLife.txt') as read:
    start_from = int(read.read().rstrip()) + 1  # we want strictly greater than so +1


def check_run(n):
    n = [int(x) for x in list(str(n))]
    index_pos = 0
    visited = set()
    for i in range(len(n)):
        index_pos = (n[index_pos] + index_pos) % len(n)
        if n[index_pos] in visited:
            return False
        visited.add(n[index_pos])
    return True


while True:
    if len(set(list(str(start_from)))) != len(list(str(start_from))):  # if has repeating digits, don't even bother
        start_from += 1
        continue
    if check_run(start_from):
        with open('outputs.txt', 'w') as written:
            written.write(str(start_from))
            exit()
    start_from += 1
